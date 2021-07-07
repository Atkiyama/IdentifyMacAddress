package graph.type;

import java.io.IOException;
import java.util.ArrayList;

import graph.Graph;
import graph.Read;

public class DataSet extends Graph{
	boolean isMax;
	ArrayList<String> tables;
	public DataSet(ArrayList<String[]> data, String parameta) {
		super(data);
		tables = new ArrayList<>();
		tables.add("data/result/table/evaluationTable5.csv");
		tables.add("data/result/table/evaluationTable10.csv");
		tables.add("data/result/table/evaluationTable15.csv");
		tables.add("data/result/table/evaluationTable20.csv");
		// TODO 自動生成されたコンストラクター・スタブ
		if(parameta.equals("max"))
			isMax = true;
		else
			isMax = false;
	}



	@Override
	protected void extract() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		for(String table:tables)
			extract(table);
	}

	protected void removePercent() {

	}

	public void print() {
		int i=0;
		for(double outputData:output) {
			System.out.println(tables.get(i).replace("data/result/table/evaluationTable","").replace(".csv","")+","+outputData);
			i++;
		}

	}

	public void extract(String table) throws IOException {
		data = Read.read(table);
		super.removePercent();
		ArrayList<Double> calc = new ArrayList<>();
		for(int i=0 ;i<data.size();i++) {
			for(int j=0;j<data.get(i).length;j++) {
				if(i!=0&&j!=0)
					calc.add(Double.parseDouble(data.get(i)[j]));
			}
		}
		if(isMax)
			calcMax(calc);
		else{
			calcAve(calc);
		}
	}



	private void calcMax(ArrayList<Double> calc) {
		// TODO 自動生成されたメソッド・スタブ
		double max = 0;
		for(Double number:calc) {
			if(max<number)
				max=number;
		}
		output.add(max);

	}



	private void calcAve(ArrayList<Double> calc) {
		// TODO 自動生成されたメソッド・スタブ
		double add = 0;
		for(Double number:calc) {
			add+=number;
		}
		output.add(add/calc.size());
	}



}
