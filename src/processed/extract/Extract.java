package processed.extract;

import java.io.IOException;
import java.util.ArrayList;

import processed.extract.io.ReadTXT;
import processed.extract.io.Write;
import processed.extract.node.Address;
import processed.extract.node.Packet;

/**
 * キャプチャしたパケット情報から改良手法に必要なデータを抽出するクラス
 * @author akiyamashuuhei
 *
 */
public class Extract {
	public ArrayList<Packet> packets;
	public ArrayList<Address>  addressList;


	public Extract(ArrayList<Packet> packets) {
		this.packets = packets;
		addressList = new ArrayList<>();
	}
	/**
	 * アドレスリストを生成する
	 */
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
	
	/*
	 * Writeクラスにアドレスリストの書き込みを投げる
	 */
	private void writeAllAddress() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Write.writeAllAddress(addressList);

	}
	/**
	 * WriteクラスにfAddressとlAddressの書き込みを投げる
	 * @throws IOException
	 */
	private void writeAddress() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		for(Address address:addressList) {
			Write.writeFAddress(address);
			Write.writeLAddress(address);
		}

	}
	
	/**
	 * ftimeとfPacketとlPacketをセットする
	 * @param T 抽出する秒数
	 */
	private void extract(int T) {
		// TODO 自動生成されたメソッド・スタブ
		for(Address address:addressList) {
			address.setlTime();
			address.setFPackets(T);
			address.setLPackets(T);
		}

	}

}
