package graph.type;

import java.util.ArrayList;

import graph.Graph;

public class Time extends Graph{

	public Time(ArrayList<String[]> data, String parameta) {
		super(data);
		super.parameta=Integer.parseInt(parameta);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	protected void extract() {
		// TODO 自動生成されたメソッド・スタブ
		for(String[] line:this.data) {
			for(int i=0 ;i<line.length;i++) {
				if(i==parameta)
					output.add(Double.parseDouble(line[i]));
			}
		}
		output.remove(0);

	}

}
