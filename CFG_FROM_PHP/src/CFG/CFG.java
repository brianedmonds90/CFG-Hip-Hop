package CFG;

import java.util.ArrayList;

public class CFG {
	ArrayList<BasicBlock> nodes;//Nodes are basic blocks
	ArrayList<Edge> edges;
	BasicBlock entry;
	String fileName;
	Function function;
	private ArrayList<BasicBlock> exitNodes;
	
	public CFG(){
		nodes = new ArrayList<BasicBlock>();
		edges = new ArrayList<Edge>();
		exitNodes = new ArrayList<BasicBlock>();
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
	
	public void setExitNodes(){
		BasicBlock a,b;
		for(int i =0;i<nodes.size();i++){
			a = nodes.get(i);
			for(int j = 0;j<edges.size();j++){
				b = edges.get(j).u;
				if(a.getBlockNo()==b.getBlockNo()){
					continue;
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
	
	public String toDot(){
		String ret = "";
		
		for(BasicBlock bb: nodes){
			ret+=bb.toDot()+"\n";
		}
		
		for(Edge e: edges){
			ret+=e.toDot()+"\n";
		}
		
		return ret;
	}
	
	public String getFileName(){
		int index = fileName.indexOf(".");
		return fileName.substring(0,index);
	}
	
	public void setFileName(String str){
		fileName = str;
	}
	
	public void setFunction(Function f) {
		function = f;
		
	}
	public String getFunctionName() {
		return function.getName();
	}
}
