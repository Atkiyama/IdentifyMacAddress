package identifyMacAddress.node;

import java.util.ArrayList;
/**
 * macアドレスを示すクラス
 * B4森本研究コード untiiled.ipynbを元に作成
 * @author akiyama
 * @version 1.0
 *
 */
public class Address {
/**
 * アドレス名
 */
	private String advA;
	/**
	 * 初期受診時刻
	 */
	private double ftime;
	/**
	 * 最終受診時刻
	 */
	private double ltime;
	/**
	 * rssiのリスト
	 */
	private ArrayList<Integer> rssi;
	/**
	 * パケット数
	 */
	private int numPkt;
	/**
	 * この次のものと思われるmacアドレス
	 */
	private ArrayList<Address> nextAdr;
	/**
	 * 初期化し代入
	 * @param advA アドレス
	 * @param ftime 初期受診時刻
	 * @param ltime 最終受診時刻
	 * @param rssi rssiのリスト
	 * @param numPkt パケット数
	 */
	public Address(String advA, double ftime, double ltime, int rssi, int numPkt) {
		this.advA = advA;
		this.ftime = ftime;
		this.ltime = ltime;
		this.rssi = new ArrayList<>();
		this.rssi.add(rssi);
		this.numPkt = numPkt;
		this.nextAdr = new ArrayList<>();
	}

	/**
	 * rssiをaddする
	 * @param rssi addするrssi
	 */
	public void addRssi(int rssi) {
		this.rssi.add(rssi);
	}

	/**
	 * 次のアドレスをaddする
	 * @param nextAdr addするアドレス
	 */
	public void addNextAddr(Address nextAdr) {
		this.nextAdr.add(nextAdr);
	}

	/**
	 * 引数のアドレスがnextAdrに含まれているか判別するメソッド
	 * @param address
	 * @return 含まれていたらtrueを返す
	 */
	public boolean containNextAdr(Address address) {
		for(Address next:nextAdr) {
			if(next.equals(address))
				return true;
		}
		return false;

	}

	/*
	 * このインスタンスの情報を表示するメソッド
	 * デバック用
	 */
	public void printData() {
		System.out.print(advA + ",");
		System.out.print(ftime + ",");
		System.out.print(ltime + ",");
		System.out.print(getAverageRssi() + ",");
		System.out.println(numPkt);
		for (Address nextAdr : this.nextAdr) {
			if(this.nextAdr.size()!=1)
				System.out.print("nextAdr size is"+this.nextAdr.size());
			nextAdr.printData();
		}
		System.out.println();

	}
	/**
	 * 平均rssiのゲッター
	 * @return 平均rssi
	 *
	 */
	public int getAverageRssi() {
		double sum = 0;
		for (Integer rssi : this.rssi) {
			sum += rssi;
		}
		return (int) Math.round(sum / rssi.size());

	}


	public ArrayList<Address> getNextAdr() {
		return nextAdr;
	}

	public void incrementNumPkt() {
		numPkt++;
	}

	public void setLtime(double ltime) {
		this.ltime = ltime;
	}


	public String getAdvA() {
		return advA;
	}

	public double getFtime() {
		return ftime;
	}

	public double getLtime() {
		return ltime;
	}

	public int getNumPkt() {
		return numPkt;
	}
}
