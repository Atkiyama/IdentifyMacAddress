package dataAnalyze.old.analyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import dataAnalyze.old.node.Data;
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
	 * 読み込んだデータのリスト
	 */
	private ArrayList<Data> datas;

	/**
	 * flieNameを引数から代入し、datasをnewする
	 * @param fileName 読み込むファイル名
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
       Data data = null;
       while(str != null) {
    	  //インスタンス生成とともに1行目を記録

    	   data = new Data(str);
    	  str = in.readLine();
    	  //１行目がくるまで記録
    	   while(str !=null&&!str.contains("R=")&&!str.contains("T=")) {
    		   data.addLine(str);
    		   str = in.readLine();
    	   }
    	   //記録し終わったらaddする
    	   datas.add(data);
       }


       System.out.println("テスト");
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
