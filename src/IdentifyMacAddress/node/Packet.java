package IdentifyMacAddress.node;

public class Packet {
	public Packet(String address, double time, int rssi) {
		super();
		this.address = address;
		this.time = time;
		this.rssi = rssi;
	}
	private String address;
	private double time;
	private int rssi;
	public String getAddress() {
		return address;
	}
	public double getTime() {
		return time;
	}
	public int getRssi() {
		return rssi;
	}


}
