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
 * @param fileName 書き出すファイル名
 * @throws IOException
 */
	public void write(ArrayList<Packet> packets,String fileName) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		 FileWriter fileWriter = new FileWriter(fileName);
		 fileWriter.append("address,time,rssi\r\n");
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
