package evaluation.table;

import java.io.FileWriter;
import java.io.IOException;
/**
 * データを表形式にして出力するためのクラス
 * @author akiyama
 *
 */
public class Write {
	/**
	 * データを表形式にして出力するためのメソッド
	 * @param data 出力するデータ
	 * @throws IOException
	 */
	public static void write(String[][] data) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		FileWriter fw = new FileWriter("data/result/evaluationTable.csv");
		//列数の出力

		for (int i = 1; i < data.length; i++) {
			fw.append(",");
			fw.append(Integer.toString(i));
		}
		fw.append("\r\n");

		for (int i = 1; i < data[0].length; i++) {
			//行数の出力
			fw.append("T="+Integer.toString(i));
			for (int j = 1; j < data.length; j++) {
				fw.append(",");
				fw.append(data[j][i] + "%");
			}
			fw.append("\r\n");
		}

		for(int i=0;i<4;i++)
			fw.append("\r\n");


		for (int i = 1; i < data.length; i++) {
			fw.append(",");
			fw.append("R="+Integer.toString(i));
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
