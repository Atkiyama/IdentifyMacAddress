package convertMacAddress;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import convertMacAddress.io.ReadAnswer;
import convertMacAddress.node.BTMachine;

/**
 * convertMacAddressのテストコード
 * @author akiyama
 *
 */
public class TestConvert {

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ReadAnswer read = new ReadAnswer();
		ConvertMacAddress.main(args);
		ArrayList<BTMachine> btMachines =read.read();
		ArrayList<String> addressList = new ArrayList<>();
		for(BTMachine btMachine:btMachines) {
			for(String address2:btMachine.getAddress())
				addressList.add(address2);
		}
		File file = new File("data/result/multi/mulitData.csv");
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
		String str = in.readLine();
		ArrayList<String> data = new ArrayList<>();
		while(str != null) {
			data.add(str.split(",")[0]);
			str = in.readLine();
		}
		in.close();

		for(String s:data) {
			if(!addressList.contains(s))
				System.out.println(s+"はconvertされるべきアドレスではありません");
		}

		for(String s:addressList) {
			if(!data.contains(s))
				System.out.println(s+"がconvertされていません");
		}

	}

}
