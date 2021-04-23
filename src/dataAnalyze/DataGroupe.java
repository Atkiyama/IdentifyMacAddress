package dataAnalyze;

import java.util.ArrayList;

public class DataGroupe {
	private ArrayList<String> secondLines;
	private ArrayList<String> data;

	public DataGroupe(Data data) {
		secondLines = new ArrayList<>();
		secondLines.add(data.getSecondLine());
		this.data =data.getData();
	}
	public void addData(Data base) {
		// TODO 自動生成されたメソッド・スタブ
		secondLines.add(base.getSecondLine());
	}
	public ArrayList<String> getSecondLines() {
		return secondLines;
	}
	public ArrayList<String> getData() {
		return data;
	}
}
