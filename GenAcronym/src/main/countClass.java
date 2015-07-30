package main;

import java.io.IOException;

import Handler.classCount;

public class countClass {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		classCount cc=new classCount();
		cc.readList();
		cc.writeFile();
	}

}
