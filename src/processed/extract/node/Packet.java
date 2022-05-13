package processed.extract.node;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Packet implements Cloneable{
	private String address;
	private double time;
	private int rssi;
	private String fileName;
	public Packet(String address, double time, int rssi) {
		this.address = address;
		this.time = time;
		this.rssi = rssi;
	}
	public Packet(String address, double time, int rssi,String fileName) {
		this.address = address;
		this.time = time;
		this.rssi = rssi;
		this.fileName = fileName;
	}
	public String getFileName() {
		return fileName;
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
	public void setTime(double d) {
		this.time = d;
	}
	public int getRssi() {
		return rssi;
	}
	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public void setDelay(double delay) {
		 BigDecimal bTime = new BigDecimal(time);
		 BigDecimal bDelay = new BigDecimal(delay);
		 bTime =bTime.add(bDelay);
		 time = bTime.doubleValue();
		
	}
	
	/**
	 * 時間を最初のパケットの受信時刻を0として合わせる
	 * @param fTime
	 */
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
