package identifyMacAddress.node;

import java.math.BigDecimal;

/**
 * パケットを示すクラス
 * @author akiyama
 *
 */
public class Packet {
	/**
	 * 引数でフィールドを初期化する
	 * @param address macアドレス
	 * @param time 受診時刻
	 * @param rssi RSSI
	 */
	public Packet(String address, double time, int rssi) {
		this.address = address;
		this.time = time;
		this.rssi = rssi;
	}
	/**
	 * macアドレス
	 */
	private String address;
	/**
	 * 受診時刻
	 */
	private double time;
	/**
	 *RSSI
	 */
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

	public void formatTime(double fTime) {
		 BigDecimal bTime = new BigDecimal(time);
		 BigDecimal bFTime = new BigDecimal(fTime);
		if(fTime<=time) {
			bTime = bTime.subtract(bFTime);
		}else {
			time = time + 24*3600;
			bTime = new BigDecimal(time);
		}
		BigDecimal bTime2 = bTime.setScale(6,BigDecimal.ROUND_HALF_UP);
		time = bTime2.doubleValue();

	}

	public void printData() {
		System.out.println(address+"|"+time+"|"+rssi);
	}


}
