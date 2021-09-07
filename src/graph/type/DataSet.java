package graph.type;

import java.io.IOException;
import java.util.ArrayList;

import graph.Graph;
import graph.Read;

/**
 * データセット毎のデータを作成するためのメソッド
 * @author akiyama
 *
 */
public class DataSet extends Graph{
	/**
	 * 最大値を求めるかどうか(falseの場合は平均値を求める)
	 */
	boolean isMax;
	/**
	 * 読み込むファイル名
	 */
	ArrayList<String> tables;

	/**
	 * 引数で初期化する
	 * tablesはあらかじめ決めたものをリストに追加する
	 * @param data 読み込むデータ(ただしこのクラスの場合は意味がない)
	 * @param parameta maxなら最大値、そうでないなら平均値を求める
	 */
	public DataSet(ArrayList<String[]> data, String parameta) {
		super(data);
		tables = new ArrayList<>();
		tables.add("data/result/table/evaluationTable5,2.csv");
		tables.add("data/result/table/evaluationTable10,2.csv");
		tables.add("data/result/table/evaluationTable15,2.csv");
		tables.add("data/result/table/evaluationTable20,2.csv");
		// TODO 自動生成されたコンストラクター・スタブ
		if(parameta.equals("max"))
			isMax = true;
		else
			isMax = false;
	}



	/**
	 * データを生成するためのメソッド
	 */
	@Override
	protected void extract() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		for(String table:tables)
			extract(table);
	}

	/**
	 * %を取り除くためのメソッド(メインメソッドで何もしないためのメソッド)
	 */
	protected void removePercent() {

	}

	/**
	 * データを出力するメソッド
	 *
	 */
	@Override
	public void print() {
		int i=0;
		for(double outputData:output) {
			System.out.println(tables.get(i).replace("data/result/table/evaluationTable","").replace(".csv","")+","+outputData);
			i++;
		}

	}

	/**
	 * 引数の表のデータを生成するためのメソッド
	 * @param table データを生成したい表
	 * @throws IOException
	 */
	public void extract(String table) throws IOException {
		data = Read.read(table);
		super.removePercent();
		ArrayList<Double> calc = new ArrayList<>();
		for(int i=0 ;i<data.size();i++) {
			for(int j=0;j<data.get(i).length;j++) {
				if(i!=0&&j!=0)
					calc.add(Double.parseDouble(data.get(i)[j]));
			}
		}
		if(isMax)
			calcMax(calc);
		else{
			calcAve(calc);
		}
	}



	/**
	 * 引数の最大値を求めるメソッド
	 * @param calc 表データ
	 */
	private void calcMax(ArrayList<Double> calc) {
		// TODO 自動生成されたメソッド・スタブ
		double max = 0;
		for(Double number:calc) {
			if(max<number)
				max=number;
		}
		output.add(max);

	}



	/**
	 * 引数の平均値を求めるメソッド
	 * @param calc 表データ
	 */
	private void calcAve(ArrayList<Double> calc) {
		// TODO 自動生成されたメソッド・スタブ
		double add = 0;
		for(Double number:calc) {
			add+=number;
		}
		output.add(add/calc.size());
	}



}
