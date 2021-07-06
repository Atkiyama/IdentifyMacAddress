package graph.type;

import java.util.ArrayList;

import graph.Graph;

public class DataSet extends Graph{
	boolean isMax;
	public DataSet(ArrayList<String[]> data, String parameta) {
		super(data);
		// TODO 自動生成されたコンストラクター・スタブ
		if(parameta.equals("max"))
			isMax = true;
		else
			isMax = false;
	}

	@Override
	protected void print() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void extract() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
