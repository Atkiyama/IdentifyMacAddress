package evaluation;

import java.util.ArrayList;

public class CaptureFile {
	private String fileName;
	ArrayList<String> address;
	public CaptureFile(String[] line) {
		address = new ArrayList<>();
		for(String data:line) {
			if(fileName == null)
				fileName = data;
			else
				address.add(data);
		}
	}
	public ArrayList<String> getAddress(){
		return address;
	}

}
