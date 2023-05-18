package evaluation.evaluation;

import java.io.IOException;
import java.util.ArrayList;

import evaluation.read.ReadAnswer;
import evaluation.read.ReadData;
/**
 * multi以下のファイルに対して一斉評価を行うためのクラス
 * @author akiyama
 *
 */
public class Evaluation100Old {
	/**
	 *
	 * @param args 0には闘値R,1には闘値Tを入れること,2に使用するデータ数を入れる,3にmoveかstayかを入れる
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		ReadAnswer readAnswer = new ReadAnswer();
		double sum =0;
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<EvaluationOld> evals = new ArrayList<>();
		int M =Integer.parseInt(args[2]);
		for (int i = 1; i <= 100; i++) {
			ReadData readData = new ReadData(Integer.parseInt(args[0]), Integer.parseInt(args[1]),M, i);

			EvaluationOld eval = new EvaluationOld(readData.readOld(), readAnswer.readOld(), Integer.parseInt(args[0]),
					Integer.parseInt(args[1]));
			eval.evaluation();
			evals.add(eval);
			sum += eval.getAccuracy();
		}
		sum = sum/100;
		if(Double.isNaN(sum)) {
			long sumlong = 0;
			for(EvaluationOld eval:evals) {
				sumlong = (long) eval.getAccuracy();
			}
			sum = sumlong/100;
		}
		 System.out.println(args[2]+"," + sum);
	}


	/**
	 *詳細を表示するメソッド
	 * @param evals 評価のリスト
	 * @throws IOException
	 */
	
	/*
	private static void outputDetails(ArrayList<EvaluationOld> evals) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		HashMap<String,Boolean> score = calcScore(evals);
		for(Entry<String,Boolean> sc:score.entrySet()) {
			System.out.print(sc.getKey()+":");
			if(sc.getValue())
				System.out.println("○");
			else {
				System.out.println("×");
			}
		}
	}*/

	/**
	 * スコアを計算するメソッド
	 * @param evals 評価のリスト
	 * @return スコアのリスト
	 */
	
	/*
	private static HashMap<String,Boolean> calcScore(ArrayList<EvaluationOld> evals) {
		HashMap<String,Boolean> score = new HashMap<>();
		// TODO 自動生成されたメソッド・スタブ
		for(EvaluationOld eval:evals) {
			for(Entry<String,Boolean> sc:eval.getScore().entrySet()) {
				if(!sc.getValue()) {
					score.put(sc.getKey(),sc.getValue());
				}
			}
		}

		for(Entry<String,Boolean> sc:evals.get(0).getScore().entrySet()) {
			if(!score.containsKey(sc.getKey())) {
				score.put(sc.getKey(),sc.getValue());
			}
		}
		return score;
	}*/

}
