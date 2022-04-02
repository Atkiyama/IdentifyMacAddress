package processed.lineUp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import processed.ReadCSV;
import processed.extract.node.Address;
import processed.extract.node.Packet;

public class LineUp {
	private ArrayList<String[]> originalAddressList;
	private ArrayList<Pair> pairs;
	private HashMap<String,Double> substract;

	public LineUp(ArrayList<String[]> originalAddressList) {
		this.originalAddressList = originalAddressList;
		pairs = new ArrayList<>();
		substract = new HashMap<>();
	}

	/**
	 * 
	 * @param args 0に抽出するペア数
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
			LineUp lineUp = new LineUp(ReadCSV.read("data/address/original/addressList.csv"));
			lineUp.setPairs();//
			lineUp = LineUp.format(lineUp);
			LineUp.toWrite(lineUp,0);
		if(args.length==1){
			
			int numOfPair = Integer.parseInt(args[0]);
			for(int i=1;i<=100;i++) {
				lineUp = new LineUp(ReadCSV.read("data/address/original/addressList.csv"));
				lineUp.setPairs();
				lineUp.extractPair(numOfPair);
				lineUp = LineUp.format(lineUp);
				LineUp.toWrite(lineUp,i);
			}
		}else if(args.length!=0){
			int numOfPair = Integer.parseInt(args[0]);
			for(int i=1;i<=100;i++) {
				lineUp = new LineUp(ReadCSV.read("data/address/original/addressList.csv"));
				lineUp.setPairs();
				lineUp.extractPair(numOfPair);
				LineUp lineUp2 = LineUp.format(lineUp,Integer.parseInt(args[1]));
				LineUp.toWrite(lineUp2,i);
			}
		}

	}
	public static LineUp format(LineUp lineUp) throws IOException {
		lineUp.readfPackets();
		lineUp.readlPackets();
		lineUp.formatPairs();
		lineUp.formatfPackets();
		lineUp.formatlPackets();
		return lineUp;
	}
	
	/**
	 * 遅延処理を加える用
	 * @param lineUp
	 * @return
	 * @throws IOException
	 */
	public static LineUp format(LineUp lineUp,int delay) throws IOException {
		lineUp.readfPackets();
		lineUp.readlPackets();
		lineUp.formatPairs();
		lineUp.addDelay(delay);
		lineUp.formatfPackets();
		lineUp.formatlPackets();
		return lineUp;
	}
	
	public void addDelay(int delayRange) {
		Random random = new Random();
		for(Pair pair:pairs) {
			int delay = random.nextInt(delayRange*2)-delayRange;
			pair.getFrontAddress().setfTime(pair.getFrontAddress().getfTime()+delay);
			pair.getFrontAddress().setlTime(pair.getFrontAddress().getlTime()+delay);		
			pair.getBackAddress().setfTime(pair.getBackAddress().getfTime()+delay);
			pair.getBackAddress().setlTime(pair.getBackAddress().getlTime()+delay);
			substract.put(pair.getFrontAddress().getName(),pair.getFrontAddress().getlTime()+delay);
			substract.put(pair.getBackAddress().getName(),pair.getBackAddress().getlTime()+delay);
		}
		
		
	}
	
	public static void toWrite(LineUp lineUp,int i) throws IOException {
		//ここまでアドレスリストの処理
		ArrayList<Address> addressList = new ArrayList<>();
		for(Pair pair:lineUp.getPairs()) {
			addressList.add(pair.getFrontAddress());
			addressList.add(pair.getBackAddress());
		}
		Write.writeAllAddress(addressList,i);
		for(Address address:addressList) {
			Write.writeFAddress(address,i);
			Write.writeLAddress(address,i);
		}
	}

	public ArrayList<Pair> getPairs() {
		return pairs;
	}
	
	public void extractPair(int numOfPair) {
		Random random = new Random();
		while(pairs.size()!=numOfPair) {
			int randomInt = random.nextInt(pairs.size());
			pairs.remove(randomInt);
		}
		
	}

