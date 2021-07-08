package makeCDF;

import java.io.IOException;
import java.util.ArrayList;

import makeCDF.cdf.AddressTime;
import makeCDF.cdf.Make;
import makeCDF.cdf.PacketRssi;
import makeCDF.cdf.PacketTime;
import makeCDF.io.ReadAnswer;
import makeCDF.io.ReadData;
import makeCDF.node.BTMachine;

/**
 * cdfを作るためのクラス
 * @author akiyama
 *
 */
public class MakeCDF {

	/**
	 *
	 * @param args 0の文字列に応じてどのcdfを作るかを決める
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
			ReadAnswer readAnswer = new ReadAnswer();
			ReadData readData = new ReadData(readAnswer.read());
			readData.readData();
			Make makeCDF = getInstance(readData.getBtMachines(),args[0]);
			makeCDF.setAddressList();
			makeCDF.makeData();
			makeCDF.sort();
			makeCDF.printData();

	}

	/**
	 * 文字列instanceに応じたインスタンスを返すメソッド
	 * @param btMachines 機器のリスト
	 * @param instance　生成したいインスタンス名
	 * @return 引数に応じたインスタンス
	 */
	private static Make getInstance(ArrayList<BTMachine> btMachines, String instance) {
		// TODO 自動生成されたメソッド・スタブ
		switch (instance) {
			case "AddressTime":
				return new AddressTime(btMachines);
			case "PacketTime":
				return new PacketTime(btMachines);
			case "PacketRssi":
				return new PacketRssi(btMachines);
		}
		System.exit(0);
		return null;
	}

}
