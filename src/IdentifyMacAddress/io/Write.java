package identifyMacAddress.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import identifyMacAddress.node.Address;
import identifyMacAddress.node.Packet;

public class Write {
	public static void write(Address adr_base, Address adr_tmp, int P) throws IOException {
		ArrayList<Packet> train = extractTrain(adr_base, P);
		ArrayList<Double> test = extractTest(adr_tmp, P);
		int roop;
		if (train.size() > test.size())
			roop = train.size();
		else
			roop = test.size();

		String fileName = "data/result/forRegression.csv";
		FileWriter fileWriter = new FileWriter(fileName);
		fileWriter.append("trainTime,trainRssi,testTime");
		for (int i = 0; i < roop; i++) {
			if (i >= train.size()) {
				fileWriter.append("");
				fileWriter.append(",");
				fileWriter.append("");
				fileWriter.append(",");
			} else {
				fileWriter.append(String.valueOf(train.get(i).getTime()));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(train.get(i).getRssi()));
				fileWriter.append(",");
			}
			if (i >= test.size()) {
				fileWriter.append("");
			} else {
				fileWriter.append(String.valueOf(test.get(i)));
			}

			fileWriter.append("\r\n");
		}
		fileWriter.close();
	}

	private static ArrayList<Double> extractTest(Address adr_tmp, int p) {
		// TODO 自動生成されたメソッド・スタブ
		Packet first = adr_tmp.getPackets().get(0);
		ArrayList<Double> test = new ArrayList<>();
		for (Packet packet : adr_tmp.getPackets()) {
			if (packet.getTime() - first.getTime() <= p)
				test.add(packet.getTime());

		}
		return test;
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
