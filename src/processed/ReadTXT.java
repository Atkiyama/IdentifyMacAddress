package processed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import processed.extract.node.Packet;
/**
 * テキストファイルからパケット情報を読み取るクラス
 * メインメソッドではtxtをcsvに変換する(convertCSV.shを使用)
 * @author akiyama
 *
 */
public class ReadTXT{
	

	/**
	 * fileNameは引数で初期化、その他は各の正規表現で初期化する
	 * @param fileName ファイル名
	 */
	
	/**
	 * ファイルから情報を読み取るメソッド
	 * @return パケットのリスト
	 */
	public static ArrayList<Packet> read(String fileName) throws IOException {
		
		
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
		
		pTime = Pattern.compile("([0-9]{2}):([0-9]{2}):(.{9})");
		pAddress = Pattern.compile("(..:..:..:..:..:..)");
		pRssi = Pattern.compile("(-[0-9]{1,2})");
		// TODO 自動生成されたメソッド・スタブ
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);
		String str = in.readLine();
		ArrayList<Packet> packets = new ArrayList<>();
		Matcher mTime;
		Matcher mAddress;
		Matcher mRssi;
		int i = 0;
		while (str != null) {
			i++;
			mTime = pTime.matcher(str);
			mAddress = pAddress.matcher(str);
			mRssi = pRssi.matcher(str);
			if (mTime.find() && mAddress.find() && mRssi.find()) {
				packets.add(makePackets(mTime, mAddress, mRssi,fileName));
			} else if(i != 1){
				//例外処理
				System.out.println(i + "行目にパターンに一致しない文字列を確認しました。ご確認ください");
				System.exit(0);
			}
			str = in.readLine();
		}
		in.close();//初回受診時刻を取得
	
		

		return packets;

	}

	/**
	 * パケットのインスタンスを作るメソッド
	 * @param mTime 時間のmatcher
	 * @param mAddress macアドレスのmatcher
	 * @param mRssi rssiのmatcher
	 * @return パケットのインスタンス
	 */

	public static Packet makePackets(Matcher mTime, Matcher mAddress, Matcher mRssi, String fileName) {
		// TODO 自動生成されたメソッド・スタブ
		double hour = Double.parseDouble(mTime.group(1));
		double minute = Double.parseDouble(mTime.group(2));
		double second = Double.parseDouble(mTime.group(3));
		return new Packet(mAddress.group(1), hour * 3600 + minute * 60 + second, Integer.parseInt(mRssi.group(1)),fileName);
	}


	

}
