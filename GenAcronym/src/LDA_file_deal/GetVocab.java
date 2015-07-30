package LDA_file_deal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.omg.PortableInterceptor.INACTIVE;

import io.TextReader;
import io.TextWriter;

public class GetVocab {
    HashMap<String, Integer> vocab=new HashMap<String, Integer>();
    HashMap<String, Integer> StopWord=new HashMap<String, Integer>();
	TextWriter tw=new TextWriter("ComVocabStop.txt");
	public boolean isLetter(String str){
		for(int i=0;i<str.length();i++){
			if(!(str.charAt(i)-'a'>=0&&str.charAt(i)-'a'<26))
				return false;
		}
		return true;
	}
	
	public void readComPaper() throws IOException{
		TextReader txread_stop=null;
		try {
			txread_stop = new TextReader("english.stop");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader breader_stop = txread_stop.getReader();
		String stop;
		while((stop = breader_stop.readLine()) != null){
			StopWord.put(stop,0);
		}
		TextReader txread=null;
		try {
			txread = new TextReader("computerPaper.txt");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader breader = txread.getReader();
		String text,text1="";
		try {
			text1=breader.readLine();
			while((text = breader.readLine()) != null){
				if(!text.split(" ")[0].contains("0"))
					text1+=text;
					//System.out.println(text);
				else{
					if(text1.length()>100){
						text1.replaceAll(":", " ");
						text1.replaceAll(",", " ");
						text1.replaceAll(";", " ");
						text1.replaceAll("keyword", " ");
						text1.replaceAll("\\(", " ");
						//text1.replaceAll("?", " ");
						text1.replaceAll("\\)", " ");
						text1.replaceAll("<", " ");
						text1.replaceAll(">", " ");
						text1.replaceAll("\\.", " ");
						//System.out.println(text1);
						String[] sp=text1.split(" ");
					    HashMap<Integer, Integer> temp=new HashMap<Integer, Integer>();
						for(int i=2;i<sp.length;i++){
							if(!vocab.containsKey(sp[i].toLowerCase())&&
									isLetter(sp[i].toLowerCase())&&
									!StopWord.containsKey(sp[i].toLowerCase()))
								vocab.put(sp[i].toLowerCase(), 0);
						}
					}
					text1=text;
				}
			}
			breader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator iter = vocab.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			tw.write(key+"\n");
			System.out.println(key);
		}
		txread.close();
		tw.close();
	}
	
}
