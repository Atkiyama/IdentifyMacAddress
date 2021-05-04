package identifyMacAddress;

import java.io.IOException;
import java.util.ArrayList;

import identifyMacAddress.node.Address;
import identifyMacAddress.node.Packet;


public class Identify {

	/**
	 * macアドレスのリスト
	 */
	private ArrayList<Address> addressList;
	private ArrayList<Address> addressListBase;
	private ArrayList<Packet> packets;
	private final int N=100;
	private int T;
	private double R;
	public Identify(ArrayList<Packet> packets) {
		this.packets = packets;
		addressList = new ArrayList<>();
		addressListBase = new ArrayList<>();
	}



	/**
	 * 特定を行うメソッド
	 * @param originalData csvデータを配列のリストにしたもの
	 * @throws IOException
	 */
	public void identify(int T,int R) throws IOException{
		//ここ以下でアドレスを特定する
		addressList = addressListBase;
		this.T = T;
		this.R = R;
		for(Address adr_base:addressList) {
			for(Address adr_tmp:addressList) {
				//同一機器のものとみなしたらnextAdrにaddする
				if(!adr_base.getAdvA().equals(adr_tmp.getAdvA())&&checkT(adr_base,adr_tmp)&&checkR(adr_base,adr_tmp))
					adr_base.addNextAddr(adr_tmp);
			}
		}

		System.out.println("R="+R+"T="+T);
		for(Address address:addressList) {
			address.printData();
		}





	}
	/**
	 * リストの中からパケット数が闘値N(デフォルトは100)以下のものを削除するメソッド
	 */
	public void removeFewAddress() {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<Address> removeList = new ArrayList<>();
		for(Address address:addressList) {
			if(address.getNumPkt() <= N) {
				removeList.add(address);
			}
		}
		for(Address address:removeList) {
			addressList.remove(address);
		}
		addressListBase = addressList;

	}


	/**
	 * 配列のリストからアドレスのリストを作成するメソッド
	 * @param originalData csvを読み込んだ配列のリスト 0,1,2にそれぞれadva,time,rssiが入っている
	 */
	public void makeAddressList() {
		//既知のアドレスかどうかのフラグ
		boolean ad_known = false;
		addressList = new ArrayList<>();
		for(Packet packet:packets) {
			for(Address address:addressList) {
				if(address.getAdvA().equals(packet.getAddress())) {
					address.setLtime(packet.getTime());
					address.addRssi(packet.getRssi());
					address.incrementNumPkt();
					ad_known = true;
				}

			}
			if(!ad_known) {
				addressList.add(new Address(packet.getAddress(),packet.getTime(),packet.getTime(),packet.getRssi(),1));
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
	public boolean checkT(Address adr_base,Address adr_tmp) {
		if (adr_base.getLtime() <= adr_tmp.getFtime() && adr_tmp.getFtime() -adr_base.getLtime() <= T) {
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
	public boolean checkR(Address adr_base,Address adr_tmp) {
		if (Math.abs(adr_base.getAverageRssi() -adr_tmp.getAverageRssi()) <= R)
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
}
