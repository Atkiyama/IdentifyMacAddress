package processed.extract.node;

import java.util.ArrayList;

/**
 * アドレスを示すクラス
 * lineUPでも使用している
 * @author akiyama
 *
 */
public class Address {
	private String name;
	private ArrayList<Packet> packets;
	private double fTime;
	private double lTime;
	private ArrayList<Packet> fPackets;
	private ArrayList<Packet> lPackets;
	private String fileName;
	public Address(String[] address) {
		fileName = address[0];
		name = address[1];
		fTime = Double.parseDouble(address[2]);
		lTime = Double.parseDouble(address[3]);
		packets = new ArrayList<>();
		fPackets = new ArrayList<>();
		lPackets = new ArrayList<>();
	}
	public void setfTime(double fTime) {
		this.fTime = fTime;
	}
	public void setlTime(double lTime) {
		this.lTime = lTime;
	}
	public Address(Packet packet) {
		packets = new ArrayList<>();
		fPackets = new ArrayList<>();
		lPackets = new ArrayList<>();
		packets.add(packet);
		fTime = packet.getTime();
		lTime = 0;
		name = packet.getAddress();
		this.fileName = packet.getFileName();
	}
	public String getFileName() {
		return fileName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * パケットデータを元にlTimeを決める
	 */
	public void setlTime() {
		for(Packet packet:packets) {
			double time = packet.getTime();
			if(lTime<time)
				lTime = time;
		}
	}
	public void addPacket(Packet packet) {
		packets.add(packet);
	}

	public void setFPackets(int T) {
		for(Packet packet:packets) {
			if(packet.getTime()-fTime<=T)
				fPackets.add(packet);
		}

	}
	public double getfTime() {
		return fTime;
	}
	public double getlTime() {
		return lTime;
	}
	public ArrayList<Packet> getfPackets() {
		return fPackets;
	}
	public ArrayList<Packet> getlPackets() {
		return lPackets;
	}
	public ArrayList<Packet> getPackets() {
		return packets;
	}
	public void setLPackets(int T) {
		for(Packet packet:packets) {
			if(lTime-packet.getTime()<=T)
				lPackets.add(packet);
		}
	}
	public void addFPacket(Packet packet) {
		fPackets.add(packet);
	}

	public void addLPacket(Packet packet) {
		lPackets.add(packet);
	}

	public double getAverageRssi() {
		double sum = 0;
		for(Packet packet:packets) {
			double rssi = packet.getRssi();
			sum += rssi;
		}
		return sum/packets.size();
	}

}
