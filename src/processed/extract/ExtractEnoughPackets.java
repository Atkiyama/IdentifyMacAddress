package processed.extract;

import java.io.IOException;

import processed.extract.io.ReadTXT;

public class ExtractEnoughPackets{

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Extract ex = new Extract(ReadTXT.read());
		ex.makeAddressList();
		int I = Integer.parseInt(args[0]);
		ex.extract(I);
		ex.extractEnoughAddress(I);
		ex.writeAddress();
		ex.writeAllAddress();
	}

}
