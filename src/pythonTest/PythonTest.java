package pythonTest;

import java.io.IOException;

public class PythonTest {

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ProcessBuilder processBuilder = new ProcessBuilder("/Users/akiyama/anaconda3/bin/python3","regression.py","data/capture/single/move/csv/move0.csv","58:b4:ad:58:e0:0c","52:ae:43:5a:b9:6f","3");
		Process process = processBuilder.start();
		System.out.print(process.getInputStream());
	}

}
