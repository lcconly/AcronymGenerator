package io;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class TextReader {
	private InputStreamReader isr = null;
	private BufferedReader reader = null;
	public TextReader(String filename) throws UnsupportedEncodingException{
		try {
			isr = new InputStreamReader(new FileInputStream(filename));
			reader = new BufferedReader(isr);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public BufferedReader getReader(){
		return reader;
	}
	public void close(){
		try {
			reader.close();
			isr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
