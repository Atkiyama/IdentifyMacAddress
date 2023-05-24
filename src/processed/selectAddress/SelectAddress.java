package processed.selectAddress;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import processed.ReadCSV;
import processed.ReadTXT;
import processed.extract.node.Packet;

public class SelectAddress {
	String fileName;
	String address;
	ArrayList<Packet> packets;

	public SelectAddress(String fileName, String address) {
		super();
		this.fileName = fileName;
		this.address = address;
		packets = new ArrayList<>();
	}

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<String[]> selectAddressTable = ReadCSV.read("data/capture/ver3/selectAddress.csv");
		//ヘッダを削除
		selectAddressTable.remove(0);
		ArrayList<SelectAddress> selectAddresses = new ArrayList<>();

		for (String[] fileData : selectAddressTable) {
			String fileName = fileData[0];
			String address = fileData[1];

			SelectAddress selectAddress = new SelectAddress(fileName, address);
			selectAddresses.add(selectAddress);

		}

		for (SelectAddress selectAddress : selectAddresses) {
			selectAddress.selectAddress();
			selectAddress.writeCSV();

		}

	}

	private void writeCSV() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		FileWriter fileWriter = new FileWriter("data/capture/ver3/csv/" + fileName + ".csv");
		fileWriter.append("address,time,rssi");
		fileWriter.append("\r\n");
		for (Packet packet : packets) {
			fileWriter.append(packet.getAddress() + "," + packet.getTime() + "," + packet.getRssi());
			fileWriter.append("\r\n");
		}
		fileWriter.close();

	}

	private void selectAddress() throws IOException {
		// TODO 自動生成されたメソッド・スタブ

		ArrayList<Packet> read = ReadTXT.read("data/capture/ver3/txt/" + fileName + ".txt");

		for (Packet packet : read) {
			//System.out.println(packet.getAddress()+"," +address);
			if (packet.getAddress().equals(address)) {
				this.packets.add(packet);
				
			}
		}
	}

}
