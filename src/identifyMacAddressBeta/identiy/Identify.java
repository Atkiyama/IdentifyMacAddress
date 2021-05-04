package identifyMacAddressBeta.identiy;

import java.io.IOException;
import java.util.ArrayList;
/**
 * 読み込んだパケット情報をもとにmacアドレスの特定を
 * 行うクラス
 * @author akiyama
 *
 */
public class Identify {
	/**
	 * macアドレスのリスト
	 */
	public ArrayList<Address> addressList;
	/**
	 * 平均RSSIの闘値
	 */
	public int R;
	/**
	 * 時間(s)の闘値
	 */
	public int T;
	/**
	 * パケット数の闘値
	 */
	public int N;
	/**
	 * デフォルト値をそれぞれ代入する
	 */
	public void setDefault() {
		R=5;
		T=10;
		N=100;
	}
	/**
	 * 引数を代入する
	 * @param R 平均RSSIの闘値
	 * @param T 時間(s)の闘値
	 * @param N パケット数の闘値
	 */
	public Identify(int R,int T,int N) {
		setDefault();
		this.R=R;
		this.T=T;
		this.N=N;
	}
	/**
	 * Nだけデフォルト値を採用する場合のコンストラクタ
	 * @param R 平均RSSIの闘値
	 * @param T 時間(s)の闘値
	 */
	public Identify(int R,int T) {
		setDefault();
		this.R=R;
		this.T=T;
	}
	/**
	 * 特定を行うメソッド
	 * @param originalData csvデータを配列のリストにしたもの
	 * @throws IOException
	 */
	public void identify(ArrayList<String[]> originalData) throws IOException{
		makeAddressList(originalData);
		removeFewAddress();
		//ここ以下でアドレスを特定する
		for(Address adr_base:addressList) {
			for(Address adr_tmp:addressList) {
				//同一機器のものとみなしたらnextAdrにaddする
				if(!adr_base.getAdvA().equals(adr_tmp.getAdvA())&&checkT(adr_base,adr_tmp)&&checkR(adr_base,adr_tmp))
					adr_base.addNextAddr(adr_tmp);
			}
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

	}

	/**
	 * 配列のリストからアドレスのリストを作成するメソッド
	 * @param originalData csvを読み込んだ配列のリスト 0,1,2にそれぞれadva,time,rssiが入っている
	 */
	public void makeAddressList(ArrayList<String[]> originalData) {
		//既知のアドレスかどうかのフラグ
		boolean ad_known = false;
		addressList = new ArrayList<>();
		for(String[] row:originalData) {
			for(Address address:addressList) {
				if(address.getAdvA().equals(row[0])) {
					address.setLtime(Integer.parseInt(row[1]));
					address.addRssi(Integer.parseInt(row[2]));
					address.incrementNumPkt();
					ad_known = true;
				}

			}
			if(!ad_known) {
				addressList.add(new Address(row[0],Integer.parseInt(row[1]),Integer.parseInt(row[1])
						,Integer.parseInt(row[2]),1));
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
