package graph.makeCDF.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import graph.makeCDF.node.BTMachine;
import graph.makeCDF.node.Packet;


/**
 * 正解macアドレスを元に各キャプチャファイルからパケットを読み出すクラス
 * (パスを考慮してコーディングしなおすこと)
 * @author akiyama
 *
 */
public class ReadData {
	/**
	 * 引数を代入する
	 * @param btMachines 機器(キャプチャファイル)のリスト
	 */
	/**
	 * 時間の正規表現
	 */
	final Pattern pTime;
	/**
	 * macアドレスの正規表現
	 */
	final Pattern pAddress;
	/**
	 * rssiの正規表現
	 */
	final Pattern pRssi;
	public ReadData(ArrayList<BTMachine> btMachines) {
		this.btMachines = btMachines;
		pTime = Pattern.compile("([0-9]{2}):([0-9]{2}):(.{9})");
		pAddress = Pattern.compile("(..:..:..:..:..:..)");
		pRssi = Pattern.compile("(-[0-9]{1,2})");
	}
	/**
	 * btMachines 機器(キャプチャファイル)のリスト
	 */
	private ArrayList<BTMachine> btMachines;

	/**
	 * フィールドの情報を頼りに正解データを各ファイルから読み出すメソッド
	 * @throws IOException
	 */
	public void readData() throws IOException {
		File file;
		FileReader fileReader;
		BufferedReader in;

		for (BTMachine btMachine : btMachines) {
			file = new File("data/capture/single/move/txt/"+btMachine.getFileName()+".txt");
			fileReader = new FileReader(file);
			in = new BufferedReader(fileReader);
			//必要な値を格納する変数
			String str = in.readLine();
			ArrayList<Packet> packets = new ArrayList<>();
			Matcher mTime;
			Matcher mAddress;
			Matcher mRssi;
			int i = 0;
			while (str != null) {
				i++;
				mTime = pTime.matcher(str);
				mAddress = pAddress.matcher(str);
				mRssi = pRssi.matcher(str);
				if (mTime.find() && mAddress.find() && mRssi.find()) {
					packets.add(makePackets(mTime, mAddress, mRssi));
				} else if(i != 1){
					//例外処理
					System.out.println(i + "行目にパターンに一致しない文字列を確認しました。ご確認ください");
					System.exit(0);
				}
				str = in.readLine();
			}
			in.close();
			//初回受診時刻を取得
			double fTime = packets.get(0).getTime();
			//データをフォーマット
			for (Packet packet : packets) {
				packet.formatTime(fTime);
				if(btMachine.getAddresses().contains(packet.getAddress())) {
					btMachine.addPacket(packet);
				}
			}


		}

	}

	/**
	 * パケットのインスタンスを生成するメソッド
	 * @param mTime timeのmatcher
	 * @param mAddress addressのmatcher
	 * @param mRssi rssiのmatcher
	 * @return パケットのインスタンス
	 */
	public Packet makePackets(Matcher mTime, Matcher mAddress, Matcher mRssi) {
		// TODO 自動生成されたメソッド・スタブ
		double hour = Double.parseDouble(mTime.group(1));
		double minute = Double.parseDouble(mTime.group(2));
		double second = Double.parseDouble(mTime.group(3));
		return new Packet(mAddress.group(1), hour * 3600 + minute * 60 + second, Integer.parseInt(mRssi.group(1)));
	}


	public ArrayList<BTMachine> getBtMachines() {
		return btMachines;
	}

}
