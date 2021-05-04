package identifyMacAddress;

import java.io.IOException;

import identifyMacAddress.input.ReadData;

public class IdentifyMacAddress {
	public static void main(String[] args) throws IOException {
		ReadData readData = new ReadData();
		Identify identify = new Identify(readData.read());
		identify.makeAddressList();
		identify.removeFewAddress();
		for(int t=0;t<11;t++) {
			for(int r=0;r<11;r++) {
				identify.identify(t,r);
			}
		}
	}


}
