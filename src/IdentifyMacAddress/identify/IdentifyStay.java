package identifyMacAddress.identify;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import identifyMacAddress.node.Address;
import identifyMacAddress.node.Packet;
import identifyMacAddress.read.Read;
import identifyMacAddress.read.ReadCSV;
import identifyMacAddress.read.ReadTXT;

public class IdentifyStay extends Identify {

	public IdentifyStay(ArrayList<Packet> packets,int R,double T) {
		super(packets,R,T);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * 平均RSSIの闘値
	 */


	/**
	 * 特定を行うメソッド
	 * @param T 受診時刻の闘値
	 * @param R 平均RSSIの闘値
	 * @throws IOException
	 */
	public void identify() throws IOException {
		//ここ以下でアドレスを特定する
		for (Address adr_base : addressList) {
			for (Address adr_tmp : addressList) {
				//同一機器のものとみなしたらnextAdrにaddする
				if (!adr_base.getAdvA().equals(adr_tmp.getAdvA()) && checkT(adr_base, adr_tmp)
						&& checkR(adr_base, adr_tmp))
					adr_base.addNextAddr(adr_tmp);
			}
		}

		/**
		 * 結果を出力
		 */
		System.out.println("R=" + R + "T=" + T);
		for (Address address : addressList) {
			if (address.getNextAdr().size() > 1)
				identify(address);
			address.printData();
		}
	}

	/**
	 * nextAddrが複数ある場合にそれを一つに絞るメソッド
	 * RとTを正規化して二次元の座標に起こした上でその距離が近い方を採用する
	 * 丸め込み処理による例外のエラー(以下)が出るが気にしないこと
	 * (java.lang.ArithmeticException: Non-terminating decimal expansion; no exact representable decimal result)
	 * @param address nextAdrが複数あるアドレス
	 */
	private void identify(Address address) {
		//ここの計算にある程度の丸め込みが必要？
		// TODO 自動生成されたメソッド・スタブ
		address.setDeltaLT(T);
		address.setDeltaR(R);
		BigDecimal length = BigDecimal.valueOf(999999999);
		Address nextAdr = new Address();
		for (Address tmpNextAdr : address.getNextAdr()) {
			tmpNextAdr.setDeltaFT(T);
			tmpNextAdr.setDeltaR(R);
			tmpNextAdr.setTmpLength((address.getDeltaLT().subtract(tmpNextAdr.getDeltaFT())).pow(2)
					.add((address.getDeltaR().subtract(tmpNextAdr.getDeltaR())).pow(2))
					.setScale(3, RoundingMode.HALF_UP));
			//			System.out.println("address: deltaT="+address.getDeltaLT()+",deltaR="+address.getDeltaR());
			//			System.out.println("nextAdr: deltaT="+tmpNextAdr.getDeltaFT()+",deltaR="+tmpNextAdr.getDeltaR()+"tmpLength="+tmpNextAdr.getTmpLength());
			if (tmpNextAdr.getTmpLength().compareTo(length) < 0) {
				nextAdr = tmpNextAdr;
				length = tmpNextAdr.getTmpLength();
			}
		}
		ArrayList<Address> na = new ArrayList<>();
		na.add(nextAdr);
		address.setNextAdr(na);

	}

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
	 */
	public static void main(String[] args) throws IOException {
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
