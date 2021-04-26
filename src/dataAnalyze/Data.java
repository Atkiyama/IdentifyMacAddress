package dataAnalyze;

import java.util.ArrayList;
/**
 * 出力されたデータを表すクラス
 * @author akiyama
 *
 */
public class Data {
	/**
	 * ２行目(闘値データ)記録する変数
	 */
	private String secondLine;
	/**
	 * ２行目以降の本データを１行ごとにリストにしたもの
	 */
	private ArrayList<String> data;
	/**
	 * 引数で初期化しdataもnewする
	 * @param secondLine ２行目(闘値データ)記録する変数
	 */
	public Data(String secondLine) {
		this.secondLine = secondLine;
		data = new ArrayList<>();
	}
	/**
	 * データに１行分のデータを記録するメソッド
	 * @param line １行分のデータ
	 */
	public void addLine(String line) {
		data.add(line);
	}
	/**
	 * 闘値データのゲッター
	 * @return 闘値データ
	 */
	public String getSecondLine() {
		return secondLine;
	}
	/**
	 * データのゲッター
	 * @return data
	 */
	public ArrayList<String> getData() {
		return data;
	}
}
