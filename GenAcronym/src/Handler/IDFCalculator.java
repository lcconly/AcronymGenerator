 package Handler;

import io.TextWriter;

import java.util.ArrayList;

public class IDFCalculator {
	final int TEXTNUM = 148688;
	final int WORDNUM = 27544;
	final int CLASSNUM = 10;
	
	private int[] count = new int[WORDNUM];
	private double[] idf = new double[WORDNUM];
	
	public IDFCalculator(ArrayList<String> trainingSet, ArrayList<String> words){
		init();
		TextWriter te=new TextWriter("IDF.txt");
		for(int i = 0; i < trainingSet.size(); i++){
			System.out.println("num: "+ i);
			String temp = trainingSet.get(i);
			for(int j = 0; j < WORDNUM; j++){
				if(temp.contains(words.get(j))){
					count[j]++;
				}
			}
		}
		for(int i = 0; i < WORDNUM; i++){
			idf[i] = Math.log((double)TEXTNUM/(double)(count[i]+1));
			te.write(idf[i]+"\n");
		}
	}
	
	public double[] getIDF(){
		return idf;
	}
	
	private void init(){
		for(int i = 0; i < WORDNUM; i++){
			count[i] = 0;
		}
	}
}
