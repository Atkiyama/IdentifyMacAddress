package identifyMacAddress.identify;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import dataAnalyze.node.Address;
import dataAnalyze.node.Packet;

/**
 * 平均RSSIとパケット受診時刻を元にmacアドレスの特定を行うための
 * @author akiyama
 *
 */
public abstract class Identify {

	/**
	 * macアドレスのリスト
	 */
	protected ArrayList<Address> addressList;
	/**
	 * パケットのリスト(これからアドレスリストを作成)
	 */
	protected ArrayList<Packet> packets;
	/**
	 * N以下のパケット数のアドレスを省くための定数
	 */
	protected final int N = 0;
	/**
	 * 受診時刻の闘値
	 */
	protected final double T;

	protected final int R;

	public Identify(ArrayList<Packet> packets,int R,double T) {
		this.packets = packets;
		addressList = new ArrayList<>();
		this.R = R;
		this.T = T;

	}

	/**
	 * 特定を行うメソッド
	 * @param T 受診時刻の闘値
	 * @param R 平均RSSIの闘値
	 * @throws IOException
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

	public void identify() throws IOException, InterruptedException {
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


	protected abstract boolean checkR(Address adr_base, Address adr_tmp) throws IOException, InterruptedException;

	/**
	 * nextAddrが複数ある場合にそれを一つに絞るメソッド
	 * RとTを正規化して二次元の座標に起こした上でその距離が近い方を採用する
	 * 丸め込み処理による例外のエラー(以下)が出るが気にしないこと
	 * (java.lang.ArithmeticException: Non-terminating decimal expansion; no exact representable decimal result)
	 * @param address nextAdrが複数あるアドレス
	 */
	protected void identify(Address address) {
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
	 * パケットの時間差が闘値Tに収まっているか判定するメソッド
	 * @param adr_base 比較するアドレス1
	 * @param adr_tmp 比較するアドレス2
	 * @return 条件を満たしていればtrueを返す
	 */
	public boolean checkT(Address adr_base, Address adr_tmp) {
		if (adr_base.getLtime() <= adr_tmp.getFtime() && adr_tmp.getFtime() - adr_base.getLtime() <= T) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * アドレスリストのゲッター
	 * @return アドレスリスト
	 */
	public ArrayList<Address> getAddressList() {
		return addressList;
	}

	/**
	 * データが正しく取れているかチェックするメソッド
	 * データの最終時刻が3999(59分55秒)以前の場合と受診間隔が10秒以上空いている場合に警告文を出力する
	 * 単体データのときはこのチェックをかけること
	 */
	public void checkData() {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<String> exeptionList = new ArrayList<>();
		if (packets.get(packets.size() - 1).getTime() <= 3595) {
			exeptionList.add("最後にとったパケットの時間が" + packets.get(packets.size() - 1).getTime() + "です");
		}
		double tmp = 0;
		for (Packet packet : packets) {
			if (packet.getTime() - tmp >= 10) {
				String timeDiff = String.valueOf(packet.getTime() - tmp);
				exeptionList.add("パケットの受診間隔が" + timeDiff + "秒空いています in" + packet.getTime());
			}
			tmp = packet.getTime();
		}
		if (exeptionList.size() > 0) {
			System.out.println("以下の理由から正しくパケットをキャプチャできていない可能性があります");
			for (String error : exeptionList) {
				System.out.println(error);
			}
		}

	}

}
