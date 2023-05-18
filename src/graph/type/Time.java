package graph.type;

import java.util.ArrayList;

import graph.Graph;

/**
 * 時間のグラフを作るためのクラス
 * @author akiyama
 *
 */
public class Time extends Graph{

	/**
	 * 引数で初期化する
	 * @param data 読み込んだデータ
	 * @param parameta 出力したい闘値Rの値
	 */
	public Time(ArrayList<String[]> data, String parameta) {
		super(data);
		super.parameta=Integer.parseInt(parameta);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * データを生成するメソッド
	 *
	 */
	@Override
	protected void extract() {
		// TODO 自動生成されたメソッド・スタブ
		for(String[] line:this.data) {
			for(int i=0 ;i<line.length;i++) {
				if(i==parameta)
					output.add(Double.parseDouble(line[i]));
			}
		}
		output.remove(0);

	}

}
