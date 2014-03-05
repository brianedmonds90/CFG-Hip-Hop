package CFG;

import java.util.ArrayList;

public class BasicBlock {
	ArrayList<Instruction> instructions;
	int block_no;
	
	public BasicBlock(){
		instructions = new ArrayList<Instruction>();
	}
	
	public BasicBlock(Instruction inst) {
		this();
		instructions.add(inst);
	}

	public String toString(){
		if(instructions.size()>0){
			StringBuilder ret = new StringBuilder("B"+block_no+"  head: "+instructions.remove(0));
		
			for(Instruction i: instructions){
				ret.append(i.toString()+ "\n");
			}
	
			return ret.toString();
		}
		return "empty";
	}
	
	public Instruction getLeader(){
		return instructions.get(0);
	}
	
	public void addInstruction(Instruction i){
		instructions.add(i);
	}

	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}
	
	public void setBlockNo(int block_no) {
		this.block_no = block_no;
	}

	public int getBlockNo() {
		return block_no;
	}
}
