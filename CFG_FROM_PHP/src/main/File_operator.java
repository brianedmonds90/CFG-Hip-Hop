package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
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
	 * @param parse_php true if you want to parse all files, false if you 
	 * only want to parse .bc files
	 */
	void parse_byte_code(File file, boolean parse_php){
		Scanner scan = null;
		try {
			scan = new Scanner(file);
			String fileName = file.getName();
			System.out.println("Reading "+ fileName);
			if(!parse_php){
				String extentsion = fileName.substring(fileName.length()-2);
				if(!extentsion.equals("bc")){
					return;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
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
	
}
