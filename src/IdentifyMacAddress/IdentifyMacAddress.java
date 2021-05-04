package identifyMacAddress;

import java.io.IOException;

import identifyMacAddress.input.ReadData;

public class IdentifyMacAddress {
	public static void main(String[] args) throws IOException {
		ReadData readData = new ReadData();
		Identify identify = new Identify(readData.read());
		identify.makeAddressList();
		identify.removeFewAddress();
		identify.identify(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
	}


}
