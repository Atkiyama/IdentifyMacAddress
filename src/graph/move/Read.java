package graph.move;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * ファイルを読み込むクラス
 * @author akiyama
 *
 */
public class Read {

	/**
	 * ファイルを読み込むクラス
	 * @return　配列のリストに納めたデータ
	 * @throws IOException
	 */


	/**
	 * 読み込むファイル名
	 */
	private String inputFileName;

	/**
	 * 引数で初期化する
	 * @param inputFileName 読み込むファイル名
	 */
	public Read(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	/**
	 * デフォルトの値で初期化する
	 */
	public Read() {
		this.inputFileName = "data/result/evaluation.txt";
	}

	/**
	 * ファイルを読み込むメソッド
	 * @return　配列のリストに納めたデータ
	 * @throws IOException
	 */

	public  ArrayList<Data> read() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		//評価のパターン
		final Pattern pEval = Pattern.compile("(R=)([0-9]+)(T=)([0-9]+)(P=)([0-9]+)(,score is )(.+)(%)");
		Matcher mEval;
		File file = new File(inputFileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
		String str = in.readLine();
		//配列のリストにデータを納める
		ArrayList<Data> dataList = new ArrayList<>();
		while (str != null) {
			mEval = pEval.matcher(str);
			if (mEval.find()) {
				dataList.add(new Data(mEval.group(2), mEval.group(4), mEval.group(6), mEval.group(8) ));
			}
			str = in.readLine();
		}

		in.close();
		return dataList;
	}

}
