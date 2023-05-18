 package processed.delay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DelayForR_I extends Delay {
	/**
	 *
	 * @param args 0に使用データ数を入れる
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		int numOfData = Integer.parseInt(args[0]);
		ArrayList<String[]> addressList;
		Random random = new Random();
		for (int i = 1; i <= 100; i++) {
			if (numOfData != 20)
				addressList = extractAddressList(numOfData,random);
			else {
				addressList = read("data/address/original/addressList.csv");
				addressList.remove(0);
			}

			ArrayList<Double> delayList = makeDelayList(numOfData);
			HashMap<String, Double> delayMap = makeDelayMap(addressList, delayList);
			addressList = setDelay(addressList, delayMap);
			rewriteAddressList(addressList,"data/address/processed/addressList/addressList" + i + ".csv");
			for (String[] address : addressList) {
				rewriteAddress("data/address/original/fAddress/" + address[1] + ".csv", delayMap.get(address[1]),
						"data/address/processed/fAddress/" + address[1] + "_" + i + ".csv");
				rewriteAddress("data/address/original/lAddress/" + address[1] + ".csv", delayMap.get(address[1]),
						"data/address/processed/lAddress/" + address[1] + "_" + i + ".csv");

			}

		}
	}

}
