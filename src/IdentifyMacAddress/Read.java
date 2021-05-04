package identifyMacAddress;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import identifyMacAddress.node.Packet;
/**
 * BLEパケットデータを読み込むクラス
 * @author akiyama
 *
 */
public class Read {
	/**
	 * BLEパケットデータを読み込むメソッド
	 * @return パケットのリスト
	 * @throws IOException
	 */
	public ArrayList<Packet> read() throws IOException{
		File file = new File("data/result/multi/multiData.csv");
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
		//その後pakcetのリストに書き換える
		ArrayList<Packet> packets =new ArrayList<>();
		for(String[] line:data) {
			packets.add(new Packet(line[0],Double.parseDouble(line[1]),Integer.parseInt(line[2])));
		}
		return packets;

	}
}
