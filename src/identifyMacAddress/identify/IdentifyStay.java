package identifyMacAddress.identify;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import dataAnalyze.node.Address;
import dataAnalyze.node.Packet;

/**
 * 旧同定用プログラム
 * @author akiyama
 *
 */
public class IdentifyStay extends Identify {
	int fileNumber;

	public IdentifyStay(ArrayList<Packet> packets,int R,double T) {
		super(packets,R,T);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public IdentifyStay(int fileNumber,int R,double T) {
		super(R,T);
		this.fileNumber=fileNumber;
		// TODO 自動生成されたコンストラクター・スタブ
	}
	/**
	 * 平均RSSIの闘値
	 */





	/**
	 * リストの中からパケット数が闘値N(デフォルトは100)以下のものを削除するメソッド
	 * (作ったものの結局使用してない)
	 */
	public void removeFewAddress() {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<Address> removeList = new ArrayList<>();
		for (Address address : addressList) {
			if (address.getNumPkt() <= N) {
				removeList.add(address);
			}
		}
		for (Address address : removeList) {
			addressList.remove(address);
		}

	}

	/**
	 * 配列のリストからアドレスのリストを作成するメソッド
	 * @throws IOException 
	 *
	 */
	public void makeAddressList() throws IOException {
		ArrayList<String[]> addressListCSV = read("data/address/processed/addressList/addressList"+fileNumber+".csv");
		ArrayList<String[]> rssiListCSV = read("data/address/original/aveRssiList.csv");
		for(int i=0;i<addressListCSV.size();i++) {
			for(int j=0;j<rssiListCSV.size();j++) {
				if(addressListCSV.get(i)[0].equals(rssiListCSV.get(j)[0])||addressListCSV.get(i)[1].contains(rssiListCSV.get(j)[1])) {
					Address address = new Address(addressListCSV.get(i)[0],addressListCSV.get(i)[1],Double.parseDouble(addressListCSV.get(i)[2])
							,Double.parseDouble(addressListCSV.get(i)[3]),Double.parseDouble(rssiListCSV.get(j)[2]));
					addressList.add(address);
					
				}
			}
		}
		

	}

	/**
	 * パケットの時間差が闘値Tに収まっているか判定するメソッド
	 * @param adr_base 比較するアドレス1
	 * @param adr_tmp 比較するアドレス2
	 * @return 条件を満たしていればtrueを返す
	 */


	/**
	 * パケットのRSSI差が闘値Rに収まっているか判定するメソッド
	 * @param adr_base 比較するアドレス1
	 * @param adr_tmp 比較するアドレス2
	 * @return 条件を満たしていればtrueを返す
	 */
	public boolean checkR(Address adr_base, Address adr_tmp) {
		if (Math.abs(adr_base.getAveRssi() - adr_tmp.getAveRssi()) <= R)
			return true;
		else
			return false;
	}

	/**
	 * アドレスリストのゲッター
	 * @return アドレスリスト
	 */
	public ArrayList<Address> getAddressList() {
		return addressList;
	}
	
	public ArrayList<String[]> read(String fileName) throws IOException{
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
		String str =in.readLine();
		ArrayList<String[]> data=new ArrayList<>();
		str = in.readLine();
		//まずはデータを一次元配列のリストとして保存
		while(str != null) {
			String[] st = str.split(",");
			//System.out.println(st[2]);
			data.add(st);
			str = in.readLine();
		}
		return data;

	}
	
	public void identify() throws IOException, InterruptedException {
		//ここ以下でアドレスを特定する
		for (Address adr_base : addressList) {
			for (Address adr_tmp : addressList) {
				//同一機器のものとみなしたらnextAdrにaddする
				if (!adr_base.getAdvA().equals(adr_tmp.getAdvA()) && checkT(adr_base, adr_tmp)
						&& checkR(adr_base, adr_tmp))
					adr_base.addNextAddr(adr_tmp);
			}
		}

		/**
		 * 結果を出力
		 */
		
		for (Address address : addressList) {
			if (address.getNextAdr().size() > 1)
				identify(address);
			address.printData2();
		}
	}
	
	protected void identify(Address address) {
		//ここの計算にある程度の丸め込みが必要？
		// TODO 自動生成されたメソッド・スタブ
		address.setDeltaLT(T);
		address.setDeltaR(R);
		BigDecimal length = BigDecimal.valueOf(999999999);
		Address nextAdr = new Address();
		for (Address tmpNextAdr : address.getNextAdr()) {
			tmpNextAdr.setDeltaFT(T);
			tmpNextAdr.setDeltaR(R);
			tmpNextAdr.setTmpLength((address.getDeltaLT().subtract(tmpNextAdr.getDeltaFT())).pow(2)
					.add((address.getDeltaR().subtract(tmpNextAdr.getDeltaR())).pow(2))
					.setScale(3, RoundingMode.HALF_UP));
			//			System.out.println("address: deltaT="+address.getDeltaLT()+",deltaR="+address.getDeltaR());
			//			System.out.println("nextAdr: deltaT="+tmpNextAdr.getDeltaFT()+",deltaR="+tmpNextAdr.getDeltaR()+"tmpLength="+tmpNextAdr.getTmpLength());
			if (tmpNextAdr.getTmpLength().compareTo(length) < 0) {
				nextAdr = tmpNextAdr;
				length = tmpNextAdr.getTmpLength();
			}
		}
		ArrayList<Address> na = new ArrayList<>();
		na.add(nextAdr);
		address.setNextAdr(na);

	}


	/**
	 * メインメソッド
	 * @param args 0に読み込むファイルのNo,１にR,2にTを入れる(別途スクリプトで操作)
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		
	
		Identify identify = new IdentifyStay(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		identify.makeAddressList();
		//identify.removeFewAddress();
		((IdentifyStay) identify).identify();
		//identify.checkData();
	}
}
