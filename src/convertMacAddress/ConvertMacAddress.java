package convertMacAddress;

import java.io.IOException;
import java.util.ArrayList;

import convertMacAddress.node.BTMachine;
import convertMacAddress.node.Packet;
import convertMacAddress.read.ReadAnswer;
import convertMacAddress.read.ReadData;

/**
 * 複数macアドレスの観測結果を一つにまとめるクラス
 * @author akiyama
 *
 */

public class ConvertMacAddress {
	private ArrayList<BTMachine> btMachines;
	private ArrayList<Packet> packets;
	public ConvertMacAddress(ArrayList<BTMachine> btMachines) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.btMachines = btMachines;
		packets = new ArrayList<>();
	}
	public void convert() {

	}

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ReadAnswer read = new ReadAnswer();
		ReadData readData = new ReadData(read.read());
		readData.readData();
		readData.getBtMachines();
		ConvertMacAddress convert = new ConvertMacAddress(readData.getBtMachines());
		convert.convert();
	}	

}
