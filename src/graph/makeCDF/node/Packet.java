package graph.makeCDF.node;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * パケットを示すクラス
 */
public class Packet {
	/**
	 * パケット取得時間
	 */
    private double time;
    /**
     * rssi
     */
    private int rssi;
    /**
     * macアドレス
     */
	private String address;

    public String getAddress() {
		return address;
	}

    public void printData() {
    	System.out.println(address+","+rssi+","+time);
    }

    /**
     * 引数で初期化する
     * @param address macアドレス
     * @param d 受診時刻
     * @param rssi rssi
     */
	public Packet(String address, double d, int rssi) {
		// TODO 自動生成されたコンストラクター・スタブ
    	this.address = address;
    	this.time = d;
    	this.rssi = rssi;
	}

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    /**
     * 遅延時間を設定するメソッド
     * @param delay 設定する遅延時間
     */
    public void setDelay(int delay) {
    	time += delay;
    }

	public double getTime() {
		return time;
	}

	/**
	 * timeをフォーマットするメソッド
	 * @param fTime フォーマットする時間
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


