package identifyMacAddress;

import java.io.IOException;
import java.util.ArrayList;

import identifyMacAddress.node.Address;
import identifyMacAddress.node.Packet;

/**
 * 平均RSSIとパケット受診時刻を元にmacアドレスの特定を行うための
 * @author akiyama
 *
 */
public class Identify {

	/**
	 * macアドレスのリスト
	 */
	private ArrayList<Address> addressList;
	/**
	 * パケットのリスト(これからアドレスリストを作成)
	 */
	private ArrayList<Packet> packets;
	/**
	 * N以下のパケット数のアドレスを省くための定数
	 */
	private final int N=0;
	/**
	 * 受診時刻の闘値
	 */
	private double T;
	/**
	 * 平均RSSIの闘値
	 */
	private double R;
	public Identify(ArrayList<Packet> packets) {
		this.packets = packets;
		addressList = new ArrayList<>();

	}



/**
 * 特定を行うメソッド
 * @param T 受診時刻の闘値
 * @param R 平均RSSIの闘値
 * @throws IOException
 */
	public void identify(int T,int R) throws IOException{
		//ここ以下でアドレスを特定する
		this.T = T;
		this.R = R;
		for(Address adr_base:addressList) {
			for(Address adr_tmp:addressList) {
				//同一機器のものとみなしたらnextAdrにaddする
				if(!adr_base.getAdvA().equals(adr_tmp.getAdvA())&&checkT(adr_base,adr_tmp)&&checkR(adr_base,adr_tmp))
					adr_base.addNextAddr(adr_tmp);
			}
		}

		/**
		 * 結果を出力
		 */
		System.out.println("R="+R+"T="+T);
		for(Address address:addressList) {
			if(address.getNextAdr().size()>1)
				identify(address);
			address.printData();
		}
	}

	/**
	 * nextAddrが複数ある場合にそれを一つに絞るメソッド
	 * @param address nextAdrが複数あるアドレス
	 */
	private void identify(Address address) {
	// TODO 自動生成されたメソッド・スタブ
		address.setDeltaLT(T);
		address.setDeltaR(R);
		double length = 999999;
		Address nextAdr = new Address();;
		for(Address tmpNextAdr:address.getNextAdr()){
			tmpNextAdr.setDeltaFT(T);
			tmpNextAdr.setDeltaR(R);
			tmpNextAdr.setTmpLength(Math.pow(address.getDeltaFT()-tmpNextAdr.getDeltaLT(),2)+Math.pow(address.getDeltaR()-tmpNextAdr.getDeltaR(),2));
			if(tmpNextAdr.getTmpLength()<length) {
				nextAdr = tmpNextAdr;
				length = tmpNextAdr.getTmpLength();
			}
		}
		address.getNextAdr().clear();
		address.addNextAddr(nextAdr);
	}



	/**
	 * リストの中からパケット数が闘値N(デフォルトは100)以下のものを削除するメソッド
	 * (作ったものの結局使用してない)
	 */
	public void removeFewAddress() {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<Address> removeList = new ArrayList<>();
		for(Address address:addressList) {
			if(address.getNumPkt() <= N) {
				removeList.add(address);
			}
		}
		for(Address address:removeList) {
			addressList.remove(address);
		}


	}


	/**
	 * 配列のリストからアドレスのリストを作成するメソッド
	 *
	 */
	public void makeAddressList() {
		//既知のアドレスかどうかのフラグ
		boolean ad_known = false;
		addressList = new ArrayList<>();
		for(Packet packet:packets) {
			for(Address address:addressList) {
				if(address.getAdvA().equals(packet.getAddress())) {
					address.setLtime(packet.getTime());
					address.addRssi(packet.getRssi());
					address.incrementNumPkt();
					ad_known = true;
				}

			}
			if(!ad_known) {
				addressList.add(new Address(packet.getAddress(),packet.getTime(),packet.getTime(),packet.getRssi(),1));
			}
			ad_known = false;
		}

	}

	/**
	 * パケットの時間差が闘値Tに収まっているか判定するメソッド
	 * @param adr_base 比較するアドレス1
	 * @param adr_tmp 比較するアドレス2
	 * @return 条件を満たしていればtrueを返す
	 */
	public boolean checkT(Address adr_base,Address adr_tmp) {
		if (adr_base.getLtime() <= adr_tmp.getFtime() && adr_tmp.getFtime() -adr_base.getLtime() <= T) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * パケットのRSSI差が闘値Rに収まっているか判定するメソッド
	 * @param adr_base 比較するアドレス1
	 * @param adr_tmp 比較するアドレス2
	 * @return 条件を満たしていればtrueを返す
	 */
	public boolean checkR(Address adr_base,Address adr_tmp) {
		if (Math.abs(adr_base.getAverageRssi() -adr_tmp.getAverageRssi()) <= R)
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


	/**
	 * データが正しく取れているかチェックするメソッド
	 * データの最終時刻が3999(59分55秒)以前の場合と受診間隔が10秒以上空いている場合に警告文を出力する
	 */
	public void checkData() {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<String> exeptionList = new ArrayList<>();
		if(packets.get(packets.size()-1).getTime() >= 3595) {
			exeptionList.add("最後にとったパケットの時間が"+packets.get(packets.size()-1).getTime()+"です");
		}
		double tmp = 0;
		for(Packet packet:packets) {
			if(packet.getTime() -tmp >= 10) {
				String timeDiff = String.valueOf(packet.getTime() -tmp);
				exeptionList.add("パケットの受診間隔が"+timeDiff+"秒空いています in"+packet.getTime());
			}
			tmp = packet.getTime();
		}
		if(exeptionList.size()>0) {
			System.out.println("以下の理由から正しくパケットをキャプチャできていない可能性があります");
			for(String error:exeptionList) {
				System.out.println(error);
			}
		}

	}
}
