package CFG;

import java.util.ArrayList;

public class BasicBlock {
	ArrayList<Instruction> instructions;
	int block_no;
	private boolean exitNode;
	
	public BasicBlock(){
		instructions = new ArrayList<Instruction>();
		exitNode = false;
	}
	
	public BasicBlock(Instruction inst) {
		this();
		instructions.add(inst);
	}

	public String toString(){
		if(instructions.size()>0){
			StringBuilder ret = new StringBuilder("B"+block_no+"  head: ");
		
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

	public String toDot() {
		String ret ="";
		
		ret = getBlockNo()+ " [label=\""+instructions.get(0).line+"\"";


		ret+=" tooltip=\"";
		for(Instruction in:instructions){
			ret+=in.instruction_text+"\n";
		}
		ret+="\"];";
		return ret;

	}

	public void setAsExitNode() {
		exitNode = true;
		
	}

	public BasicBlock split(int index) {
		BasicBlock b = new BasicBlock();
		for(int i = index;i<instructions.size();i++){
			b.addInstruction(instructions.remove(i));
		}
		return b;
	}
	
	
}
