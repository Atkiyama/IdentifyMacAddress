package convertMacAddress;

import java.io.IOException;

import convertMacAddress.read.ReadAnswer;
import convertMacAddress.read.ReadData;

/**
 * 複数macアドレスの観測結果を一つにまとめるクラス
 * @author akiyama
 *
 */
public class ConvertMacAddress {

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ReadAnswer read = new ReadAnswer();
		ReadData  readData = new ReadData(read.read());
		readData.readData();
		readData.getBtMachines();

	}

}
