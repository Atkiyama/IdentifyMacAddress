package processed.delay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class StaticDelayForM extends DelayForM {
	public static ArrayList<Double> makeDelayList(int numOfData) {
		ArrayList<Double> delayList = new ArrayList<>();
		for(int i=0;i<numOfData;i++) {
			delayList.add((double)i*30);
		}
		return delayList;
		
	}

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		int numOfData = Integer.parseInt(args[0]);
		ArrayList<String[]> addressList;

		addressList = read("data/address/original/addressList.csv");
		addressList.remove(0);

		ArrayList<Double> delayList = makeDelayList(numOfData);
		HashMap<String, Double> delayMap = makeDelayMap(addressList, delayList);
		int i=0;

		makeConvert(addressList, delayMap, i);
		addressList = setDelay(addressList, delayMap);
		rewriteAddressList(addressList, "data/address/processed/addressList/addressList" + i + ".csv");
		for (String[] address : addressList) {
			rewriteAddress("data/address/original/fAddress/" + address[1] + ".csv", delayMap.get(address[1]),
					"data/address/processed/fAddress/" + address[1] + "_" + i + ".csv");
			rewriteAddress("data/address/original/lAddress/" + address[1] + ".csv", delayMap.get(address[1]),
					"data/address/processed/lAddress/" + address[1] + "_" + i + ".csv");

		}

	}

}
