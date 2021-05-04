package identifyMacAddress.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import identifyMacAddress.node.Packet;

public class ReadData {
	public ArrayList<Packet> read() throws IOException{
		File file = new File("data/result/multi/multiData.csv");
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
		String str =in.readLine();
		ArrayList<String[]> data=new ArrayList<>();
		while(str != null) {
			data.add(str.split(","));
			str = in.readLine();
		}

		in.close();
		ArrayList<Packet> packets =new ArrayList<>();
		for(String[] line:data) {
			packets.add(new Packet(line[0],Double.parseDouble(line[1]),Integer.parseInt(line[2])));
		}
		return packets;

	}
}
