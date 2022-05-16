package processed.delay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Hoge {

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		int combination = Integer.parseInt(args[0]);
		ArrayList<String[]> addressList;
		for (int i = 1; i <= 1; i++) {
			addressList = DelayForC.extractAddressList(combination);
			System.out.println(addressList.size());
			ArrayList<Double> delayList =DelayForC.makeDelayList(42);
			HashMap<String, Double> delayMap = DelayForC.makeDelayMap(addressList, delayList);
			DelayForC.makeConvert(addressList,delayMap,i);
			addressList =DelayForC.setDelay(addressList, delayMap);
			System.out.println(addressList.size());
			
		}
	}

}
