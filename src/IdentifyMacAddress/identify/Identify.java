package identifyMacAddress.identify;

import java.io.IOException;
import java.util.ArrayList;

import identifyMacAddress.node.Address;
import identifyMacAddress.node.Packet;

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
	protected double T;

	public Identify(ArrayList<Packet> packets) {
		this.packets = packets;
		addressList = new ArrayList<>();

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
				}

			}
			if (!ad_known) {
				addressList
						.add(new Address(packet.getAddress(), packet.getTime(), packet.getTime(), packet.getRssi(), 1));
			}
			ad_known = false;
		}

	}

	public abstract void identify(int R,int T) throws IOException;

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
	 * パケットのRSSI差が闘値Rに収まっているか判定するメソッド
	 * @param adr_base 比較するアドレス1
	 * @param adr_tmp 比較するアドレス2
	 * @return 条件を満たしていればtrueを返す
	 */

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
