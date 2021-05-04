package identifyMacAddressSingle;

import java.io.IOException;

import identifyMacAddressSingle.identiy.Address;
import identifyMacAddressSingle.identiy.Identify;
import identifyMacAddressSingle.identiy.Read;


/**
 * BLE機器のパケット情報を読み込んだテキストファイルからmacアドレスの特定を行うクラスのテスト用コード
 * 引数にはそれぞれ 読み込むファイル名 R T N(それぞれ闘値、Nはデフォルト値を設定しているので最悪不要)を入れること
 * @author akiyama
 *
 */
public class IdentifyMacAddress {
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Read read = new Read(args[0]);
		Identify identify = null;
		if(args.length == 4)
			 identify = new Identify(Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
		else if(args.length == 3)
			 identify = new Identify(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		else {
			System.out.println("引数エラー");
			System.exit(1);
		}
		identify.identify(read.read());
		//以下テスト用
		System.out.println(args[0]+","+read.getDate());
		System.out.println("R="+args[1]+",T="+args[2]);
		for(Address address:identify.getAddressList()) {
			address.printData();
		}


	}

}
