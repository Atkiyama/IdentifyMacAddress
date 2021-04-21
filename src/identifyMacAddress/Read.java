package identifyMacAddress;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * ShapeData.javaで作ったcsvファイルを読みこむクラス
 * @author akiyama
 * @version 1.0
 */
public class Read {
	private String inputFileName; //読み込むファイル名
	private String date;//日付(csvファイル１行目)
	public Read(String inputFileName) {
		this.inputFileName = inputFileName;
	}
	/**
	 * csvファイルを読み込んで二次元配列として返すメソッド
	 * @return csvファイルを読み込んだ結果(二次元配列)
	 * @throws IOException
	 */
	public ArrayList<String[]> read() throws IOException {
		File file = new File(inputFileName);
        FileReader fileReader =  new FileReader(file);
        BufferedReader in = new BufferedReader(fileReader);
        //日付の読み込み
        date = in.readLine();
        //２行目(Adva,time,rssi)を読み飛ばす
        in.readLine();
        String str = in.readLine();
        ArrayList<String[]> inputData = new ArrayList<>();
        //行末まで読み込む
        while(str != null) {
        	inputData.add(str.split(","));
        }

		return inputData;

	}
	/**
	 * 日付のゲッター
	 * @return 日付(csvファイル１行目)
	 */
	public String getDate() {
		return date;
	}

}
