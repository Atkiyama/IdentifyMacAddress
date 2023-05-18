package dataAnalyze.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import dataAnalyze.node.Packet;
/**
 * BLEパケットデータを読み込むクラス
 * CSVファイル用
 * @author akiyama
 *
 */
public class ReadCSV extends Read{
	public ReadCSV(String fileName) {
		super(fileName);
	}

	/**
	 * BLEパケットデータを読み込むメソッド
	 * @return パケットのリスト
	 * @throws IOException
	 */
	public ArrayList<Packet> read() throws IOException{
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
		String str =in.readLine();
		ArrayList<String[]> data=new ArrayList<>();
		//まずはデータを一次元配列のリストとして保存
		while(str != null) {
			data.add(str.split(","));
			str = in.readLine();
		}

		in.close();
		//１行目(address,time,rssi)をカット
		data.remove(0);
		//その後pakcetのリストに書き換える
		ArrayList<Packet> packets =new ArrayList<>();
		for(String[] line:data) {
			packets.add(new Packet(line[0],Double.parseDouble(line[1]),Integer.parseInt(line[2])));
		}
		return packets;

	}
	public static void main(String args[]) throws IOException {
		Read read = new ReadCSV(args[0]);
		ArrayList<Packet> packets= read.read();
		System.out.println(packets.size()/360);
	}
}
