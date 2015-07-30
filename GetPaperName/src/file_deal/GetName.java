package file_deal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Jama.Matrix;

public class GetName {
	private String word_list="sel_words.txt";
    private Map<Integer, String> wordsMap_temp=null;
	private Map<String, Integer> wordsMap=null;
    public Matrix V_Matrix=new Matrix(6733,6733);
    public Matrix S_Matrix=new Matrix(6733,6733);
    public GetName() throws IOException {
		// TODO Auto-generated constructor stub
    	wordsMap=new HashMap<String, Integer>();
    	wordsMap_temp=new HashMap<Integer, String>();
    	Map_init();
		set_matrix(V_Matrix, 6733, 6733, "V.txt");
		set_matrix(S_Matrix, 6733, 6733, "S.txt");
    }
    public Map<String, Integer> getwordsMap(){
    	return wordsMap;
    }
    public Map<Integer, String> getwordsMapTemp(){
    	return wordsMap_temp;
    }
    public Matrix getV(){
    	return V_Matrix;
    }
    public Matrix getS(){
    	return S_Matrix;
    }
    public void Map_init() throws IOException{
		File read_file=new File(word_list);
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
			wordsMap_temp.put(count, temp);
			count++;
		}
    }
    public void set_matrix(Matrix a,int line,int col,String txtname) throws IOException{
    	File read_file=new File(txtname);
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
			String[] spirt=temp.split(" ");
			for(int i=0;i<spirt.length;i++)
				a.set(count, i, Double.parseDouble(spirt[i]));
			count++;
		}
    }
}
