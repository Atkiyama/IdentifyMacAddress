package dataAnalyze.io;

import java.io.IOException;
import java.util.ArrayList;

import dataAnalyze.node.Packet;

/**
 * ファイルを読み込むための抽象クラス
 * @author akiyama
 *
 */
public abstract class Read {
	/**
	 * ファイル名
	 */
	protected String fileName;
	/**
	 * 引数で初期化する
	 * @param fileName ファイル名
	 */
	public Read(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * パケットデータを読み込むクラス
	 * 子クラスで実装する
	 * @return パケットのリスト
	 * @throws IOException
	 */
	public abstract ArrayList<Packet> read() throws IOException;
}
