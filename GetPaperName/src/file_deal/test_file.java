package file_deal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class test_file {
	public static void main(String[] args) throws IOException {
		Map<String, Integer> wordsMap=new HashMap<String, Integer>();
		ArrayList<String> wordlist=new ArrayList<String>();
		ArrayList<Integer> intlist=new ArrayList<Integer>();
	    Scanner sc = new Scanner(System.in);
	    System.out.println("please input a sentence:");
	    String name = sc.nextLine();
	    String[] sp=name.split(" ");
	    for(int i=0;i<sp.length;i++){
	    	wordlist.add(sp[i].toLowerCase());
	    }
		File read_file=new File("sel_words.txt");
		if(!read_file.exists())
			try {
				throw new FileNotFoundException();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		@SuppressWarnings("resource")
		BufferedReader br=new BufferedReader(new FileReader(read_file));
		String temp=null;
		int count=0;
		while((temp=br.readLine())!=null){
			wordsMap.put(temp, count);
			count++;
		}
	    ALLAcronmy all=new ALLAcronmy(wordlist, intlist, 0, wordsMap);
	}
}
