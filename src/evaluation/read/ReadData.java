package evaluation.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 結果を読み込むクラス
 * @author akiyama
 *
 */
public class ReadData {
	/**
	 * 闘値R
	 */
	private int R;
	/**
	 * 闘値T
	 */
	private int T;
	/**
	 * 読み込むファイル名
	 */
	private String fileName;


/**
 * 結合データ単体用
 * @param r
 * @param t
 */
	public ReadData(int r, int t) {
		R = r;
		T = t;
		fileName = "data/result/multi/"+R+","+T+".txt";
	}
	/**
	 * Evaluation100用
	 * @param r
	 * @param t
	 * @param fileNumber 読み込むフォルダ(mulit以下)のナンバー
	 */
	public ReadData(int r,int t,int numOfData,int fileNumber,String isMove) {
		R = r;
		T = t;
		fileName = "data/result/multi/"+isMove+"/"+numOfData+"/"+fileNumber+"/"+R+","+T+".txt";
	}

/**
 * 読み込むメソッド
 * @return 配列のリストにした結果
 * @throws IOException
 */
	public ArrayList<String[]> read() throws IOException{
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
