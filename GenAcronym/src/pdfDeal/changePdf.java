package pdfDeal;

import io.TextWriter;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class changePdf {
	public static void main(String[] args)  {
		File file=new File("paper/");
		String text[]=file.list();
		for(int i=0;i<text.length;i++){
			System.out.println("paper/"+text[i]);
			PDDocument doc;
			try {
				doc = PDDocument.load("paper/"+text[i]);
			PDFTextStripper stripper=new PDFTextStripper();
			String nr = stripper.getText(doc);
			TextWriter tw=new TextWriter("text/"+text[i].split("/.")[0]+".txt");
			tw.write(nr);
			tw.close();
			doc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			} 

		}

	}
}
