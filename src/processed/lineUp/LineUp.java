package processed.lineUp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import processed.ReadCSV;
import processed.extract.node.Address;
import processed.extract.node.Packet;

public class LineUp {
	/**
	 * ファイルから読み取ったままのアドレスリスト
	 */
	private ArrayList<String[]> originalAddressList;
	/**
	 * アドレスを変化前、変化後のペアに分けた時のリスト
	 */
	private ArrayList<Pair> pairs;
	/**
	 * 各アドレスを整列する際に引く値のリスト
	 */
	private HashMap<String, Double> substract;

	/**
	 * 初期化する
	 * 
	 * @param originalAddressList
	 */
	public LineUp(ArrayList<String[]> originalAddressList) {
		this.originalAddressList = originalAddressList;
		pairs = new ArrayList<>();
		substract = new HashMap<>();
	}

	/**
	 * 
	 * @param args 0に抽出するペア数 1整列した状態からずらす秒数
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		// パケットのリストを作成
		ArrayList<String[]> originalPacketList = new ArrayList<>();
		ArrayList<Packet> fullPacketList = new ArrayList<>();
		//全てのキャプチャファイル(CSVから読み込む)
		for (int i = 1; i <= 20; i++) {
			originalPacketList = ReadCSV.read("data/capture/single/move/csv/move" + i + ".csv");

			for (String[] originalPacket : originalPacketList) {
				if (!originalPacket[1].equals("time"))
					fullPacketList.add(new Packet(originalPacket[0], Double.parseDouble(originalPacket[1]),
							Integer.parseInt(originalPacket[2])));
			}
		}
		LineUp lineUp = new LineUp(ReadCSV.read("data/address/original/addressList.csv"));
		lineUp.setPairs();//
		lineUp = LineUp.format(lineUp);
		LineUp.toWrite(lineUp, 0, fullPacketList);
		if (args.length == 1) {

			int numOfPair = Integer.parseInt(args[0]);
			for (int i = 1; i <= 100; i++) {
				lineUp = new LineUp(ReadCSV.read("data/address/original/addressList.csv"));
				lineUp.setPairs();
				lineUp.extractPair(numOfPair);
				lineUp = LineUp.format(lineUp);
				ArrayList<Packet> packetList = lineUp.extractPacket(fullPacketList);
				packetList = lineUp.formatPacketList(packetList);
				LineUp.toWrite(lineUp, i, packetList);
			}
		} else if (args.length != 0) {
			int numOfPair = Integer.parseInt(args[0]);
			for (int i = 1; i <= 100; i++) {
				lineUp = new LineUp(ReadCSV.read("data/address/original/addressList.csv"));
				lineUp.setPairs();
				lineUp.extractPair(numOfPair);
				lineUp = LineUp.format(lineUp, Integer.parseInt(args[1]));
				ArrayList<Packet> packetList = lineUp.extractPacket(fullPacketList);
				packetList = lineUp.formatPacketList(packetList);
				LineUp.toWrite(lineUp, i, packetList);
			}
		}

	}

	private ArrayList<Packet> formatPacketList(ArrayList<Packet> packetList) {
		// TODO 自動生成されたメソッド・スタブ
		for (Packet packet : packetList) {
			packet.setTime(packet.getTime() - substract.get(packet.getAddress()));
		}
		return packetList;
	}

	/**
	 * 各パラメータをフォーマットする
	 * 
	 * @param lineUp
	 * @return
	 * @throws IOException
	 */
	public static LineUp format(LineUp lineUp) throws IOException {
		lineUp.readfPackets();
		lineUp.readlPackets();
		lineUp.formatPairs();
		lineUp.formatfPackets();
		lineUp.formatlPackets();
		return lineUp;
	}

	/**
	 * 各パラメータをフォーマットする 遅延処理を加える用
	 * 
	 * @param lineUp
	 * @param delay  遅延させる秒数
	 * @return
	 * @throws IOException
	 */
	public static LineUp format(LineUp lineUp, int delay) throws IOException {
		lineUp.readfPackets();
		lineUp.readlPackets();
		lineUp.formatPairs();
		lineUp.addDelay(delay);
		lineUp.formatfPackets();
		lineUp.formatlPackets();
		return lineUp;
	}

