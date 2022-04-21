package processed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * CSVを読み込むクラス
 * 複数パッケージから使用するので変更には注意
 * @author akiyamashuuhei
 *
 */
public class ReadCSV {
	public static ArrayList<String[]> read(String fileName) throws IOException {
		File file;
		FileReader fileReader;
		BufferedReader in;
		file = new File(fileName);
		fileReader = new FileReader(file);
		in = new BufferedReader(fileReader);
		String str = in.readLine();
		ArrayList<String[]> data = new ArrayList<>();
		while (str != null) {
			data.add(str.split(","));
			str = in.readLine();
		}
		in.close();
		return data;
	}
}
