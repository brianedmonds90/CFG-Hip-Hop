package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import CFG.CFG;
import CFG.Instruction;

public class Main {
	
	public Main(){}
	
	public static void main(String [] args){
		
		ArrayList<File> byteCodeFiles = new ArrayList<File>();
		init();
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
	

	
	public static ArrayList<String> cfgInstructions;
	public static ArrayList<String> getInstructions;
	public static String setL = "SetL";
	
	public static void init(){
		
		getInstructions = new ArrayList<String>();
		cfgInstructions = new ArrayList<String>();
		
		//Add the CFG instructions
		cfgInstructions.add("Jmp");
		cfgInstructions.add("JmpNS");
		cfgInstructions.add("JmpZ");
		cfgInstructions.add("JmpNZ");
		cfgInstructions.add("Switch");
		cfgInstructions.add("SSwitch");
		cfgInstructions.add("Unwind");
		cfgInstructions.add("Throw");
		
		//Add the getInstructions
		getInstructions.add("CGetL");
		getInstructions.add("CGetL2");
		getInstructions.add("CGetL3");
		getInstructions.add("PushL");
		getInstructions.add("CGetN");
		getInstructions.add("CGetG");
		getInstructions.add("CGetS");
		getInstructions.add("VGetL");
		getInstructions.add("VGetN");
		getInstructions.add("VGetG");
		getInstructions.add("VGetS");
		getInstructions.add("AGetC");
		getInstructions.add("AGetL");
	}
}
