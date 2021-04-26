package convertMacAddress;

import java.util.ArrayList;

public class BTMachine {
	private ArrayList<String> address;
	private ArrayList<Packet> packets;
	private String fileName;
	public void addData(String data) {
		// TODO 自動生成されたメソッド・スタブ
		if(fileName == null)
			fileName = data;
		else
			address.add(data);

	}
	public ArrayList<Packet> getPackets() {
		return packets;
	}
	public void setPackets(ArrayList<Packet> packets) {
		this.packets = packets;
	}
	public ArrayList<String> getAddress() {
		return address;
	}
	public String getFileName() {
		return fileName;
	}
}
