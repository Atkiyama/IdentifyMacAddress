package processed.timeAdjustment;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import processed.ReadTXT;
import processed.extract.node.Packet;

public class ConvertCSV {

	public static double parseTime(String str) {
		Pattern pTime = Pattern.compile("([0-9]{2}):([0-9]{2}):([0-9]{2})");
		Matcher mTime = pTime.matcher(str);
		mTime.find();
		double hour = Double.parseDouble(mTime.group(1));
		double minute = Double.parseDouble(mTime.group(2));
		double second = Double.parseDouble(mTime.group(3));
		return hour * 3600 + minute * 60 + second;
	}

	public static void main(String args[]) throws IOException {
		ArrayList<Packet> packets = ReadTXT.read(args[0]);
		ArrayList<Packet> toGetFTime = ReadTXT.read(args[0].replace("txt/", "original/"));

		double fTime = toGetFTime.get(0).getTime();

		FileWriter fileWriter = new FileWriter(
				args[0].replace("txt/", "csv/").replace(".txt", ".csv"));
		
		fileWriter.append("address,time,rssi\n");
		for (Packet packet : packets) {
			
				fileWriter
						.append(packet.getAddress() + "," + (packet.getTime() - fTime) + "," + packet.getRssi() + "\n");
			
		}
		fileWriter.close();

	}
}
