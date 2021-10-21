package extract.node;

import java.util.ArrayList;

public class Address {
	private String name;
	private ArrayList<Packet> packets;
	private double fTime;
	private double lTime;
	private ArrayList<Packet> fPackets;
	private ArrayList<Packet> lPackets;
	private String fileName;
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

}
