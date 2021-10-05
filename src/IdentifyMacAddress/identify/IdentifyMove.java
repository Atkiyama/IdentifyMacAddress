package identifyMacAddress.identify;

import java.io.IOException;
import java.util.ArrayList;

import identifyMacAddress.io.Read;
import identifyMacAddress.io.ReadCSV;
import identifyMacAddress.io.ReadTXT;
import identifyMacAddress.io.Write;
import identifyMacAddress.node.Address;
import identifyMacAddress.node.Packet;

/**
 * 移動するBLE機器のMACアドレスを同定するクラス
 * @author akiyama
 *
 */
public class IdentifyMove extends Identify{
	/**
	 * 閾値P
	 * 回帰する際に使用するデータの範囲(秒)
	 * predictから
	 */
	private int P;

	/**
	 * pythonを使って回帰を行う際のコマンド
	 */
	ArrayList<String> command;

	/**
	 * 引数で初期化,コマンドを作る
	 * @param read パケットのリスト
	 * @param fileName 読み込むファイル名
	 * @param R RSSIの閾値
	 * @param T	時間の閾値
	 * @param P 回帰範囲(秒)の閾値
	 */
	public IdentifyMove(ArrayList<Packet> read,String fileNumber,int R,double T,int P) {
		// TODO 自動生成されたコンストラクター・スタブ
		super(read,R,T);
		this.P = P;
		command = new ArrayList<>();
		command.add("python");
		command.add("regression.py");
		command.add(String.valueOf(P));
	}

	/**
	 * メインメソッド
	 * @param args 0に読み込むキャプチャファイル名(絶対or相対パス),１にTを入れる
	 * @throws IOException
	 * @throws InterruptedException
	 */

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

		System.out.println("R=" + R + "T=" + (int)T+ "P=" + P);
		for (Address address : addressList) {
			if (address.getNextAdr().size() > 1)
				identify(address);
			address.printData();
		}
	}


	/**
	 * 回帰を用いてRSSIを精査する
	 * マルチスレッド用に修正中
	 */
	protected boolean checkR(Address adr_base, Address adr_tmp) throws IOException, InterruptedException {
		// TODO 自動生成されたメソッド・スタブ
		//コマンドライン引数を更新
		Write.write(adr_base,adr_tmp,P);
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		Process process = processBuilder.start();
		//この行を絶対に消さないこと
		//プロセスが終了するまでプログラムを一時停止させるメソッド
		process.waitFor();
		ReadTXT read = new ReadTXT("data/regression/regression.txt");
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

	/**
	 * メインメソッド
	 * @param args 0に読み込むファイル名,1,2,3に閾値R,T,Pを入れる,4にファイルナンバーを入れる
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws NumberFormatException, IOException, InterruptedException {
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
		Identify identify = new IdentifyMove(read.read(),args[4],Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
		identify.makeAddressList();
		//identify.removeFewAddress();
		identify.identify();
		//identify.checkData();
	}



}
