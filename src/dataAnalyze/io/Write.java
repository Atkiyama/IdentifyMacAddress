package dataAnalyze.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import dataAnalyze.node.Address;
import dataAnalyze.node.Packet;

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
		train = null;

		fileWriter = new FileWriter(testFile);

		for (double i = 0; i < P; i += 0.1) {
			for (int j = 0 , len = adr_base.getNextAdr().size() ; j<len ; j++) {
				fileWriter.append(String.valueOf(adr_base.getNextAdr().get(j).getFtime() + i));
				if(j!=adr_base.getNextAdr().size()-1)
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
