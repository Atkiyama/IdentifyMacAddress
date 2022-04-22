package evaluation.evaluation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import evaluation.read.ReadAnswer;
import evaluation.read.ReadData;

/**
 * identifyMacAddressで出した結果を評価するクラス mainメソッドは結合データ単体用
 * 
 * @author akiyama
 *
 */
public class EvaluationOld {
	/**
	 * 読み込んだデータ
	 */
	private ArrayList<String[]> data;
	/**
	 * 正解データ
	 */
	private ArrayList<String[]> answer;
	/**
	 * 各機器ごとの正誤を格納したマップ
	 */
	private HashMap<String, Boolean> score;
	/**
	 * RSSIの闘値
	 */
	private int R;
	/**
	 * 受診時刻の闘値
	 */
	private int T;
	/**
	 * 回帰の閾値
	 */
	private int P;

	/**
	 * 各引数で初期化,scoreのみnewする
	 * 
	 * @param data   読み込んだデータ
	 * @param answer 正解データ
	 * @param R      RSSIの闘値
	 * @param T      受診時刻の闘値
	 */
	public EvaluationOld(ArrayList<String[]> data, ArrayList<String[]> answer, int R, int T) {
		this.data = data;
		this.answer = answer;
		score = new HashMap<>();
		this.R = R;
		this.T = T;
	}

	public EvaluationOld(ArrayList<String[]> data, ArrayList<String[]> answer, int R, int T, int P) {
		this.data = data;
		this.answer = answer;
		score = new HashMap<>();
		this.R = R;
		this.T = T;
		this.P = P;
	}

	/**
	 * 精度を評価するメソッド
	 */
	public void evaluation() {
		for (String[] ans : answer) {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i)[0].equals(ans[1])) {
					// answerの一つ目とdataが一致したら評価を開始する
					score.put(ans[0], check(i, ans));
				}
			}
		}

	}

	/**
	 * 正誤判定をするメソッド
	 * 
	 * @param i   評価するデータの開始行
	 * @param ans 正解データ
	 * @return 一致する場合はtrueを返す
	 */
	public boolean check(int i, String[] ans) {
		// TODO 自動生成されたメソッド・スタブ
		for (int j = 1; j < ans.length; j++) {
			if (!data.get(i)[0].equals(ans[j])) {
				return false;
			}
			i++;
		}
		return true;

	}

	/**
	 * 精度を計算するメソッド
	 * 
	 * @return 精度
	 */
	public double getAccuracy() {
		double trueCount = 0;
		for (Boolean tr : score.values()) {
			if (tr)
				trueCount++;
		}
		return (trueCount / score.size()) * 100;

	}

	/**
	 * 結果を表示するメソッド
	 */
	public void showScore() {
		System.out.println("R=" + R + "T=" + T + ",score is " + getAccuracy() + "%");
		for (Entry<String, Boolean> sc : score.entrySet()) {
			System.out.print(sc.getKey() + ":");
			if (sc.getValue())
				System.out.println("○");
			else {
				System.out.println("×");
			}
		}
	}

	/**
	 * メインメソッド 引数によって結果出力方法が変わる
	 * 
	 * @param args 0には闘値R,1には闘値Tを入れること.2に引数を入れると精度のみ出力する(スクリプト用)
	 * @throws IOException
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		ReadAnswer readAnswer = new ReadAnswer();
		double sum = 0;
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<EvaluationOld> evals = new ArrayList<>();
		int M = Integer.parseInt(args[2]);
		int i = 0;

		ReadData readData = new ReadData(Integer.parseInt(args[0]), Integer.parseInt(args[1]), M, i);

		EvaluationOld eval = new EvaluationOld(readData.readOld(), readAnswer.readOld(), Integer.parseInt(args[0]),
				Integer.parseInt(args[1]));
		eval.evaluation();
		evals.add(eval);
		sum += eval.getAccuracy();

		System.out.println(args[2] + "," + sum);
	}

	public HashMap<String, Boolean> getScore() {
		return score;
	}

	public int getR() {
		return R;
	}

	public int getT() {
		return T;
	}

}
