package identifyMacAddress.identify;

import java.io.IOException;
import java.util.ArrayList;

import identifyMacAddress.node.Packet;
import identifyMacAddress.read.Read;
import identifyMacAddress.read.ReadCSV;
import identifyMacAddress.read.ReadTXT;

public class IdentifyMove extends Identify{
	public IdentifyMove(ArrayList<Packet> read) {
		// TODO 自動生成されたコンストラクター・スタブ
		super(read);
	}

	/**
	 * メインメソッド
	 * @param args 0に読み込むキャプチャファイル名(絶対or相対パス),１にTを入れる
	 * @throws IOException
	 */


	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		Read read;
		if(args[0].contains("txt"))
			read = new ReadTXT(args[0]);
		else if(args[0].contains("csv"))
			read = new ReadCSV(args[0]);
		else {
			System.out.println("このプログラムはcsvファイルかtxtファイルのみ読み込めます。ファイルの拡張子を確認してください");
			read = null;
			System.exit(0);
		}
		Identify identify = new IdentifyMove(read.read());
		identify.makeAddressList();
		//identify.removeFewAddress();
		((IdentifyStay) identify).identify(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		//identify.checkData();
	}


}
