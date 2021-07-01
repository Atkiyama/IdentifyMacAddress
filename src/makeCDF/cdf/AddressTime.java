package makeCDF.cdf;



import java.util.ArrayList;
import java.util.Objects;

import makeCDF.node.Address;
import makeCDF.node.BTMachine;

public class AddressTime extends Make{

	public AddressTime(ArrayList<BTMachine> btMachines) {
		super(btMachines);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * データからdeltaTを読み込むメソッド
	 */
	public void makeData() {
		// TODO 自動生成されたメソッド・スタブ
		for(BTMachine btMachine:btMachines) {
			Address base = null;
			for(Address address:btMachine.getAddressList()) {
				address.setTimes();
				if(Objects.isNull(base)) {
					base = address;
				}else{
					data.add(format(address.getFtime(),base.getLtime()));
					base = address;
				}
			}
		}
	}





}
