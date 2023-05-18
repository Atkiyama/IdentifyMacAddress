package dataAnalyze.node;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
	public Packet(String address, double time, int rssi,String uuid) {
		this.address = address;
		this.time = time;
		this.rssi = rssi;
		this.uuid = uuid;
	}
	public String getUuid() {
		return uuid;
	}
	public void setRssi(int rssi) {
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
	
	private String uuid;
	public String getAddress() {
		return address;
	}
	public double getTime() {
		return time;
	}
	public int getRssi() {
		return rssi;
	}
	
	

	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * timeを初期受診時刻をベースにしてフォーマットします
	 * @param fTime 初期受診時刻
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

	/**
	 * パケット情報を出力するメソッド ReadTXTのテスト用
	 */
	public void printData() {
		System.out.println(address+","+time+","+rssi);
	}


}
