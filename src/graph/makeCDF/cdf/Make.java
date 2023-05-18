package graph.makeCDF.cdf;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

import graph.makeCDF.node.BTMachine;

/**
 * 単体データからCDFを作成するクラス
 * @author akiyama
 *
 */
public abstract class Make {
	/**
	 * 機器毎のリスト
	 */
	ArrayList<BTMachine> btMachines;
	/**
	 * データのリスト
	 */
	ArrayList<Double> data;
	/**
	 * 引数で初期化する
	 * @param btMachines 機器のリスト
	 */
	public Make(ArrayList<BTMachine> btMachines) {
		this.btMachines = btMachines;
		data = new ArrayList<>();
	}

	/**
	 * メイン関数
	 * @param args
	 * @throws IOException
	 */


	/**
	 * ソートするメソッド
	 */
	public void sort() {
		// TODO 自動生成されたメソッド・スタブ
		Collections.sort(data);
	}



	/**
	 * データを出力するメソッド
	 */
	public void printData() {
		// TODO 自動生成されたメソッド・スタブ
		BigDecimal i = BigDecimal.ONE;
		BigDecimal size = BigDecimal.valueOf(data.size());
		for(Double t:data) {
			System.out.println(t+","+i.divide(size,20,RoundingMode.HALF_UP).toPlainString());
			i=i.add(BigDecimal.ONE);
		}
	}

	public ArrayList<Double> getData() {
		return data;
	}

	public void setData(ArrayList<Double> data) {
		this.data = data;
	}

	/**
	 * データからdataを読み込むメソッド
	 */
	public abstract void makeData();

	/**
	 * データをフォーマットするメソッド
	 * @param d1 フォーマットするデータ1
	 * @param d2 フォーマットするデータ2
	 * @return d1-d2を小数点第二位まで丸め込んだもの
	 */
	protected Double format(double d1,double d2) {
		// TODO 自動生成されたメソッド・スタブ
		BigDecimal bd1 = BigDecimal.valueOf(d1);
		BigDecimal bd2 = BigDecimal.valueOf(d2);
		BigDecimal sub = bd1.subtract(bd2);
		return sub.setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public  void setAddressList() {
		for(BTMachine btMachine:btMachines) {
			btMachine.setAddressList();
		}
	}
}
