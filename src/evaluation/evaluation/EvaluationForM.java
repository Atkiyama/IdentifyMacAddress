package evaluation.evaluation;

import java.io.IOException;
import java.util.ArrayList;

public class EvaluationForM {
	/**
	 *
	 * @param args0にM(データ数、1に回帰手法を入れる)
	 * @throws IOException
	 */
	 public static void main(String[] args) throws IOException{
	        //出力パス data/result/evalation/move/
	        int M = Integer.parseInt(args[0]);
			int R = Integer.parseInt(args[2]);
			int T = Integer.parseInt(args[3]);
			int I = Integer.parseInt(args[4]);
			if(args.length>5) {
				Evaluation eval = new Evaluation("data/result/multi/move/"+args[1]+"/"+0+"/"+R+","+ T +","+I+".txt");
				eval.evaluation();
	            double sumScore =eval.getScore();
	            System.out.println(M+"," +sumScore);

			}else {
				ArrayList<Evaluation> evals = new ArrayList<>();
	        //評価用のインスタンスを作成

				for(int n=1;n<=100;n++){
					evals.add(new Evaluation("data/result/multi/move/"+args[1]+"/"+n+"/"+R+","+ T +","+I+".txt"));
				}
				double sumScore = 0;
				for(Evaluation eval:evals){
					eval.evaluation();
					sumScore +=eval.getScore();
				}
				System.out.println(M+"," +sumScore/evals.size());

			}
			

	 }

}
