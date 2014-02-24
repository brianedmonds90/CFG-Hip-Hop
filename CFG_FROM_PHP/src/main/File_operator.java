package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import CFG.Function;
import CFG.Line;
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
		Function func = null;
		StateMachine state_machine= new StateMachine();
		//try and create a scanner object from the file
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//while the file has another line in it
		while(scan.hasNextLine()){
			String str = scan.nextLine();
			//Determine what type of input the parsed string is
			state_machine.handleInput(str);
			int state = state_machine.getCurrentState();
			//System.out.println(str + " "+state);		
			if(state == state_machine.function){
					func = new Function(str);
					ret.add(func);	
			}
			else if(state == state_machine.fpi){
				func = ret.get(ret.size()-1);
				func.addFPI(str);
			}
			else if(state == state_machine.line){
					func = ret.get(ret.size()-1);
					String number = str.substring(10);
					func.addLine(Integer.parseInt(number));
				
			}
			else if(state == state_machine.instruction){
				//System.out.println("--"+str);
				func = ret.get(ret.size()-1);
				Line l = func.getLines().get(func.getLines().size()-1);
				int firstChar = str.indexOf(":")+2; 
//				System.out.println("firstChar: "+firstChar);
				if(firstChar<str.length()){
					//System.out.println("Instruction:"+str.substring(firstChar));
					l.addInstruction(str.substring(firstChar));
				}
			}
		}
		for(Function f: ret){
			System.out.println(f);
		}
		
		return ret;
	}
	
	/**
	 * adds the fp informatio to a given function object
	 * @param scan2
	 * @param func
	 */
	private void getFPI(Scanner scan2, Function func) {
		while(scan2.hasNext()){
			String str = scan2.nextLine();
			if(str.contains("FPI")){
				func.addFPI(str);
			}
			else {
				return;
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
			//	System.out.println("Comments: "+str);
			}
			//If the line is a function in the php
			else if(str.contains("function")){
				Function function = new Function(str);
				ret.add(function);
			//	System.out.println(function);
				//function.getLines(file, scan);
						
			}
			else{
				//System.out.println(str);	
			}
		}
		return ret;
	}

	public ArrayList<Function> getFunctions_php(File file) {
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
			//	System.out.println("Comments: "+str);
			}
			//If the line is a function in the php
			else if(str.contains("function")){
				Function function = new Function(str);
				ret.add(function);
						
			}
			else{
				//System.out.println(str);	
			}
		}
		return ret;
	}
	
	
}
