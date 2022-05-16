package processed.delay;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import processed.ReadCSV;

public class Delay {
	/**
	 *
	 * @param args 0使用データ数
	 * @throws IOException
	 */
	/*
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Random randomInstance = new Random();
		for (int i = 1; i <= 100; i++) {
			ArrayList<String[]> addressList = read("data/address/original/addressList.csv");
			ArrayList<String> useData = new ArrayList<>();
			ArrayList<String[]> replace = new ArrayList<>();
			while (true) {
				if (useData.size() == Integer.parseInt(args[0]) || Integer.parseInt(args[0]) == 20) {
					break;
				}
				int r = randomInstance.nextInt(20) + 1;
				String fileName = "move" + r;
				if (!useData.contains(fileName)) {
					useData.add(fileName);
				}
			}
			if (!args[0].equals("20")) {
				for (String[] address : addressList) {
					for (String fileName : useData) {
						if (address[0].equals(fileName))
							replace.add(address);
					}
				}
				addressList = replace;
			} else {
				addressList.remove(0);
			}
			//double random = randomInstance.nextInt(599) + randomInstance.nextDouble();
			//初期化タイミング
			double random1 = randomInstance.nextInt(600);
			rewriteConvert("data/convertOriginal.csv", random1,
					"data/capture/convert/move/convert" + i + ".csv", addressList);
			rewriteAddressList(addressList, random1, "data/address/delay/addressList/addressList" + i + ".csv");
			for (String[] address : addressList) {
				random1 = randomInstance.nextInt(600);
				rewriteAddress("data/address/original/fAddress/" + address[1] + ".csv", random1,
						"data/address/delay/fAddress/" + address[1] + "_" + i + ".csv");
				rewriteAddress("data/address/original/lAddress/" + address[1] + ".csv", random1,
						"data/address/delay/lAddress/" + address[1] + "_" + i + ".csv");

			}
		}

	}*/

	protected static ArrayList<String[]> extractAddressList(int numOfData) throws IOException {
		Random randomInstance = new Random();
		ArrayList<String[]> addressList = read("data/address/original/addressList.csv");
		ArrayList<String> useData = new ArrayList<>();
		ArrayList<String[]> replace = new ArrayList<>();
		while (true) {
			if (useData.size() == numOfData) {
				break;
			}

			int r = randomInstance.nextInt(20) + 1;
			String fileName = "move" + r;
			if (!useData.contains(fileName)) {
				useData.add(fileName);
			}
		}

		for (String[] address : addressList) {
			for (String fileName : useData) {
				if (address[0].equals(fileName))
					replace.add(address);
			}
		}

		return replace;

	}


	public static void rewriteAddressList(ArrayList<String[]> addressList, String outputFileName)
			throws IOException {
		FileWriter fileWriter = new FileWriter(outputFileName);
		fileWriter.append("fileName");
		fileWriter.append(",");
		fileWriter.append("address");
		fileWriter.append(",");
		fileWriter.append("fTime");
		fileWriter.append(",");
		fileWriter.append("lTime");
		fileWriter.append("\r\n");
		for (String[] address : addressList) {
			fileWriter.append(address[0]);
			fileWriter.append(",");
			fileWriter.append(address[1]);
			fileWriter.append(",");
			fileWriter.append(address[2]);
			fileWriter.append(",");
			fileWriter.append(address[3]);
			fileWriter.append("\r\n");

		}
		fileWriter.close();
	}

	public static void rewriteAddress(String inputFileName, double random, String outputFileName) throws IOException {
		FileWriter fileWriter = new FileWriter(outputFileName);
		ArrayList<String[]> input = read(inputFileName);
		for (String[] line : input) {
			if (line == input.get(0)) {
				fileWriter.append("time");
				fileWriter.append(",");
				fileWriter.append("rssi");
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
		return ReadCSV.read(fileName);
	}

	public static HashMap<String,Double> makeDelayMap(ArrayList<String[]> addressList,ArrayList<Double> delayList) {
		HashMap<String,Double> delayMap = new HashMap<>();
		String fileName = addressList.get(0)[0];
		int i=0;
		for(String[] address:addressList) {
			if(!fileName.equals(address[0])) {
				i++;
				//System.out.println(i+","+fileName+","+address[0]);
				fileName = address[0];
			}
			delayMap.put(address[1],delayList.get(i));
		}
		return delayMap;

	}

	protected static ArrayList<String[]> setDelay(ArrayList<String[]> addressList,HashMap<String, Double> delayMap){
		for(String[] line:addressList) {
			line[2] = String.valueOf(Double.parseDouble(line[2])+delayMap.get(line[1]));
			line[3] = String.valueOf(Double.parseDouble(line[3])+delayMap.get(line[1]));
		}
		return addressList;

	}

	public static ArrayList<Double> makeDelayList(int numOfData) {
		Random randomInstance = new Random();
		ArrayList<Double> delayList = new ArrayList<>();
		for(int i=0;i<numOfData;i++)
			delayList.add(randomInstance.nextInt(600) + randomInstance.nextDouble());
		return delayList;
	}

}
