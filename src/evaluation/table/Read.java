package evaluation.table;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Read {

	/**
	 * ファイルを読み込むクラス
	 * @return　配列のリストに納めたデータ
	 * @throws IOException
	 */
	public static ArrayList<String[]> read() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		//評価のパターン
		final Pattern pEval = Pattern.compile("(R=)([0-9]+)(T=)([0-9]+)(,score is )(.+)(%)");
		Matcher mEval;
		File file = new File("data/result/evaluation.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
		String str = in.readLine();
		//配列のリストにデータを納める
		ArrayList<String[]> dataList = new ArrayList<>();
		while (str != null) {
			mEval = pEval.matcher(str);
			if (mEval.find()) {
				String row[] = { mEval.group(2), mEval.group(4), mEval.group(6) };
				dataList.add(row);
			} else
				break;
			str = in.readLine();
		}
		//余計なデータを削除
		dataList.remove(dataList.size() - 1);
		in.close();
		return dataList;
	}

}
