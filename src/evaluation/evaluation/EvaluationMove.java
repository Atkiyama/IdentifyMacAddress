package evaluation.evaluation;

import java.io.IOException;
import java.util.ArrayList;

import evaluation.read.ReadAnswer;
import evaluation.read.ReadData;

public class EvaluationMove {
	/**
	 *
	 * @param args 0には闘値R,1には闘値T,2には閾値Pを入れること
	 * 3に使用するデータ数を入れる
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		ReadAnswer readAnswer = new ReadAnswer();
		double sum =0;
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<Evaluation2> evals = new ArrayList<>();
		for (int i = 1; i <= 100; i++) {
			ReadData readData = new ReadData(Integer.parseInt(args[0]), Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]), i,args[4]);

			Evaluation2 eval = new Evaluation2(readData.read(), readAnswer.read(), Integer.parseInt(args[0]),
					Integer.parseInt(args[1]),Integer.parseInt(args[3]));
			eval.evaluation();
			evals.add(eval);
			sum += eval.getAccuracy();
		}
		sum = sum/100;
		if(Double.isNaN(sum)) {
			long sumlong = 0;
			for(Evaluation2 eval:evals) {
				sumlong = (long) eval.getAccuracy();
			}
			sum = sumlong/100;
		}
		System.out.println("R="+args[0]+"T="+args[1]+"P="+args[2]+",score is "+sum+"%");
	}





}
