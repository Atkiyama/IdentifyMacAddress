package graph.type;

import java.util.ArrayList;

import graph.Graph;

public class Rssi extends Graph{

	public Rssi(ArrayList<String[]> data, String parameta) {
		super(data);
		super.parameta=Integer.parseInt(parameta);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	protected void extract() {
		// TODO 自動生成されたメソッド・スタブ
		String[] line = data.get(parameta);
		for(String outputData:line)
			output.add(Double.parseDouble(outputData));

		output.remove(0);


	}

}
