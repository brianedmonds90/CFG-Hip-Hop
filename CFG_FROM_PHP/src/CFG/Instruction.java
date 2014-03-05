package CFG;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Instruction{
	public int type;
	String [] args;
	int line;
	String instruction_text;
	String instr_text;
	String bc_line_no;
	private Instruction destination;
	private boolean unconditional;
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
		destination = null;
		unconditional = false;
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
		
		if(instruction_text.equals("Jmp") || instruction_text.equals("JmpNS"))
			unconditional = true;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("		"+bc_line_no+": "+instruction_text+ " " );
		for (int i=0; i<args.length; i++){
			if(args[i]!=null)
				sb.append(args[i]+" ");
		}
			
		sb.append("\n");
		return sb.toString();
	}

	public void setBC_Line_No(String bc_line) {
		bc_line_no = bc_line;
	}

	public String[] getArgs() {
		
		return args;
	}

	public String getBCLineNO() {
		
		return bc_line_no;
	}
	
	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof Instruction))return false;
		Instruction e = (Instruction) other;
		if(e.getBCLineNO().equals(this.getBCLineNO())) return true;
		return false;
	
	}
	
	public int compareTo(Instruction b) {
		if(this.equals(b))
			return 0;
		int a = Integer.parseInt(getBCLineNO());
		int bb = Integer.parseInt(b.getBCLineNO());
		if(a<bb) return -1;
		return 1;
	}
	
	public void setDestination(Instruction destination) {
		this.destination = destination;
	}

	public Instruction getDestination() {
		return destination;
	}

	public boolean getUnconditional() {
		return unconditional;
	}


}
