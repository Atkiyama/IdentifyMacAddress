package dataAnalyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Read {
	private String fileName;
	private String firstLine;
	private ArrayList<Data> datas;

	public Read(String fileName) {
		this.fileName = fileName;
		datas = new ArrayList<>();
	}


	public void read() throws IOException {
	   File file = new File(fileName);
       FileReader fileReader =  new FileReader(file);
       BufferedReader in = new BufferedReader(fileReader);
       String str = in.readLine();
       firstLine = str;


       while(str != null) {
    	  Data data = new Data(str);
    	  str = in.readLine();
    	   while(str !=null&&!str.equals(firstLine)) {
    		   data.addLine(str);
    		   str = in.readLine();

    	   }
    	   datas.add(data);
       }



	}


	public ArrayList<Data> getDatas() {
		return datas;
	}

}
