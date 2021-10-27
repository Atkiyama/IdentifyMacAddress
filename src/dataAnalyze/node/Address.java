package dataAnalyze.node;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	 * 正規化初期受信時刻
	 */
	private BigDecimal deltaFT;
	/**
	 * 正規化最終受診時刻
	 */
	private BigDecimal deltaLT;
	/**
	 * 正規化平均RSSI
	 */
	private BigDecimal deltaR;
	/**
	 * この次のものと思われるmacアドレス
	 */
	private ArrayList<Address> nextAdr;
	/**
	 *RとTの相対距離
	 */

	private ArrayList<Packet> packets;
	private BigDecimal tmpLength;
	public BigDecimal getTmpLength() {
		return tmpLength;
	}

	public void setTmpLength(BigDecimal tmpLength) {
		this.tmpLength = tmpLength;
	}

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
		packets = new ArrayList<>();
		packets.add(new Packet(advA,ftime,rssi));
	}

	public void addPacket(Packet packet) {
		packets.add(packet);
	}

	public ArrayList<Packet> getPackets() {
		return packets;
	}

	/**
	 * 相対値で比較するときの初期値用
	 */
	public Address() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * 闘値が0のときの例外処理を記述しているが結局使わない。他も同様
	 * @param t
	 */
	public void setDeltaFT(double t) {
		BigDecimal ft = BigDecimal.valueOf(ftime);
		BigDecimal bigT;
		if(t==0)
			bigT =BigDecimal.ONE;
		else
			bigT =BigDecimal.valueOf(t);
		this.deltaFT = ft.divide(bigT,4,RoundingMode.HALF_UP);
	}

	public BigDecimal getDeltaFT() {
		return deltaFT;
	}

	public BigDecimal getDeltaLT() {
		return deltaLT;
	}

	public BigDecimal getDeltaR() {
		return deltaR;
	}

	public void setDeltaLT(double t) {
		BigDecimal lt =BigDecimal.valueOf(ltime);
		BigDecimal bigT;
		if(t==0)
			bigT =BigDecimal.ONE;
		else
			bigT =BigDecimal.valueOf(t);
		this.deltaLT = lt.divide(bigT,3,RoundingMode.HALF_UP);
	}

	public void setDeltaR(double r) {
		BigDecimal aveR = BigDecimal.valueOf(getAverageRssi());
		BigDecimal bigR;
		if(r == 0)
			bigR = BigDecimal.ONE;
		else
			bigR = BigDecimal.valueOf(r);
		this.deltaR = aveR.divide(bigR,3,RoundingMode.HALF_UP);
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
	 * @param address macアドレス
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
		System.out.print(advA + ",ftime =");
		System.out.print(ftime + ",ltime =");
		System.out.print(ltime + ",aveRssi=");
		System.out.print(getAverageRssi() + ",numPkt=");
		System.out.println(numPkt);
		for (Address nextAdr : this.nextAdr) {
			nextAdr.printData();
		}
		System.out.println();

	}

	public void printDataForAnalyze() {
		System.out.print(advA + ",ftime =");
		System.out.print(ftime + ",ltime =");
		System.out.print(ltime + ",aveRssi=");
		System.out.print(getAverageRssi() + ",numPkt=");
		System.out.println(numPkt);

	}

	public void setNextAdr(ArrayList<Address> nextAdr) {
		this.nextAdr = nextAdr;
	}

	/**
	 * 平均rssiのゲッター
	 * @return 平均rssi
	 *
	 */


	public int getAverageRssi() {
		double sum = 0;
		for (int rssi : this.rssi) {
			sum += rssi;
		}
		return (int) Math.round(sum / this.rssi.size());

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

	public void setNumPkt(int numPkt) {
		this.numPkt = numPkt;
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
