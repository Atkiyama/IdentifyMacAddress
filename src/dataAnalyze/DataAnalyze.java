package dataAnalyze;

import java.io.IOException;
import java.util.ArrayList;

import identifyMacAddress.node.Address;
import identifyMacAddress.node.Packet;
import identifyMacAddress.read.Read;
import identifyMacAddress.read.ReadTXT;
/**
 * キャプチャデータを抽出するクラス
 * @author akiyama
 *
 */
public class DataAnalyze {
	public ArrayList<Packet> packets;
	public DataAnalyze(ArrayList<Packet> packets) {
		this.packets = packets;
		addressList = new ArrayList<>();
		history = new ArrayList<>();
	}

	public ArrayList<Address> addressList;

	public ArrayList<Address> history;


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

	public void identify(int time){
		for (Address adr_base : addressList) {
			for (Address adr_tmp : addressList) {
				//同一機器のものとみなしたらnextAdrにaddする
				if (!adr_base.getAdvA().equals(adr_tmp.getAdvA()) &&adr_base.getLtime() <= adr_tmp.getFtime() && adr_tmp.getFtime() - adr_base.getLtime() <= time)
					adr_base.addNextAddr(adr_tmp);
			}
		}

	}
	public void extract(int lowwer,int upper) {
		ArrayList<Address> extract = new ArrayList<>();
		for(Address address:addressList) {
			if(lowwer < address.getFtime() && address.getFtime() < upper)
				extract.add(address);
		}
		addressList = extract;

	}

	/**
	 * @param args 0に読み込むファイル名,1,2に最初のデータ範囲(秒),3にデータ範囲(T)
	 * @throws IOException
	 *
	 */
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Read read = new ReadTXT(args[0]);
		DataAnalyze analyze = new DataAnalyze(read.read());
		analyze.makeAddressList();
		analyze.identify(Integer.parseInt(args[3]));
		analyze.extract(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		analyze.print();


	}
	private void print() {
		// TODO 自動生成されたメソッド・スタブ
		for(Address first:addressList) {
			printNext(first);
		}
	}

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
