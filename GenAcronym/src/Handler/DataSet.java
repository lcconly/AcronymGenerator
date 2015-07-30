package Handler;

import io.TextReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class DataSet {
	private ArrayList<String> allText = new ArrayList<String>();
	static final int TEXTNUM = 148688;
	
	public DataSet(){
		TextReader tReader = null;
		try {
			tReader = new TextReader("result_del_again.txt");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader breader = tReader.getReader();
		String text;
		try {
			while((text = breader.readLine()) != null){
				allText.add(text);
			}
			breader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Text Number: " + allText.size());
		tReader.close();
	}
	public ArrayList<String> getAllText(){
		return allText;
	}
}
