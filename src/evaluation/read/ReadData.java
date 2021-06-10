package evaluation.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadData {
	private int R;
	private int T;
	private String fileName;



	public ReadData(int r, int t) {
		R = r;
		T = t;
		fileName = "data/result/multi/"+R+","+T+".txt";
	}
	public ReadData(int r,int t,int fileNumber) {
		R = r;
		T = t;
		fileName = "data/result/multi/"+fileNumber+"/"+R+","+T+".txt";
	}


	public ArrayList<String[]> read() throws IOException{
		File file = new File("fileName");
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
