package graph;

import java.io.IOException;
import java.util.ArrayList;

import dataAnalyze.io.Read;
import dataAnalyze.io.ReadTXT;
import dataAnalyze.node.Packet;

/**
 * 単一MACアドレスのグラフを描画するためのデータを出力するクラス
 * @author akiyama
 *
 */


public class SingleAddress {

	private ArrayList<Packet> packets;

	private String address;
/**
 * 時間を
 */
	private ArrayList<Packet> data;


	public SingleAddress(ArrayList<Packet> packets, String address) {
		this.packets = packets;
		this.address = address;
		data = new ArrayList<>();
	}

	/**
	 * 単一MACアドレスのグラフを描画するためのデータを出力するクラス
	 * @param args 0に読み込むファイル名 1に抽出するアドレスを入れる
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		Read read = new ReadTXT(args[0]);
		SingleAddress graph = new SingleAddress(read.read(),args[1]);
		graph.add();
		graph.smoothing3();
		graph.print();
	}

	private void print() {
		// TODO 自動生成されたメソッド・スタブ
		for(Packet packet:data)
			System.out.println(packet.getTime()+","+packet.getRssi());

	}

	private void add() {
		// TODO 自動生成されたメソッド・スタブ
		for(Packet packet:packets) {
			if(packet.getAddress().equals(address)) {
				data.add(packet);
			}
		}

	}

	/**
	 * 平滑化する
	 */
	public void smoothing3() {
		for(int i=1 ;i<data.size()-1;i++) {
			int sum =data.get(i-1).getRssi();
			sum += data.get(i).getRssi();
			sum += data.get(i+1).getRssi();
			data.get(i).setRssi(sum/3);
		}
	}

	/**
	 * 平滑化する
	 */
	public void smoothing5() {
		for(int i=2 ;i<data.size()-2;i++) {
			int sum = data.get(i-1).getRssi();
			sum += data.get(i).getRssi();
			sum += data.get(i+1).getRssi();
			sum += data.get(i+2).getRssi();
			sum += data.get(i-2).getRssi();
			data.get(i).setRssi(sum/5);
		}
	}

}
