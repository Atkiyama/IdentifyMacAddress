package evaluation.evaluation;

import java.io.IOException;
import java.util.ArrayList;

import evaluation.CaptureFile;
import evaluation.read.ReadAnswer;
import evaluation.read.ReadData;

public class Evaluation {
    ArrayList<ArrayList<String>> answers;
    ArrayList<Boolean> score;
    int R;
    int T;
    int I;
    int numOfData;
    int n;
    String method;
    ArrayList<CaptureFile> captures;
    ArrayList<String> datas;
    public Evaluation(int R,int T,int I,int numOfData,int n,String method) throws IOException{
        this.R=R;
        this.T=T;
        this.I=I;
        this.n=n;
        this.numOfData= numOfData;
        this.method=method;
    	captures = ReadAnswer.read();
    	ReadData read = new ReadData(R,T,I,numOfData,n,method);
    	datas = read.read();
        score = new ArrayList<>();
    }

    public void evaluation() {
    	for(int i=0;i<datas.size();i++) {
            for(CaptureFile capture :captures){
                for(int j=0;j<capture.getAddress().size();j++){
                    //評価すべきかを判断する
                    if(capture.getAddress().get(i).equals(datas.get(i))&&i+1<datas.size()&&!datas.get(i+i).equals("brank"))
                        score.add(judge(capture,i,j));
                }
            
            }
    		
    	}
    }

    public boolean judge(CaptureFile capture,int i,int j){
        //次の正解アドレスが存在するか判定
        if(j>=capture.getAddress().size())
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

        return correct/score.size();

    }

    public static void main(String[] args) throws IOException{
        //出力パス data/result/evalation/move/
        int R = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        int I = Integer.parseInt(args[2]);
        int numOfData = Integer.parseInt(args[3]);
        String method = args[4];

        ArrayList<Evaluation> evals = new ArrayList<>();;
        //評価用のインスタンスを作成
        
        for(int n=1;n<=10000;n++){
            evals.add(new Evaluation(R,T,I,numOfData,n,method));
        }
        for(Evaluation eval:evals){
            eval.evaluation();
            System.out.println("R="+R+",T="+T+",I="+I+",score is" +eval.getScore()+"%");
        }

    }
}
