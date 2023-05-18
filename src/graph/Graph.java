package graph;

import java.io.IOException;
import java.util.ArrayList;

import graph.type.DataSet;
import graph.type.Rssi;
import graph.type.Time;
/**
 * gnuplotを用いてのグラフ制作のためのtxtファイルを作成するためのクラス
 *
 * @author akiyama
 *
 */
public abstract class Graph {
	/**
	 * 読み込んだデータ
	 */
	protected ArrayList<String[]> data;
	/**
	 * 各種パラメータ(子クラスによって変わる)
	 */
	protected int parameta;
	/**
	 * 出力するデータ
	 */
	protected ArrayList<Double> output;

	/**
	 * 引数で初期化しリストをnewする
	 * @param data 読み込んだデータ
	 */
	public Graph(ArrayList<String[]> data) {
		this.data = data;
		output = new ArrayList<>();
	}
	/**
	 *
	 * @param args 1 読み込むファイル名 2処理方法 3その他パラメタ
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Read.read(args[0]);
		Graph graph = getInstance(Read.read(args[0]),args[1],args[2]);
		graph.removePercent();
		graph.extract();
		graph.print();
	}

	/**
	 * データを出力するメソッド
	 */
	public void print() {
		int i=1;
		for(double outputData:output) {
			System.out.println(i+","+outputData);
			i++;
		}
	}

	/**
	 * データを抽出するメソッド
	 * @throws IOException
	 */
	protected abstract void extract() throws IOException;

	/**
	 * 引数に応じたインスタンスを生成するメソッド
	 * @param data 読み込んだデータ
	 * @param instance 生成したいインスタンスの文字列
	 * @param parameta　各種パラメタ
	 * @return 引数に応じたインスタンス
	 */
	private static Graph getInstance(ArrayList<String[]> data, String instance,String parameta) {
		// TODO 自動生成されたメソッド・スタブ
		switch (instance) {
			case "Rssi":
				return new Rssi(data,parameta);
			case "Time":
				return new Time(data,parameta);
			case "DataSet":
				return new DataSet(data,parameta);
		}
		System.out.print("エラー");
		return null;
	}

	/**
	 * 読み込んだデータから%の記号を取り除くメソッド
	 */
	protected void removePercent() {
		ArrayList<String[]> swaps = new ArrayList<>();
		String swap[];
		for(String[] line:data) {
			swap = new String[line.length];
			int i=0;
			for(String inputData:line) {
					swap[i] = inputData.replace("%","");
					i++;
			}

			swaps.add(swap);
		}
		data = swaps;
	}

}
