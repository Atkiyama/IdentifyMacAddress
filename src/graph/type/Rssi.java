package graph.type;

import java.util.ArrayList;

import graph.Graph;

/**
 * rssiのデータを生成するためのクラス
 * @author akiyama
 *
 */
public class Rssi extends Graph{
	/**
	 * 引数で初期化する
	 * @param data 読み込んだデータ
	 * @param parameta 抽出したい闘値Tの値
	 */
	public Rssi(ArrayList<String[]> data, String parameta) {
		super(data);
		super.parameta=Integer.parseInt(parameta);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * データを抽出するためのメソッド
	 */
	@Override
	protected void extract() {
		// TODO 自動生成されたメソッド・スタブ
		String[] line = data.get(parameta);
		for(String outputData:line)
			output.add(Double.parseDouble(outputData));

		output.remove(0);


	}

}
