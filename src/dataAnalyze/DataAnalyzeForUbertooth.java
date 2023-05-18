package dataAnalyze;

import java.io.IOException;

import dataAnalyze.io.Read;
import dataAnalyze.io.ReadUbertooth;

public class DataAnalyzeForUbertooth{

	/**
	 * @param args 0に読み込むファイル名,1,2に最初のデータ範囲(秒),3にデータ範囲(T),4にカットするアドレス下限
	 * @throws IOException
	 *
	 */

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
	
		Read read = new ReadUbertooth(args[0]);
		DataAnalyze analyze = new DataAnalyze(read.read());
		
		analyze.makeAddressList();
		
		//analyze.removeFewAddress(Integer.parseInt(args[4]));
		//analyze.identify(Integer.parseInt(args[3]));
		analyze.extract(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
	
		analyze.print();
		

	}

}
