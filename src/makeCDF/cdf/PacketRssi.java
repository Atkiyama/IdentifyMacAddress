package makeCDF.cdf;

import java.util.ArrayList;
import java.util.Objects;

import makeCDF.node.Address;
import makeCDF.node.BTMachine;
import makeCDF.node.Packet;
/**
 * パケット間のRssiのcdfを書くためのクラス
 * @author akiyama
 *
 */
public class PacketRssi extends Make{

	/**
	 * 親クラスのフィールドを初期化する
	 * @param btMachines　機器のリスト
	 */
	public PacketRssi(ArrayList<BTMachine> btMachines) {
		super(btMachines);
		// TODO 自動生成されたコンストラクター・スタブ
	}
/**
 * rssiの差分のデータを生成するメソッド
 */
	@Override
	public void makeData() {
		// TODO 自動生成されたメソッド・スタブ
		Packet base;
		for(BTMachine btMachine:btMachines) {
			for(Address address:btMachine.getAddressList()) {
				base = null;
				for(Packet packet:address.getPackets()) {
					if(Objects.isNull(base)) {
						base = packet;
					}else{
						data.add(Math.abs(format(packet.getRssi(),base.getRssi())));
						base = packet;
					}
				}
			}
		}

	}
}
