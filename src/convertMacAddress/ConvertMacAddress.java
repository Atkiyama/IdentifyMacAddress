package convertMacAddress;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import convertMacAddress.io.ReadAnswer;
import convertMacAddress.io.ReadData;
import convertMacAddress.io.WriteConvertData;
import convertMacAddress.node.BTMachine;
import convertMacAddress.node.Packet;
import convertMacAddress.node.PacketComparator;

/**
 * 複数macアドレスの観測結果を一つにまとめるクラス
 * @author akiyama
 *
 */

public class ConvertMacAddress {
	/**
	 * 機器(読みこむファイル)のリスト
	 */
	private ArrayList<BTMachine> btMachines;
	/**
	 * パケットリスト
	 */
	private ArrayList<Packet> packets;
	/**
	 * 引数で初期化 packetsはnewする
	 * @param btMachines 機器(キャプチャファイル)リスト
	 */
	public ConvertMacAddress(ArrayList<BTMachine> btMachines) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.btMachines = btMachines;
		packets = new ArrayList<>();
	}
	/**
	 * ランダムに遅延を発生させるメソッド
	 * 0~600(10分)の遅れを想定
	 */
	public void setDelay() {
		 Random random = new Random();
		 //リストの中からひとつだけ遅延なしのものを作る
		 int setZero = random.nextInt(btMachines.size()-1);
		 for(int i=0;i<btMachines.size();i++) {
			 if(i!=setZero)
				 btMachines.get(i).setDelay(random.nextInt(600));
		 }
	}

	/**
	 * 全パケットデータを結合させるメソッド
	 */
	public void convert() {
		for(BTMachine btMachine:btMachines) {
			for(Packet packet:btMachine.getPackets()) {
				packets.add(packet);
			}
		}
		//時間順にソート
		Collections.sort(packets, new PacketComparator());
	}

	/**
	 * パケットリストのゲッター
	 * @return パケットのリスト
	 */
	public ArrayList<Packet> getPackets() {
		return packets;
	}

	/**
	 * メインメソッド 正解データを読み込んで結合してcsvに起こす
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ReadAnswer read = new ReadAnswer();
		ReadData readData = new ReadData(read.read());
		readData.readData();
		ConvertMacAddress convert = new ConvertMacAddress(readData.getBtMachines());
		if(args.length==1) {
		convert.setDelay();
		convert.convert();
		WriteConvertData write = new WriteConvertData();
		write.write(convert.getPackets(),args[0]);
		}else {
			convert.getAverageRssi();
		}
	}
	public void getAverageRssi() {
		// TODO 自動生成されたメソッド・スタブ
		for(BTMachine btMachine:btMachines) {
			System.out.println(btMachine.getFileName()+":averageRssi ="+btMachine.getAverageRssi());
		}
	}


}
