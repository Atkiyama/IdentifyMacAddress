package graph.makeCDF.node;

import java.util.Comparator;
/**
 * パケットをソートするのに使うクラス
 * @author akiyama
 *
 */
public class PacketComparator implements Comparator<Packet>{

	@Override
	/**
	 * パケットを比較するメソッド
	 */
	public int compare(Packet o1, Packet o2) {
		return o1.getTime()<o2.getTime() ? -1:1;
	}

}
