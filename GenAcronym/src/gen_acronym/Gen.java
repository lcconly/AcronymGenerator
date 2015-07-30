package gen_acronym;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import stemmer.*;

import javax.swing.text.AbstractDocument.BranchElement;

import Trie.Trie;
import io.*;
public class Gen{
	private String name;
	private ArrayList<String> resAcr;//=new ArrayList<String>();
	//private ArrayList<String> noun=new ArrayList<String>();
	//private ArrayList<String> adj=new ArrayList<String>();
	//private ArrayList<String> adv=new ArrayList<String>();
	//private ArrayList<String> verb=new ArrayList<String>();
	//private ArrayList<String> sense=new ArrayList<String>();
	//private ArrayList<String> prep=new ArrayList<String>();
	private Trie noun=new Trie();
	private Trie adj=new Trie();
	private Trie adv=new Trie();
	private Trie verb=new Trie();
	private Trie sense=new Trie();
	private Trie prep=new Trie();
	private Trie AllWords=new Trie();
	private Trie LDAWords=new Trie();
	private HashMap<String , String> LDAResult=new HashMap<String, String>();
	private String[] words;
	public Gen(){
		init_index("dic/index_del.noun", noun);
		init_index("dic/index_del.adj", adj);
		init_index("dic/index_del.adv", adv);
		init_index("dic/index_del.verb", verb);
		init_index("dic/index_del.sense", sense);
		init_index("dic/index_del.prep", prep);
		init_index("LDA_result/ComVocabStop.txt", LDAWords);
		init_Map("LDA_result/ComClass.txt", LDAResult);
		init();
	}
	public void init(){
		init_index("dic/index_del.noun", AllWords);
		init_index("dic/index_del.adj", AllWords);
		init_index("dic/index_del.adv", AllWords);
		init_index("dic/index_del.verb", AllWords);
		init_index("dic/index_del.sense", AllWords);
	}

	public void run(String name){
		this.name=name;
		words=this.name.split(" ");
		resAcr=new ArrayList<String>();
		//System.out.println("end init");
		FirstLetter("", 0);
		preAndSuf_2("", 0);//, words.length-1);
		preAndSuf();
		preposition();
		changeAcr();
	}
	public Trie getLDAWords(){
		return LDAWords;
	}
	public HashMap<String, String> getLDAResult(){
		return LDAResult;
	}
	public Trie getALLwords(){
		return AllWords;
	}
	
