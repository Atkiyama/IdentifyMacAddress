package evaluation.table.stay;

import java.io.FileWriter;
import java.io.IOException;
/**
 * データを表形式にして出力するためのクラス
 * @author akiyama
 *
 */
public class Write {

	/**
	 *出力ファイル名
	 */
	private String outputFileName;
	/**
	 * ファイル名をデフォルトで初期化する
	 */
	public Write() {
		this.outputFileName = "data/result/table/evaluationTable.csv";
	}
	/**
	 *
	 * @param outputFileName 出力ファイル名
	 */
	public Write(String outputFileName) {
		this.outputFileName = outputFileName;
	}
	/**
	 * データを表形式にして出力するためのメソッド
	 * @param data 出力するデータ
	 * @throws IOException
	 */
	public  void write(String[][] data) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		FileWriter fw = new FileWriter(outputFileName);
		//列数の出力

		for (int i = 1; i < data.length; i++) {
			fw.append(",");
			fw.append(Integer.toString(i));
		}
		fw.append("\r\n");

		for (int i = 1; i < data[0].length; i++) {
			//行数の出力
			fw.append(Integer.toString(i));
			for (int j = 1; j < data.length; j++) {
				fw.append(",");
				fw.append(data[j][i] + "%");
			}
			fw.append("\r\n");
		}


		fw.close();

	}

}
