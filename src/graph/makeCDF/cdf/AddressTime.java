package graph.makeCDF.cdf;



import java.util.ArrayList;
import java.util.Objects;

import graph.makeCDF.node.Address;
import graph.makeCDF.node.BTMachine;

public class AddressTime extends Make{

	/**
	 * 親クラスのフィールドを初期化する
	 * @param btMachines 機器のリスト
	 */
	public AddressTime(ArrayList<BTMachine> btMachines) {
		super(btMachines);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * アドレス間の時間の差分データを作成するメソッド
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
