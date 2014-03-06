package CFG;

import java.util.ArrayList;

public class CFG {
	ArrayList<BasicBlock> nodes;//Nodes are basic blocks
	ArrayList<Edge> edges;
	BasicBlock entry,exit;
	
	public CFG(){
		nodes = new ArrayList<BasicBlock>();
		edges = new ArrayList<Edge>();
	}
	public CFG(ArrayList<BasicBlock> blocks) {
		this();
		nodes = blocks;
	}
	/*
	 * Returns the basic block that was just created
	 * adds it to this CFG
	 */
	public BasicBlock addBasicBlock(BasicBlock b) {
		BasicBlock ret = new BasicBlock();
		ret = b;
		nodes.add(ret);
		return ret;
	}
	
	public ArrayList<BasicBlock> getNodes() {
		return nodes;
	}
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	public void addEdge(BasicBlock bi, BasicBlock bj, String label) {
		edges.add(new Edge(bi, bj, label));
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
	
	public void setEntryNode(){
		entry = nodes.get(0);
	}
	
	public void setExitNode(){
		exit = nodes.get(nodes.size()-1);
	}
}
