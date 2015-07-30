package Handler;

import io.TextReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class WordSet {
	private ArrayList<String> words = new ArrayList<String>();
	static final int WORDNUM = 27544;
	
	public WordSet(){
		TextReader tReader = null;
		try {
			tReader = new TextReader("sel_words.txt");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader breader = tReader.getReader();
		String text;
		try {
			while((text = breader.readLine()) != null){
				words.add(text);
			}
			breader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Text Number: " + words.size());
		tReader.close();
	}
	public ArrayList<String> getWords(){
		return words;
	}

}
