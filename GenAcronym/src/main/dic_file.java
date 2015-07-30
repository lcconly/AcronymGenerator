package main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import io.TextReader;
import io.TextWriter;

public class dic_file {
	public static boolean isAllLetter(String name){
		int flag=0;
		for(int i=0;i<name.length();i++){
			if(name.charAt(i)-'a'>=0&&name.charAt(i)-'a'<26){
				
			}
			else {
				flag=1;
			}
		}
		if(flag==1)
			return false;
		else {
			return true;
		}
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TextReader tr=new TextReader("dic/index.verb");
		TextWriter tw=new TextWriter("dic/index_del.verb");
		String temp=null;
		while((temp=tr.getReader().readLine())!=null){
			String[] sp=temp.split(" ");
			if(isAllLetter(sp[0]))
				tw.write(sp[0]+"\n");
		}
		tr.close();
		tw.close();
	}

}
