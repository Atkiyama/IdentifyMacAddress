package graph.makeCDF.time;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

import dataAnalyze.io.Read;
import dataAnalyze.io.ReadTXT;
import dataAnalyze.node.Packet;



/**
 * cdfを作るためのクラス
 * @author akiyama
 *
 */
public class MakeCDF {

	/**
	 *
	 * @param args 0の文字列に応じてどのcdfを作るかを決める
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
			Read read = new ReadTXT(args[0]);
			ArrayList<Packet> packets = read.read();
			ArrayList<Double> data =new ArrayList<>();



			double base = 9999;
			for(Packet packet:packets) {
				if(base != 9999) {
					data.add(packet.getTime() - base);
				}
				base = packet.getTime();
			}
			Collections.sort(data);
			for(Double t:data) {
				System.out.println(t);
			}

			BigDecimal i = BigDecimal.ONE;
			BigDecimal size = BigDecimal.valueOf(data.size());
			for(Double t:data) {
				System.out.println(t+","+i.divide(size,20,RoundingMode.HALF_UP).toPlainString());
				i=i.add(BigDecimal.ONE);
			}



	}



}
