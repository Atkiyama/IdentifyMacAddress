package evaluation;

import java.io.IOException;
import java.util.ArrayList;

import evaluation.read.ReadAnswer;
import evaluation.read.ReadData;
/**
 * identifyMacAddressで出した結果を評価するクラス
 * @author akiyama
 *
 */
public class Evaluation {
	/**
	 * 読み込んだデータ
	 */
	private ArrayList<String[]> data;
	/**
	 * 正解データ
	 */
	private ArrayList<String[]> answer;
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

	/**
	 *  各引数で初期化,scoreのみnewする
	 * @param data 読み込んだデータ
	 * @param answer 正解データ
	 * @param R RSSIの闘値
	 * @param T 受診時刻の闘値
	 */
	public Evaluation(ArrayList<String[]> data, ArrayList<String[]> answer,int R,int T) {
		this.data = data;
		this.answer = answer;
		score = new ArrayList<>();
		this.R = R;
		this.T = T;
	}

	/**
	 * 精度を評価するメソッド
	 */
	public void evaluation() {
		for(String[] ans:answer) {
			for(int i=0;i<data.size();i++) {
				if(data.get(i)[0].equals(ans[1])) {
					//answerの一つ目とdataが一致したら評価を開始する
					score.add(check(i,ans));
				}
			}
		}
		//scoreとanswerのサイズが一致しない場合は一致させる
		if(score.size()<answer.size()) {
			while(score.size() <answer.size())
				score.add(false);
		}


	}

	/**
	 * 正誤判定をするメソッド
	 * @param i 評価するデータの開始行
	 * @param ans 正解データ
	 * @return 一致する場合はtrueを返す
	 */
	public boolean check(int i, String[] ans) {
		// TODO 自動生成されたメソッド・スタブ
		for(int j = 1;j<ans.length;j++) {
			if(!data.get(i)[0].equals(ans[j])) {
				return false;
			}
			i++;
		}
		return true;


	}

	/**
	 * 精度を計算するメソッド
	 * @return 精度
	 */
	public double getAccuracy() {
		double trueCount =0;
		for(Boolean tr:score) {
			if(tr)
				trueCount++;
		}
		return trueCount/score.size()*100;

	}

	/**
	 * 結果を表示するメソッド
	 */
	public void showScore() {
		System.out.println("R="+R+"T="+T+",score is "+getAccuracy()+"%");
		for(int i=0;i<answer.size();i++) {
			System.out.print(answer.get(i)[0]+":");
			if(score.get(i))
				System.out.println("○");
			else {
				System.out.println("×");
			}
		}
	}

	/**
	 * メインメソッド 引数によって結果出力方法が変わる
	 * @param args 0には闘値R,1には闘値Tを入れること.２に何かを入れると精度のみ出力する(スクリプト用)
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ReadData readData = new ReadData(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		ReadAnswer readAnswer = new ReadAnswer();
		Evaluation eval = new Evaluation(readData.read(),readAnswer.read(),Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		eval.evaluation();
		if(args.length == 3)
			System.out.println("R="+eval.getR()+"T="+eval.getT()+",score is "+eval.getAccuracy()+"%");
		else {
			eval.showScore();
		}
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
