package dataAnalyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * identyfiMacAddressの出力結果を読み込むクラス
 * @author akiyama
 *
 */
public class Read {
	/**
	 * 読み込むファイル名
	 */
	private String fileName;
	/**
	 * データ１行目(日付データ)
	 */
	private String firstLine;
	/**
	 * 読み込んだデータのリスト
	 */
	private ArrayList<Data> datas;

	/**
	 * flieNameを引数から代入し、datasをnewする
	 * @param fileName
	 */
	public Read(String fileName) {
		this.fileName = fileName;
		datas = new ArrayList<>();
	}

	/**
	 * 読み込むメソッド
	 * @throws IOException
	 */
	public void read() throws IOException {
	   File file = new File(fileName);
       FileReader fileReader =  new FileReader(file);
       BufferedReader in = new BufferedReader(fileReader);
       String str = in.readLine();
       //１行目を記録
       firstLine = str;


       while(str != null) {
    	  str = in.readLine();
    	  //インスタンス生成とともに２行目を記録
    	  Data data = new Data(str);
    	  str = in.readLine();
    	  //１行目がくるまで記録
    	   while(str !=null&&!str.equals(firstLine)) {
    		   data.addLine(str);
    		   str = in.readLine();
    	   }
    	   //記録し終わったらaddする
    	   datas.add(data);
       }
       /*
        *デバック用
       for(Data data:datas) {
    	   System.out.println(data.getSecondLine());
    	   for(String line :data.getData()) {
    		   System.out.println(line);
    	   }
       }*/
       in.close();

	}

	/**
	 * datasのゲッター
	 * @return datas
	 */
	public ArrayList<Data> getDatas() {
		return datas;
	}

}
