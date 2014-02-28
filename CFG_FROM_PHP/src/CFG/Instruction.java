package CFG;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Instruction {
	public int type;
	String [] args;
	int line;
	String instruction_text;
	String instr_text;

	/************************
	 * TYPES OF INSTRUCTIONS
	 *************************/
	public final int unidentified = -1;
	public final int basic = 0;
	public final int literal_and_constant = 1;
	public final int operator = 2;
	public final int control_flow = 3;
	public final int get = 4;
	public final int isset_empty_type_querying = 5;
	public final int mutator = 6;
	public final int call = 7;
	public final int member_operations = 8;
	public final int  member = 9;
	public final int  iterator = 10;
	public final int  include_eval_define = 11;
	public final int  miscellaneous = 12;
	public final int  continuation_creation_execution = 13;
	ArrayList<String> cfgInstructions;
	
	public Instruction(){
		args= new String [10];
		cfgInstructions = new ArrayList<String>();
		cfgInstructions.add("Jmp");
		cfgInstructions.add("JmpNS");
		cfgInstructions.add("JmpZ");
		cfgInstructions.add("JmpNZ");
		cfgInstructions.add("Switch");
		cfgInstructions.add("SSwitch");
		//cfgInstructions.add("RetC");
		//cfgInstructions.add("RetV");
		cfgInstructions.add("Unwind");
		cfgInstructions.add("Throw");
	}
	
	public Instruction(int t){
		this();
		type = t;
	}
	
	public Instruction(String text){
		this();
		instruction_text = text;
		int index = instruction_text.indexOf(" ");
		
		if(index!=-1){
			instruction_text = instruction_text.substring(0,index);
		}
		
		if(cfgInstructions.contains(instruction_text)){
			type = control_flow;
		}
		else{
			type = unidentified;
		}
		
		/* Parse the instruction */
		boolean instr = true;
		int numArgs = 0;
		StringTokenizer st = new StringTokenizer(text, " ");
		while (st.hasMoreTokens()) {
			if (instr) {
				instr_text = st.nextToken();
				instr = false;
			}
			else {
				args[numArgs] = st.nextToken();
				numArgs++;
			}
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("		"+instruction_text+ " " +type + " ");
		for (int i=0; i<args.length; i++){
			if(args[i]!=null)
				sb.append(args[i]+" ");
		}
			
		sb.append("\n");
		return sb.toString();
	}
}
