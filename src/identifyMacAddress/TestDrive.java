package identifyMacAddress;

import java.io.IOException;
import java.util.Objects;
/**
 * BLE機器のパケット情報を読み込んだcsvファイルからmacアドレスの特定を行うクラス
 * 引数にはそれぞれ 読み込むファイル名 R T N(それぞれ闘値、Nはデフォルト値を設定しているので最悪不要)を入れること
 * @author akiyama
 *
 */
public class IdentifyMacAddress {

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Read read = new Read(args[0]);
		Identify identify;
		if(Objects.nonNull(args[2]))
			 identify = new Identify(Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
		else
			 identify = new Identify(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		identify.identify(read.read());
		//以下テスト用
		for(Address address:identify.getAddressList()) {
			address.printData();
		}

	}

}
