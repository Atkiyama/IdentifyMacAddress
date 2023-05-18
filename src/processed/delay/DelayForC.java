package processed.delay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import processed.extract.node.Packet;
import processed.extract.node.PacketComparator;

public class DelayForC extends Delay {
	public static void main(String[] args) throws IOException {
		int combination = Integer.parseInt(args[0]);
		ArrayList<String[]> addressList;
		for (int i = 1; i <= 100; i++) {
			addressList = extractAddressList(combination);
			ArrayList<Double> delayList = makeDelayList(200);
			HashMap<String, Double> delayMap = makeDelayMap(addressList, delayList);
			//makeConvert(addressList, delayMap, i);
			addressList = setDelay(addressList, delayMap);
			rewriteAddressList(addressList, "data/address/processed/addressList/addressList" + i + ".csv");
			for (String[] address : addressList) {
				rewriteAddress("data/address/original/fAddress/" + address[1] + ".csv", delayMap.get(address[1]),
						"data/address/processed/fAddress/" + address[1] + "_" + i + ".csv");
				rewriteAddress("data/address/original/lAddress/" + address[1] + ".csv", delayMap.get(address[1]),
						"data/address/processed/lAddress/" + address[1] + "_" + i + ".csv");

			}

		}

	}

	/**
	 * 旧手法に使う結合キャプチャデータを作成する
	 * 
	 * @param addressList
	 * @param delayMap遅延させた秒数とファイルのmap
	 * @param i                        通し番号
	 * @throws IOException
	 */
	static void makeConvert(ArrayList<String[]> addressList, HashMap<String, Double> delayMap, int i)
			throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<Packet> packets = readCaptureFile(addressList);
		for (Packet packet : packets) {
			packet.setDelay(delayMap.get(packet.getAddress()));
			// System.out.println(packet.getTime());
		}
		Collections.sort(packets, new PacketComparator());
		writeConvert(packets, i);

	}

	/**
	 * 旧手法に使う結合キャプチャデータを書き出す
	 * 
	 * @param packets
	 * @param i
	 * @throws IOException
	 */
	private static void writeConvert(ArrayList<Packet> packets, int i) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		FileWriter fileWriter = new FileWriter("data/capture/convert/move/" + i + ",convertData.csv");
		fileWriter.append("address");
		fileWriter.append(",");
		fileWriter.append("time");
		fileWriter.append(",");
		fileWriter.append("rssi");
		for (Packet packet : packets) {
			fileWriter.append("\r\n");
			fileWriter.append(packet.getAddress());
			fileWriter.append(",");
			fileWriter.append(String.valueOf(packet.getTime()));
			fileWriter.append(",");
			fileWriter.append(String.valueOf(packet.getRssi()));
		}
		fileWriter.close();

	}

	private static ArrayList<Packet> readCaptureFile(ArrayList<String[]> addressList) throws IOException {
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
		for (String[] address : addressList) {
			String fileName = address[0];
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
						&& isExist(fileName, mAddress.group(1), addressList)) {
					capturePackets.add(makePackets(mTime, mAddress, mRssi, fileName));
				}
				str = in.readLine();
			}
			in.close();
			// 初回受診時刻を取得
			double fTime = capturePackets.get(0).getTime();
			// データをフォーマット
			for (Packet packet : capturePackets) {
				packet.formatTime(fTime);
				packets.add(packet);
			}

		}

		return packets;

	}

	/**
	 * アドレスリストを抽出するメソッド オーバーライドしてる
	 * 
	 * @param combination 取り出す組み合わせ数
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String[]> extractAddressList(int combination) throws IOException {
		ArrayList<String[]> addressList = read("data/address/original/addressList.csv");
		ArrayList<String[]> frontAddressList = new ArrayList<>();
		addressList.remove(0);
		HashMap<String[], String[]> combiMap = new HashMap<>();
		for (int i = 0; i < addressList.size(); i += 2) {
			combiMap.put(addressList.get(i), addressList.get(i + 1));
			frontAddressList.add(addressList.get(i));
		}
		Random random = new Random();
		ArrayList<String[]> replace = new ArrayList<>();
		if (combination == 42) {
			replace = addressList;
		} else {
			while (true) {
				int rm = random.nextInt(frontAddressList.size()-1);
				//System.out.print(addNum);
				frontAddressList.remove(rm);
				//System.out.println(frontAddressList.size());
				if (frontAddressList.size() == combination)
					break;
			}
		}
		for (int i = 0; i < frontAddressList.size(); i++) {
			if (combiMap.containsKey(frontAddressList.get(i))) {
				replace.add(frontAddressList.get(i));
				replace.add(combiMap.get(frontAddressList.get(i)));
			}
		}
		return replace;
	}

	private static boolean isExist(String fileName, String searchedAddress, ArrayList<String[]> addressList) {
		// TODO 自動生成されたメソッド・スタブ
		for (String[] address : addressList) {
			if (address[0].equals(fileName) && searchedAddress.equals(address[1]))
				return true;
		}
		return false;
	}

	/**
	 * パケットのインスタンスを作るメソッド
	 * 
	 * @param mTime    時間のmatcher
	 * @param mAddress macアドレスのmatcher
	 * @param mRssi    rssiのmatcher
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
