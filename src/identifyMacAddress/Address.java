package identifyMacAddress;

import java.util.ArrayList;
/**
 * macアドレスを示すクラス
 * B4森本研究コード untiiled.ipynbを元に作成
 * @author akiyama
 * @version 1.0
 *
 */
public class Address {

	private String advA;
	private int ftime;
	private int ltime;
	private ArrayList<Integer> rssi;
	private int numPkt;
	private ArrayList<Address> nextAdr;
	/**
	 * 初期化し代入
	 * @param advA アドレス
	 * @param ftime 初期受診時刻
	 * @param ltime 最終受診時刻
	 * @param rssi rssiのリスト
	 * @param numPkt パケット数
	 * @param nextAdr この次のものと思われるmacアドレス
	 */
	public Address(String advA, int ftime, int ltime, int rssi, int numPkt, Address nextAdr) {
		this.advA = advA;
		this.ftime = ftime;
		this.ltime = ltime;
		this.rssi = new ArrayList<>();
		this.rssi.add(rssi);
		this.numPkt = numPkt;
		this.nextAdr = new ArrayList<>();
		this.nextAdr.add(nextAdr);
	}

	public void addRssi(int rssi) {
		this.rssi.add(rssi);
	}

	public void addNextAddr(Address nextAdr) {
		this.nextAdr.add(nextAdr);
	}

	public void printData() {
		System.out.print(advA + ",");
		System.out.print(ftime + ",");
		System.out.print(ltime + ",");
		System.out.print(getAverageRssi() + ",");
		System.out.print(numPkt);
		for (Address nextAdr : this.nextAdr) {
			System.out.print(advA + ",");
			nextAdr.printData();
		}
		System.out.println();

	}

	public int getAverageRssi() {
		double sum = 0;
		for (Integer rssi : this.rssi) {
			sum += rssi;
		}
		return (int) Math.round(sum / rssi.size());

	}
}
