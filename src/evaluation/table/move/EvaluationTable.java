package evaluation.table.move;

import java.io.IOException;
/**
 * EvaluationAll.shの結果を表にまとめるためのクラス
 * @author akiyama
 *
 */
public class EvaluationTable {
/**
 * メインメソッド
 * @param args 0に読み込みファイル名 1に書き込み先
 * @throws IOException
 */
	public static void main(String[] args) throws IOException {
		Read read;
		Write write;
		if(args.length == 2) {
			read = new Read(args[0]);
			write = new Write(args[1]);
		}else {
			read = new Read();
			write = new Write();
		}

		// TODO 自動生成されたメソッド・スタブ
			write.write(Format.format(read.read()));
	}

}
