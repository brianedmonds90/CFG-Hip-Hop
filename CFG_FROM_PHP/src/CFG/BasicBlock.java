package CFG;

import java.util.ArrayList;
/**
 * This class contains a block number and all the instructions belong to that basic block.
 * It serves as the foundation of a CFG.
 *
 */
public class BasicBlock {
	private ArrayList<Instruction> instructions;
	private int block_no;
	private boolean exitNode;
	
	public BasicBlock(){
		instructions = new ArrayList<Instruction>();
		exitNode = false;
	}
	
	public BasicBlock(Instruction inst) {
		this();
		instructions.add(inst);
	}

	public BasicBlock split(int index) {
		BasicBlock b = new BasicBlock();
		for(int i = index;i<instructions.size();i++){
			b.addInstruction(instructions.remove(i));
		}
		return b;
	}
	
	public void addInstruction(Instruction i){
		instructions.add(i);
	}
	
	public void setAsExitNode() {
		exitNode = true;
		
	}
	
	public void setBlockNo(int block_no) {
		this.block_no = block_no;
	}
	
	public Instruction getLeader(){
		return instructions.get(0);
	}
	
	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}
	
	public int getBlockNo() {
		return block_no;
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
	
	public String toDot() {
		String ret ="";
		
		ret = getBlockNo()+ " [label=\""+instructions.get(0).line+"\"";

		ret+=" tooltip=\"";
		for(Instruction in:instructions){
			ret+=in.getInstrText()+"\n";
		}
		ret+="\"];";
		return ret;

	}
}
