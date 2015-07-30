package io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class TextWriter {
	//BufferedOutputStream buff = null;
	private FileOutputStream fos = null;
	OutputStreamWriter out = null;
	public TextWriter(String filename){
		try {
			fos = new FileOutputStream(filename);
			out = new OutputStreamWriter(fos);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void write(String content){
		try {
			out.write(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close(){
		try {
			out.flush();
			out.close();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
