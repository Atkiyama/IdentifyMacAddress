package evaluation.evaluation;

import java.io.IOException;
import java.util.ArrayList;

import evaluation.CaptureFile;
import evaluation.read.ReadAnswer;
import evaluation.read.ReadData;

public class Evaluation {
    ArrayList<ArrayList<String>> answers;
    ArrayList<Boolean> score;
    int n;
    ArrayList<CaptureFile> captures;
    ArrayList<String> datas;
    public Evaluation(int R,int T,int I,int n) throws IOException{

    	captures = ReadAnswer.read();
    	ReadData read = new ReadData(R,T,I,n);
    	datas = read.read();
        score = new ArrayList<>();
    }

    public Evaluation(String fileName) throws IOException{
        captures = ReadAnswer.read();
    	ReadData read = new ReadData(fileName);
    	datas = read.read();
        score = new ArrayList<>();

    }

    public void output() {
    	for(String data:datas) {
    		System.out.println(data);
    	}

    	for(CaptureFile capture:captures) {
    		for(String str :capture.getAddress()) {
    			System.out.println(str);
    		}
    	}
    }

    public void evaluation() {
    	for(int i=0;i<datas.size();i++) {
            for(CaptureFile capture :captures){
                for(int j=0;j<capture.getAddress().size();j++){
                    //評価すべきかを判断する
                    if(capture.getAddress().get(j).equals(datas.get(i))
                    		&&i+1<=datas.size()
                    		&&!datas.get(i+1).equals("brank"))
                        score.add(judge(capture,i,j));
                }

            }

    	}
    }

    public boolean judge(CaptureFile capture,int i,int j){
        //次の正解アドレスが存在するか判定
        if(j+1>=capture.getAddress().size())
            return false;
        //次アドレスを比較
        else if(!datas.get(i+1).equals(capture.getAddress().get(j+1)))
            return false;
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

        for(int n=1;n<=10000;n++){
            evals.add(new Evaluation(R,T,I,n));
        }
        double sumScore = 0;
        for(Evaluation eval:evals){
            eval.evaluation();
            sumScore +=eval.getScore();
        }
        System.out.println("R="+R+",T="+T+",I="+I+",score is " +sumScore/evals.size()+"%");

    }
}
