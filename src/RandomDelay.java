import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomDelay {
	/**
	 *
	 * @param args 0に生成したいランダム値の数 1に生成したいファイル数
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Random random = new Random();
		int fileNumber = Integer.parseInt(args[1]);
		int roop = Integer.parseInt(args[0]);
		FileWriter fileWriter = null;
		for (int j = 1; j <= fileNumber; j++) {
			fileWriter = new FileWriter("data/address/delay/randomDelay"+j+".csv");
			for (int i = 1; i <= roop; i++) {
				fileWriter.append(String.valueOf(random.nextInt(600)));
				fileWriter.append("\r\n");
			}
		}
		fileWriter.close();

	}

}
