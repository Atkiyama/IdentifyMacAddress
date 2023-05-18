package dataAnalyze.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dataAnalyze.node.Packet;

/**
 * テキストファイルからパケット情報を読み取るクラス
 * メインメソッドではtxtをcsvに変換する(convertCSV.shを使用)
 * 
 * @author akiyama
 *
 */
public class ReadTXT extends Read {
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

	/**
	 * fileNameは引数で初期化、その他は各の正規表現で初期化する
	 * 
	 * @param fileName ファイル名
	 */
	public ReadTXT(String fileName) {
		super(fileName);
		pTime = Pattern.compile("([0-9]{2}):([0-9]{2}):(.{9})");
		pAddress = Pattern.compile("(..:..:..:..:..:..)");
		pRssi = Pattern.compile("(-[0-9]{1,2})");
	}

	/**
	 * ファイルから情報を読み取るメソッド
	 * 
	 * @return パケットのリスト
	 */
	public ArrayList<Packet> read() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
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
			} else if (i != 1) {
				// 例外処理
				System.out.println(i + "行目にパターンに一致しない文字列を確認しました。ご確認ください");
				System.exit(0);
			}
			str = in.readLine();
		}
		in.close();
		// 初回受診時刻を取得
		double fTime = getFTime();
		// データをフォーマット
		for (Packet packet : packets)
			packet.formatTime(fTime);

		return packets;

	}

	/**
	 * パケットのインスタンスを作るメソッド
	 * 
	 * @param mTime    時間のmatcher
	 * @param mAddress macアドレスのmatcher
	 * @param mRssi    rssiのmatcher
	 * @return パケットのインスタンス
	 */

	public Packet makePackets(Matcher mTime, Matcher mAddress, Matcher mRssi) {
		// TODO 自動生成されたメソッド・スタブ
		double hour = Double.parseDouble(mTime.group(1));
		double minute = Double.parseDouble(mTime.group(2));
		double second = Double.parseDouble(mTime.group(3));
		return new Packet(mAddress.group(1), hour * 3600 + minute * 60 + second, Integer.parseInt(mRssi.group(1)));
	}

	/**
	 * テスト用メインメソッド。読み込んでパケット情報を出力する
	 * 
	 * @param args 読み込むデータ名
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Read readtxt = new ReadTXT(args[0]);
		ArrayList<Packet> packets = readtxt.read();
		System.out.println("address,time,rssi");
		for (Packet packet : packets)
			packet.printData();
	}

	public ArrayList<Double> readRegression() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
		String str = in.readLine();
		ArrayList<Double> data = new ArrayList<>();
		while (str != null) {
			data.add(Double.parseDouble(str));
			str = in.readLine();
		}
		in.close();
		return data;

	}

	public double getFTime() throws IOException {
		File file = new File(fileName.replace("txt/", "original/"));
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
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
			} else if (i != 1) {
				// 例外処理
				System.out.println(i + "行目にパターンに一致しない文字列を確認しました。ご確認ください");
				System.exit(0);
			}
			str = in.readLine();
		}
		in.close();

		double fTime = packets.get(0).getTime();
		return fTime;

	}

}
