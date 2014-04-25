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
		String pathOfCompiledFiles = args[0];
		//System.out.println("Path: "+pathOfCompiledFiles);
		init();
		//System.out.println("Please enter the path of the directory that you want to parse:");
		Scanner scan = new Scanner(System.in);
		//String path = scan.nextLine();
		File_operator f_operator = new File_operator();
		//Get all the files from a directory specified by the user
		f_operator.listFilesForFolder(new File(pathOfCompiledFiles),byteCodeFiles);
		//parse the given bytecode files from the directory
		PrintWriter writer;
		ArrayList<CFG> cfgs = new ArrayList<CFG>();
		File directory = new File (".");
		//TODO uncomment this
		//String currentDirectory = directory.getCanonicalPath();
		String currentDirectory = "/Users/brianedmonds/Documents/orso_research";
		String outputPath = currentDirectory+"/graphViz/";
		boolean success = new File(outputPath).mkdirs();
		if(success){
			System.out.println("output directory created successfully");
		}
		else{
			System.out.println("error in creating output directory");
		}
		String graphName = "testName";
		CFG interconnectedCFG = new CFG();
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
					System.out.println("Check your "+outputPath+"for the graphviz file");
				}
			}
		}
		
	}
	
	public static ArrayList<String> cfgInstructions;
	public static ArrayList<String> useInstructions;
	public static ArrayList<String> defInstructions;
	public static ArrayList<String> globalInstructions;
	public static ArrayList<String> callInstructions,mutator;
	public static ArrayList<String> fPushInstructions;
	
	
	public static void init(){
		useInstructions = new ArrayList<String>();
		cfgInstructions = new ArrayList<String>();
		globalInstructions = new ArrayList<String>();
		defInstructions = new ArrayList<String>();
		callInstructions = new ArrayList<String>();
		mutator = new ArrayList<String>();
		fPushInstructions = new ArrayList<String>();
		//Add the Control flow instructions
		cfgInstructions.add("Jmp");
		cfgInstructions.add("JmpNS");
		cfgInstructions.add("JmpZ");
		cfgInstructions.add("JmpNZ");
		cfgInstructions.add("Switch");
		cfgInstructions.add("SSwitch");
		cfgInstructions.add("Unwind");
		cfgInstructions.add("Throw");
		
		//Add the getInstructions
		useInstructions.add("CGetL");
		useInstructions.add("CGetL2");
		useInstructions.add("CGetL3");
		useInstructions.add("PushL");
		useInstructions.add("CGetN");
		useInstructions.add("CGetS");
		useInstructions.add("VGetL");
		useInstructions.add("VGetN");
		useInstructions.add("VGetS");
		useInstructions.add("AGetC");
		useInstructions.add("AGetL");
		useInstructions.add("VGetG");
		useInstructions.add("CGetG");
		useInstructions.add("FPassL"); //this is a pass instruction
		useInstructions.add("FPassC");
		useInstructions.add("FPassCW");
		useInstructions.add("FPassCE");
		useInstructions.add("FPassV");
		useInstructions.add("FPassVNop");
		useInstructions.add("FPassR");
		useInstructions.add("FPassN");
		useInstructions.add("FPassG");
		useInstructions.add("FPassS");
		
		//Add the setter instructions
		defInstructions.add("SetL");
		defInstructions.add("SetN");
		defInstructions.add("SetS");
		defInstructions.add("SetOpL");
		defInstructions.add("SetOpN");
		defInstructions.add("SetOpS");
		//defInstructions.add("FPushFuncD");
		
		mutator.add("IncDecL");
		mutator.add("IncDecN");
		mutator.add("IncDecS");
		mutator.add("BindL");
		mutator.add("BindN");
		mutator.add("BindS");
		mutator.add("UnsetL");
		mutator.add("UnsetN");
		mutator.add("CheckProp");
		mutator.add("InitProp");
		
		globalInstructions.add("UnsetG");
		globalInstructions.add("SetG");
		globalInstructions.add("SetOpG");
		globalInstructions.add("BindG");
		globalInstructions.add("IncDecG");
		globalInstructions.add("VGetG");
		globalInstructions.add("CGetG");
		
		fPushInstructions.add("FPushFunc");
		fPushInstructions.add("FPushFuncD");
		fPushInstructions.add("FPushFuncU");
		fPushInstructions.add("FPushObjMethod");
		fPushInstructions.add("FPushObjMethodD");
		fPushInstructions.add("FPushClsMethod");
		fPushInstructions.add("FPushClsMethodF");
		fPushInstructions.add("FPushClsMethodD");
		fPushInstructions.add("FPushCtor");
		fPushInstructions.add("FPushCtorD");
		fPushInstructions.add("DecodeCufIter");
		fPushInstructions.add("FPushCufIter");
		fPushInstructions.add("FPushCuf");
		fPushInstructions.add("FPushCufF");
		fPushInstructions.add("FPushCufSafe");
		fPushInstructions.add("CufSafeArray");
		fPushInstructions.add("CufSafeReturn");

		//TODO: THESE SHOULD BE USES******************
		callInstructions.add("FPassC");
		callInstructions.add("FPassCW");
		callInstructions.add("FPassCE");
		callInstructions.add("FPassV");
		callInstructions.add("FPassVNop");
		callInstructions.add("FPassR");
		callInstructions.add("FPassN");
		callInstructions.add("FPassL");
		callInstructions.add("FPassG");
		callInstructions.add("FPassS");
		//*****************************************
		
		callInstructions.add("FCall");
		callInstructions.add("FCallD");
		callInstructions.add("FCallArray");
		callInstructions.add("FCallBuiltin");	
	}
}
