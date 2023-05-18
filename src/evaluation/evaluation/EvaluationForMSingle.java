package evaluation.evaluation;

import java.io.IOException;

public class EvaluationForMSingle {
	 public static void main(String[] args) throws IOException{
	        //出力パス data/result/evalation/move/
	        int M = Integer.parseInt(args[0]);
			int R = Integer.parseInt(args[2]);
			int T = Integer.parseInt(args[3]);
			int I = Integer.parseInt(args[4]);
	        int n=0;
	        //評価用のインスタンスを作成
	        Evaluation eval = new Evaluation("data/result/multi/move/"+args[1]+"/"+n+"/"+R+","+ T +","+I+".txt");
	        eval.evaluation();
	        //eval.output();
	        System.out.println(M+","+eval.getScore());

	    }

}
