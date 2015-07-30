package Handler;

import io.TextReader;
import io.TextWriter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class classCount {
	private String readWords="Com_all.txt";
	private String wordsList="ComVocabStop.txt";
	private String writeClass="ComClass.txt";
	private HashMap<String,String> wordsArray = new HashMap<String, String>();
	private int AllNum=142586;
	public classCount() throws IOException{
		String temp=null;
		TextReader tr=new TextReader(wordsList);
		while((temp=tr.getReader().readLine())!=null){
			wordsArray.put(temp,"");
		}
		tr.close();
	}
	public void readList() throws IOException{
		String temp=null;
		TextReader tr=new TextReader(readWords);
		for(int j=0;j<30;j++)
			for(int i=0;i<AllNum;i++){
				temp=tr.getReader().readLine();
				if(i==0||i==142585||i==142584) continue;
				wordsArray.put(temp.substring(3, temp.length()), 
						wordsArray.get(temp.substring(3, temp.length()))+i+" ");
			}
		tr.close();
	}
	public void writeFile() {
		TextWriter tw=new TextWriter(writeClass);
		Iterator it=wordsArray.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			tw.write(entry.getKey()+" "+entry.getValue()+"\n");
			System.out.print(entry.getKey()+" "+entry.getValue()+"\n");
		}
		tw.close();
	}
}