	private void formatlPackets() {
		// TODO 自動生成されたメソッド・スタブ
		for(Pair pair:pairs) {
			for(Packet packet:pair.getFrontAddress().getlPackets()) {
				packet.setTime(packet.getTime()-substract.get(pair.getFrontAddress().getName()));
			}
			for(Packet packet:pair.getBackAddress().getlPackets()) {
				packet.setTime(packet.getTime()-substract.get(pair.getBackAddress().getName()));
			}
		}

	}
	

	private void formatfPackets() {
		// TODO 自動生成されたメソッド・スタブ
		for(Pair pair:pairs) {
			for(Packet packet:pair.getFrontAddress().getfPackets()) {
				packet.setTime(packet.getTime()-substract.get(pair.getFrontAddress().getName()));
			}
			for(Packet packet:pair.getBackAddress().getfPackets()) {
				packet.setTime(packet.getTime()-substract.get(pair.getBackAddress().getName()));
			}
		}

	}

	private void readfPackets() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<String[]> fPackets;
		for(Pair pair:pairs) {
			fPackets = ReadCSV.read("data/address/original/fAddress/"+pair.getFrontAddress().getName()+".csv");
			for(String[] line:fPackets) {
				if(line!=fPackets.get(0)) {
					pair.getFrontAddress().addFPacket(new Packet(pair.getFrontAddress().getName(),Double.parseDouble(line[0]),Integer.parseInt(line[1]),pair.getFrontAddress().getFileName()));
				}
			}
			fPackets = ReadCSV.read("data/address/original/fAddress/"+pair.getBackAddress().getName()+".csv");
			for(String[] line:fPackets) {
				if(line!=fPackets.get(0)) {
					pair.getBackAddress().addFPacket(new Packet(pair.getBackAddress().getName(),Double.parseDouble(line[0]),Integer.parseInt(line[1]),pair.getBackAddress().getFileName()));
				}
			}
		}
	}

	private void readlPackets() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<String[]> lPackets;
		for(Pair pair:pairs) {
			lPackets = ReadCSV.read("data/address/original/lAddress/"+pair.getFrontAddress().getName()+".csv");
			for(String[] line:lPackets) {
				if(line!=lPackets.get(0)) {
					pair.getFrontAddress().addLPacket(new Packet(pair.getFrontAddress().getName(),Double.parseDouble(line[0]),Integer.parseInt(line[1]),pair.getFrontAddress().getFileName()));
				}
			}
			lPackets = ReadCSV.read("data/address/original/lAddress/"+pair.getBackAddress().getName()+".csv");
			for(String[] line:lPackets) {
				if(line!=lPackets.get(0)) {
					pair.getBackAddress().addLPacket(new Packet(pair.getBackAddress().getName(),Double.parseDouble(line[0]),Integer.parseInt(line[1]),pair.getBackAddress().getFileName()));
				}
			}
		}
	}

	private void formatPairs() {
		// TODO 自動生成されたメソッド・スタブ
		for(Pair pair:pairs) {
			double ltime = pair.getFrontAddress().getlTime();
			pair.getFrontAddress().setfTime(pair.getFrontAddress().getfTime()-ltime);
			pair.getFrontAddress().setlTime(0);
			pair.getBackAddress().setfTime(pair.getBackAddress().getfTime()-ltime);
			pair.getBackAddress().setlTime(pair.getBackAddress().getlTime()-ltime);
			pair.getBackAddress().setName(pair.getBackAddress().getName()+"_2");
			substract.put(pair.getFrontAddress().getName(),ltime);
			substract.put(pair.getBackAddress().getName(),ltime);
		}
	}
	
	public void setPairs() {
		// TODO 自動生成されたメソッド・スタブ
		String[] beforeAddress = {""};
		for(String[] address:originalAddressList) {
			if(address[0].equals(beforeAddress[0]))
				pairs.add(new Pair(new Address(beforeAddress),new Address(address)));
			beforeAddress=address;
		}
		
		
	}

}
