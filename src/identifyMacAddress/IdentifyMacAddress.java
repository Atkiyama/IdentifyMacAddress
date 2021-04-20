package identifyMacAddress;

import java.io.IOException;
import java.util.Objects;

public class IdentifyMacAddress {

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Read read = new Read();
		Identify identify;
		if(Objects.nonNull(args[2]))
			 identify = new Identify(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		else
			 identify = new Identify(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		identify.identify(read.read());


	}

}
