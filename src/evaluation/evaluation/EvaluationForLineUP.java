package evaluation.evaluation;

import java.io.IOException;

public class EvaluationForLineUP {
	public static void main(String[] args) throws IOException {
	//出力パス data/result/evalation/move/
    int R = Integer.parseInt(args[0]);
    int T = Integer.parseInt(args[1]);
    int I = Integer.parseInt(args[2]);
    //評価用のインスタンスを作成

    Evaluation eval = new Evaluation("data/address/processed/addressList/addressList0.csv","data/result/multi/move/"+args[3]+"/"+0+"/"+R+","+T+","+I+".txt");
    eval.evaluation();
    System.out.println(R+","+T+","+I+"," +eval.getScore()+"%");

	}

}
