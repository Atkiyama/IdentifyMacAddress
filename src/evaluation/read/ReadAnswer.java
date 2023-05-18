package evaluation.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import evaluation.CaptureFile;
/**
 * 各ファイルの正解macアドレスを読み出すクラス
 * @author akiyama
 *
 */
public class ReadAnswer {
	/**
	 * 正解とされるmacアドレスを抽出する
	 * @return 機器(キャプチャファイル)のリスト
	 * @throws IOException
	 */
	
	/**
	 * 整列されたアドレスリストを回答とする場合のメソッド
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<CaptureFile> readLineUp(String fileName) throws IOException {
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);;
		in.readLine();
		//ヘッダを読み飛ばす
		String str = in.readLine();
		ArrayList<CaptureFile> captures = new ArrayList<>();
		ArrayList<String> addressList = new ArrayList<>();
		while(str != null) {
			//配列で取得してリストにする
			String[] line = str.split(",");
			addressList.add(line[1]);
			str = in.readLine();
		}
		in.close();
		for(int i=0;i<addressList.size();i+=2) {
			String[] pair = {String.valueOf(i),addressList.get(i),addressList.get(i+1)};
			captures.add(new CaptureFile(pair));		
		}
		/*
		for(CaptureFile capture:captures) {
			System.out.println(capture.getAddress());
		}*/
		return captures;
	}
	
	/**
	 * キャプチャファイルにおけるアドレスの変化を記録したmoveresult.csvを回答とする場合のメソッド
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<CaptureFile> read(String fileName) throws IOException {
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);;
		String str = in.readLine();
		ArrayList<CaptureFile> captures = new ArrayList<>();
		while(str != null) {
			//配列で取得してリストにする
			captures.add(new CaptureFile(str.split(",")));
			str = in.readLine();
		}
		in.close();
		return captures;
	}
	
	public static ArrayList<CaptureFile> read() throws IOException{
		return read("data/result/moveResult.csv");

	}
	public ArrayList<String[]> readOld(String fileName) throws IOException {
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);;
		String str = in.readLine();
		ArrayList<String[]> data = new ArrayList<>();
		while(str != null) {
			//配列で取得してリストにする
			data.add(str.split(","));
			str = in.readLine();
		}
		in.close();
		return data;
	}
	public ArrayList<String[]> readOld() throws IOException{
		return readOld("data/result/moveResult.csv");

	}
}
