package evaluation.evaluation;

import java.io.IOException;
import java.util.ArrayList;

public class EvaluationForLIneUp2 {

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		 //出力パス data/result/evalation/move/
        int R = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        int I = Integer.parseInt(args[2]);
        ArrayList<Evaluation> evals = new ArrayList<>();;
        //評価用のインスタンスを作成

        for(int n=1;n<=100;n++){
            evals.add(new Evaluation("data/result/multi/move/"+args[3]+"/"+n+"/"+R+","+ T +","+I+".txt"));
        }
        double sumScore = 0;
        for(Evaluation eval:evals){
            eval.evaluation();
            sumScore +=eval.getScore();
        }
        System.out.println(R+","+T+","+I+"," +sumScore/evals.size()+"%");


	}

}
