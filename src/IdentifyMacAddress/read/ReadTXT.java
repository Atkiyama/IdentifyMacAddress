package identifyMacAddress.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import identifyMacAddress.node.Packet;

public class ReadTXT extends Read {
	/**
	 * 時間の正規表現
	 */
	final Pattern pTime;
	/**
	 * macアドレスの正規表現
	 */
	final Pattern pAddress;
	/**
	 * rssiの正規表現
	 */
	final Pattern pRssi;

	public ReadTXT(String fileName) {
		super(fileName);
		pTime = Pattern.compile("([0-9]{2}):([0-9]{2}):(.{9})");
		pAddress = Pattern.compile("(..:..:..:..:..:..)");
		pRssi = Pattern.compile("(-[0-9]{1,2})");
	}

	@Override
	public ArrayList<Packet> read() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
		String str = in.readLine();
		ArrayList<Packet> packets = new ArrayList<>();
		Matcher mTime;
		Matcher mAddress;
		Matcher mRssi;
		int i=0;
		while (str != null) {
			i++;
			mTime = pTime.matcher(str);
			mAddress = pAddress.matcher(str);
			mRssi = pRssi.matcher(str);
			if(mTime.find()&&mAddress.find()&&mRssi.find()) {
				packets.add(makePackets(mTime,mAddress,mRssi));
				System.out.println(mTime.group(3));
			}else {
				System.out.println(i+"行目にパターンに一致しない文字列を確認しました。ご確認ください");
			}
			str = in.readLine();
		}
		in.close();

		double fTime = packets.get(0).getTime();
		for(Packet packet:packets)
			packet.formatTime(fTime);

		return packets;

	}

	public Packet makePackets(Matcher mTime, Matcher mAddress, Matcher mRssi) {
		// TODO 自動生成されたメソッド・スタブ
		double hour = Double.parseDouble(mTime.group(1));
		double minute = Double.parseDouble(mTime.group(2));
		double second = Double.parseDouble(mTime.group(3));
		return new Packet(mAddress.group(1),hour*3600+minute*60+second,Integer.parseInt(mRssi.group(1)));
	}

	public static void main(String[] args) throws IOException {
		Read readtxt = new ReadTXT("data/capture/test.txt");
		ArrayList<Packet> packets =readtxt.read();
		for(Packet packet:packets)
			packet.printData();
	}

}
