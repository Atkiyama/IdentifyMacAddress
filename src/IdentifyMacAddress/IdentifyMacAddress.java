package identifyMacAddress;

import java.io.IOException;
/**
 * 平均RSSIとパケット受診時刻を元にmacアドレスの特定を行うクラス
 * @author akiyama
 *
 */
public class IdentifyMacAddress {
	/**
	 * メインメソッド
	 * @param args 0にR,１にTを入れる(別途スクリプで操作)
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Read readData = new Read();
		Identify identify = new Identify(readData.read());
		identify.makeAddressList();
		identify.removeFewAddress();
		identify.identify(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
	}


}
