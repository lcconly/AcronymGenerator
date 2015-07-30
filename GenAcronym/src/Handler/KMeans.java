package Handler;

import io.TextWriter;

import java.util.ArrayList;
import java.util.Random;

import org.ujmp.core.matrix.SparseMatrix;

public class KMeans {
	final int TEXTNUM = 152566;
	final int WORDNUM = 27544;
	final int CLASSNUM = 10;

	private int B[] = new int[TEXTNUM];
	private int count[] = new int[CLASSNUM];
	private double[][] clusterCenter = new double[CLASSNUM][WORDNUM+2];
	
	public KMeans(SparseMatrix alltext){
		init(alltext);
		
		int[] count_temp = new int[CLASSNUM];
		boolean again = true;
		while(again){
			again = false;
			for(int i = 0; i < alltext.getRowCount(); i++){
				double max = 0;
				int max_j = 0;
				for(int j = 0; j < CLASSNUM; j++){
					alltext.setAsDouble(similar(clusterCenter[j],alltext,i),i, WORDNUM+1);
					if(alltext.getAsDouble(i,WORDNUM+1) > max){
						max = alltext.getAsDouble(i,WORDNUM+1);
						max_j = j;
					}
				}
				B[i] = max_j+1;
				count_temp[max_j]++;
			}

			for(int i = 0; i < CLASSNUM; i++){
				if(count[i] != count_temp[i]){
					count[i] = count_temp[i];
					again = true;
				}
				count_temp[i] = 0;
			}
			if(again){
				//CHOOSE ANOTHER CLUSTER CENTERS
				for(int i = 0; i < CLASSNUM; i++){
					for(int j = 0; j < WORDNUM; j++){
						clusterCenter[i][j] = 0;
					}
				}
				for(int i = 0; i < alltext.getRowCount(); i++){
					for(int j = 0; j < WORDNUM; j++){
						clusterCenter[B[i]-1][j] += alltext.getAsDouble(i,j);
					}
				}
				for(int i = 0; i < CLASSNUM; i++){
					for(int j = 0; j < WORDNUM; j++){
						if(count[i]==0)
							clusterCenter[i][j] =clusterCenter[i][j]+1 /(double)count[i]+1;
						else
							clusterCenter[i][j] /= (double)count[i];
					}
				}
			}
		}
		TextWriter tw=new TextWriter("clusterCenter.txt");
		for(int i=0;i<CLASSNUM;i++)
			for(int j=0;j<WORDNUM;j++){
				if(j!=WORDNUM-1)
					tw.write(clusterCenter[i][j]+" ");
				else {
					tw.write(clusterCenter[i][j]+"\n");
				}
			}
		tw.close();
	}
	private double similar(double[] a, SparseMatrix b,int count){
		double result = 0;
		for(int i = 0; i < a.length-2; i++){
			result = result + a[i]*b.getAsDouble(count,i);
		}
		double temp1 = 0, temp2 = 0;
		for(int i = 0; i < a.length-2; i++){
			temp1 += a[i]*a[i];
			temp2 +=b.getAsDouble(count,i)*b.getAsDouble(count,i) ;
		}
		result = result / (Math.sqrt(temp1)*Math.sqrt(temp2));
		return result;
	}
	
	private void init(SparseMatrix alltext){
		ArrayList<Integer> choosen = new ArrayList<Integer>();
		Random r = new Random();
		for(int i = 0; i < CLASSNUM; i++){
			Integer num = new Integer(r.nextInt(TEXTNUM));
			if(!choosen.contains(num)){
				choosen.add(num);
			}
			else i--;
			count[i] = 0;
		}
		int centerCount = 0;
		for(int i = 0; i < TEXTNUM; i++){
			if(choosen.contains(i)){
				for(int j = 0; j < WORDNUM+2; j++){
					clusterCenter[centerCount][j] = alltext.getAsDouble(i,j);
				}
				centerCount++;
			}
			B[i] = 0;
		}
	}
	public int[] getB(){
		return B;
	}
}
