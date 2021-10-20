package extract;

import java.io.IOException;
import java.util.ArrayList;

import extract.io.ReadTXT;
import extract.io.Write;
import extract.node.Address;
import extract.node.Packet;

public class Extract {
	public ArrayList<Packet> packets;
	public ArrayList<Address>  addressList;


	public Extract(ArrayList<Packet> packets) {
		this.packets = packets;
		addressList = new ArrayList<>();
	}
	public void makeAddressList() {
		//既知のアドレスかどうかのフラグ
		boolean ad_known = false;
		for (Packet packet : packets) {
			for (Address address : addressList) {
				if (address.getName().equals(packet.getAddress())) {
					ad_known = true;
					address.addPacket(packet);
				}
			}
			if (!ad_known) {
				addressList.add(new Address(packet));
			}
			ad_known = false;
		}

	}
	/**
	 *
	 * @param args 0に抽出する秒数
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Extract ex = new Extract(ReadTXT.read());
		ex.makeAddressList();
		ex.extract(Integer.parseInt(args[0]));
		ex.writeAddress();
		ex.writeAllAddress();
	}
	private void writeAllAddress() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Write.writeAllAddress(addressList);

	}
	private void writeAddress() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		for(Address address:addressList) {
			Write.writeFAddress(address);
			Write.writeLAddress(address);
		}

	}
	private void extract(int T) {
		// TODO 自動生成されたメソッド・スタブ
		for(Address address:addressList) {
			address.setlTime();
			address.setFPackets(T);
			address.setLPackets(T);
		}

	}

}
