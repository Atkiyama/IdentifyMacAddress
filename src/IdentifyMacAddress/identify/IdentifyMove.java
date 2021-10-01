package identifyMacAddress.identify;

import java.io.IOException;
import java.util.ArrayList;

import identifyMacAddress.node.Address;
import identifyMacAddress.node.Packet;
import identifyMacAddress.read.Read;
import identifyMacAddress.read.ReadCSV;
import identifyMacAddress.read.ReadTXT;

public class IdentifyMove extends Identify{
	/**
	 * 閾値P
	 * 回帰する際に使用するデータの範囲(秒)
	 * predictから
	 */
	private int P;

	private String fileName;

	public IdentifyMove(ArrayList<Packet> read,String fileName,int R,double T,int P) {
		// TODO 自動生成されたコンストラクター・スタブ
		super(read,R,T);
		this.fileName = fileName;
		this.P = P;
	}

	/**
	 * メインメソッド
	 * @param args 0に読み込むキャプチャファイル名(絶対or相対パス),１にTを入れる
	 * @throws IOException
	 */

	public void identify() throws IOException {
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

		System.out.println("R=" + R + "T=" + T);
		for (Address address : addressList) {
			if (address.getNextAdr().size() > 1)
				identify(address);
			address.printData();
		}*/
	}


	protected boolean checkR(Address adr_base, Address adr_tmp) throws IOException {
		// TODO 自動生成されたメソッド・スタブ

		String command = "regression.py " + fileName + " " + adr_base.getAdvA() + " " + adr_tmp.getAdvA() + " " + P;
		ProcessBuilder processBuilder = new ProcessBuilder("python", command);
		processBuilder.start();
		ReadTXT read = new ReadTXT("data/result/regression.txt");
		//回帰の平均値
		double regression = read.readRegression();
		ArrayList<Integer> rssis = new ArrayList<>();
		double first = 0;
		int sum = 0;
		for(Packet packet:adr_tmp.getPackets()) {
			//adr_tmpの中からP秒以内のパケットを抽出
			//その後平均を出してregressionがR以内に収まってるかをチェック
			if(first == 0) {
				first = packet.getTime();
				rssis.add(packet.getRssi());
				sum += packet.getRssi();
			}
			else if(packet.getTime()-first <= P) {
				rssis.add(packet.getRssi());
				sum += packet.getRssi();
			}else {
				break;
			}
		}


		if(Math.abs(sum/rssis.size()-regression) <= R)
			return true;
		else
			return false;
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		Read read;
		if(args[0].contains("txt"))
			read = new ReadTXT(args[0]);
		else if(args[0].contains("csv"))
			read = new ReadCSV(args[0]);
		else {
			System.out.println("このプログラムはcsvファイルかtxtファイルのみ読み込めます。ファイルの拡張子を確認してください");
			read = null;
			System.exit(0);
		}
		Identify identify = new IdentifyMove(read.read(),args[0],Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
		identify.makeAddressList();
		//identify.removeFewAddress();
		identify.identify();
		//identify.checkData();
	}



}
