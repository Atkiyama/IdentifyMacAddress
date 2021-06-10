package evaluation;

import java.io.IOException;

import evaluation.read.ReadAnswer;
import evaluation.read.ReadData;

public class Evalation100 {
	/**
	 *
	 * @param args 0には闘値R,1には闘値Tを入れること
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		ReadAnswer readAnswer = new ReadAnswer();
		double sum =0;
		// TODO 自動生成されたメソッド・スタブ
		for (int i = 1; i <= 100; i++) {
			ReadData readData = new ReadData(Integer.parseInt(args[0]), Integer.parseInt(args[1]), i);

			Evaluation eval = new Evaluation(readData.read(), readAnswer.read(), Integer.parseInt(args[0]),
					Integer.parseInt(args[1]));
			eval.evaluation();
			sum += eval.getAccuracy();
		}
		System.out.println("R="+args[0]+"T="+args[1]+",score is "+sum/100+"%");
	}

}
