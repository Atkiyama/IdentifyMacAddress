package convertMacAddress.node;
/**
 * パケットを示すクラス
 */
public class Packet {
    private int time;
    private int rssi;
	private String address;

    public Packet(String address, int time, int rssi) {
		// TODO 自動生成されたコンストラクター・スタブ
    	this.address = address;
    	this.time = time;

	}

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    /**
     * パケット情報を表示するメソッド.
     * デバック用
     */

}


