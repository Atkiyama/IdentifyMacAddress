package processed.lineUp;

import java.io.IOException;
import java.util.ArrayList;

import processed.ReadCSV;
import processed.extract.node.Address;

public class LineUp {
	ArrayList<String[]> originalAddressList;
	ArrayList<Pair> pairs;
	ArrayList<Double> substract;

	public LineUp(ArrayList<String[]> originalAddressList) {
		this.originalAddressList = originalAddressList;
		pairs = new ArrayList<>();
		substract = new ArrayList<>();
	}

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		LineUp lineUp = new LineUp(ReadCSV.read("data/address/original/addressList.csv"));
		lineUp.setPairs();
		lineUp.formatPairs();
		//ここまでアドレスリストの処理

	}

	private void formatPairs() {
		// TODO 自動生成されたメソッド・スタブ
		for(Pair pair:pairs) {
			double ltime = pair.getFrontAddress().getlTime();
			pair.getFrontAddress().setfTime(pair.getFrontAddress().getfTime()-ltime);
			pair.getFrontAddress().setlTime(0);
			pair.getBackAddress().setfTime(pair.getBackAddress().getfTime()-ltime);
			pair.getBackAddress().setlTime(pair.getBackAddress().getlTime()-ltime);
			pair.getBackAddress().setName(pair.getBackAddress()+"_2");
			substract.add(ltime);
		}
	}

	public void setPairs() {
		// TODO 自動生成されたメソッド・スタブ
		String[] beforeAddress = {""};
		for(String[] address:originalAddressList) {
			if(address[0].equals(beforeAddress[0]))
				pairs.add(new Pair(new Address(beforeAddress),new Address(address)));
			beforeAddress=address;
		}
	}

}
