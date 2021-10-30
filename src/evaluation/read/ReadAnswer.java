package evaluation.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import evaluation.CaptureFile;
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
	public static ArrayList<CaptureFile> read(String fileName) throws IOException {
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);;
		String str = in.readLine();
		ArrayList<CaptureFile> captures = new ArrayList<>();
		while(str != null) {
			//配列で取得してリストにする
			captures.add(new CaptureFile(str.split(",")));
			str = in.readLine();
		}
		in.close();
		return captures;
	}
	public static ArrayList<CaptureFile> read() throws IOException{
		return read("data/result/moveResult.csv");

	}
}
