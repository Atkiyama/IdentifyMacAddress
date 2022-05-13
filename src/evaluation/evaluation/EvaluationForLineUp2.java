package evaluation.evaluation;

import java.io.IOException;
import java.util.ArrayList;

public class EvaluationForLineUp2 {

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		 //出力パス data/result/evalation/move/
        int R = Integer.parseInt(args[2]);
        int T = Integer.parseInt(args[3]);
        int I = Integer.parseInt(args[4]);
        ArrayList<Evaluation> evals = new ArrayList<>();;
        //評価用のインスタンスを作成
        for(int n=1;n<=100;n++){
            evals.add(new Evaluation("data/address/processed/addressList/addressList"+n+".csv","data/result/multi/move/"+args[1]+"/"+n+"/"+R+","+ T +","+I+".txt"));
        }
        double sumScore = 0;
        for(Evaluation eval:evals){
            eval.evaluation();
            sumScore +=eval.getScore();
        }
        System.out.println(args[0]+"," +sumScore/evals.size()+"%");


	}

}
