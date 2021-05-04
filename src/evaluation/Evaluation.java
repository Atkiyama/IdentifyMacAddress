package evaluation;

import java.io.IOException;
import java.util.ArrayList;

public class Evaluation {
	private ArrayList<String> data;
	private ArrayList<String[]> answer;

	public Evaluation(ArrayList<String> data, ArrayList<String[]> answer) {
		this.data = data;
		this.answer = answer;
	}

	public void evaluation() {
		for(String[] ans:answer) {
			for(String line:data) {
				if(line.contains(ans[0])) {

				}
			}
		}

	}

	public static void main(String[] args) throws IOException {
		ReadData readData = new ReadData(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		ReadAnswer readAnswer = new ReadAnswer();
		Evaluation eval = new Evaluation(readData.read(),readAnswer.read());
		eval.evaluation();
	}

}
