package processed.extract.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import processed.extract.node.Packet;

public class ReadCSV {
	public static ArrayList<Packet> read() throws IOException{
		File file = new File("data/capture/single/stay/csv/l70cm.csv");
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
		String str = in.readLine();
		double fTime = 0;
		ArrayList<Packet> packets = new ArrayList<>();
		in.readLine();
		while(str != null) {
			String[] line = str.split(",");
			if(!line[1].equals("time"))
				packets.add(new Packet(line[0],Double.parseDouble(line[1]),Integer.parseInt(line[2])));
			if(!line[1].equals("time")&&fTime ==0)
				fTime =Double.parseDouble(line[1]);
			str = in.readLine();
			
		}
		return packets;
	}

}
