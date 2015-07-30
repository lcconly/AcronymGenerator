package main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.ujmp.core.matrix.SparseMatrix;

import io.TextReader;
import Handler.DataSet;
import Handler.IDFCalculator;
import Handler.KMeans;
import Handler.TFCalculator;
import Handler.WordSet;

public class test {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		/*DataSet data=new DataSet();
		WordSet words=new WordSet();
		TextReader tr=new TextReader("IDF.txt");
		String temp=null;
		int count=0;
		double[] idf=new double[27544];
		while((temp=tr.getReader().readLine())!=null){
			idf[count]=Double.parseDouble(temp);
			count++;
		}
		//IDFCalculator idf=new IDFCalculator(data.getAllText(), words.getWords());
		TFCalculator tf=new TFCalculator(data.getAllText(),words.getWords(),idf);*/
		TextReader tr=new TextReader("tfidf.txt");
		String temp=null;
		SparseMatrix sm=SparseMatrix.factory.zeros(152566,27544);
		
		while((temp=tr.getReader().readLine())!=null){
			String[] split=temp.split(" ");
			sm.setAsDouble(Double.parseDouble(split[2]),Integer.parseInt(split[0]),
					Integer.parseInt(split[1]));
		}
		tr.close();
		KMeans km=new KMeans(sm);
	}

}
