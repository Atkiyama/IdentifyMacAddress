package dataAnalyze;

import java.util.ArrayList;

/**
 * 出力が同じデータをひとまとめにしたグループのクラス
 * @author akiyama
 *
 */
public class DataGroupe {
	/**
	 * グループにある２行目(闘値データ)のリスト
	 */
	private ArrayList<String> secondLines;
	/**
	 * 出力データ
	 */
	private ArrayList<String> data;
	/**
	 * dataを引数で初期化後、secondLinesをnewしてdataのsecondlineをaddする
	 * @param data データ
	 */
	public DataGroupe(Data data) {
		secondLines = new ArrayList<>();
		secondLines.add(data.getSecondLine());
		this.data =data.getData();
	}
	/**
	 * secondLinesに闘値データをaddするメソッド
	 * @param base addするデータ
	 */
	public void addData(Data base) {
		// TODO 自動生成されたメソッド・スタブ
		secondLines.add(base.getSecondLine());
	}
	/**
	 * secondLinesのゲッター
	 * @return secondLines
	 */
	public ArrayList<String> getSecondLines() {
		return secondLines;
	}
	/**
	 * データのゲッター
	 * @return data
	 */
	public ArrayList<String> getData() {
		return data;
	}
}
