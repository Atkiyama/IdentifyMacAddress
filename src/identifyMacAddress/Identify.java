package identifyMacAddress;

import java.io.IOException;
import java.util.ArrayList;

public class Identify {
	public ArrayList<Address> addressList;
	public int R;
	public int T;
	public int N;
	public Identify(int R,int T,int N) {
		this.R=R;
		this.T=T;
		this.N=N;
	}
	public void identify(ArrayList<String[]> originalData) throws IOException{
		makeAddressList(originalData);
		removeFewAddress();
		for(Address adr_base:addressList) {
			for(Address adr_tmp:addressList) {
				if(adr_base.getAdvA().equals(adr_tmp.getAdvA())&&checkT(adr_base,adr_tmp)&&checkR(adr_base,adr_tmp))
					adr_base.addNextAddr(adr_tmp);
			}
		}

	}
	public void removeFewAddress() {
		// TODO 自動生成されたメソッド・スタブ
		for(Address address:addressList) {
			if(address.getNumPkt() <= N) {
				addressList.remove(address);
			}
		}

	}
	public void makeAddressList(ArrayList<String[]> originalData) {
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
			if(ad_known)
				addressList.add(new Address(row[0],Integer.parseInt(row[1]),Integer.parseInt(row[1]),Integer.parseInt(row[2]),1));
		}
		ad_known = false;
	}
	public boolean checkT(Address adr_base,Address adr_tmp) {
		if (adr_base.getLtime() <= adr_tmp.getFtime() && adr_tmp.getFtime() -adr_base.getLtime() <= T) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ランダムmacアドレスのRSSIの差がR以内に収まっているか判別するメソッド
	 */
	public boolean checkR(Address adr_base,Address adr_tmp) {
		if (Math.abs(adr_base.getAverageRssi() -adr_tmp.getAverageRssi()) <= R)
			return true;
		else
			return false;
	}

}
