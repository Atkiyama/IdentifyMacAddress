package processed.extract.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import processed.extract.node.CaptureFile;
import processed.extract.node.Packet;

/**
 * テキストファイルからパケット情報を読み取るクラス
 * @author akiyama
 *
 */
public class ReadTXT {

	/**
	 * ファイルから情報を読み取るメソッド
	 * @return パケットのリスト
	 */
	public static ArrayList<Packet> read() throws IOException {
		return readData(readAnswer());
	}

	public static ArrayList<Packet> readData(ArrayList<CaptureFile> captures) throws IOException {
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
		for (CaptureFile capture : captures) {
			file = new File("data/capture/single/move/txt/"+capture.getFileName()+".txt");
			fileReader = new FileReader(file);
			in = new BufferedReader(fileReader);
			str = in.readLine();
			capturePackets = new ArrayList<>();


			while (str != null) {
				mTime = pTime.matcher(str);
				mAddress = pAddress.matcher(str);
				mRssi = pRssi.matcher(str);
				if (mTime.find() && mAddress.find() && mRssi.find() &&capture.getAddressList().contains(mAddress.group(1))) {
					capturePackets.add(makePackets(mTime, mAddress, mRssi,capture.getFileName()));
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

	private static ArrayList<CaptureFile> readAnswer() throws IOException {
		ArrayList<CaptureFile> captures = new ArrayList<>();
		File file = new File("data/result/moveResult.csv");
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
		String str = in.readLine();
		while (str != null) {
			String[] line = str.split(",");
			CaptureFile capture = new CaptureFile(line[0]);
			for (String cell : line) {
				if (!cell.equals(line[0]))
					capture.add(cell);
			}
			captures.add(capture);
			str = in.readLine();
		}
		in.close();
		return captures;
		// TODO 自動生成されたメソッド・スタブ

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
		return new Packet(mAddress.group(1), hour * 3600 + minute * 60 + second, Integer.parseInt(mRssi.group(1)),fileName);
	}

	/**
	 * テスト用メインメソッド。読み込んでパケット情報を出力する
	 * @param args 読み込むデータ名
	 * @throws IOException
	 */

}
