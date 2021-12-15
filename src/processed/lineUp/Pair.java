package processed.lineUp;

import processed.extract.node.Address;

public class Pair {
	public Address frontAddress;
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
