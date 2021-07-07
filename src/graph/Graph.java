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
	protected ArrayList<String[]> data;
	protected int parameta;
	protected ArrayList<Double> output;

	public Graph(ArrayList<String[]> data) {
		super();
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
	public void print() {
		int i=1;
		for(double outputData:output) {
			System.out.println(i+","+outputData);
			i++;
		}
	}
	protected abstract void extract() throws IOException;
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
