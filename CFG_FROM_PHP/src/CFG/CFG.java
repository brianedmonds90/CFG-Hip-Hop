package CFG;

import java.util.ArrayList;

public class CFG {
	private String name; // Function name
	ArrayList<BasicBlock> nodes;//Nodes are basic blocks
	ArrayList<Edge> edges;
	BasicBlock entry;
	private ArrayList<BasicBlock> exitNodes;
	
	public CFG(){
		nodes = new ArrayList<BasicBlock>();
		edges = new ArrayList<Edge>();
		exitNodes = new ArrayList<BasicBlock>();
		name = null;
	}
	
	public CFG(ArrayList<BasicBlock> blocks) {
		this();
		nodes = blocks;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public void setExitNodes(){
		BasicBlock a,b;
		for(int i =0;i<nodes.size();i++){
			a = nodes.get(i);
			for(int j = 0;j<edges.size();j++){
				b = edges.get(j).u;
				if(a.getBlockNo()==b.getBlockNo()){
					break;
				}
				else if(j == edges.size()-1){
					exitNodes.add(a);
				}
			}
		}
	}
	
	public ArrayList<BasicBlock> getExitNodes() {
		return exitNodes;
	}
	public void setExitNodes(ArrayList<BasicBlock> exitNodes) {
		this.exitNodes = exitNodes;
	}
}
