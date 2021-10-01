package evaluation.evaluation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import evaluation.read.ReadAnswer;
import evaluation.read.ReadData;

/**
 * identifyMacAddressで出した結果を評価するクラス
 * mainメソッドは結合データ単体用
 * @author akiyama
 *
 */
public class Evaluation {
	/**
	 * 読み込んだデータ
	 */
	private ArrayList<String> data;
	/**
	 * 正解データ
	 */
	private HashMap<String, ArrayList<String>> answer;
	/**
	 * 各機器ごとの正誤を格納したリスト
	 */
	private ArrayList<Boolean> score;
	/**
	 * RSSIの闘値
	 */
	private int R;
	/**
	 * 受診時刻の闘値
	 */
	private int T;
	private int change;

	/**
	 *  各引数で初期化,scoreのみnewする
	 * @param data 読み込んだデータ
	 * @param answer 正解データ
	 * @param R RSSIの闘値
	 * @param T 受診時刻の闘値
	 */
	public Evaluation(ArrayList<String[]> data, ArrayList<String[]> answer, int R, int T) {
		this.answer = new HashMap<>();
		this.data = new ArrayList<>();
		for (String[] line : answer) {
			ArrayList<String> strs = new ArrayList<>();
			for (int i = 1; i < line.length; i++) {
				strs.add(line[i]);
			}
			this.answer.put(line[0], strs);
		}

		for (String[] str : data)
			this.data.add(str[0]);

		score = new ArrayList<>();
		this.R = R;
		this.T = T;
		this.change = 0;
	}

	/**
	 * 精度を評価するメソッド
	 */
	public void evaluation() {
		for (String address : data) {
			//System.out.println(address);
			for (ArrayList<String> answerAddressList : answer.values())
				if (answerAddressList.contains(address) && existNext(address))
					if(!data.get(data.indexOf(address) + 1).equals(""))
							check(address, answerAddressList);
		}
	}

	private void check(String address, ArrayList<String> answerAddressList) {
//		System.out.print(data.get(data.indexOf(address) + 1)+",");
//		System.out.println(answerAddressList.get(answerAddressList.indexOf(address) + 1));
		change++;
		if (existNext(address, answerAddressList)) {
			// TODO 自動生成されたメソッド・スタブ
			if (data.get(data.indexOf(address) + 1)
					.equals(answerAddressList.get(answerAddressList.indexOf(address) + 1)))
				score.add(true);
			else
				score.add(false);
		}else {
			score.add(false);
		}

	}

	private boolean existNext(String address, ArrayList<String> answerAddressList) {
		// TODO 自動生成されたメソッド・スタブ
		if(answerAddressList.indexOf(address)<answerAddressList.size()-1)
			return true;
		else
			return false;
	}

	private boolean existNext(String address) {
		// TODO 自動生成されたメソッド・スタブ
		if (data.indexOf(address)< data.size() - 1)
			return true;
		else
			return false;
	}



	/**
	 * 精度を計算するメソッド
	 * @return 精度
	 */
	public double getAccuracy() {
		double trueCount = 0;
		for (Boolean tr : score) {
			if (tr)
				trueCount++;
		}
		System.out.println(trueCount);
		System.out.println(getChange());

		return (trueCount / change) * 100;

	}

	private double getChange() {
		// TODO 自動生成されたメソッド・スタブ
		return change;
	}

	/**
	 * メインメソッド 引数によって結果出力方法が変わる
	 * @param args 0には闘値R,1には闘値Tを入れること.2に引数を入れると精度のみ出力する(スクリプト用)
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ReadData readData = new ReadData(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		ReadAnswer readAnswer = new ReadAnswer();
		Evaluation eval = new Evaluation(readData.read(), readAnswer.read(), Integer.parseInt(args[0]),
				Integer.parseInt(args[1]));
		eval.evaluation();
		if (args.length > 3)
			System.out.print(eval.getAccuracy());
		else
			System.out.println("R=" + eval.getR() + "T=" + eval.getT() + ",score is " + eval.getAccuracy() + "%");
	}

	public ArrayList<Boolean> getScore() {
		return score;
	}

	public int getR() {
		return R;
	}

	public int getT() {
		return T;
	}

}
