package dataAnalyze.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dataAnalyze.node.Packet;

public class ReadUbertooth extends Read{
	
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
	/**
	 * uuidの正規表現
	 */
	final Pattern pUuid;
	/**
	 * 
	 * @param fileName ファイル名
	 */
	public ReadUbertooth(String fileName) {
		super(fileName);
		// TODO 自動生成されたコンストラクター・スタブ
		pTime = Pattern.compile("systime=(\\d+)");
		pAddress = Pattern.compile("AdvA:\\s+(\\S+)");
		pRssi = Pattern.compile("rssi=(-?\\d+)");
		pUuid = Pattern.compile("UUID:\\s*([a-f0-9]{4})");
	}
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
		Matcher mUuid;
		long time =-1;
		String address = null;
		int rssi = 0;
		String uuid = null;
		long fTime =-1;
		
		
		int i = 0;
		while (str != null) {
			i++;
			mTime = pTime.matcher(str);
			mAddress = pAddress.matcher(str);
			mRssi = pRssi.matcher(str);
			mUuid = pUuid.matcher(str);
			
			
			if(mTime.find()) {
				time = Long.parseLong(mTime.group(1));
				if(fTime == -1) {
					fTime =time;
					time = 0;
				}else {
					time-=fTime;
				}
				//System.out.print("time="+time);
			}
			if(mAddress.find() && time != -1 && rssi !=0) {
				address = mAddress.group(1);
				//System.out.println("address="+address);
			}
			if(mRssi.find()&&time != -1) {
				 rssi = Integer.parseInt(mRssi.group(1));
				 //System.out.print("rssi="+rssi);
			}
			if(mUuid.find()
					//&& time != -1 && rssi !=0 && address != null
					) {
				uuid = mUuid.group(1);
				//System.out.println("uuid="+uuid);
			}
			if (time!=-1 && address!=null && rssi!=0) {
				Packet packet;
				if(uuid!=null) {
					packet = new Packet(address,time,rssi,uuid);
				}else {
					packet = new Packet(address,time,rssi);
				}
				packets.add(packet);
				
				time = -1;
				address = null;
				rssi = 0;
				uuid = null;
			} 
			str = in.readLine();
		}
		in.close();
		// 初回受診時刻を取得
		
		HashSet<String> addressSet = new HashSet<>();
		ArrayList<Packet> inENS = new ArrayList<>();
		for (Packet packet : packets) {
			

			if(packet.getUuid()!=null &&packet.getUuid().equals("fd6f")) {
				addressSet.add(packet.getAddress());
				
			}
		}
		for(Packet packet:packets) {
			if(addressSet.contains(packet.getAddress())) {
				inENS.add(packet);
			}
		}
		
		//System.out.println(inENS.isEmpty());

		return packets;

	}

	

}
