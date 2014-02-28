package CFG;

import java.util.ArrayList;

public class BasicBlock {
	ArrayList<Instruction> instructions;
	public BasicBlock(){
		instructions = new ArrayList<Instruction>();
	}
	
	public BasicBlock(Instruction inst) {
		this();
		instructions.add(inst);
	}

	public String toString(){
		StringBuilder ret = new StringBuilder("head: "+instructions.remove(0));
		for(Instruction i: instructions){
			ret.append(i.toString()+ "\n");
		}
	
		return ret.toString();
	}
	
	public Instruction getLeader(){
		return instructions.get(0);
	}
	
	public void addInstruction(Instruction i){
		instructions.add(i);
	}
}
