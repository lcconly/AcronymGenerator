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
		
		//����1��ÿ������ȡһ���ֳ����������һ�����ʾ�Ҫ
		//getSubWord("", wordList,wordsMap);
		
		
		//����2�������������е�������
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
	//�жϵ���
	private void getSubWord(String word, ArrayList<String> wordList,Map<String, Integer> wordsMap){
		if(wordList.size() == 0){
			//�ж��ǲ���һ���ʣ�����ǣ��ͼ���
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
		//�̶�����ĸ
		if(charList.length() < length) return;
		getSubsequence(word+charList.substring(0, 1), charList.substring(1), length-1, which+1,wordsMap);
		if(!fixed.contains(Integer.valueOf(which))){//�̶���ĸһ��Ҫѡ��
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

