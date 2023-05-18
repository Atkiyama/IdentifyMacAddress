package dataAnalyze;

import java.io.IOException;
import java.util.ArrayList;

import dataAnalyze.io.Read;
import dataAnalyze.io.ReadTXT;
import dataAnalyze.node.Address;
import dataAnalyze.node.Packet;
/**
 * キャプチャデータを抽出するクラス
 * アドレス間の秒数のみで一旦抽出する
 * @author akiyama
 *
 */
public class DataAnalyze {
	/**
	 * パケットのリスト
	 */
	public ArrayList<Packet> packets;
	public DataAnalyze(ArrayList<Packet> packets) {
		this.packets = packets;
		addressList = new ArrayList<>();
		history = new ArrayList<>();
	}

	/**
	 * アドレスのリスト
	 */
	public ArrayList<Address> addressList;

	/**
	 * アドレス変更の履歴
	 */
	public ArrayList<Address> history;


	/**
	 * アドレスリストを生成するメソッド
	 */
	public void makeAddressList() {
		//既知のアドレスかどうかのフラグ
		boolean ad_known = false;
		for (Packet packet : packets) {
			for (Address address : addressList) {
				if (address.getAdvA().equals(packet.getAddress())) {
					address.setLtime(packet.getTime());
					address.addRssi(packet.getRssi());
					address.incrementNumPkt();
					ad_known = true;
					address.addPacket(packet);
				}

			}
			if (!ad_known) {
				addressList
						.add(new Address(packet.getAddress(), packet.getTime(), packet.getTime(), packet.getRssi(), 1));
			}
			ad_known = false;
		}

	}

	/**
	 * パケット間の最大間隔だけで擬似的に同定するクラス
	 * @param time パケット間の最大間隔
	 */
	public void identify(int time){
		for (Address adr_base : addressList) {
			for (Address adr_tmp : addressList) {
				//同一機器のものとみなしたらnextAdrにaddする
				if (!adr_base.getAdvA().equals(adr_tmp.getAdvA()) &&adr_base.getLtime() <= adr_tmp.getFtime() && adr_tmp.getFtime() - adr_base.getLtime() <= time)
					adr_base.addNextAddr(adr_tmp);
			}
		}

	}

	/**
	 * 最初のアドレスを抽出するメソッド
	 * @param lowwer 下限
	 * @param upper 上限
	 */
	public void extract(int lowwer,int upper) {
		ArrayList<Address> extract = new ArrayList<>();
		for(Address address:addressList) {
			if(lowwer < address.getFtime() && address.getFtime() < upper)
				extract.add(address);
		}
		addressList = extract;

	}

	/**
	 * @param args 0に読み込むファイル名,1,2に最初のデータ範囲(秒),3にデータ範囲(T),4にカットするアドレス下限
	 * @throws IOException
	 *
	 */
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Read read = new ReadTXT(args[0]);
		DataAnalyze analyze = new DataAnalyze(read.read());
		//System.out.println("done");
		analyze.makeAddressList();
		//System.out.println("done");
		//analyze.removeFewAddress(Integer.parseInt(args[4]));
		analyze.identify(Integer.parseInt(args[3]));
		//System.out.println("done");
		analyze.extract(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		//System.out.println("done");
		analyze.print();


	}
	 private void removeFewAddress(int i) {
		// TODO 自動生成されたメソッド・スタブ
		 ArrayList<Address> toRemove = new ArrayList<>();
		 for(Address address:addressList) {
			 if(address.getNumPkt() <i)
				 toRemove.add(address);

		 }
		 for(Address address:toRemove)
			 addressList.remove(address);

	}

	/**
	  * データを出力するクラス
	  */
	void print() {
		// TODO 自動生成されたメソッド・スタブ
		for(Address first:addressList) {
			first.printDataForAnalyze();
			//printNext(first);
		}
		if(addressList.isEmpty()) {
			System.out.print("No result");
		}
	}

	/**
	 * 再起処理によりデータを出力するクラス
	 * @param address データ出力をしたいアドレス
	 */
	public void printNext(Address address) {
		if(address.getNextAdr().size() == 0) {
			for(Address his:history)
				his.printDataForAnalyze();
			address.printDataForAnalyze();
			System.out.println();

		}else {
			history.add(address);
			for(Address next:address.getNextAdr()) {
				printNext(next);
			}
			history.remove(address);
		}
	}

}
