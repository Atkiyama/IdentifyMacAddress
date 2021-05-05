package evaluation.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadData {
	private int R;
	private int T;



	public ReadData(int r, int t) {
		R = r;
		T = t;
	}


	public ArrayList<String> read() throws IOException{
		File file = new File("data/result/multi/"+R+","+T+".txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);;
		String str = in.readLine();
		ArrayList<String> data = new ArrayList<>();
		while(str != null) {
			//配列で取得してリストにする
			data.add(str);
			str = in.readLine();
		}
		in.close();
		return data;

	}
}
