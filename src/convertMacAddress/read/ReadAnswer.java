package convertMacAddress.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import convertMacAddress.node.BTMachine;
/**
 * singleReasut.csvから各ファイルの正解macアドレスを読み出すクラス
 * @author akiyama
 *
 */
public class ReadAnswer {
	public ArrayList<BTMachine> read() throws IOException {
		File file = new File("singleResult.csv");
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
		ArrayList<BTMachine> btMachines = new ArrayList<>();
		String str = in.readLine();
		while(str != null) {
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
