package dataAnalyze;

import java.util.ArrayList;
import java.util.Objects;

public class DataGroupe {
	private ArrayList<String> secondLines;
	private ArrayList<String> data;
	public void addData(Data base) {
		// TODO 自動生成されたメソッド・スタブ
		secondLines.add(base.getSecondLine());
		if(Objects.isNull(data))
			data = base.getData();

	}
	public ArrayList<String> getSecondLines() {
		return secondLines;
	}
	public ArrayList<String> getData() {
		return data;
	}
}
