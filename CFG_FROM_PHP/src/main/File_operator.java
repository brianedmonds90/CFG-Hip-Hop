package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import CFG.Function;
/**
 * Class is used to parse given hip hop bytecode files
 * @author brianedmonds
 *
 */
public class File_operator {
	String path;
	Scanner scan;
	public File_operator(String p){
		path = p;
		scan= new Scanner(path);
	}
	
	public File_operator(){
		
	}
	
	
	/**
	 * Parses and prints the lines of file
	 * @param file
	 */
	ArrayList<Function> parse_byte_code(File file){
		Scanner scan = null;
		ArrayList<Function> ret= new ArrayList<Function>();
		//try and create a scanner object from the file
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//while the file has another line in it
		while(scan.hasNextLine()){
			String str = scan.nextLine();
			if(str.contains("//")){
				System.out.println("Comments: "+str);
			}
			//If the line is a function in the bytecode
//			else if(str.contains("Function")){
//				Function function = new Function(str);
//				//function.getLines(file, scan);
//				//ret.add(fun);
//				
//			}
			else{
				System.out.println(str);	
			}
		}
		return ret;
	}
	
	/**
	 * Returns a list of files in a directory
	 * @param folder: the folder to explore
	 * @param l: the list to return
	 * @return
	 */
	public ArrayList<File> listFilesForFolder(File folder,ArrayList<File> l) {
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry,l);
	        } else {
	            System.out.println("Adding "+fileEntry.getName()+" to return list");
	            l.add(fileEntry);
	        }
	    }
		return l;
	}

	public ArrayList<Function> parse_php_code(File file) {
		Scanner scan = null;
		ArrayList<Function> ret= new ArrayList<Function>();
		//try and create a scanner object from the file
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//while the file has another line in it
		while(scan.hasNextLine()){
			String str = scan.nextLine();
			if(str.contains("//")){
				System.out.println("Comments: "+str);
			}
			//If the line is a function in the bytecode
//			else if(str.contains("Function")){
//				Function function = new Function(str);
//				//function.getLines(file, scan);
//				//ret.add(fun);
//				
//			}
			else{
				System.out.println(str);	
			}
		}
		return ret;
	}
	
	
}