	public String getName(){
		return name;
	}
	public ArrayList<String> getRes(){
		return resAcr;
	}
	public void printRes(){
		System.out.println(name);
		for(int i=0;i<resAcr.size();i++){
			System.out.println(resAcr.get(i));
		}
	}
	public void init_index(String filename,Trie noun) {
		TextReader tr=null;
		try {
			tr=new TextReader(filename);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String temp=null;
		try {
			while((temp=tr.getReader().readLine())!=null)
				//if(temp)
				noun.addWord(temp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tr.close();
	}
	public void init_Map(String filename,HashMap<String, String> noun) {
		TextReader tr=null;
		try {
			tr=new TextReader(filename);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String temp=null;
		try {
			while((temp=tr.getReader().readLine())!=null)
				//if(temp)
				noun.put(temp.split(" ")[0],temp.substring(temp.split(" ")[0].length()+1, 
						temp.length()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tr.close();
	}
	public void preAndSuf(){
		//int[] strCount=new int[30];//存储字符串标记，0为adj，1为noun,2为介词，-1为空
		int[] adjSt=new int[30];
		int[] advSt=new int[30];
		int[] nounSt=new int[30];
		int adjCount=0,advCount=0,nounCount=0;
		String str="";
		//统计名词、动词、副词个数
		for(int i=0;i<words.length;i++){
			if(adj.countWords(words[i].toLowerCase())!=0){
				adjSt[adjCount]=i;
				adjCount++;
			}
			else if(noun.countWords(words[i].toLowerCase())!=0){
				nounSt[nounCount]=i;
				nounCount++;
			}
			else if(adv.countWords(words[i].toLowerCase())!=0){
				advSt[advCount]=i;
				advCount++;
			}
		}
		/*
		for(int i=0;i<words.length;i++){
			Stemmer stem=new Stemmer();
			stem.add(words[i].toCharArray(), words[i].length());
			stem.stem();
			System.out.println(stem.toString());
		}*/
		for(int k=0;k<nounCount;k++){
			//名词前缀
			for(int i=3;i<=4;i++){
				for(int j=0;j<adjCount;j++){
					if(adjSt[j]<=nounSt[k])
						str+=words[adjSt[j]].charAt(0);
				}
				if(words[nounSt[k]].length()<=i)
					str+=words[nounSt[k]];
				else {
					Stemmer stem=new Stemmer();
					stem.add(words[nounSt[k]].toLowerCase().toCharArray(), words[nounSt[k]].length());
					stem.stem();
					//System.out.println(stem.toString());
					if(stem.toString().length()<6)
						str+=stem.toString();
					else 
						str+=words[nounSt[k]].substring(0, i);
				}
				if(!resAcr.contains(str)&&str.length()>4)
					resAcr.add(str);
				str="";
			}
			//名词后缀
			for(int i=3;i<=4;i++){
				for(int j=0;j<adjCount;j++){
					if(adjSt[j]<=nounSt[k])
						str+=words[adjSt[j]].charAt(0);
				}
				if(words[nounSt[k]].length()<=i)
					str+=words[nounSt[k]];
				else
					str+=words[nounSt[k]].substring(words[nounSt[k]].length()-i, words[nounSt[k]].length());
				if(!resAcr.contains(str)&&str.length()>4
						&&str.substring(str.length()-3, str.length())!="ing")//&&
						//noun.countWords(str.substring(str.length()-3, str.length()))!=0)
					resAcr.add(str);
				str="";
			}
		}
		
	}
	//含介词
	public void preposition(){
		String[] str=new String[2];
		str[0]="";
		str[1]="";
		for(int m=0;m<words.length;m++){
			if(prep.countWords(words[m].toLowerCase())!=0){
				//System.out.println("1111");
				String[] sp=name.split(words[m]);
				String[] sp1=sp[0].split(" ");
				String[] sp2=sp[1].split(" ");
				for(int i=0;i<=sp1.length-1;i++){
					if((sp1.length==1||noun.countWords(sp1[i].toLowerCase())!=0||sense.countWords(sp1[i].toLowerCase())!=0)&&adj.countWords(sp1[i].toLowerCase())==0){
						//System.out.println("1111");
						for(int j=3;j<=6;j++){
							//前缀
							if(sp1[i].length()<=j&&sp1[i].matches("^[a-zA-Z]+"))
								str[0]+=sp1[i];
							else {
								Stemmer stem=new Stemmer();
								stem.add(sp1[i].toLowerCase().toCharArray(), sp1[i].length());
								stem.stem();
								//System.out.println(stem.toString());
								if(stem.toString().length()<6&&sp1[i].matches("^[a-zA-Z]+"))
									str[0]+=stem.toString();
								else {
									//System.out.println(sp1[i].substring(0, j));
									str[0]+=sp1[i].substring(0, j);
									//if(str[0].contains("null"))
									//	System.out.println("-------");
								}
							}
							//后缀
							if(sp1[i].length()<=j&&sp1[i].matches("^[a-zA-Z]+"))
								str[1]+=sp1[i];
							else if(!sp1[i].equals("")&&noun.countWords(sp1[i].substring(sp1[i].length()-j, sp1[i].length()))!=0)
								str[1]+=sp1[i].substring(sp1[i].length()-j, sp1[i].length());
//--------------------------//名词前后缀+名词前后缀
							for(int k=0;k<sp2.length;k++){
								if((noun.countWords(sp2[k].toLowerCase())!=0||sense.countWords(sp2[k].toLowerCase())!=0)&&adj.countWords(sp2[k].toLowerCase())==0){
									//前缀
									for(int n=3;n<=4;n++){
										if(sp2[k].length()<=n&&sp2[k].matches("^[a-zA-Z]+")){
											if(!resAcr.contains(str[0]+sp2[k]))
												resAcr.add(str[0]+sp2[k]);
											if(!resAcr.contains(str[1]+sp2[k]))
												resAcr.add(str[1]+sp2[k]);										
										}
										else if(sp2[k].matches("^[a-zA-Z]+")){
											Stemmer stem=new Stemmer();
											stem.add(sp2[k].toLowerCase().toCharArray(), sp2[k].length());
											stem.stem();
											//System.out.println(stem.toString());
											if(stem.toString().length()<6){
												if(!resAcr.contains(str[0]+stem.toString()))
													resAcr.add(str[0]+stem.toString());
												if(!resAcr.contains(str[1]+stem.toString()))
													resAcr.add(str[1]+stem.toString());								
											}
											else {
												if(!resAcr.contains(str[0]+sp2[k].substring(0, n)))
													resAcr.add(str[0]+sp2[k].substring(0, n));
												if(!resAcr.contains(str[1]+sp2[k].substring(0, n)))
													resAcr.add(str[1]+sp2[k].substring(0, n));										
											}
										}
									}
									//后缀
									for(int n=3;n<=4;n++){
										if(sp2[k].length()<=n&&sp2[k].matches("^[a-zA-Z]+")){
											if(!resAcr.contains(str[0]+sp2[k]))
												resAcr.add(str[0]+sp2[k]);
											if(!resAcr.contains(str[1]+sp2[k]))
												resAcr.add(str[1]+sp2[k]);										
										}
										else if(sp2[k].matches("^[a-zA-Z]+")&&noun.countWords(sp2[k].substring(sp2[k].length()-n, sp2[k].length()))!=0){
											if(!resAcr.contains(str[0]+sp2[k].substring(sp2[k].length()-n, sp2[k].length())))
												resAcr.add(str[0]+sp2[k].substring(sp2[k].length()-n, sp2[k].length()));
											if(!resAcr.contains(str[1]+sp2[k].substring(sp2[k].length()-n, sp2[k].length())))
												resAcr.add(str[1]+sp2[k].substring(sp2[k].length()-n, sp2[k].length()));										

										}
									}							
								}
							}
//--------------------------//名词前后缀+介词后首字母
							String strFirst="";
							for(int k=0;k<sp2.length;k++){
								if(sp2[k].matches("^[a-zA-Z]+"))
								//System.out.println(sp2[k]);
									strFirst+=sp2[k].charAt(0);
							}
							//System.out.println("str "+strFirst);
							if(!resAcr.contains(str[0]+strFirst)&&!strFirst.equals(""))
								resAcr.add(strFirst+str[0]);
							if(!resAcr.contains(str[1]+strFirst)&&!strFirst.equals(""))
								resAcr.add(strFirst+str[1]);
							strFirst="";
							str[0]="";
							str[1]="";
						}
					}
				}
			}
		}
	}
	public void changeAcr(){
		int tag=1,temp=-1;
		String str="";
		for(int i=0;i<words.length;i++){
			for(int j=0;j<words[i].length();j++){
				if(words[i].charAt(j)-'a'>=0&&words[i].charAt(j)-'a'<26){
					tag=0;
					break;
				}
			}
			if(tag==1)
				temp=i;
			tag=1;
		}
		for(int i=0;i<temp;i++){
			if((sense.countWords(words[i].toLowerCase())!=0||adv.countWords(words[i].toLowerCase())!=0)&&words[i].length()>4)
				str+=words[i].charAt(0);
		}
		if(temp!=-1){
			str+=words[temp];
			/*for(int i=temp+1;i<words.length;i++){
				if((adj.contains(words[i])||adv.contains(words[i]))&&words[i].length()>4)
					str+=words[i].charAt(0);
			}*/
			resAcr.add(str);
		}
	}
	//首字母成单词
	public void FirstLetter(String word,int i){
		/*if((sense.countWords(word.toLowerCase())!=0||noun.countWords(word.toLowerCase())!=0||adj.countWords(word.toLowerCase())!=0||adv.countWords(word.toLowerCase())!=0||
				verb.countWords(word.toLowerCase())!=0||AllWords.countWords(word.toLowerCase())!=0)&&(!resAcr.contains(word))&&word.length()>=3){
			resAcr.add(word);
		}*/
		if(!resAcr.contains(word)&&word.length()>=3)
			resAcr.add(word);
		if(i>=words.length)
			return;
		int num=0;
		if(words[i].length()<3)
			num=words[i].length();
		else {
			num=3;
		}
		for(int j=0;j<num;j++){
			String newWord=new String(word.toString());
			newWord+=words[i].substring(0, j);
			FirstLetter(newWord, i+1);
		}
	}
	//前缀后缀成单词
	public void preAndSuf_2(String word,int i){//,int j){
		//if((sense.countWords(word.toLowerCase())!=0||noun.countWords(word.toLowerCase())!=0||adj.countWords(word.toLowerCase())!=0||adv.countWords(word.toLowerCase())!=0||
		//		verb.countWords(word.toLowerCase())!=0||AllWords.countWords(word.toLowerCase())!=0)&&(!resAcr.contains(word))&&word.length()>=3){
		if(!resAcr.contains(word)&&word.length()>=3)
			resAcr.add(word);
		//}
		if(i>=words.length) return;
		int num1=0;//,num2=0;
		if(words[i].length()<2)
			num1=words[i].length();
		else {
			num1=2;
		}
		/*if(words[i].length()<3)
			num2=words[j].length();
		else {
			num2=3;
		}*/
		for(int m=0;m<num1;m++){
			String newWord=new String(word.toString());
			newWord+=words[i].substring(0, m);
			for(int n=0;n<num1;n++){
				newWord+=words[i].substring(words[i].length()-n, words[i].length());
				preAndSuf_2(newWord, i+1);
			}
		}
	}

}
