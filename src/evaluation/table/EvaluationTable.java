package evaluation.table;

import java.io.IOException;
/**
 * EvaluationAll.shの結果を表にまとめるためのクラス
 * @author akiyama
 *
 */
public class EvaluationTable {
/**
 * メインメソッド
 * @param args
 * @throws IOException
 */
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Write.write(Format.format(Read.read()));
	}

}