	/**
	 * 遅延を加える
	 * 
	 * @param delayRange 遅延の範囲
	 */
	public void addDelay(int delayRange) {
		Random random = new Random();
		if(delayRange==0)
			return;
		for (Pair pair : pairs) {
			int delay = random.nextInt(delayRange * 2) - delayRange;
			pair.getFrontAddress().setfTime(pair.getFrontAddress().getfTime() - delay);
			pair.getFrontAddress().setlTime(pair.getFrontAddress().getlTime() - delay);
			pair.getBackAddress().setfTime(pair.getBackAddress().getfTime() - delay);
			pair.getBackAddress().setlTime(pair.getBackAddress().getlTime() - delay);
			substract.put(pair.getFrontAddress().getName(), substract.get(pair.getFrontAddress().getName()) + delay);
			substract.put(pair.getBackAddress().getName(), substract.get(pair.getBackAddress().getName()) + delay);
		}

	}

	/**
	 * 書き込みをWirteクラスに投げる
	 * 
	 * @param lineUp
	 * @param i
	 * @throws IOException
	 */
	public static void toWrite(LineUp lineUp, int i, ArrayList<Packet> packetList) throws IOException {
		// ここまでアドレスリストの処理
		ArrayList<Address> addressList = new ArrayList<>();
		for (Pair pair : lineUp.getPairs()) {
			addressList.add(pair.getFrontAddress());
			addressList.add(pair.getBackAddress());
		}
		Write.writeAllAddress(addressList, i);
		for (Address address : addressList) {
			Write.writeFAddress(address, i);
			Write.writeLAddress(address, i);
		}
		Write.writeConvert(packetList, i);
	}

	public ArrayList<Pair> getPairs() {
		return pairs;
	}

	/**
	 * 引数の数のペアを抽出する
	 * 
	 * @param numOfPair
	 */
	public void extractPair(int numOfPair) {
		Random random = new Random();
		while (pairs.size() != numOfPair) {
			int randomInt = random.nextInt(pairs.size());
			pairs.remove(randomInt);
		}

	}

	/**
	 * lPacketをフォーマットする
	 */
	private void formatlPackets() {
		// TODO 自動生成されたメソッド・スタブ
		for (Pair pair : pairs) {
			for (Packet packet : pair.getFrontAddress().getlPackets()) {
				packet.setTime(packet.getTime() - substract.get(pair.getFrontAddress().getName()));
			}
			for (Packet packet : pair.getBackAddress().getlPackets()) {
				packet.setTime(packet.getTime() - substract.get(pair.getBackAddress().getName()));
			}
		}

	}

	/**
	 * fPacketをフォーマットする
	 */
	private void formatfPackets() {
		// TODO 自動生成されたメソッド・スタブ
		for (Pair pair : pairs) {
			for (Packet packet : pair.getFrontAddress().getfPackets()) {
				packet.setTime(packet.getTime() - substract.get(pair.getFrontAddress().getName()));
			}
			for (Packet packet : pair.getBackAddress().getfPackets()) {
				packet.setTime(packet.getTime() - substract.get(pair.getBackAddress().getName()));
			}
		}

	}

