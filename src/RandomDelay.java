import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RandomDelay {
	/**
	 *
	 * @param args 0に生成したいランダム値の数 1に生成したいファイル数
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Random randomInstance = new Random();

		double random = randomInstance.nextInt(599) + randomInstance.nextDouble();
		ArrayList<String[]> addressList = read("data/address/original/addressList.csv");
		rewriteAddressList(addressList, random, "data/address/delay/addressList.csv");
		for (String[] address : addressList) {
			if (address != addressList.get(0)) {
				rewriteAddress("data/address/original/fAddress/" + address[1] + ".csv", random,
						"data/address/delay/fAddress/" + address[1] + ".csv");
				rewriteAddress("data/address/original/lAddress/" + address[1] + ".csv", random,
						"data/address/delay/lAddress/" + address[1] + ".csv");
			}
		}

	}

	public static void rewriteAddressList(ArrayList<String[]> addressList, double random, String outputFileName)
			throws IOException {
		FileWriter fileWriter = new FileWriter(outputFileName);
		for (String[] address : addressList) {
			fileWriter.append(address[0]);
			fileWriter.append(",");
			fileWriter.append(address[1]);
			fileWriter.append(",");
			if (address == addressList.get(0)) {
				fileWriter.append(address[2]);
				fileWriter.append(",");
				fileWriter.append(address[3]);
				fileWriter.append("\r\n");
			} else {
				fileWriter.append(String.valueOf(Double.parseDouble(address[2]) + random));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(Double.parseDouble(address[3]) + random));
				fileWriter.append("\r\n");
			}
		}
		fileWriter.close();
	}

	public static void rewriteAddress(String inputFileName, double random, String outputFileName) throws IOException {
		FileWriter fileWriter = new FileWriter(outputFileName);
		ArrayList<String[]> input = read(inputFileName);
		for (String[] line : input) {
			if (line == input.get(0)) {
				fileWriter.append(line[0]);
				fileWriter.append(",");
				fileWriter.append(line[1]);
				fileWriter.append("\r\n");
			} else {
				fileWriter.append(String.valueOf(Double.parseDouble(line[0]) + random));
				fileWriter.append(",");
				fileWriter.append(line[1]);
				fileWriter.append("\r\n");
			}
		}
		fileWriter.close();
	}

	public static ArrayList<String[]> read(String fileName) throws IOException {
		File file;
		FileReader fileReader;
		BufferedReader in;
		file = new File(fileName);
		fileReader = new FileReader(file);
		in = new BufferedReader(fileReader);
		String str = in.readLine();
		ArrayList<String[]> data = new ArrayList<>();
		while (str != null) {
			data.add(str.split(","));
			str = in.readLine();
		}
		in.close();
		return data;
	}

}
