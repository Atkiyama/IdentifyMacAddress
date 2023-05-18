package processed.lineUp;

import processed.extract.node.Address;

/**
 * アドレス変化前、変化後のペアを示すクラス
 * @author akiyama
 */
public class Pair {
	/**
	 * 変化前のアドレス
	 */
	public Address frontAddress;
	/**
	 * 変化後のアドレス
	 */
	public Address backAddress;
	public Pair(Address frontAddress, Address backAddress) {
		this.frontAddress = frontAddress;
		this.backAddress = backAddress;
	}
	public Address getFrontAddress() {
		return frontAddress;
	}
	public Address getBackAddress() {
		return backAddress;
	}

}
