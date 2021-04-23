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
		boolean ad_known = false;
		for(Data base:datas) {
			for(DataGroupe dataGroupe:dataGroupes) {
				if(dataGroupe.getData().equals(base.getData())) {
					dataGroupe.addData(base);
					ad_known =true;
				}
			}
			if(!ad_known) {
				dataGroupes.add(new DataGroupe(base));
			}else {
				ad_known = false;
			}
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

	public boolean containData(Data data) {
		for(DataGroupe dataGroupe:dataGroupes) {
			if(dataGroupe.getData().equals(data.getData()))
				return true;
		}
		return false;
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
		int i=1;
		for(DataGroupe dataGroupe:dataGroupes) {
			System.out.println("グループ"+i);
			System.out.println("闘値");
			for(String secondLine:dataGroupe.getSecondLines()) {
				int count =0;
				System.out.println(secondLine);
				count++;
				if(count>=5) {
					System.out.println();
					count=0;
				}
			}
			System.out.println("データ");
			for(String data:dataGroupe.getData()) {
				System.out.println(data);
			}
			i++;
		}

	}
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Read read = new Read(args[0]);
		read.read();
		DataAnalyze dataAnalyze = new DataAnalyze(read.getDatas());
		dataAnalyze.analyze();
		dataAnalyze.showResult();



	}

}
