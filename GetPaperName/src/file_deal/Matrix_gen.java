package file_deal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.AbstractDocument.BranchElement;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
public class Matrix_gen {
	private String word_list="sel_words.txt";
	private String title="result_del.txt";
    private Map<Integer, String> wordsMap_temp=null;
	private Map<String, Integer> wordsMap=null;
    private Map<String, Double> resultMap=null;
    private Matrix count_Matrix=null;
    private Matrix U_Matrix=null;
    private Matrix S_Matrix=null;
    private Matrix V_Matrix=null;
    private Matrix result_Matrix=null;
    private SingularValueDecomposition s=null;
    public Matrix_gen(){
    	wordsMap=new HashMap<String, Integer>();
    	wordsMap_temp=new HashMap<Integer, String>();
    	resultMap=new HashMap<String, Double>();
    	count_Matrix=new Matrix(6733,9728);
    	//count_Matrix=new Matrix(23,13);

    } 
    private Matrix get_Matrix(){
    	return count_Matrix;
    }
    private Map<String, Integer> get_wordsMap(){
    	return wordsMap;
    }
    private Matrix get_U(){
    	return U_Matrix;
    }
    private Matrix get_S(){
    	return S_Matrix;
    }
    private Matrix get_V(){
    	return V_Matrix;
    }
    private Matrix get_result_Matrix(){
    	return result_Matrix;
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
    public void Mat_read() throws IOException{
		File read_file=new File(title);
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
            String[] spirt=null;
            String[] spirt_1=null;
            String[] spirt_2=null;
            spirt=temp.split(" ");
            for(int i=0;i<spirt.length;i++){
            	if(spirt[i].contains(":")){
            		spirt_1=spirt[i].split(":");
            		for(int j=0;j<spirt_1.length;j++){
            			if(spirt_1[j].contains(",")){
            				spirt_2=spirt_1[j].split(",");
            				for(int k=0;k<spirt_2.length;k++){
            					if(wordsMap.containsKey(spirt_2[k].toLowerCase())){
            						count_Matrix.set(wordsMap.get(spirt_2[k].toLowerCase()), count,
       								count_Matrix.get(wordsMap.get(spirt_2[k].toLowerCase()), count)+1);
            					}
            				}
            				spirt_2=null;
            				continue;
            			}
    					if(wordsMap.containsKey(spirt_1[j].toLowerCase())){
    						count_Matrix.set(wordsMap.get(spirt_1[j].toLowerCase()), count,
								count_Matrix.get(wordsMap.get(spirt_1[j].toLowerCase()), count)+1);
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
            					if(wordsMap.containsKey(spirt_2[k].toLowerCase())){
            						count_Matrix.set(wordsMap.get(spirt_2[k].toLowerCase()), count,
       								count_Matrix.get(wordsMap.get(spirt_2[k].toLowerCase()), count)+1);
            					}
            				}
            				spirt_2=null;
            				continue;
            			}
    					if(wordsMap.containsKey(spirt_1[j].toLowerCase())){
    						count_Matrix.set(wordsMap.get(spirt_1[j].toLowerCase()), count,
								count_Matrix.get(wordsMap.get(spirt_1[j].toLowerCase()), count)+1);
    					}
            		}
            		spirt_1=null;
            		continue;	
            	}  
            	if(spirt[i].contains("-")){
            		spirt_1=spirt[i].split("-");
            		for(int j=0;j<spirt_1.length;j++){
    					if(wordsMap.containsKey(spirt_1[j].toLowerCase())){
    						count_Matrix.set(wordsMap.get(spirt_1[j].toLowerCase()), count,
								count_Matrix.get(wordsMap.get(spirt_1[j].toLowerCase()), count)+1);
    					}
            		}
            		spirt_1=null;
            		continue;	
            	}
				if(wordsMap.containsKey(spirt[i].toLowerCase())){
					count_Matrix.set(wordsMap.get(spirt[i].toLowerCase()), count,
						count_Matrix.get(wordsMap.get(spirt[i].toLowerCase()), count)+1);
				}
            }
			count++;
		}
//		count_Matrix.print(12, 13);
    }
    public void SVD_cal() throws FileNotFoundException{
		File write_file_U=new File("U.txt");
        FileOutputStream out_u=new FileOutputStream(write_file_U,true);        
        PrintStream p_u=new PrintStream(out_u);
		File write_file_S=new File("S.txt");
        FileOutputStream out_S=new FileOutputStream(write_file_S,true);        
        PrintStream p_S=new PrintStream(out_S);
		File write_file_V=new File("V.txt");
        FileOutputStream out_V=new FileOutputStream(write_file_V,true);        
        PrintStream p_V=new PrintStream(out_V);
    	System.out.println("SVD_cal");
    	s=count_Matrix.transpose().svd();
    	U_Matrix=s.getU();
    	//U_Matrix.print(13, 13);
    	//System.out.println(U_Matrix.getRowDimension()+"  "+U_Matrix.getColumnDimension());
    	for(int i=0;i<U_Matrix.getRowDimension();i++)
    		for(int j=0;j<U_Matrix.getColumnDimension();j++){
    			if(j<=U_Matrix.getColumnDimension()-2){
    				//System.out.println(i+"  "+j);
    				//System.out.println(U_Matrix.getRowDimension()+"  "+U_Matrix.getColumnDimension());
    				p_u.print(U_Matrix.get(i, j)+" ");
    			}
    			else 
    				p_u.println(U_Matrix.get(i, j));
    		}
    	V_Matrix=s.getV();
    	for(int i=0;i<V_Matrix.getRowDimension();i++)
    		for(int j=0;j<V_Matrix.getColumnDimension();j++){
    			if(j<=V_Matrix.getColumnDimension()-2)
    				p_V.print(V_Matrix.get(i, j)+" ");
    			else
    				p_V.println(V_Matrix.get(i, j));
    		}
    	S_Matrix=s.getS();
    	//S_Matrix.print(13, 13);

    	for(int i=0;i<S_Matrix.getRowDimension();i++)
    		for(int j=0;j<S_Matrix.getColumnDimension();j++){
    			if(j<=S_Matrix.getColumnDimension()-2)
    				p_S.print(S_Matrix.get(i, j)+" ");
    			else
    				p_S.println(S_Matrix.get(i, j));
    		}

       	System.out.println("SVD_end");   	
    }
    public void ReduceDim(){
    	for(int i=3;i<S_Matrix.getColumnDimension();i++){
    		S_Matrix.set(i, i, 0);
    	}
    }
    public void Mul_Matrix(){
    	System.out.println("MUL_Matrix");
    	result_Matrix=U_Matrix.times(S_Matrix).times(V_Matrix.transpose());
    }
    /*public void get_names(){
    	for(int i=0;i<29659;i++){
    		resultMap.put(wordsMap_temp.get(i), result_Matrix.get(i, 29659-1));
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
    	for (int i = 0; i < list_Data.size(); i++) {
    	    String id = list_Data.get(i).toString();
    	    System.out.println(id);
    	}
    }*/
}
