package graph;

import java.io.IOException;
import java.util.ArrayList;

import graph.type.DataSet;
import graph.type.Rssi;
import graph.type.Time;
/**
 * グラフ制作のためのtxtファイルを作成するためのクラス
 *
 * @author akiyama
 *
 */
public abstract class Graph {
	private ArrayList<String[]> data;
	protected int parameta;

	public Graph(ArrayList<String[]> data) {
		super();
		this.data = data;
	}
	/**
	 *
	 * @param args 1 読み込むファイル名 2処理方法 3その他パラメタ
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Read.read(args[0]);
		Graph graph = getInstance(Read.read(args[0]),args[1],args[2]);
		graph.extract();
		graph.print();
	}
	protected abstract void print();
	protected abstract void extract();
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
		System.exit(0);
		return null;
	}

}
