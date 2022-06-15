package processed.extract;

import java.io.IOException;
import java.util.ArrayList;

import processed.extract.io.ReadTXT;
import processed.extract.io.Write;
import processed.extract.node.Address;
import processed.extract.node.Packet;

/**
 * キャプチャしたパケット情報から改良手法に必要なデータを抽出するクラス
 * @author akiyama
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
		if(args.length>1)
			ex.smoothing(Integer.parseInt(args[1]));
		ex.makeAddressList();
		ex.extract(Integer.parseInt(args[0]));
		ex.writeAddress();
		ex.writeAllAddress();
	}

	/*
	 * Writeクラスにアドレスリストの書き込みを投げる
	 */
	void writeAllAddress() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Write.writeAllAddress(addressList);

	}
	/**
	 * WriteクラスにfAddressとlAddressの書き込みを投げる
	 * @throws IOException
	 */
	void writeAddress() throws IOException {
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
	void extract(int T) {
		// TODO 自動生成されたメソッド・スタブ
		for(Address address:addressList) {
			address.setlTime();
			address.setFPackets(T);
			address.setLPackets(T);
		}

	}
	public void extractEnoughAddress(int I) {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<Address> replace = new ArrayList<>();
		for(int i=0;i<addressList.size()-1;i++) {
		if(addressList.get(i).getlPackets().size()>=I&&addressList.get(i+1).getfPackets().size()>I)  {
				replace.add(addressList.get(i));
				replace.add(addressList.get(i+1));
			}

		}
		addressList = replace;

	}
	private boolean existAddresss(ArrayList<Address> replace, Address address) {
		// TODO 自動生成されたメソッド・スタブ
		for(Address rAddress:replace) {
			if(rAddress==address)
				return true;

		}
		return false;
	}

	public void smoothing(int range) {
		for(int i=0;i<packets.size();i++) {
			double smoothing =0;
			int numOfRange =0;
			for(int j=0;j<range*2+1;j++) {
				int argument = i-range+j;
				if(0<=argument&&argument<packets.size()) {
					smoothing+=packets.get(argument).getRssi();
					numOfRange++;
				}

			}
			smoothing/=numOfRange;
			packets.get(i).setRssi((int) smoothing);


		}

	}

}
