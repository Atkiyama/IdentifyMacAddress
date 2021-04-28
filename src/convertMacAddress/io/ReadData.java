package convertMacAddress.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import convertMacAddress.node.BTMachine;
import convertMacAddress.node.Packet;
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
	public ReadData(ArrayList<BTMachine> btMachines) {
		this.btMachines = btMachines;
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
		BufferedReader buffReader;
		final Pattern ptn = Pattern
				.compile("(systime=)(.*?)( freq.*? addr=)(.*?)( delta_t=)([[1-9]|¥.]*?)( ms rssi=)(-?[0-9]+)");
		//入力される文字列をパターンにする(今回は手動で書き換える)
		final Pattern filter = Pattern.compile("6f fd");
		//アドレスの形
		final Pattern ad = Pattern.compile("(.. .. )(..)( )(..)( )(..)( )(..)( )(..)( )(..)");
		for (BTMachine btMachine : btMachines) {
			file = new File(btMachine.getFileName());
			fileReader = new FileReader(file);
			buffReader = new BufferedReader(fileReader);
			//必要な値を格納する変数
			String s;
			//systimeから始まる行と次の1行を取るためのフラグ
			boolean flag;
			//time,addr,rssi 時間はdelta_tから取らないと小数点がない
			String rssi = "";
			String address = "";
			//Double time = 0.0; //ミリ秒
			int time = 0;
			int ftime = 0;
			boolean first = true;
			Matcher m;
			//1行ずつ読み込む
			while ((s = buffReader.readLine()) != null) {
				flag = false;
				//systimeで始まる行
				m = ptn.matcher(s);
				if (m.find()) {
					if (first) {
						first = false;
						ftime = Integer.parseInt(m.group(2));
					}
					//delta_tを足していく
					time = Integer.parseInt(m.group(2)) - ftime;
					//time += Double.parseDouble(m.group(4));
					//データ取得
					rssi = m.group(8);
					//2行目は１６進数の行
					s = buffReader.readLine();
					flag = filter.matcher(s).find();
					if (flag) {
						m = ad.matcher(s);
						m.find();
						address = m.group(12) + ":" + m.group(10) + ":" + m.group(8) + ":" + m.group(6) + ":"
								+ m.group(4)
								+ ":" + m.group(2);
						if (!btMachine.getAddress().contains(address)) {
							break;
						} else {
							btMachine.addPacket(new Packet(address, Math.round(time), Integer.parseInt(rssi)));
						}
					}
				}
			}
			buffReader.close();

		}

	}

	public ArrayList<BTMachine> getBtMachines() {
		return btMachines;
	}

}
