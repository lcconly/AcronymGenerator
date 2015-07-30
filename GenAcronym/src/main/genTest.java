package main;

import gen_acronym.AcronymSort;
import gen_acronym.Gen;

import java.io.IOException;
import java.util.Scanner;

import LDA_file_deal.GetVocab;

public class genTest {
	protected static boolean isAllLetters(String name){
		int flag=0;
		for(int i=0;i<name.length();i++){
			if((name.charAt(i)-'a'>=0&&name.charAt(i)-'a'<26)||
					(name.charAt(i)-'A'>=0&&name.charAt(i)-'A'<26)||
					name.charAt(i)-' '==0){}
			else 
				flag=1;
				 
		}
		if(flag==0)
			return true;
		else 
			return false;
	}
	public static void main(String[] args)  {
		System.out.println("The system is being initialized...\nPlease wait for some minutes...");
		Gen ge=new Gen();
		while(true){
			System.out.println("Please input the title:");
			Scanner in=new Scanner(System.in);
			String name=in.nextLine();
			if(name.length()<3) continue;
			if(isAllLetters(name)==true){
				ge.run(name);//.toLowerCase());
				//ge.printRes();
				AcronymSort sort=new AcronymSort(ge.getRes(), ge.getName(),
						ge.getALLwords(),ge.getLDAResult(),ge.getLDAWords());
				sort.calIndex();
				System.out.print("\n\n\n");
			}
			else {
				System.out.println("\nYour input contains special symbol. Please check you input."
						+ "\neg. A New Benchmark Suite for Measuring Parallel Computer Performance\n");
			}
		}
	}
}
