package graph.makeCDF.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import graph.makeCDF.node.BTMachine;


/**
 * singleReasut.csvから各ファイルの正解macアドレスを読み出すクラス
 * @author akiyama
 *
 */
public class ReadAnswer {
	/**
	 * data/result/single/singleResult.csv から正解とされるmacアドレスを抽出する
	 * @return 機器(キャプチャファイル)のリスト
	 * @throws IOException
	 */
	public ArrayList<BTMachine> read() throws IOException {
		File file = new File("data/result/moveResult.csv");
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
		ArrayList<BTMachine> btMachines = new ArrayList<>();
		String str = in.readLine();
		while(str != null) {
			//配列で取得してリストにする
			String[] line = str.split(",");
			BTMachine btMachine = new BTMachine();
			for(String data:line) {
				btMachine.addData(data);
			}
			btMachines.add(btMachine);
			str = in.readLine();
		}
		in.close();
		return btMachines;
	}
}
