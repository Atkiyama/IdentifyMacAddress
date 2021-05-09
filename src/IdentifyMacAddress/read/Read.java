package identifyMacAddress.read;

import java.io.IOException;
import java.util.ArrayList;

import identifyMacAddress.node.Packet;

public abstract class Read {
	protected String fileName;
	public Read(String fileName) {
		this.fileName = fileName;
	}
	public abstract ArrayList<Packet> read() throws IOException;
}
