package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import CFG.BasicBlock;
import CFG.CFG;
import CFG.Function;
import CFG.Instruction;
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
	CFG parse_byte_code(File file){
		Scanner scan = null;
		ArrayList<Function> functions= new ArrayList<Function>();
		CFG cfg_ret = new CFG();
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
			if(state == state_machine.function){
					func = new Function(str);
					functions.add(func);	
			}
			else if(state == state_machine.fpi){
				func = functions.get(functions.size()-1);
				func.addFPI(str);
			}
			else if(state == state_machine.line){
					func = functions.get(functions.size()-1);
					String number = str.substring(10);
					func.addLine(Integer.parseInt(number));
				
			}
			else if(state == state_machine.instruction){
				//Get the most current function
				func = functions.get(functions.size()-1);
				Line l = func.getLines().get(func.getLines().size()-1);
				//Remove spaces from front of instruction bytecode
				while(str.length()>0&&str.charAt(0)==' '){
					str= str.substring(1);
				}
				int firstChar = str.indexOf(":")+2; 
				String bc_line_no; 
				
				if(firstChar<str.length()){
					bc_line_no = str.substring(0,firstChar-2);
					Instruction inst = l.addInstruction(str.substring(firstChar));
					inst.setBC_Line_No(bc_line_no);
				}
			}
		}
		ArrayList<Instruction> leaders = new ArrayList<Instruction>();
		ArrayList<Instruction> allInstructions = new ArrayList<Instruction>();
		for(Function f: functions){
			leaders.addAll(getLeaders(f));
			for(Line l: f.getLines())
				allInstructions.addAll(l.getInstructions());
		}
		
		
		Collections.sort(leaders, new Comparator<Instruction> () {


			@Override
			public int compare(Instruction a, Instruction b) {
				
				return a.compareTo(b);
			}
		});
		
		System.out.println("leaders for file: "+file.getName()+"\n");
		for(Instruction in: leaders){
			System.out.println(in);
		}
		
		System.out.println("end of leaders--------------------------------");
		

		
		ArrayList<BasicBlock> basicBlocks = getBasicBlocks(allInstructions, leaders);
		System.out.println("basic Blocks for file: "+file.getName()+"\n");
		for(BasicBlock b: basicBlocks){
			System.out.println(b);
		}
		System.out.println("end of basic blocks-----------------------------");
		return cfg_ret;
	}
	
	/**
	* Takes in a function f and returns the basic blocks of the function
	* @param file
	*/
	ArrayList<BasicBlock> getBasicBlocks(ArrayList<Instruction> instructions,ArrayList<Instruction> leaders){
		ArrayList<BasicBlock> basicBlocks= new ArrayList<BasicBlock>();
		//List of leaders of the program 
		int i = 1;
		BasicBlock b = new BasicBlock();
		Instruction currentLeader = null;
		for(Instruction inst: instructions){
			
			if(i<leaders.size()){
				currentLeader = leaders.get(i);
			}
			
			//if the current instruction is a leader
			if(currentLeader.equals(inst)){
					basicBlocks.add(b);
					b= new BasicBlock(inst);
					i++;
			}

			
			//add the current instruction to the current basic block
			else{
				b.addInstruction(inst);
			}
		}
		basicBlocks.add(b);
		return basicBlocks;
	}
		
	/**
	 * Takes in a function f and returns the leaders of the function
	 * @param function
	 */
	ArrayList<Instruction> getLeaders(Function f){
		//List of leaders of the program 
		ArrayList<Instruction> leaders = new ArrayList<Instruction>();
		int i;
		boolean followingConditional;
		followingConditional = false;
		for(Line l: f.getLines()){
			i= 0 ;
			for(Instruction inst: l.getInstructions()){
					//first program statement in the function
					if(i == 0){
						//Check if the current instruction is already in the leaders list
						if(!leaders.contains(inst)){
							leaders.add(inst);
						}
						i++;
					}
					//If the current instruction is a conditional jmp
					if(inst.type == inst.control_flow){
						//Find the destination of the current branching instruction
						int offset = Integer.parseInt(inst.getArgs()[0]);
						int destination = Integer.parseInt(inst.getBCLineNO())+offset;
						Instruction dest_instruction = getDestinationInstruction(destination,f);
						//Check if the leader to be added is already contained in the leaders list
						if(!leaders.contains(dest_instruction))
							leaders.add(dest_instruction);
						followingConditional = true;
					}
					//If the instruction is following a conditional branch instruction
					else if(followingConditional){
						//Check if the leader to be added is already contained in the leaders list
						if(!leaders.contains(inst))
							leaders.add(inst);
						followingConditional = false;
				}
			}
		}
		return leaders;
	}
	
	
	
	private Instruction getDestinationInstruction(int destination,Function func) {
		for(Line l : func.getLines()){
			for(Instruction in: l.getInstructions()){
				if(in.getBCLineNO().equals(destination+"")){
					return in;
				}
			}
		}
		return null;
	}

	/**
	 * adds the fp information to a given function object
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

	/**Depreciated
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
	}**/
}
