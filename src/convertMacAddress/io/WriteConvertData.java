package convertMacAddress.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import convertMacAddress.node.Packet;
/**
 * 結合データを書き出すクラス
 * @author akiyama
 *
 */
public class WriteConvertData {
/**
 * 結合データを書き出すメソッド
 * @param packets パケットのリスト
 * @throws IOException
 */
	public void write(ArrayList<Packet> packets) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		 FileWriter fileWriter = new FileWriter("data/result/multi/mulitData.csv");
		 for(Packet packet:packets) {
			 fileWriter.append(packet.getAddress());
			 fileWriter.append(",");
			 fileWriter.append(String.valueOf(packet.getTime()));
			 fileWriter.append(",");
			 fileWriter.append(String.valueOf(packet.getRssi()));
			 fileWriter.append("\r\n");
		 }
		 fileWriter.close();

	}

}
