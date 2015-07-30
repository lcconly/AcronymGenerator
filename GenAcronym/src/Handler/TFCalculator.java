package Handler;

import io.TextWriter;

import java.util.ArrayList;
import java.util.HashSet;

public class TFCalculator {
	final int TEXTNUM = 148688;
	final int WORDNUM = 27544;
	final int CLASSNUM = 5;
	//private int TF[][] = new int[TEXTNUM][WORDNUM+2];
	//private int count[] = new int[CLASSNUM];
	private ArrayList<Integer> intsplit = new ArrayList<Integer>();
	
//	private double[][]tfidf = new double[TEXTNUM][WORDNUM+2];
	
	public TFCalculator(ArrayList<String> trainingSet, ArrayList<String> words,double[] idf){
		//System.out.println("===========TF-IDF Calculator=======");
		init();
		int count=0;
		TextWriter tx=new TextWriter("tfidf.txt");
		for(int m = 0; m < trainingSet.size(); m++){
			System.out.println("num: "+m);
			/*String[] temp = trainingSet.get(i).split("\t");
			if(!label.equals(temp[0])){
				index++;
				label = temp[0];
				intsplit.add(new Integer(i));
			}
			TF[i][WORDNUM] = intsplit.size();
			if(temp.length<=1){
				continue;
			}
			String[] word = temp[1].split(" ");
			count[index] += word.length;
			
			for(int j = 0; j < word.length; j++){
				TF[i][words.indexOf(word[j])]++;
			}*/
            String[] spirt=null;
            String[] spirt_1=null;
            String[] spirt_2=null;
            spirt=trainingSet.get(m).split(" ");
            for(int i=0;i<spirt.length;i++){
            	if(spirt[i].contains(":")){
            		spirt_1=spirt[i].split(":");
            		for(int j=0;j<spirt_1.length;j++){
            			if(spirt_1[j].contains(",")){
            				spirt_2=spirt_1[j].split(",");
            				for(int k=0;k<spirt_2.length;k++){
            					if(words.contains(spirt_2[k].toLowerCase())){
            						//TF[i][words.indexOf(spirt_2[k].toLowerCase())]++;
            						tx.write(m+" "+words.indexOf(spirt_2[k].toLowerCase())+" "+
            								idf[words.indexOf(spirt_2[k].toLowerCase())]+"\n");
            						count++;
            					}
            				}
            				spirt_2=null;
            				continue;
            			}
    					if(words.contains(spirt_1[j].toLowerCase())){
    						//TF[i][words.indexOf(spirt_1[j].toLowerCase())]++;
    						tx.write(m+" "+words.indexOf(spirt_1[j].toLowerCase())+" "+
    								idf[words.indexOf(spirt_1[j].toLowerCase())]+"\n");

    						count++;
    					}
            		}
            		spirt_1=null;
            		continue;	
            	}
            	if(spirt[i].contains(",")){
            		spirt_1=spirt[i].split(",");
            		for(int j=0;j<spirt_1.length;j++){
            			if(spirt_1[j].contains(":")){
            				spirt_2=spirt_1[j].split(":");
            				for(int k=0;k<spirt_2.length;k++){
            					if(words.contains(spirt_2[k].toLowerCase())){
            						//tflist[count]=new SparseList();
            						tx.write(m+" "+words.indexOf(spirt_2[k].toLowerCase())+" "+
            								idf[words.indexOf(spirt_2[k].toLowerCase())]+"\n");
            						count++;
            					}
            				}
            				spirt_2=null;
            				continue;
            			}
    					if(words.contains(spirt_1[j].toLowerCase())){
    						//TF[i][words.indexOf(spirt_1[j].toLowerCase())]++;
    						tx.write(m+" "+words.indexOf(spirt_1[j].toLowerCase())+" "+
    								idf[words.indexOf(spirt_1[j].toLowerCase())]+"\n");
    						count++;
    					}
            		}
            		spirt_1=null;
            		continue;	
            	}  
            	if(spirt[i].contains("-")){
            		spirt_1=spirt[i].split("-");
            		for(int j=0;j<spirt_1.length;j++){
    					if(words.contains(spirt_1[j].toLowerCase())){
    						//TF[i][words.indexOf(spirt_1[j].toLowerCase())]++;
    						tx.write(m+" "+words.indexOf(spirt_1[j].toLowerCase())+" "+
    								idf[words.indexOf(spirt_1[j].toLowerCase())]+"\n");
    						count++;
    						}
            		}
            		spirt_1=null;
            		continue;	
            	}
				if(words.contains(spirt[i].toLowerCase())){
					//TF[i][words.indexOf(spirt_1[i].toLowerCase())]++;
					tx.write(m+" "+words.indexOf(spirt[i].toLowerCase())+" "+
							idf[words.indexOf(spirt[i].toLowerCase())]+"\n");
					count++;
				}
            }

		}
		System.out.println(count);
		//intsplit.add(new Integer(TEXTNUM));
	}
	
	private void init(){

	}
}
