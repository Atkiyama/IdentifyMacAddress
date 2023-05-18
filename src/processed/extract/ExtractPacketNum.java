package processed.extract;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

import processed.extract.io.ReadCSV;
import processed.extract.node.Address;
import processed.extract.node.Packet;

public class ExtractPacketNum extends Extract{
	ArrayList<Integer> fPackets;
	ArrayList<Integer> lPackets;

	public ExtractPacketNum(ArrayList<Packet> packets) {
		super(packets);
		// TODO 自動生成されたコンストラクター・スタ
		fPackets = new ArrayList<>();
		lPackets = new ArrayList<>();
	}
	
	public void extractPacketNum() {
		for(Address address:addressList) {
			fPackets.add(address.getfPackets().size());
			lPackets.add(address.getfPackets().size());
		}
		
	}

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Extract ex = new ExtractPacketNum(ReadCSV.read());
		ex.makeAddressList();
		ex.extract(Integer.parseInt(args[0]));
		((ExtractPacketNum) ex).extractPacketNum();
		((ExtractPacketNum) ex).sort();
		((ExtractPacketNum) ex).printFPackets();
		
		

	}

	private void printLPackets() {
		// TODO 自動生成されたメソッド・スタブ
		BigDecimal i = BigDecimal.ONE;
		BigDecimal size = BigDecimal.valueOf(lPackets.size());
		for(Integer t:lPackets) {
			System.out.println(t+","+i.divide(size,20,RoundingMode.HALF_UP).toPlainString());
			i=i.add(BigDecimal.ONE);
		}
		
	}
	
	private void printFPackets() {
		// TODO 自動生成されたメソッド・スタブ
		BigDecimal i = BigDecimal.ONE;
		BigDecimal size = BigDecimal.valueOf(fPackets.size());
		for(Integer t:fPackets) {
			System.out.println(t+","+i.divide(size,20,RoundingMode.HALF_UP).toPlainString());
			i=i.add(BigDecimal.ONE);
		}
		
	}

	private void sort() {
		// TODO 自動生成されたメソッド・スタブ
		Collections.sort(fPackets);
		Collections.sort(lPackets);
		
	}

}
