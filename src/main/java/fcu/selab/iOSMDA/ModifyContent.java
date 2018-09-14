package fcu.selab.iOSMDA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModifyContent {
	public void modifyContent(String filePath, String oldWord, String newWord) throws IOException{
		 
		FileReader fr= new FileReader(filePath);  // new一個FileReader物件
		BufferedReader br=new BufferedReader(fr);  //new一個FileRead的BufferedReader物件
		 
		String line;
		String newContent="";
		 
		while((line = br.readLine()) != null){  //逐行讀入檔案內容
		 
		  newContent = newContent+line.replace(oldWord, newWord)+"\n";
		  
		}
		 
		FileWriter fw = new FileWriter(filePath);  //new 一個原本檔案的FileWriter物件
		fw.write(newContent);  //將置換過的檔案內容寫回
		 
		//關閉物件
		br.close(); 
		fr.close();
		fw.close();
	}
}


