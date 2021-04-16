package identifyMacAddress;

import java.io.BufferedReader;
/**
 * macアドレスを示すクラス
 * B4森本研究コード
 * @author B4森本
 * @version 1.0
 *
 */
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShapeData {
	  public static void main(String[] args) {
		  //絶対パス取得（パスが分かっているなら必要ではない）
		  File sample = new File("cocoa.txt");
		  String path = sample.getAbsolutePath();
		  //パターン
		  final Pattern ptn = Pattern.compile("(systime=)(.*?)( freq.*? addr=)(.*?)( delta_t=)([[1-9]|¥.]*?)( ms rssi=)(-?[0-9]+)");
		  //入力される文字列をパターンにする(今回は手動で書き換える)
		  final Pattern filter = Pattern.compile("6f fd");
		  //アドレスの形
		  final Pattern ad = Pattern.compile("(.. .. )(..)( )(..)( )(..)( )(..)( )(..)( )(..)");
		  //出力先
		  File oFile = new File("output.csv");
		  FileWriter fw = null;
		  //読み込みと書き込みを分離する配列
		  ArrayList<String> data = new ArrayList<>();
		  String[] pkt = {"","",""};
		    try {
		    	//テキスト読み込み準備
		    	BufferedReader buffReader =
		          new BufferedReader(
		              new FileReader(path));
		    	//書き込み準備
		    	fw = new FileWriter(oFile);
		    	fw.write("AdvA,time,rssi"+System.getProperty("line.separator"));
		      //必要な値を格納する変数
		      String s;
		      //systimeから始まる行と次の1行を取るためのフラグ
		      boolean flag;
		      //time,addr,rssi 時間はdelta_tから取らないと小数点がない
		      String rssi = "";
		      String address = "";
		      //Double time = 0.0; //ミリ秒
		      int time = 0;
		      int ftime = 0;
		      boolean first = true;
		      Matcher m;
		      //1行ずつ読み込む
		      while ((s = buffReader.readLine()) != null) {
		    	  flag = false;
		    	  //systimeで始まる行
		    	  m = ptn.matcher(s);
		    	  if(m.find()){
	    			  if(first){
	    				  first = false;
	    				  ftime = Integer.parseInt(m.group(2));
	    			  }
		    		  //delta_tを足していく
	    			  time = Integer.parseInt(m.group(2)) - ftime;
		    		  //time += Double.parseDouble(m.group(4));
		    		  //データ取得
		    		  rssi = m.group(8);
		    		  //2行目は１６進数の行
		    		  s = buffReader.readLine();
		    		  flag = filter.matcher(s).find();
		    		  if(flag){
		    			  m = ad.matcher(s);
		    			  m.find();
		    			  address = m.group(12)+":"+m.group(10)+":"+m.group(8)+":"+m.group(6)+":"+m.group(4)+":"+m.group(2);
		    		/*  }
		    		  //3行目はAdvertising
		    		  do{
		    			  m = ad.matcher(buffReader.readLine());
		    		  }while(!m.find());
		    		  address = m.group(2);
		    		  if(flag){
		    		*/
		    			  System.out.println("time:" + Math.round(time) + "ms, rssi:" + rssi + ", AdvAd:" + address);
		    			  fw.write(address+","+Math.round(time)+","+rssi+System.getProperty("line.separator"));
		    		  }
		    	  }
		      }
		      buffReader.close();
		      fw.close();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
}
