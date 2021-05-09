package evaluation.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	public ArrayList<String[]> read() throws IOException {
		File file = new File("data/result/evaluationTest.csv");
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);;
		String str = in.readLine();
		ArrayList<String[]> data = new ArrayList<>();
		while(str != null) {
			//配列で取得してリストにする
			data.add(str.split(","));
			str = in.readLine();
		}
		in.close();
		return data;
	}
}
