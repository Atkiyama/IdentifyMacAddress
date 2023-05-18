package processed.delay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class StaticDelayForM extends DelayForM {
	public static ArrayList<Double> makeDelayList(int numOfData) {
		ArrayList<Double> delayList = new ArrayList<>();
		for(int i=0;i<numOfData;i++) {
			double a = i*30;
			delayList.add(a);
		}
		return delayList;
		
	}
	
	protected static ArrayList<String[]> extractAddressList(int numOfData) throws IOException {
		ArrayList<String[]> addressList = read("data/address/original/addressList.csv");
		ArrayList<String> useData = new ArrayList<>();
		ArrayList<String[]> replace = new ArrayList<>();
		for (int r=1;r<=numOfData;r++) {
			if (useData.size() == numOfData) {
				break;
			}

			
			String fileName = "move" + r;
			if (!useData.contains(fileName)) {
				useData.add(fileName);
			}
		}

		for (String[] address : addressList) {
			for (String fileName : useData) {
				if (address[0].equals(fileName))
					replace.add(address);
			}
		}

		return replace;

	}

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		int numOfData = Integer.parseInt(args[0]);
		ArrayList<String[]> addressList;

		addressList = extractAddressList(numOfData);
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
