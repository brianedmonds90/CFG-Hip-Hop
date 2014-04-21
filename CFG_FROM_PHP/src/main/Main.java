package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import CFG.BasicBlock;
import CFG.CFG;

public class Main {
	
	public Main(){}
	
	public static void main(String [] args) throws IOException{
		
		ArrayList<File> byteCodeFiles = new ArrayList<File>();
		init();
		System.out.println("Please enter the path of the directory that you want to parse:");
		Scanner scan = new Scanner(System.in);
		String path = scan.nextLine();
		File_operator f_operator = new File_operator();
		//Get all the files from a directory specified by the user
		f_operator.listFilesForFolder(new File(path),byteCodeFiles);
		//parse the given bytecode files from the directory
		PrintWriter writer;
		ArrayList<CFG> cfgs = new ArrayList<CFG>();
		File directory = new File (".");
		String currentDirectory = directory.getCanonicalPath();
		String outputPath = currentDirectory+"/graphViz/";
		boolean success = new File(outputPath).mkdirs();
		if(success){
			System.out.println("output directory created successfully");
		}
		else{
			System.out.println("error in creating outpute directory");
		}
		String graphName = "testName";
		for(File f: byteCodeFiles){
			String fileName = f.getName();
			String extension = fileName.substring(fileName.length()-3);
			//if the file is a bytecode file
			if(extension.equals(".bc")){
				cfgs = f_operator.parse_byte_code(f);
				for(CFG cfg: cfgs ){
					try {
						writer = new PrintWriter(outputPath+cfg.getFileName()+"_"+
								cfg.getFunctionName()+".dot", "UTF-8");
						writer.println("digraph "+graphName+" {");
						writer.println(cfg.toDot());
						writer.println("}");
						writer.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("CHECK your "+outputPath+"for the graphviz file");

				}
			}
		}		
	}
	

	
	public static ArrayList<String> cfgInstructions;
	public static ArrayList<String> getInstructions;
	public static ArrayList<String> setL;
	public static ArrayList<String> globalInstructions;
	
	
	public static void init(){
		
		getInstructions = new ArrayList<String>();
		cfgInstructions = new ArrayList<String>();
		globalInstructions = new ArrayList<String>();
		setL = new ArrayList<String>();
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
		getInstructions.add("CGetS");
		getInstructions.add("VGetL");
		getInstructions.add("VGetN");
		getInstructions.add("VGetS");
		getInstructions.add("AGetC");
		getInstructions.add("AGetL");
		//getInstructions.add("VGetG");
		getInstructions.add("CGetG");
		
		
		//Add the setter instructions
		setL.add("SetL");
		setL.add("SetN");
		setL.add("SetS");
		setL.add("SetOpL");
		setL.add("SetOpN");
		setL.add("SetOpS");
		//setL.add("IncDecL");
		//setL.add("IncDecN");
		setL.add("IncDecS");
		setL.add("BindL");
		setL.add("BindN");
		setL.add("BindS");
		setL.add("UnsetL");
		setL.add("UnsetN");
		setL.add("CheckProp");
		setL.add("InitProp");
		
		globalInstructions.add("UnsetG");
		globalInstructions.add("SetG");
		globalInstructions.add("SetOpG");
		globalInstructions.add("BindG");
		globalInstructions.add("IncDecG");
		globalInstructions.add("VGetG");
		globalInstructions.add("CGetG");
	}
}
