package evaluation;

import java.io.IOException;
import java.util.ArrayList;

import evaluation.read.ReadAnswer;
import evaluation.read.ReadData;

public class Evaluation {
	private ArrayList<String[]> data;
	private ArrayList<String[]> answer;
	private ArrayList<Boolean> score;
	private int R;
	private int T;

	public Evaluation(ArrayList<String[]> data, ArrayList<String[]> answer,int R,int T) {
		this.data = data;
		this.answer = answer;
		score = new ArrayList<>();
		this.R = R;
		this.T = T;
	}

	public void evaluation() {
		for(String[] ans:answer) {
			for(int i=0;i<data.size();i++) {
				if(data.get(i)[0].equals(ans[1])) {
					score.add(check(i,ans));
				}
			}
		}


	}

	public boolean check(int i, String[] ans) {
		// TODO 自動生成されたメソッド・スタブ
		for(int j = 1;j<ans.length;j++) {
			System.out.println("data ="+data.get(i)[0]+",ans = "+ans[j]);
			if(!data.get(i)[0].equals(ans[j])) {
				return false;
			}
			i++;
		}
		return true;


	}

	public double trueCount() {
		double trueCount =0;
		for(Boolean tr:score) {
			if(tr)
				trueCount++;
		}
		return trueCount;

	}

	public void showScore() {
		System.out.println(score.size());
		System.out.println(answer.size());
		System.out.println(trueCount()/score.size());
		System.out.println("R="+R+"T="+T+",score is "+trueCount()/score.size()*100+"%");
		for(int i=0;i<answer.size();i++) {
			System.out.print(answer.get(i)[0]+":");
			if(score.get(i))
				System.out.println("○");
			else {
				System.out.println("×");
			}
		}
	}

	public static void main(String[] args) throws IOException {
		ReadData readData = new ReadData(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		ReadAnswer readAnswer = new ReadAnswer();
		Evaluation eval = new Evaluation(readData.read(),readAnswer.read(),Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		eval.evaluation();
		if(args.length == 3)
			System.out.println("R="+eval.getR()+"T="+eval.getT()+",score is "+Math.round(eval.trueCount()/eval.getScore().size())*100+"%");
		else {
			eval.showScore();
		}
	}

	public ArrayList<Boolean> getScore() {
		return score;
	}

	public int getR() {
		return R;
	}

	public int getT() {
		return T;
	}

}
