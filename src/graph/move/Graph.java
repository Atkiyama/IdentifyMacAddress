package graph.move;

import java.io.IOException;
import java.util.ArrayList;

public class Graph {
	/**
	 * moveのグラフを作成するクラス
	 * @param args 0に入力ファイル名 1にx軸にしたいパラメータ 2に折線にしたいパラメータ 3に2の実数値
	 * 4にグラフにしたいパラメータ 5にその実数値
	 *
	 * @throws IOException
	 */

	public static void main(String args[]) throws IOException {
		if (!args[1].equals(args[2]) && !args[4].equals(args[2]) && !args[1].equals(args[4])) {
			Read read = new Read(args[0]);
			ArrayList<Data> dataList = new ArrayList<>();
			Parameta x_rabel = returnParameta(args[1]);
			Parameta line = returnParameta(args[2]);
			Parameta graph = returnParameta(args[4]);
			for (Data data : read.read()) {
				if (data.getByParameta(line) == Integer.parseInt(args[3])
						|| data.getByParameta(graph) == Integer.parseInt(args[5])) {
					dataList.add(data);
				}
			}

			for (Data data : dataList) {
				System.out.println(data.getByParameta(x_rabel) + "," + data.getAccuracy());
			}
		}
	}

	private static Parameta returnParameta(String parameta) {
		// TODO 自動生成されたメソッド・スタブ
		if (parameta == "R")
			return Parameta.R;
		else if (parameta == "T")
			return Parameta.T;
		else if (parameta == "P")
			return Parameta.P;
		else {
			System.out.println("引数エラー");
			System.exit(0);
		}
		return null;
	}

}
