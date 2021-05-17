package dataAnalyze.analyze;

import java.io.IOException;
import java.util.ArrayList;

import dataAnalyze.node.Data;
import dataAnalyze.node.DataGroupe;
/**
 * identifyMacAddressで出た出力結果を解析し、結果ごとに分類するメインクラス
 * @author akiyama
 *
 */
public class DataAnalyze {
	/**
	 * データのリスト
	 */
	private ArrayList<Data> datas;
	/**
	 * データグループのリスト
	 */
	private ArrayList<DataGroupe> dataGroupes;
	/**
	 * datasに引数を代入し、dataGroupesを初期化する
	 * @param datas データのリスト
	 */
	public DataAnalyze(ArrayList<Data> datas) {
		this.datas = datas;
		dataGroupes = new ArrayList<>();
	}
	/**
	 * データをグループごとに分類するメソッド
	 */
	public void analyze() {
		boolean ad_known = false;
		for(Data base:datas) {
			for(DataGroupe dataGroupe:dataGroupes) {
				if(dataGroupe.getData().equals(base.getData())) {
					dataGroupe.addData(base);
					ad_known =true;
				}
			}
			if(!ad_known) {
				dataGroupes.add(new DataGroupe(base));
			}else {
				ad_known = false;
			}
		}
	}

	/**
	 * データが同じものか違うものか判別するメソッド
	 * @param base 比較するデータ1
	 * @param tmp 比較するデータ2
	 * @return baseとtmpの中身が同じならtrueを返す
	 */
	public boolean checkDiff(Data base,Data tmp) {
		if(base.getData().size() == tmp.getData().size()) {
			for(int i = 0;i<base.getData().size();i++) {
				if(!base.getData().get(i).equals(tmp.getData().get(i))) {
					return false;
				}
			}
			return true;

		}else {
			return false;
		}
	}
	/**
	 * データグループリスト内に引数のデータがあるかどうか判別するメソッド
	 * @param data あるかどうかを判定したいdata
	 * @return 存在するならtrueを返す
	 */
	public boolean containData(Data data) {
		for(DataGroupe dataGroupe:dataGroupes) {
			if(dataGroupe.getData().equals(data.getData()))
				return true;
		}
		return false;
	}

	/**
	 * データが同じものか違うものか判別するメソッド
	 * @param base 比較するデータ1
	 * @param tmp 比較するデータ2
	 * @return baseとtmpの中身が同じならtrueを返す
	 */
	public boolean checkDiff(ArrayList<String> base,Data tmp) {
		if(base.size() == tmp.getData().size()) {
			for(int i = 0;i<base.size();i++) {
				if(!base.get(i).equals(tmp.getData().get(i))) {
					return false;
				}
			}
			return true;

		}else {
			return false;
		}
	}

	/**
	 * 分類結果をコンソールに出力するメソッド(実際はシェルスクリプトを使ってテキストにする)
	 */
	public void showResult() {
		int i=1;
		for(DataGroupe dataGroupe:dataGroupes) {
			System.out.println("グループ"+i);
			System.out.println("闘値");
			for(String firstLine:dataGroupe.getFirstLines()) {
				int count =0;
				System.out.println(firstLine);
				count++;
				if(count>=5) {
					System.out.println();
					count=0;
				}
			}
			System.out.println("データ");
			for(String data:dataGroupe.getData()) {
				System.out.println(data);
			}
			i++;
		}

	}
	/**
	 *
	 * @param args args[0]に読み込むファイル名(相対or絶対パス)が入る
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		Read read = new Read(args[0]);
		read.read();
		DataAnalyze dataAnalyze = new DataAnalyze(read.getDatas());
		dataAnalyze.analyze();
		dataAnalyze.showResult();
	}

}
