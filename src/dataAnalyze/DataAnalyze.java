package dataAnalyze;

import java.io.IOException;
import java.util.ArrayList;

public class DataAnalyze {
	private ArrayList<Data> datas;
	private ArrayList<DataGroupe> dataGroupes;
	public DataAnalyze(ArrayList<Data> datas) {
		this.datas = datas;
		dataGroupes = new ArrayList<>();
	}
	public void analyze() {
		boolean exist = false;
		for(Data base:datas) {
			for(Data tmp:datas) {
				if(checkDiff(base,tmp))
					exist = true;
			}
			if(exist)
				dataGroupes.add(new DataGroupe());
		}
		for(DataGroupe base:dataGroupes)
			for(Data tmp:datas) {
				if(!base.getSecondLines().contains(tmp.getSecondLine())&&checkDiff(base.getData(),tmp))
					base.addData(tmp);
			}



	}
	public boolean checkDiff(Data base,Data tmp) {
		if(base.getData().size() == tmp.getData().size()) {
			for(int i = 0;i<base.getData().size();i++) {
				if(!base.getData().get(i).equals(tmp.getData().get(i))) {
					return false;
				}
			}
			return true;

		}else {
			return false;
		}
	}

	public boolean checkDiff(ArrayList<String> base,Data tmp) {
		if(base.size() == tmp.getData().size()) {
			for(int i = 0;i<base.size();i++) {
				if(!base.get(i).equals(tmp.getData().get(i))) {
					return false;
				}
			}
			return true;

		}else {
			return false;
		}
	}
	public void showResult() {
		for(DataGroupe dataGroupe:dataGroupes) {
			for(String secondLine:dataGroupe.getSecondLines())
				System.out.print(secondLine+"::");
			System.out.println("データ");
			for(String data:dataGroupe.getData()) {
				System.out.println(data);
			}
		}

	}
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Read read = new Read(args[0]);
		read.read();
		System.out.println("テスト１");
		DataAnalyze dataAnalyze = new DataAnalyze(read.getDatas());
		dataAnalyze.analyze();
		System.out.println("テスト２");
		dataAnalyze.showResult();
		System.out.println("テスト３");


	}

}
