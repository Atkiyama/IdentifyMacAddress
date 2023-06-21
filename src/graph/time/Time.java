package graph.time;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graph.makeCDF.io.ReadAnswer;
import graph.makeCDF.io.ReadData;
import graph.makeCDF.node.Address;
import graph.makeCDF.node.BTMachine;
import graph.makeCDF.node.Packet;

public class Time {
	public static void main(String[] args) throws IOException {
		ReadAnswer readAnswer = new ReadAnswer();
		ReadData readData = new ReadData(readAnswer.read());
		readData.readData();
		int I=5;
		HashMap<Integer,Integer> map = new HashMap<>();
		ArrayList<BTMachine> machines = readData.getBtMachines();
		for(BTMachine bt:machines) {
			 bt.setAddressList();
			 for(Address address:bt.getAddressList()) {
				 address.setTimes();
				 int count = 0;
				 for(Packet packet:address.getPackets()) {
					 if(Math.abs(packet.getTime()-address.getLtime())<=I) {
						 count++;
					 }
				 }
				 if(map.containsKey(count)) {
					 map.put(count, map.get(count)+1);
				 }else {
					 map.put(count, 1);
				 }
			 }
		}
		 List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
		 list.sort(Map.Entry.comparingByKey());
		 
	        // ソート結果を出力する
	        for (Map.Entry<Integer, Integer> entry : list) {
	            System.out.println(entry.getKey() + ","+entry.getValue());
	        }
	}

}
