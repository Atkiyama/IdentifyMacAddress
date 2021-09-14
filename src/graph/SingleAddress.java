package graph;

import java.io.IOException;
import java.util.ArrayList;

import identifyMacAddress.node.Packet;
import identifyMacAddress.read.Read;
import identifyMacAddress.read.ReadTXT;

/**
 * 単一MACアドレスのグラフを描画するためのデータを出力するクラス
 * @author akiyama
 *
 */


public class SingleAddress {

	private ArrayList<Packet> packets;

	private String address;


	public SingleAddress(ArrayList<Packet> packets, String address) {
		super();
		this.packets = packets;
		this.address = address;
	}

	/**
	 * 単一MACアドレスのグラフを描画するためのデータを出力するクラス
	 * @param args 0に読み込むファイル名 1に抽出するアドレスを入れる
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		Read read = new ReadTXT(args[0]);
		SingleAddress graph = new SingleAddress(read.read(),args[1]);
		graph.graph();
	}

	private void graph() {
		// TODO 自動生成されたメソッド・スタブ
		for(Packet packet:packets) {
			if(packet.getAddress().equals(address)) {
				System.out.println(packet.getTime()+","+packet.getRssi());
			}
		}

	}

}
