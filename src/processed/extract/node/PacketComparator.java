package processed.extract.node;

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
		if(o1.getTime()<o2.getTime())
			return -1;
		else if(o1.getTime()>o2.getTime())
			return 1;
		else
			return 0;
	}

}
