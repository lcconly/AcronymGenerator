package gen_acronym;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import stemmer.*;

import javax.swing.text.AbstractDocument.BranchElement;

import Trie.Trie;
import io.*;
public class temp{
	private String name;
	private ArrayList<String> resAcr=new ArrayList<String>();
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
	private String[] words;
	public temp(){
		
	}
	public temp(String name){
		this.name=name;
		words=this.name.split(" ");
		init_index("dic/index_del.noun", noun);
		init_index("dic/index_del.adj", adj);
		init_index("dic/index_del.adv", adv);
		init_index("dic/index_del.verb", verb);
		init_index("dic/index_del.sense", sense);
		init_index("dic/index_del.prep", prep);
		System.out.println("end init");
		preAndSuf();
		preposition();
		changeAcr();
		FirstLetter("", 0);
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
			for(int i=3;i<=6;i++){
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
			for(int i=3;i<=6;i++){
				for(int j=0;j<adjCount;j++){
					if(adjSt[j]<=nounSt[k])
						str+=words[adjSt[j]].charAt(0);
				}
				if(words[nounSt[k]].length()<=i)
					str+=words[nounSt[k]];
				else
					str+=words[nounSt[k]].substring(words[nounSt[k]].length()-i, words[nounSt[k]].length());
				if(!resAcr.contains(str)&&str.length()>4
						&&str.substring(str.length()-3, str.length())!="ing")
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
				String[] sp=name.split(words[m]);
				String[] sp1=sp[0].split(" ");
				String[] sp2=sp[1].split(" ");
				for(int i=0;i<=sp1.length-1;i++){
					if((noun.countWords(sp1[i].toLowerCase())!=0||sense.countWords(sp1[i].toLowerCase())!=0)&&adj.countWords(sp1[i].toLowerCase())==0){
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
							else if(!sp1[i].equals(""))
								str[1]+=sp1[i].substring(sp1[i].length()-j, sp1[i].length());
//--------------------------//名词前后缀+名词前后缀
							for(int k=0;k<sp2.length;k++){
								if((noun.countWords(sp2[k].toLowerCase())!=0||sense.countWords(sp2[k].toLowerCase())!=0)&&adj.countWords(sp2[k].toLowerCase())==0){
									//前缀
									for(int n=3;n<=6;n++){
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
									for(int n=3;n<=6;n++){
										if(sp2[k].length()<=n&&sp2[k].matches("^[a-zA-Z]+")){
											if(!resAcr.contains(str[0]+sp2[k]))
												resAcr.add(str[0]+sp2[k]);
											if(!resAcr.contains(str[1]+sp2[k]))
												resAcr.add(str[1]+sp2[k]);										
										}
										else if(sp2[k].matches("^[a-zA-Z]+")){
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
		if((sense.countWords(word.toLowerCase())!=0||noun.countWords(word.toLowerCase())!=0||adj.countWords(word.toLowerCase())!=0||adv.countWords(word.toLowerCase())!=0||
				verb.countWords(word.toLowerCase())!=0)&&(!resAcr.contains(word))&&word.length()>3){
			resAcr.add(word);
		}
		if(i>=words.length)
			return;
		int num=0;
		if(words[i].length()<2)
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
}
