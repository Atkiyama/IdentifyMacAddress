package processed.extract.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import processed.extract.node.Address;
import processed.extract.node.Packet;

public class Write {
	public static void writeAllAddress(ArrayList<Address> addressList) throws IOException {
		FileWriter fileWriter = new FileWriter("data/address/original/addressList.csv");
		fileWriter.append("fileName,address,fTime,lTime");
		fileWriter.append("\r\n");
		for(Address address:addressList) {
			fileWriter.append(address.getFileName()+","+address.getName()+","+address.getfTime()+","+address.getlTime());
			fileWriter.append("\r\n");
		}
		fileWriter.close();
	}
	public static void writeFAddress(Address address) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		FileWriter fileWriter = new FileWriter("data/address/original/fAddress/"+address.getName()+".csv");
		fileWriter.append("time,rssi");
		fileWriter.append("\r\n");
		for(Packet packet:address.getfPackets()) {
			fileWriter.append(packet.getTime()+","+packet.getRssi());
			fileWriter.append("\r\n");
		}
		fileWriter.close();
	}
	public static void writeLAddress(Address address) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		FileWriter fileWriter = new FileWriter("data/address/original/lAddress/"+address.getName()+".csv");
		fileWriter.append("time,rssi");
		fileWriter.append("\r\n");
		for(Packet packet:address.getlPackets()) {
			fileWriter.append(packet.getTime()+","+packet.getRssi());
			fileWriter.append("\r\n");
		}
		fileWriter.close();

	}

}
