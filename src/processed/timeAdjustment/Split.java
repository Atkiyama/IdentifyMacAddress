package processed.timeAdjustment;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import processed.ReadCSV;
import processed.extract.node.Packet;

public class Split {

	public static double parseTime(String str) {
		Pattern pTime = Pattern.compile("([0-9]{2}):([0-9]{2}):([0-9]{2})");
		Matcher mTime = pTime.matcher(str);
		mTime.find();
		double hour = Double.parseDouble(mTime.group(1));
		double minute = Double.parseDouble(mTime.group(2));
		double second = Double.parseDouble(mTime.group(3));
		// System.out.println(hour+":"+minute+":"+second);
		return hour * 3600 + minute * 60 + second;
	}

	public static void main(String args[]) throws IOException {
		ArrayList<String[]> read = ReadCSV.read(args[0]);
		ArrayList<Packet> packets = new ArrayList<>();
		read.remove(0);
		for(String[] packet:read) {
			packets.add(new Packet(packet[0],Double.parseDouble(packet[1]),Integer.parseInt(packet[2])));
		}
		
		ArrayList<String[]> splitTimeTable = ReadCSV.read(args[0].replace("csv/", "split/splitTimeTable/"));
		
		for (int i = 0; i < splitTimeTable.size(); i++) {
			String path = args[0].replace("csv/", "masterPiece/").replace(".csv", "_" + (i + 1) + ".csv");
			FileWriter fileWriter = new FileWriter(path);
			//System.out.println(path);
			double fTime = parseTime(splitTimeTable.get(i)[0]);
			double lTime = parseTime(splitTimeTable.get(i)[1]);
			fileWriter.append("address,time,rssi\n");
			// System.out.println("fTime="+fTime);
			// System.out.println("lTime="+lTime);
			for (Packet packet : packets) {
				// System.out.println("time="+packet.getTime());
				if (fTime <= packet.getTime() && packet.getTime() <= lTime) {
					 //System.out.println(" *");
					fileWriter.append(
							packet.getAddress() + "," + (packet.getTime() - fTime) + "," + packet.getRssi() + "\n");
				} else {
					// System.out.println("");
				}

			}
			fileWriter.close();
		}
		// System.out.println("");
	}

}
