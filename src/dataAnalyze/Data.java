package dataAnalyze;

import java.util.ArrayList;

public class Data {
	private String secondLine;
	private ArrayList<String> data;
	public Data(String secondLine) {
		this.secondLine = secondLine;
		data = new ArrayList<>();
	}
	public void addLine(String line) {
		data.add(line);
	}
	public String getSecondLine() {
		return secondLine;
	}
	public ArrayList<String> getData() {
		return data;
	}
}
