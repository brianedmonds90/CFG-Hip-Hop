package CFG;

import java.util.ArrayList;

public class Line {
	int line_no;
	ArrayList<Instruction> instructions;
	String code;
	
	public Line(){
		instructions= new ArrayList<Instruction>();
	}
	
	public Line(int l){
		this();
		line_no = l;
	}

	public Instruction addInstruction(String str) {
		Instruction ret = new Instruction(str);
		instructions.add(ret);
		return ret;
		
	}

	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("	line_no: "+line_no+"\n");
		//String str = line_no + ": ";
		for (int i=0; i<instructions.size(); i++)
			sb.append(instructions.get(i));
		return sb.toString();
	}
}
