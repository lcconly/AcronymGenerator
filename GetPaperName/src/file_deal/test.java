package file_deal;

import java.io.FileNotFoundException;

import file_deal.GetName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Jama.Matrix;

public class test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		/*Matrix_gen a=new Matrix_gen();
		try {
			a.Map_init();
			a.Mat_read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		a.SVD_cal();
		//a.Mul_Matrix();
		//a.get_names();
	*/
		GetName a=new GetName();
		ArrayList<String> wordlist=new ArrayList<String>();
		ArrayList<Integer> intlist=new ArrayList<Integer>();
	    Scanner sc = new Scanner(System.in);
	    while(true){
		    System.out.println("please input a sentence:");
		    String name = sc.nextLine();
		    Matrix name_ma=new Matrix(1,6733);
		    String[] name_spirt=name.split(" ");
		    for(int i=0;i<name_spirt.length; i++){
		    	if(a.getwordsMap().containsKey(name_spirt[i].toLowerCase())){
		    		name_ma.set(0,a.getwordsMap().get(name_spirt[i].toLowerCase()) , 
		    				name_ma.get(0, a.getwordsMap().get(name_spirt[i].toLowerCase()))+1);
		    		//System.out.println("i= "+i);
		    	}
	    		wordlist.add(name_spirt[i].toLowerCase());
		    }
		    //ALLAcronmy all=new ALLAcronmy(wordlist, intlist, 0, a.getwordsMap());
	
		    Matrix result=new Matrix(1,6733);
		    //result=name_ma.times(a.getV().transpose()).times(a.getS().inverse());
		    double num=0;
		    for(int i=0;i<6733;i++){
		    	for(int j=0;j<6733;j++){
		    		num=num+(a.getV().get(j, i))*(name_ma.get(0, j));	
		    		/*if(name_ma.get(0,j)!=0&&a.getV().get(j, i)!=0)
		    			System.out.println("success");*/
		    	}
		    	//if(num!=0)
		    	//	System.out.println(num);
	    		if(a.getS().get(i, i)!=0){
		    		result.set(0, i, num*(1/a.getS().get(i,i)));
		    	}
		    	else {
		    		result.set(0, i, 0);
				}
		    	num=0;
		    }
		    Map resultMap =new HashMap<String, Double>();
		    for(int i=0;i<6733;i++){
	    		resultMap.put(a.getwordsMapTemp().get(i), result.get(0,i));
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
	    	for (int i = 0; i < 100; i++) {
	    	    String id = list_Data.get(i).toString();
	    	    //if(all.getAcronmy().contains(id.split("=")[0]))
	    	    System.out.println(id);
	    	}
	    }
	}

}
