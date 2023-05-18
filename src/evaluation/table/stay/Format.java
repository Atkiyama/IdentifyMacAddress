package evaluation.table.stay;

import java.util.ArrayList;
/**
 * データを二次元配列に整えるクラス
 * @author akiyama
 *
 */
public class Format {
	/**
	 * データを二次元配列に整えるメソッド
	 * @param dataList　配列のリストに納められたデータ
	 * @return 二次元配列に納められたデータ
	 */
	public static String[][] format(ArrayList<String[]> dataList) {
		// TODO 自動生成されたメソッド・スタブ
		//配列の最大サイズを計算するための変数
		int maxR = 0;
		int maxT = 0;

		for (String[] row : dataList) {
			if (maxR < Integer.parseInt(row[0]))
				maxR = Integer.parseInt(row[0]);

			if (maxT < Integer.parseInt(row[1]))
				maxT = Integer.parseInt(row[1]);
		}


		String[][] data = new String[maxR + 1][];
		//行数
		int rowNum = 0;
		data[0] = new String[maxT + 1];
		for (String[] row : dataList) {
			if (rowNum != Integer.parseInt(row[0])) {
				data[Integer.parseInt(row[0])] = new String[maxT + 1];
				rowNum = Integer.parseInt(row[0]);
			}
			data[Integer.parseInt(row[0])][Integer.parseInt(row[1])] = row[2];
		}
		return data;
	}

}
