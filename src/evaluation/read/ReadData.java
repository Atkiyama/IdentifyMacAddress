package evaluation.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 結果を読み込むクラス
 * @author akiyama
 *
 */
public class ReadData {
	/**
	 * 読み込むファイル名
	 */
	private String fileName;
	public ReadData(int n) {
		fileName = "data/result/multi/move/"+n+".txt";
	}

	public ReadData(int R,int T,int I,int numOfTime ,int n, String method, String numOfData) {
		fileName = "data/result/multi/move/"+numOfTime+"/"+method+"/"+numOfData+"/"+R+","+T+","+I+","+n+".txt";
	}

	public ReadData(String fileName){
		this.fileName = fileName;
	}

	public ReadData(int R, int T, int numOfData, int n) {
		fileName = "data/result/multi/move/old/"+n+".txt";
	}

/**
 * 読み込むメソッド
 * @return 配列のリストにした結果
 * @throws IOException
 */
	public ArrayList<String> read() throws IOException{
		final Pattern pAddress = Pattern.compile("(..:..:..:..:..:..)");
		final Pattern pAddress2 = Pattern.compile("(..:..:..:..:..:.._2)");
		Matcher mAddress;
		Matcher mAddress2;
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);;
		String str = in.readLine();
		ArrayList<String> data = new ArrayList<>();
		while(str != null) {
			mAddress = pAddress.matcher(str);
			mAddress2 = pAddress2.matcher(str);
			if(mAddress2.find())
				data.add(mAddress2.group(1));
			else if(mAddress.find())
				data.add(mAddress.group(1));
			else
				data.add("brank");
			str = in.readLine();
		}
		in.close();
		return data;

	}
	public ArrayList<String[]> readOld() throws IOException{
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
