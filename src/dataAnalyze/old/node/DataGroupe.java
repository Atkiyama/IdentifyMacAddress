package dataAnalyze.old.node;

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
	private ArrayList<String> firstLines;
	/**
	 * 出力データ
	 */
	private ArrayList<String> data;
	/**
	 * dataを引数で初期化後、firstLinesをnewしてdataのfirstLineをaddする
	 * @param data データ
	 */
	public DataGroupe(Data data) {
		firstLines = new ArrayList<>();
		firstLines.add(data.getFirstLine());
		this.data =data.getData();
	}
	/**
	 * firstLinesに闘値データをaddするメソッド
	 * @param base addするデータ
	 */
	public void addData(Data base) {
		// TODO 自動生成されたメソッド・スタブ
		firstLines.add(base.getFirstLine());
	}
	/**
	 * firstLinesのゲッター
	 * @return firstLines
	 */
	public ArrayList<String> getFirstLines() {
		return firstLines;
	}
	/**
	 * データのゲッター
	 * @return data
	 */
	public ArrayList<String> getData() {
		return data;
	}
}
