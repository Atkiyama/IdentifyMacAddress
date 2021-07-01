package makeCDF.cdf;

import java.util.ArrayList;
import java.util.Objects;

import makeCDF.node.Address;
import makeCDF.node.BTMachine;
import makeCDF.node.Packet;

public class PacketTime extends Make{

	public PacketTime(ArrayList<BTMachine> btMachines) {
		super(btMachines);
		// TODO 自動生成されたコンストラクター・スタブ
	}

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
						data.add(format(packet.getTime(),base.getTime()));
						base = packet;
					}
				}
			}
		}

	}

}
