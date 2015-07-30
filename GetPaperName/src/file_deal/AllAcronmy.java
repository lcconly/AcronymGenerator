package file_deal;
import java.util.*;

public class ALLAcronmy {
	private ArrayList<String> acronmyList = new ArrayList<String>();
	private ArrayList<Integer> fixed;
	private int categ = 0;
	private boolean[] hasBeenFirst = new boolean[26];
	
	public ALLAcronmy(){
		
	}
	public ALLAcronmy(ArrayList<String> wordList, ArrayList<Integer> fixed, int categ,Map<String, Integer> wordsMap){
		
		//方法1：每个单词取一个字出来，如果是一个单词就要
		//getSubWord("", wordList,wordsMap);
		
		
		//方法2：基本遍历所有的子序列
		setFixed(fixed);
		
		setCateg(categ);
		String str = new String("");
		for(int i = 0; i < wordList.size(); i++){
			str += wordList.get(i);
		}
		int len = wordList.size();
		if(len <= 4) len+=3;
		else if(len > 8) len = 7;
		for(int length = len-3; length < len+3; length++){
			for(int i = 0; i < 26; i++){
				hasBeenFirst[i] = false;
			}
			getSubsequence("", str, length, 0,wordsMap);
		}
		Iterator<String> iter = acronmyList.iterator();
		while(iter.hasNext()){
			System.out.println(iter.next());
		}
	}
	//判断单词
	private void getSubWord(String word, ArrayList<String> wordList,Map<String, Integer> wordsMap){
		if(wordList.size() == 0){
			//判断是不是一个词，如果是，就加入
			if(wordsMap.containsKey(word))
				acronmyList.add(word);
			return;
		}

		boolean[] hasBeenFirst = new boolean[26];
		for(int j = 0; j < 26; j++){
			hasBeenFirst[j] = false;
		}
		for(int i = 0; i < wordList.get(0).length(); i++){
			if(hasBeenFirst[wordList.get(0).charAt(i)-'a'] ==  true){
				continue;
			}else{
				hasBeenFirst[wordList.get(0).charAt(i)-'a'] = true;
			}
			getSubWord(word+wordList.get(0).charAt(i),  new ArrayList<String>(wordList.subList(1, wordList.size())), wordsMap);
		}
	}
	private void getSubsequence(String word, String charList, int length, int which,Map<String, Integer> wordsMap){
		if(length == 0){
			if(wordsMap.containsKey(word)){
				acronmyList.add(word);
			}
			return;
		}
		//固定首字母
		if(charList.length() < length) return;
		getSubsequence(word+charList.substring(0, 1), charList.substring(1), length-1, which+1,wordsMap);
		if(!fixed.contains(Integer.valueOf(which))){//固定字母一定要选！
			getSubsequence(word, charList.substring(1), length, which+1,wordsMap);
		}
	}

	private void setFixed(ArrayList<Integer> fixed){
		this.fixed = fixed;
	}
	private void setCateg(int categ){
		this.categ = categ;
	}
	public ArrayList<String> getAcronmy(){
		return acronmyList;
	}
}

