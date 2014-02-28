package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import CFG.CFG;

public class Main {
	
	public Main(){}
	
	public static void main(String [] args){
		CFG myCFG= new CFG();
		ArrayList<File> byteCodeFiles = new ArrayList<File>();
		System.out.println("Please enter the path of the directory that you want to parse:");
		Scanner scan = new Scanner(System.in);
		String path = scan.nextLine();
		File_operator f_operator = new File_operator();
		//Get all the files from a directory specified by the user
		f_operator.listFilesForFolder(new File(path),byteCodeFiles);
		//parse the given bytecode files from the directory
		for(File f: byteCodeFiles){
			String fileName = f.getName();
			String extension = fileName.substring(fileName.length()-3);
			//if the file is a bytecode file
			if(extension.equals(".bc")){
				System.out.println("parsing bytecode code... "+f.getName());
				f_operator.parse_byte_code(f);
			}
		}
		
	}
}
