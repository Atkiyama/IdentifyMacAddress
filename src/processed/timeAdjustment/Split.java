package processed.timeAdjustment;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import processed.ReadCSV;
import processed.ReadTXT;
import processed.extract.node.Packet;

public class Split {
	
	public static double parseTime(String str) {
		Pattern pTime = Pattern.compile("([0-9]{2}):([0-9]{2}):([0-9]{2})");
		Matcher mTime =pTime.matcher(str);
		mTime.find();
		double hour = Double.parseDouble(mTime.group(1));
		double minute = Double.parseDouble(mTime.group(2));
		double second = Double.parseDouble(mTime.group(3));
		//System.out.println(hour+":"+minute+":"+second);
		return hour*3600 + minute*60 +second;
	}
	public static void main(String args[]) throws IOException {
		ArrayList<Packet> packets = ReadTXT.read(args[0]);
		ArrayList<String[]> splitTimeTable = ReadCSV.read(args[0].replace("txt/", "split/splitTimeTable/").replace(".txt",".csv"));
		for(int i=0;i<splitTimeTable.size();i++) {
			FileWriter fileWriter = new FileWriter(args[0].replace("txt/", "csv/").replace(".txt","_"+(i+1)+"_.csv"));
			double fTime = parseTime(splitTimeTable.get(i)[0]);
			double lTime = parseTime(splitTimeTable.get(i)[1]);
			fileWriter.append("address,time,rssi\n");
//			System.out.println("fTime="+fTime);
//			System.out.println("lTime="+lTime);
			for(Packet packet:packets) {
				System.out.println("time="+packet.getTime());
				if(fTime<=packet.getTime()&&packet.getTime()<=lTime) {
					//System.out.println(" *");
					fileWriter.append(packet.getAddress()+","+(packet.getTime()-fTime)+","+packet.getRssi()+"\n");
				}else {
					//System.out.println("");
				}
					
			}
			fileWriter.close();
		}
		//System.out.println("");
	}
	
	
	

}
