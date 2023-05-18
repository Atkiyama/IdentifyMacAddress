package processed.lineUp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import processed.extract.node.Address;
import processed.extract.node.Packet;
/**
 * ファイルに書き込むためのクラス
 * @author akiyama
 *
 */
public class Write {
	/**
	 * アドレスリストを書き込むメソッド
	 * @param addressList
	 * @param i
	 * @throws IOException
	 */
	public static void writeAllAddress(ArrayList<Address> addressList,int i) throws IOException {
		FileWriter fileWriter = new FileWriter("data/address/processed/addressList/addressList"+i+".csv");
		fileWriter.append("fileName,address,fTime,lTime");
		fileWriter.append("\r\n");
		for(Address address:addressList) {
			fileWriter.append(address.getFileName()+","+address.getName()+","+address.getfTime()+","+address.getlTime());
			fileWriter.append("\r\n");
		}
		fileWriter.close();
	}

	/**
	 * fAddressを書き込むメソッド
	 * @param address
	 * @param i
	 * @throws IOException
	 */

	/**
	 * lAddressを書き込むメソッド
	 * @param address
	 * @param i
	 * @throws IOException
	 */
	public static void writeFAddress(Address address,int i) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		FileWriter fileWriter = new FileWriter("data/address/processed/fAddress/"+address.getName()+"_"+i+".csv");
		fileWriter.append("time,rssi");
		fileWriter.append("\r\n");
		for(Packet packet:address.getfPackets()) {
			fileWriter.append(packet.getTime()+","+packet.getRssi());
			fileWriter.append("\r\n");
		}
		fileWriter.close();
	}

	/**
	 * lAddressを書き込むメソッド
	 * @param address
	 * @param i
	 * @throws IOException
	 */
	public static void writeLAddress(Address address,int i) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		FileWriter fileWriter = new FileWriter("data/address/processed/lAddress/"+address.getName()+"_"+i+".csv");
		fileWriter.append("time,rssi");
		fileWriter.append("\r\n");
		for(Packet packet:address.getlPackets()) {
			fileWriter.append(packet.getTime()+","+packet.getRssi());
			fileWriter.append("\r\n");
		}
		fileWriter.close();

	}

	/**
	 * convertしたキャプチャデータを書き込むメソッド
	 * @param packetList
	 * @param i
	 * @throws IOException
	 */
	public static void writeConvert(ArrayList<Packet> packetList,int i) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		FileWriter fileWriter = new FileWriter("data/capture/convert/move/"+i+",convertData.csv");
		for(Packet packet:packetList) {
			fileWriter.append(packet.getAddress()+","+packet.getTime()+","+packet.getRssi());
			fileWriter.append("\r\n");
		}
		fileWriter.close();

	}

}
