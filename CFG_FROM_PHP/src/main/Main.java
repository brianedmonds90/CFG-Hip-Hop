package main;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;

public class Main {
	
	public Main(){}
	
	public static void main(String [] args){
		
		File file = new File("/Users/brianedmonds/Documents/orso_research/test_files/foo.php.bc");
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Reading "+ file.getName());
		while(scan.hasNextLine()){
			String str = scan.nextLine();
			if(str.contains("//")){
				System.out.println("Comments: "+str);
			}
			else{
				System.out.println(str);	
			}
		}
	}
}
