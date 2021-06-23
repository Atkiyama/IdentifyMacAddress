package evaluation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import evaluation.read.ReadAnswer;
import evaluation.read.ReadData;
/**
 * multi以下のファイルに対して一斉評価を行うためのクラス
 * @author akiyama
 *
 */
public class Evaluation100 {
	/**
	 *
	 * @param args 0には闘値R,1には闘値Tを入れること,2に引数を入れると詳細を表示する
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		ReadAnswer readAnswer = new ReadAnswer();
		double sum =0;
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<Evaluation> evals = new ArrayList<>();
		for (int i = 1; i <= 100; i++) {
			ReadData readData = new ReadData(Integer.parseInt(args[0]), Integer.parseInt(args[1]), i);

			Evaluation eval = new Evaluation(readData.read(), readAnswer.read(), Integer.parseInt(args[0]),
					Integer.parseInt(args[1]));
			eval.evaluation();
			evals.add(eval);
			sum += eval.getAccuracy();
		}
		sum = sum/100;
		if(Double.isNaN(sum)) {
			long sumlong = 0;
			for(Evaluation eval:evals) {
				sumlong = (long) eval.getAccuracy();
			}
			sum = sumlong/100;
		}
		System.out.println("R="+args[0]+"T="+args[1]+",score is "+sum+"%");
		if(args.length==3)
		outputDetails(evals);
	}


	/**
	 *詳細を表示するメソッド
	 * @param evals 評価のリスト
	 * @throws IOException
	 */
	private static void outputDetails(ArrayList<Evaluation> evals) throws IOException {
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
	}

	/**
	 * スコアを計算するメソッド
	 * @param evals 評価のリスト
	 * @return スコアのリスト
	 */
	private static HashMap<String,Boolean> calcScore(ArrayList<Evaluation> evals) {
		HashMap<String,Boolean> score = new HashMap<>();
		// TODO 自動生成されたメソッド・スタブ
		for(Evaluation eval:evals) {
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
	}

}
