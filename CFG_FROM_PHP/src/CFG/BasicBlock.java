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
	private boolean exitNode; // Is this an exit node?
	
	/**
	 * Initializes the instructions array.
	 * Set exitNode as default false
	 */
	public BasicBlock(){
		instructions = new ArrayList<Instruction>();
		exitNode = false;
	}
	
	/**
	 * Initializes the object with a single instruction
	 * @param inst
	 */
	public BasicBlock(Instruction inst) {
		this();
		instructions.add(inst);
	}

	/**
	 * Split current basic block into two
	 * @param index Insturction index of split point
	 * @return the new basic block with the rest of the instruction
	 */
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
	
	/**
	 * Returns a string representation:
	 * *** Block number: [block_no] ***
	 * head: [instructions[0]]
	 * 			 ...
	 * 		 [instructions[n]]
	 */
	public String toString(){
		if(instructions.size()>0){
			StringBuilder ret = new StringBuilder("*** Block number: "+block_no+" ***\nhead: ");
		
			for(Instruction i: instructions){
				ret.append(i.toString());
			}
	
			return ret.toString();
		}
		return "empty";
	}
	
	/**
	 * @return The appropriate graphviz syntax that represents this basic block
	 */
	public String toDot() {
		String ret ="";
		
		ret = getBlockNo()+ " [label=\""+instructions.get(0).line+"\"";

		//ret+=" tooltip=\"";
		for(Instruction in:instructions){
			//ret+=in.getInstrText()+"\n";
			if(in.type==Instruction.call){
				ret+=" , fillcolor = red, style = filled";
				break;
			}
		}
		ret+="];";
		return ret;

	}
}
