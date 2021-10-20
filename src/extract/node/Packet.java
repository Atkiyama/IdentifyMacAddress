package extract.node;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Packet {
	private String address;
	private double time;
	private int rssi;
	public Packet(String address, double time, int rssi) {
		this.address = address;
		this.time = time;
		this.rssi = rssi;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getRssi() {
		return rssi;
	}
	public void setRssi(int rssi) {
		this.rssi = rssi;
	}
	public void formatTime(double fTime) {
		 BigDecimal bTime = new BigDecimal(time);
		 BigDecimal bFTime = new BigDecimal(fTime);
		if(fTime<=time) {
			bTime = bTime.subtract(bFTime);
		}else {
			time = time + 24*3600;
			bTime = new BigDecimal(time);
		}
		BigDecimal bTime2 = bTime.setScale(6,RoundingMode.HALF_UP);
		time = bTime2.doubleValue();

	}


}
