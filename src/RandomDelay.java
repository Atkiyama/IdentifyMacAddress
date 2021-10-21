import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomDelay {

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Random random = new Random();
		int roop = Integer.parseInt(args[0]);
		FileWriter fileWriter = new FileWriter("data/address/randomDelay.csv");
		for(int i=1;i<=roop;i++) {
			fileWriter.append("\r\n");
			fileWriter.append(String.valueOf(random.nextInt(600)));
		}
		fileWriter.close();

	}

}
