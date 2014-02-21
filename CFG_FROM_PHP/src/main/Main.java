package main;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;

public class Main {
	
	public Main(){}
	
	public static void main(String [] args){
		System.out.println("Files in test_files");
		ArrayList<File> codeFiles = new ArrayList<File>();
		System.out.println("Please enter the path of the directory that you want to parse:");
		Scanner scan = new Scanner(System.in);
		String path = scan.nextLine();
		File_operator f_operator = new File_operator();
		//Get all the files from a directory specified by the user
		f_operator.listFilesForFolder(new File(path),codeFiles);
		//parse the given bytecode files from the directory
		for(File f: codeFiles){
			f_operator.parse_byte_code(f,false);
		}
	}
}
