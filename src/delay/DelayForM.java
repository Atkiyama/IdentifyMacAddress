package delay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import extract.node.Packet;
import extract.node.PacketComparator;

public class DelayForM extends Delay {

	public static void main(String[] args) throws IOException {
		int numOfData = Integer.parseInt(args[0]);
		ArrayList<String[]> addressList;
		for (int i = 1; i <= 100; i++) {
			if (numOfData != 20)
				addressList = extractAddressList(numOfData);
			else {
				addressList = read("data/address/original/addressList.csv");
				addressList.remove(0);
			}

			ArrayList<Double> delayList = makeDelayList(numOfData);
			HashMap<String, Double> delayMap = makeDelayMap(addressList, delayList);
			makeConvert(addressList,delayMap,i);
			addressList = setDelay(addressList, delayMap);
			rewriteAddressList(addressList, "data/address/delay/addressList/addressList" + i + ".csv");
			for (String[] address : addressList) {
				rewriteAddress("data/address/original/fAddress/" + address[1] + ".csv", delayMap.get(address[1]),
						"data/address/delay/fAddress/" + address[1] + "_" + i + ".csv");
				rewriteAddress("data/address/original/lAddress/" + address[1] + ".csv", delayMap.get(address[1]),
						"data/address/delay/lAddress/" + address[1] + "_" + i + ".csv");

			}

		}

	}

	private static void makeConvert(ArrayList<String[]> addressList,HashMap<String, Double> delayMap,int i) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<String> fileList = new ArrayList<>();
		for (String[] file : addressList) {
			if (!fileList.contains(file[0]))
				fileList.add(file[0]);
		}
		ArrayList<Packet> packets = readCaptureFile(fileList,delayMap);
		for(Packet packet:packets) {
			packet.setDelay(delayMap.get(packet.getAddress()));
		}
		Collections.sort(packets,new PacketComparator());
		writeConvert(packets,i);

	}

	private static void writeConvert(ArrayList<Packet> packets,int i) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		FileWriter fileWriter = new FileWriter("data/capture/convert/move/convert"+i+".csv");
		fileWriter.append("address");
		fileWriter.append(",");
		fileWriter.append("time");
		fileWriter.append(",");
		fileWriter.append("rssi");
		for(Packet packet:packets) {
			fileWriter.append("\r\n");
			fileWriter.append(packet.getAddress());
			fileWriter.append(",");
			fileWriter.append(String.valueOf(packet.getTime()));
			fileWriter.append(",");
			fileWriter.append(String.valueOf(packet.getRssi()));
		}
		fileWriter.close();

	}

	private static ArrayList<Packet> readCaptureFile(ArrayList<String> fileList,HashMap<String,Double> delayMap) throws IOException {
		// TODO 自動生成されたメソッド・スタブ



	final Pattern pTime = Pattern.compile("([0-9]{2}):([0-9]{2}):(.{9})");
	final Pattern pAddress = Pattern.compile("(..:..:..:..:..:..)");
	final Pattern pRssi = Pattern.compile("(-[0-9]{1,2})");
	File file;
	FileReader fileReader;
	BufferedReader in;
	String str;
	ArrayList<Packet> packets = new ArrayList<>();
	ArrayList<Packet> capturePackets;
	Matcher mTime;
	Matcher mAddress;
	Matcher mRssi;
	for(String fileName:fileList){
		file = new File("data/capture/single/move/txt/" + fileName + ".txt");
		fileReader = new FileReader(file);
		in = new BufferedReader(fileReader);
		str = in.readLine();
		capturePackets = new ArrayList<>();

		while (str != null) {
			mTime = pTime.matcher(str);
			mAddress = pAddress.matcher(str);
			mRssi = pRssi.matcher(str);
			if (mTime.find() && mAddress.find() && mRssi.find()
					&& delayMap.containsKey(mAddress.group(1))) {
				capturePackets.add(makePackets(mTime, mAddress, mRssi, fileName));
			}
			str = in.readLine();
		}
		in.close();
		//初回受診時刻を取得
		double fTime = capturePackets.get(0).getTime();
		//データをフォーマット
		for (Packet packet : capturePackets) {
			packet.formatTime(fTime);
			packets.add(packet);
		}

	}

	return packets;

}


	/**
	 * パケットのインスタンスを作るメソッド
	 * @param mTime 時間のmatcher
	 * @param mAddress macアドレスのmatcher
	 * @param mRssi rssiのmatcher
	 * @param fileName
	 * @return パケットのインスタンス
	 */

	public static Packet makePackets(Matcher mTime, Matcher mAddress, Matcher mRssi, String fileName) {
		// TODO 自動生成されたメソッド・スタブ
		double hour = Double.parseDouble(mTime.group(1));
		double minute = Double.parseDouble(mTime.group(2));
		double second = Double.parseDouble(mTime.group(3));
		return new Packet(mAddress.group(1), hour * 3600 + minute * 60 + second, Integer.parseInt(mRssi.group(1)),
				fileName);
	}

}
