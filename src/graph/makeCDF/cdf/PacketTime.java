package graph.makeCDF.cdf;

import java.util.ArrayList;
import java.util.Objects;

import graph.makeCDF.node.Address;
import graph.makeCDF.node.BTMachine;
import graph.makeCDF.node.Packet;

/**
 * パケット間の時間差のcdfを作るためのクラス
 * @author akiyama
 *
 */
public class PacketTime extends Make{

	/**
	 * 親クラスを初期化する
	 * @param btMachines　機器のリスト
	 */
	public PacketTime(ArrayList<BTMachine> btMachines) {
		super(btMachines);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * パケット間の時間差のデータを作るメソッド
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
					}else if(base.getAddress().equals(packet.getAddress())){
						data.add(format(packet.getTime(),base.getTime()));
						base = packet;
					}
				}
				base=null;
			}
			base = null;
		}

	}

}
