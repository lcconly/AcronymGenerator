package gen_acronym;

import io.TextReader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.ujmp.core.treematrix.Tree;

import Trie.Trie;

public class AcronymSort {
	private Trie AllWords;
	private char[] vowel={'a','e','i','o','u'};
	private String originalName;
	private Map resultMap =new HashMap<String, Double>();
	private HashMap<String, String> LDAres=new HashMap<String, String>();
	private Trie LDAword;
	private ArrayList<String> acronym=new ArrayList<String>();
	public AcronymSort(ArrayList<String> res,String name,Trie allTrie,
			HashMap<String,String> LDAres,Trie LDAwords){
		AllWords=allTrie;
		//init();
		//Iterator<String> it =res.iterator();
		originalName=name;
		//resultMap.put(it.next(), 0);
		acronym=res;
		this.LDAres=LDAres;
		this.LDAword=LDAwords;
	}
	public int getMinIndex(int[] num){
		int min=999999999;
		int minIndex=0;
		for(int i=0;i<num.length;i++){
			if(min>num[i]){
				minIndex=i;
				min=num[i];
			}
		}
		return minIndex;
	}
	public int getClassId() {
		String[] sp=originalName.split(" ");
		int[] classCount=new int[30];
		//System.out.println(originalName.length());
		for(int i=0;i<sp.length;i++){
			//System.out.println("i:  "+i);
			if(LDAword.countWords(sp[i].toLowerCase())!=0){
				String[] numSp=LDAres.get(sp[i].toLowerCase()).split(" ");
				for(int j=0;j<30;j++){
					classCount[j]+=Integer.parseInt(numSp[j]);
				}
			}
		}
		for(int i=0;i<30;i++){
			if(originalName.length()!=0)
				classCount[i]=classCount[i]/originalName.length();
		}		
		return getMinIndex(classCount);
	}
	public boolean isVowel(char a){
		for(int i=0;i<5;i++){
			if(a==vowel[i])
				return true;
		}
		return false;
	} 
	public double VowToCon(String name){
		double VowCount=0,conCount=0;
		for(int i=0;i<name.length();i++){
			if(isVowel(name.charAt(i)))
				VowCount++;
			else
				conCount++;
		}
		if(conCount==0)
			return -1;
		return VowCount/(conCount+VowCount);
	}
	 public  int ld(String s, String t) {  
		 int d[][];  
		 int sLen = s.length();  
		 int tLen = t.length();  
		 int si;   
		 int ti;   
		 char ch1;
		 char ch2;
		 int cost;
		 if(sLen == 0) {
			 return tLen;
		 }  
	     if(tLen == 0) {
	    	 return sLen;  
	     }
	     d = new int[sLen+1][tLen+1];  
	     for(si=0; si<=sLen; si++) {
	    	 d[si][0] = si;  
	     }  
	     for(ti=0; ti<=tLen; ti++) {  
	    	 d[0][ti] = ti;  
	     }
	     for(si=1; si<=sLen; si++) {  
	        ch1 = s.charAt(si-1);  
	        for(ti=1; ti<=tLen; ti++) {  
	            ch2 = t.charAt(ti-1);  
	            if(ch1 == ch2) {  
	                cost = 0;  
	            } else {  
	                cost = 1;  
	            }  
	            d[si][ti] = Math.min(Math.min(d[si-1][ti]+1, d[si][ti-1]+1),d[si-1][ti-1]+cost);  
            }  
	     }  
	     return d[sLen][tLen];  
	}  
	      
	public double similarity(String src, String tar) {  
	    int ld = ld(src, tar);  
	    return 1 - (double) ld / Math.max(src.length(), tar.length());   
	}
	
	public int isWords(String word){
		if(AllWords.countWords(word.toLowerCase())!=0)
			return 1;
		else
			return 0;
	}
	
	public void calIndex(){
		double w1=0.0,w2=0.0,w3=0.0;
		Iterator<String> it =acronym.iterator();
		while (it.hasNext()) {
			String itNext=it.next();//w1元音辅音比例，w2相关度，w3是否成单词。
			w1=VowToCon(itNext);
			String[] sp=originalName.split(" ");
			int flag=1;
			//System.out.println(originalName.length());
			for(int i=0;i<sp.length;i++){
				//w2+=similarity(sp[i], itNext);
				//if(temp>w2)
				//	w2=temp;
				if(sp[i].toLowerCase().indexOf(itNext.toLowerCase())!=-1)
					flag=0;
			}
			//w2=w2/sp.length;
			int classId=getClassId();
			if(LDAword.countWords(itNext.toLowerCase())!=0){
				w2=(152582.0-Double.parseDouble(LDAres.get(itNext.toLowerCase()).split(" ")[classId]))/152582.0;
				//System.out.println(itNext.toLowerCase()+"  "+w2);	
			}
			else 
				w2=0;

			w3=isWords(itNext.toLowerCase());
			//System.out.println(w1+ " "+w2+ " "+ w3);
			if(flag==0)
				resultMap.put(itNext, 0.0);
			else
				resultMap.put(itNext, 0.1*w1+0.7*w2+0.2*w3);
			System.out.println(itNext.toLowerCase()+"  "+w1+"  "+w2+"  "+w3);
			w1=0.0;w2=0.0;w3=0.0;
		}
		
		
		List<Map.Entry<String, Double>> list_Data = 
    			new ArrayList<Map.Entry<String, Double>>(resultMap.entrySet());     
    	Collections.sort(list_Data, new Comparator<Map.Entry<String, Double>>()  
    	        {    
    	      public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2)  
    	      {  
    	        if ((o2.getValue() - o1.getValue())>0)  
    	          return 1;  
    	        else if((o2.getValue() - o1.getValue())==0)  
    	          return 0;  
    	        else   
    	          return -1;  
    	      }  });
    	int num=0;
    	if(list_Data.size()<=100)
    		num=list_Data.size();
    	else 
    		num=100;
    	for (int i = 0; i < num; i++) {
    	    String id = list_Data.get(i).toString();
    	    //if(id.split("=")[0])
    	    /*System.out.printf("%-15s",id.split("=")[0]);
    	    if((i+1)%4==0)
    	    	System.out.println();*/
    	    System.out.println(id);
    	}
    	
	}
	
}
