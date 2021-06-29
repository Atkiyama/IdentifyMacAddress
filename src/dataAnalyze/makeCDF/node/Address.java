package dataAnalyze.makeCDF.node;

import java.util.ArrayList;

public class Address {
	private String addressName;
	private ArrayList<Packet> packets;
	/**
	 * 初期受診時刻
	 */
	private double ftime;
	/**
	 * 最終受診時刻
	 */
	private double ltime;
	public Address(String addressName) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.addressName = addressName;
		packets = new ArrayList<>();
		ltime = 0;
		ftime = 9999999;
	}
	public String getAddressName() {
		return addressName;
	}
	public  void addPacket(Packet packet) {
		// TODO 自動生成されたメソッド・スタブ
		packets.add(packet);
	}

	public void setTimes() {
		for(Packet packet:packets) {
			if(packet.getTime()<ftime)
				ftime=packet.getTime();
			if(packet.getTime()>ltime)
				ltime=packet.getTime();
		}

	}
	public ArrayList<Packet> getPackets() {
		return packets;
	}
	public double getFtime() {
		return ftime;
	}
	public double getLtime() {
		return ltime;
	}

}
