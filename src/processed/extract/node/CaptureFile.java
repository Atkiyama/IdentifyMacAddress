package processed.extract.node;

import java.util.ArrayList;

public class CaptureFile {
	private String fileName;
	private ArrayList<String> addressList;
	public CaptureFile(String fileName) {
		super();
		this.fileName = fileName;
		addressList = new ArrayList<>();
	}
	public void add(String address) {
		// TODO 自動生成されたメソッド・スタブ
		addressList.add(address);
	}
	public String getFileName() {
		return fileName;
	}
	public ArrayList<String> getAddressList() {
		return addressList;
	}

}
