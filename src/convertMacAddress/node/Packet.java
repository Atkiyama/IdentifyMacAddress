package convertMacAddress.node;
/**
 * パケットを示すクラス
 */
public class Packet {
	/**
	 * パケット取得時間
	 */
    private int time;
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

    /**
     * 引数で初期化する
     * @param address macアドレス
     * @param time 受診時刻
     * @param rssi rssi
     */
	public Packet(String address, int time, int rssi) {
		// TODO 自動生成されたコンストラクター・スタブ
    	this.address = address;
    	this.time = time;
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

	public int getTime() {
		return time;
	}


}


