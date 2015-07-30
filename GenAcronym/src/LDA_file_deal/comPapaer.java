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

public class comPapaer {
    HashMap<String, Integer> vocab=new HashMap<String, Integer>();
	TextWriter tw=new TextWriter("ComPapStop.txt");

    public comPapaer(){
    	TextReader tx=null;
		try {
			tx = new TextReader("ComVocabStop.txt");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader breader = tx.getReader();
		String text;
		int count=0;
		try {
			while((text = breader.readLine()) != null){
				if(!vocab.containsKey(text.toLowerCase()))
					vocab.put(text.toLowerCase(), count);
				count++;	
			}
			breader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tx.close();

    }
	public boolean isLetter(String str){
		for(int i=0;i<str.length();i++){
			if(!(str.charAt(i)-'a'>=0&&str.charAt(i)-'a'<26))
				return false;
		}
		return true;
	}

	public void readComPaper(){
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
							if(vocab.containsKey(sp[i].toLowerCase())){
								if(!temp.containsKey(vocab.get(sp[i].toLowerCase())))
									temp.put(vocab.get(sp[i].toLowerCase()), 1);
								else{
									temp.put(vocab.get(sp[i].toLowerCase()), 
											temp.get(vocab.get(sp[i].toLowerCase()))+1);
								}
							}
						}
						String out="";
						int out_count=0;
						Iterator iter = temp.entrySet().iterator();
							while (iter.hasNext()) {
								Map.Entry entry = (Map.Entry) iter.next();
								Object key = entry.getKey();
								Object val = entry.getValue();
								out+=" "+key+":"+val;
								out_count++;
							}
						
						tw.write(out_count+out+"\n");
						System.out.print(out_count+out+"\n");
					}
					text1=text;
				}
			}
			breader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txread.close();
		tw.close();
	}
	
}
