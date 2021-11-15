package identifyMacAddress.identify;

import java.io.IOException;
import java.util.ArrayList;

import dataAnalyze.io.Read;
import dataAnalyze.io.ReadCSV;
import dataAnalyze.io.ReadTXT;
import dataAnalyze.node.Address;
import dataAnalyze.node.Packet;

/**
 * 旧同定用プログラム
 * @author akiyama
 *
 */
public class IdentifyStay extends Identify {

	public IdentifyStay(ArrayList<Packet> packets,int R,double T) {
		super(packets,R,T);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * 平均RSSIの闘値
	 */





	/**
	 * リストの中からパケット数が闘値N(デフォルトは100)以下のものを削除するメソッド
	 * (作ったものの結局使用してない)
	 */
	public void removeFewAddress() {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<Address> removeList = new ArrayList<>();
		for (Address address : addressList) {
			if (address.getNumPkt() <= N) {
				removeList.add(address);
			}
		}
		for (Address address : removeList) {
			addressList.remove(address);
		}

	}

	/**
	 * 配列のリストからアドレスのリストを作成するメソッド
	 *
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
	 * パケットの時間差が闘値Tに収まっているか判定するメソッド
	 * @param adr_base 比較するアドレス1
	 * @param adr_tmp 比較するアドレス2
	 * @return 条件を満たしていればtrueを返す
	 */


	/**
	 * パケットのRSSI差が闘値Rに収まっているか判定するメソッド
	 * @param adr_base 比較するアドレス1
	 * @param adr_tmp 比較するアドレス2
	 * @return 条件を満たしていればtrueを返す
	 */
	public boolean checkR(Address adr_base, Address adr_tmp) {
		if (Math.abs(adr_base.getAverageRssi() - adr_tmp.getAverageRssi()) <= R)
			return true;
		else
			return false;
	}

	/**
	 * アドレスリストのゲッター
	 * @return アドレスリスト
	 */
	public ArrayList<Address> getAddressList() {
		return addressList;
	}


	/**
	 * メインメソッド
	 * @param args 0に読み込むキャプチャファイル名(絶対or相対パス),１にR,2にTを入れる(別途スクリプトで操作)
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		Read read;
		if(args[0].contains("txt"))
			read = new ReadTXT(args[0]);
		else if(args[0].contains("csv"))
			read = new ReadCSV(args[0]);
		else {
			System.out.println("このプログラムはcsvファイルかtxtファイルのみ読み込めます。ファイルの拡張子を確認してください");
			read = null;
			System.exit(0);
		}
		Identify identify = new IdentifyStay(read.read(),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		identify.makeAddressList();
		//identify.removeFewAddress();
		((IdentifyStay) identify).identify();
		//identify.checkData();
	}
}
