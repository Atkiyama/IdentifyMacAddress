package identifyMacAddress.node;

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


}
