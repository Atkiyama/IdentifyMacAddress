package identifyMacAddress;

import java.io.IOException;

import identifyMacAddress.read.Read;
import identifyMacAddress.read.ReadCSV;
import identifyMacAddress.read.ReadTXT;
/**
 * 平均RSSIとパケット受診時刻を元にmacアドレスの特定を行うクラス
 * @author akiyama
 *
 */
public class IdentifyMacAddress {
	/**
	 * メインメソッド
	 * @param args 0に読み込むキャプチャファイル名(絶対or相対パス),１にR,2にTを入れる(別途スクリプトで操作)
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
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
		Identify identify = new Identify(read.read());
		identify.makeAddressList();
		identify.removeFewAddress();
		identify.identify(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
	}


}
