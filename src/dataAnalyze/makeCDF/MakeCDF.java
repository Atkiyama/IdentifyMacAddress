package dataAnalyze.makeCDF;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import dataAnalyze.makeCDF.io.ReadAnswer;
import dataAnalyze.makeCDF.io.ReadData;
import dataAnalyze.makeCDF.node.Address;
import dataAnalyze.makeCDF.node.BTMachine;

/**
 * 単体データからCDFを作成するクラス
 * @author akiyama
 *
 */
public class MakeCDF {
	/**
	 * 機器毎のリスト
	 */
	ArrayList<BTMachine> btMachines;
	/**
	 * CDFのx軸(時間)のリスト
	 */
	ArrayList<Double> deltaT;
	/**
	 * 引数で初期化する
	 * @param btMachines 機器のリスト
	 */
	public MakeCDF(ArrayList<BTMachine> btMachines) {
		this.btMachines = btMachines;
		deltaT = new ArrayList<>();
	}

	/**
	 * メイン関数
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ReadAnswer readAnswer = new ReadAnswer();
		ReadData readData = new ReadData(readAnswer.read());
		readData.readData();
		MakeCDF makeCDF = new MakeCDF(readData.getBtMachines());
		makeCDF.readDeltaT();
		makeCDF.sort();
		makeCDF.printDeltaT();
	}

	/**
	 * ソートするメソッド
	 */
	private void sort() {
		// TODO 自動生成されたメソッド・スタブ
		Collections.sort(deltaT);
	}

	/**
	 * データを出力するメソッド
	 */
	private void printDeltaT() {
		// TODO 自動生成されたメソッド・スタブ

		for(Double t:deltaT) {
			System.out.println(t);
		}

	}

	/**
	 * データからdeltaTを読み込むメソッド
	 */
	private void readDeltaT() {
		// TODO 自動生成されたメソッド・スタブ
		for(BTMachine btMachine:btMachines) {
			btMachine.setAddressList();

		}
		for(BTMachine btMachine:btMachines) {
			Address base = null;
			for(Address address:btMachine.getAddressList()) {
				address.setTimes();
				if(Objects.isNull(base)) {
					base = address;
				}else{
					deltaT.add(format(address.getFtime(),base.getLtime()));
					base = address;
				}
			}
		}
	}

	/**
	 * データをフォーマットするメソッド
	 * @param d1 フォーマットするデータ1
	 * @param d2 フォーマットするデータ2
	 * @return d1-d2を小数点第二位まで丸め込んだもの
	 */
	private Double format(double d1,double d2) {
		// TODO 自動生成されたメソッド・スタブ
		BigDecimal bd1 = BigDecimal.valueOf(d1);
		BigDecimal bd2 = BigDecimal.valueOf(d2);
		BigDecimal sub = bd1.subtract(bd2);
		return sub.setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
}
