package graph.makeCDF.node;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 機器(キャプチャファイル)を示すクラス
 * @author akiyama
 *
 */
public class BTMachine {
	/**
	 * macアドレス名のリスト
	 */
	private ArrayList<String> addresses;

	public ArrayList<Address> getAddressList() {
		return addressList;
	}

	/**
	 * パケットのリスト
	 */
	private ArrayList<Packet> packets;
	/**
	 * ファイル名
	 */
	private String fileName;
	/**
	 * アドレスのリスト
	 */
	private ArrayList<Address> addressList;

	/**
	 * リストをそれぞれ初期化する
	 */
	public BTMachine() {
		addresses = new ArrayList<>();
		packets = new ArrayList<>();
		addressList = new ArrayList<>();
	}

	public void addPacket(Packet packet) {
		packets.add(packet);
	}

	/**
	 * 引数の文字列をaddする。一度目ならファイル名、それ以外ならアドレスにaddする
	 * @param data addする文字列(ファイル名orMacアドレス)
	 */
	public void addData(String data) {
		// TODO 自動生成されたメソッド・スタブ
		if (fileName == null)
			fileName = data;
		else
			addresses.add(data);

	}

	public ArrayList<Packet> getPackets() {
		return packets;
	}

	public void setPackets(ArrayList<Packet> packets) {
		this.packets = packets;
	}

	public ArrayList<String> getAddresses() {
		return addresses;
	}

	public String getFileName() {
		return fileName;
	}

	public int getAverageRssi() {
		int sum = 0;
		for (Packet packet : packets)
			sum += packet.getRssi();
		return Math.round(sum / packets.size());

	}

	/**
	 * 各パケットに遅延時間を設定する
	 * @param delay 設定する遅延時間
	 */
	public void setDelay(int delay) {
		for (Packet packet : packets) {
			packet.setDelay(delay);
		}
	}

	/**
	 * 標準偏差のゲッター
	 * @return 標準偏差
	 */
	public double getStandardDeviation() {
		// TODO 自動生成されたメソッド・スタブ
		double sum = 0;
		for (Packet packet : packets) {
			sum += Math.pow(packet.getRssi() - getAverageRssi(), 2);
		}
		sum /= packets.size();
		BigDecimal bd = BigDecimal.valueOf(Math.sqrt(sum));
		return bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * アドレスリストを生成するメソッド
	 */
	public void setAddressList() {
		Collections.sort(packets, new PacketComparator());
		for (String addressName : addresses) {
			addressList.add(new Address(addressName));
		}
		for (Packet packet : packets) {
			for (Address address : addressList) {
				if (packet.getAddress().equals(address.getAddressName()))
					address.addPacket(packet);
			}


		}


	}

}
