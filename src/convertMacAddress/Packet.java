package convertMacAddress;
/**
 * パケットを示すクラス
 */
public class Packet {
    private int systime;
    private int rssi;
	private String advA;

    public int getSystime() {
        return systime;
    }

    public String getAdvA() {
        return advA;
    }

    public void setAdvA(String advA) {
        this.advA = advA;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }


    public void setSystime(int systime) {
        this.systime = systime;
    }


    /**
     * パケット情報を表示するメソッド.
     * デバック用
     */
    public void showInfo(){
        System.out.print("Macアドレス="+advA);
        System.out.print("平均RSSI="+rssi);
        System.out.print("systime="+systime);
        System.out.println();

    }
}


