package evaluation.evaluation;

import java.io.IOException;
import java.util.ArrayList;

import evaluation.CaptureFile;
import evaluation.read.ReadAnswer;
import evaluation.read.ReadData;

/**
 * 同定結果を評価するクラス
 *
 * @author akiyama
 *
 */
public class Evaluation {
    ArrayList<ArrayList<String>> answers;
    /**
     * 同定結果のスコア
     */
    ArrayList<Boolean> score;
    /**
     * キャプチャファイルのリスト
     */
    ArrayList<CaptureFile> captures;

    /**
     * データのリスト
     */
    ArrayList<String> datas;
    public Evaluation(int n) throws IOException{

    	captures = ReadAnswer.read();
    	ReadData read = new ReadData(n);
    	datas = read.read();
        score = new ArrayList<>();
    }



    public Evaluation(int R,int T,int I,int numOfTime,int n ,String method,String numOfData) throws IOException{
        captures = ReadAnswer.read();
    	ReadData read = new ReadData(R,T,I,numOfTime,n,method,numOfData);
    	datas = read.read();
        score = new ArrayList<>();
    }

    public Evaluation(String fileName) throws IOException{
        captures = ReadAnswer.read();
    	ReadData read = new ReadData(fileName);
    	datas = read.read();
        score = new ArrayList<>();

    }

    public Evaluation(String answer,String data) throws IOException{
        captures = ReadAnswer.readLineUp(answer);
    	ReadData read = new ReadData(data);
    	datas = read.read();
        score = new ArrayList<>();

    }

    public void output() {
    	for(String data:datas) {
    		System.out.println(data);
    	}

    	System.out.println("\n");
    	for(CaptureFile capture:captures) {
    		for(String str :capture.getAddress()) {
    			System.out.println(str);
    		}
    	}
    	
    	System.out.println("\n");
    	for(boolean tr:score) {
    		System.out.println(tr);
    	}
    }

    public void evaluation() {
    	for(int i=0;i<datas.size();i++) {
            for(CaptureFile capture :captures){
                for(int j=0;j<capture.getAddress().size();j++){
                    //評価すべきかを判断する
                    if(capture.getAddress().get(j).equals(datas.get(i))
                    		//ここの条件要検討?
                    		&&i+1<=datas.size()
                    		&&!datas.get(i+1).equals("brank"))
                        score.add(judge(capture,i,j));
                }

            }

    	}
    }

    public boolean judge(CaptureFile capture,int i,int j){
        //次の正解アドレスが存在するか判定
        if(j+1>=capture.getAddress().size()) {
        	//System.out.println(capture.getAddress().size());
            return false;
        }
        //次アドレスを比較
        else 
        if(!datas.get(i+1).equals(capture.getAddress().get(j+1))) {
        	//System.out.println(datas.get(i)+","+datas.get(i+1));
            return false;
        }
        else
            return true;

    }

    public double getScore(){
        double correct = 0;
        for(Boolean b:score)
            if(b)
                correct++;

        if(correct ==0 )
        	return 0;
        return correct/score.size()*100;

    }

    public static void main(String[] args) throws IOException{
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
