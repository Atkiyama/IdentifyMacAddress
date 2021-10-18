package convertMacAddress;

import java.io.IOException;

import convertMacAddress.io.ReadAnswer;
import convertMacAddress.io.ReadData;
import convertMacAddress.io.WriteConvertData;

/**
 *
 * @author akiyama
 *
 */
public class ConvertOriginal {
	/**
	 *
	 * @param args 0出力データのパス 1にデータ数 2に入力フォルダ
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		ReadAnswer read = new ReadAnswer();
		ReadData readData = new ReadData(read.read());
		readData.readData(args[2]);
		ConvertMacAddress convert = new ConvertMacAddress(readData.getBtMachines());
		int numOfData =Integer.parseInt(args[1]);
		convert.selectData(numOfData);
		convert.convert();
		WriteConvertData write = new WriteConvertData();
		write.write(convert.getPackets(), args[0]);
	}

}
