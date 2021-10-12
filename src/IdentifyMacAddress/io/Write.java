package identifyMacAddress.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import identifyMacAddress.node.Address;
import identifyMacAddress.node.Packet;

public class Write {
	public static void write(Address adr_base, String trainFile, String testFile, int P)
			throws IOException {
		ArrayList<Packet> train = extractTrain(adr_base, P);
		FileWriter fileWriter = new FileWriter(trainFile);
		fileWriter.append("trainTime,trainRssi");
		fileWriter.append("\r\n");
		for (Packet packet : train) {
			fileWriter.append(String.valueOf(packet.getTime()));
			fileWriter.append(",");
			fileWriter.append(String.valueOf(packet.getRssi()));
			fileWriter.append("\r\n");
		}
		fileWriter.close();

		fileWriter = new FileWriter(testFile);
		//通し番号でヘッダーをつける
		for (int i = 0;i< adr_base.getNextAdr().size();i++) {
			fileWriter.append(String.valueOf(i));
			fileWriter.append(",");
		}
		//改行
		fileWriter.append("\r\n");
		//データを列ごとに出力
		for (double i = 0; i < P; i += 0.1) {
			for (Address address : adr_base.getNextAdr()) {
				fileWriter.append(String.valueOf(address.getFtime() + i));
				fileWriter.append(",");
			}
			fileWriter.append("\r\n");
		}
		fileWriter.close();
	}

	private static ArrayList<Packet> extractTrain(Address adr_base, int p) {
		ArrayList<Packet> train = new ArrayList<>();
		// TODO 自動生成されたメソッド・スタブ
		Packet last = adr_base.getPackets().get(adr_base.getPackets().size() - 1);
		for (Packet packet : adr_base.getPackets()) {
			if (last.getTime() - packet.getTime() <= p)
				train.add(packet);

		}
		return train;
	}

}
