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
	 * @param args 0使用データ数
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Random randomInstance = new Random();
		ArrayList<String[]> addressList = read("data/address/original/addressList.csv");
		ArrayList<Integer> useData = new ArrayList<>();
		ArrayList<String[]> replace = new ArrayList<>();
		while(true) {
			if(useData.size()>=Integer.parseInt(args[0])||Integer.parseInt(args[0])==20)
				break;
			int numOfData = randomInstance.nextInt(19)+1;
			if(!useData.contains(numOfData)) {
				useData.add(numOfData);
			}
		}
		if(!args[0].equals("20")) {
			for(String[] address:addressList) {
				for(Integer in :useData) {
					if(address[0].contains(String.valueOf(in)))
						replace.add(address);
				}
			}
			addressList=replace;
		}
		for(int i=1;i<=100;i++) {
			//double random = randomInstance.nextInt(599) + randomInstance.nextDouble();
			double random = randomInstance.nextInt(600);
			rewriteConvert("data/convertOriginal.csv", random, "data/capture/convert/move/"+args[0]+"/convert"+i+".csv");
			rewriteAddressList(addressList, random, "data/address/delay/addressList/addressList"+i+".csv");
			for (String[] address : addressList) {
				if (address != addressList.get(0)) {
					rewriteAddress("data/address/original/fAddress/" + address[1] +".csv", random,
							"data/address/delay/fAddress/" + address[1] +"_"+i+ ".csv");
					rewriteAddress("data/address/original/lAddress/" + address[1] +".csv", random,
							"data/address/delay/lAddress/" + address[1] + "_"+i+".csv");
				}
			}
		}

	}

	private static void rewriteConvert(String inputFileName, double random, String outputFileName) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<String[]> convertOriginal = read(inputFileName);
		FileWriter fileWriter = new FileWriter(outputFileName);
		for (String[] address : convertOriginal) {
			if (address == convertOriginal.get(0)) {
				fileWriter.append("address");
				fileWriter.append(",");
				fileWriter.append("time");
				fileWriter.append(",");
				fileWriter.append("rssi");
				fileWriter.append("\r\n");
			} else {
				fileWriter.append(address[0]);
				fileWriter.append(",");
				fileWriter.append(String.valueOf(Double.parseDouble(address[1]) + random));
				fileWriter.append(",");
				fileWriter.append(address[2]);
				fileWriter.append("\r\n");
			}
		}
		fileWriter.close();

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
