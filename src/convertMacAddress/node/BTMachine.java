package convertMacAddress.node;

import java.util.ArrayList;

/**
 * 機器(キャプチャファイル)を示すクラス
 * @author akiyama
 *
 */
public class BTMachine {
	/**
	 * macアドレスのリスト
	 */
	private ArrayList<String> address;
	/**
	 * パケットのリスト
	 */
	private ArrayList<Packet> packets;
	/**
	 * ファイル名
	 */
	private String fileName;
	/**
	 * リストをそれぞれ初期化する
	 */
	public BTMachine() {
		address = new ArrayList<>();
		packets = new ArrayList<>();
	}
	public void addPacket(Packet packet) {
		packets.add(packet);
	}
	/**
	 * 引数の文字列をaddする。一度目ならファイル名、それ以外ならアドレスにaddする
	 * @param data addする文字列(ファイル名orMacアドレス)
	 */
	public void addData(String data) {
		// TODO 自動生成されたメソッド・スタブ
		if(fileName == null)
			fileName = data;
		else
			address.add(data);

	}
	public ArrayList<Packet> getPackets() {
		return packets;
	}
	public void setPackets(ArrayList<Packet> packets) {
		this.packets = packets;
	}
	public ArrayList<String> getAddress() {
		return address;
	}
	public String getFileName() {
		return fileName;
	}
	public int getAverageRssi() {
		int sum = 0;
		for(Packet packet:packets)
			sum+=packet.getRssi();
		return Math.round(sum/packets.size());

	}

	/**
	 * 各パケットに遅延時間を設定する
	 * @param delay 設定する遅延時間
	 */
	public void setDelay(int delay) {
		for(Packet packet:packets) {
			packet.setDelay(delay);
		}
	}

}
