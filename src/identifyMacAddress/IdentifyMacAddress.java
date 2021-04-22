package identifyMacAddress;

import java.io.IOException;

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
