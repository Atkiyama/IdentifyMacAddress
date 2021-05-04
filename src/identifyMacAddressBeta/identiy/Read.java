package identifyMacAddressBeta.identiy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ShapeData.javaで作ったcsvファイルを読みこむクラス
 * @author akiyama
 * @version 1.0
 */
public class Read {
	/** 読み込むファイル名 */
	private String inputFileName;
	/**日付(csvファイル１行目)*/
	private String date;

	/**
	 * 引数のファイルを読み込む
	 * @param inputFileName 読み込むファイル名
	 */
	public Read(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	/**
	 * csvファイルを読み込んで二次元配列として返すメソッド
	 * @return csvファイルを読み込んだ結果(二次元配列)
	 * @throws IOException
	 */
	public ArrayList<String[]> read() throws IOException {
		File file = new File(inputFileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader buffReader = new BufferedReader(fileReader);
		final Pattern ptn = Pattern
				.compile("(systime=)(.*?)( freq.*? addr=)(.*?)( delta_t=)([[1-9]|¥.]*?)( ms rssi=)(-?[0-9]+)");
		//入力される文字列をパターンにする(今回は手動で書き換える)
		final Pattern filter = Pattern.compile("6f fd");
		//アドレスの形
		final Pattern ad = Pattern.compile("(.. .. )(..)( )(..)( )(..)( )(..)( )(..)( )(..)");
		//出力先
		//日付の読み込み
		date = buffReader.readLine();
		ArrayList<String[]> inputData = new ArrayList<>();

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
					address = m.group(12) + ":" + m.group(10) + ":" + m.group(8) + ":" + m.group(6) + ":" + m.group(4)
							+ ":" + m.group(2);
					String[] line = { address, String.valueOf(Math.round(time)), rssi };
					for (String data : line) {
						data.trim();
						data.replaceAll("　", "");
					}
					inputData.add(line);
				}
			}
		}
		buffReader.close();

		return inputData;

	}

	/**
	 * 日付のゲッター
	 * @return 日付(csvファイル１行目)
	 */
	public String getDate() {
		return date;
	}

}
