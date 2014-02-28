package CFG;

import java.util.ArrayList;

public class CFG {
	ArrayList<BasicBlock> nodes;//Nodes are basic blocks
	ArrayList<Edge> edges;
	public CFG(){
		nodes = new ArrayList<BasicBlock>();
		edges = new ArrayList<Edge>();
	}
	/*
	 * Returns the basic block that was just created
	 * adds it to this CFG
	 */
	public BasicBlock addBasicBlock(Instruction inst) {
		BasicBlock ret = new BasicBlock(inst);
		nodes.add(ret);
		return ret;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder("CFG: \n");
		for(BasicBlock b: nodes){
			sb.append(b+"\n");
		}
		for( Edge e: edges){
			sb.append(e+"\n");
		}
		return sb.toString();
	}

	
}
