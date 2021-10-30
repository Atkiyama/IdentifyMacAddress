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
    	captures = ReadAnswer.read();
    	ReadData read = new ReadData(R,T,I,numOfData,n,method);
    	datas = read.read();
    }

    public void evaluation() {

    }
    public static void main(String[] args){

    }
}
