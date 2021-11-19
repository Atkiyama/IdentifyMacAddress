package evaluation.evaluation;

import java.io.IOException;
import java.util.ArrayList;
public class EvaluationMini{
     public static void main(String[] args) throws IOException{
        //出力パス data/result/evalation/move/
        int R = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        int I = Integer.parseInt(args[2]);
        int numOfTime = Integer.parseInt(args[3]);
        ArrayList<Evaluation> evals = new ArrayList<>();
        //評価用のインスタンスを作成

        for(int n=1;n<=numOfTime;n++){
            evals.add(new Evaluation(args[4]));
        }
        double sumScore = 0;
        for(Evaluation eval:evals){
            eval.evaluation();
            sumScore +=eval.getScore();
        }
        System.out.println(R+","+T+","+I+"," +sumScore/evals.size()+"%");

    }
}