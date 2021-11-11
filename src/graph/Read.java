package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Read {
	public static ArrayList<String[]> read(String fileName) throws IOException{
		if(!fileName.contains("."))
			return null;
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);;
		String str = in.readLine();
		ArrayList<String[]> data = new ArrayList<>();
		while(str != null) {
			data.add(str.split(","));
			str = in.readLine();
		}
		in.close();
		return data;

	}

}