	/**
	 * オリジナルのfPacketを読み込む
	 * 
	 * @throws IOException
	 */
	private void readfPackets() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<String[]> fPackets;
		for (Pair pair : pairs) {
			fPackets = ReadCSV.read("data/address/original/fAddress/" + pair.getFrontAddress().getName() + ".csv");
			for (String[] line : fPackets) {
				if (line != fPackets.get(0)) {
					pair.getFrontAddress()
							.addFPacket(new Packet(pair.getFrontAddress().getName(), Double.parseDouble(line[0]),
									Integer.parseInt(line[1]), pair.getFrontAddress().getFileName()));
				}
			}
			fPackets = ReadCSV.read("data/address/original/fAddress/" + pair.getBackAddress().getName() + ".csv");
			for (String[] line : fPackets) {
				if (line != fPackets.get(0)) {
					pair.getBackAddress()
							.addFPacket(new Packet(pair.getBackAddress().getName(), Double.parseDouble(line[0]),
									Integer.parseInt(line[1]), pair.getBackAddress().getFileName()));
				}
			}
		}
	}

	/**
	 * オリジナルのlPacketを読み込む
	 * 
	 * @throws IOException
	 */
	private void readlPackets() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<String[]> lPackets;
		for (Pair pair : pairs) {
			lPackets = ReadCSV.read("data/address/original/lAddress/" + pair.getFrontAddress().getName() + ".csv");
			for (String[] line : lPackets) {
				if (line != lPackets.get(0)) {
					pair.getFrontAddress()
							.addLPacket(new Packet(pair.getFrontAddress().getName(), Double.parseDouble(line[0]),
									Integer.parseInt(line[1]), pair.getFrontAddress().getFileName()));
				}
			}
			lPackets = ReadCSV.read("data/address/original/lAddress/" + pair.getBackAddress().getName() + ".csv");
			for (String[] line : lPackets) {
				if (line != lPackets.get(0)) {
					pair.getBackAddress()
							.addLPacket(new Packet(pair.getBackAddress().getName(), Double.parseDouble(line[0]),
									Integer.parseInt(line[1]), pair.getBackAddress().getFileName()));
				}
			}
		}
	}

	/**
	 * ペアをフォーマットする
	 */
	private void formatPairs() {
		// TODO 自動生成されたメソッド・スタブ
		for (Pair pair : pairs) {
			double ltime = pair.getFrontAddress().getlTime();
			pair.getFrontAddress().setfTime(pair.getFrontAddress().getfTime() - ltime);
			pair.getFrontAddress().setlTime(0);
			pair.getBackAddress().setfTime(pair.getBackAddress().getfTime() - ltime);
			pair.getBackAddress().setlTime(pair.getBackAddress().getlTime() - ltime);
			pair.getBackAddress().setName(pair.getBackAddress().getName() + "_2");
			substract.put(pair.getFrontAddress().getName(), ltime);
			substract.put(pair.getBackAddress().getName(), ltime);
		}
	}

	/**
	 * originalAddressListからペアを抽出する
	 */
	public void setPairs() {
		// TODO 自動生成されたメソッド・スタブ
		String[] beforeAddress = { "" };
		for (String[] address : originalAddressList) {
			if (address[0].equals(beforeAddress[0]))
				pairs.add(new Pair(new Address(beforeAddress), new Address(address)));
			beforeAddress = address;
		}

	}

	/**
	 * 抽出されたペアを元にパケットを抽出する
	 * 
	 * @param fullPacketList 全てのパケットのリスト
	 * @return 抽出したパケットのリスト
	 */
	public ArrayList<Packet> extractPacket(ArrayList<Packet> fullPacketList) {
		// 抽出されたペアを元にパケットも整理
		ArrayList<Packet> packetList = new ArrayList<>();
		int flag = 0;
		int flag2 = 0;
		for (Packet packet : fullPacketList) {
			if (substract.containsKey(packet.getAddress())) {
				packetList.add(packet);
				flag=1;
			}
			if (substract.containsKey(packet.getAddress() + "_2")) {
				Packet packet2 = new Packet(packet.getAddress()+"_2",packet.getTime(),packet.getRssi());
				packetList.add(packet2);
				flag2=1;
			}

		}
		
//エラーチェック
		if(flag*flag2==0) {
			System.out.println(flag+","+flag2);
//			for(Packet packet:fullPacketList)
//				System.out.println(packet.getAddress());
//			System.out.println();
//			for(String key:substract.keySet())
//				System.out.println(key);
//			
//			System.out.println();
			for(Packet packet:packetList)
				System.out.println(packet.getAddress());
			System.exit(0);
		}
		return packetList;
	}

}
