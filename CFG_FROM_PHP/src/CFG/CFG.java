package CFG;

import java.util.ArrayList;

public class CFG {
	private String name; // Function name
	ArrayList<BasicBlock> nodes;//Nodes are basic blocks
	ArrayList<Edge> edges;
	BasicBlock entry;
	String fileName;
	Function function;
	private ArrayList<BasicBlock> exitNodes;
	private ArrayList<Defs_Uses> definitions;
	private ArrayList<Defs_Uses> uses;
	
	public CFG(){
		nodes = new ArrayList<BasicBlock>();
		edges = new ArrayList<Edge>();
		exitNodes = new ArrayList<BasicBlock>();
		definitions = new ArrayList<Defs_Uses>();
		uses = new ArrayList<Defs_Uses>();
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
					a.setAsExitNode();
				}
			}
		}
		return;
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

	public void getUses(ArrayList<BasicBlock> basicBlocks){
		for(BasicBlock bb: basicBlocks){
			for(Instruction inst : bb.getInstructions()){
				if(inst.use){
					uses.add(new Defs_Uses(bb,inst.line,Integer.parseInt((inst.args[0]))));
				}
				
				}
			}
		return;
	}
	
	public void getDefinitions(ArrayList<BasicBlock> basicBlocks) {
		boolean bbHasDef;
		int instrucionIndex;
		int basicBlockIndex=0;
		for(BasicBlock bb: basicBlocks){
			bbHasDef = false;
			instrucionIndex = 0;
			basicBlockIndex++;
			for(Instruction inst : bb.getInstructions()){
				instrucionIndex++;
				if(inst.definition){
					if(!bbHasDef){
						bbHasDef = true;
						definitions.add(new Defs_Uses(bb,inst.line,Integer.parseInt((inst.args[0]))));
					}
					else{//Basic Block already has a definition in it
						//split the basic block and add it to the basicBlocks list
						BasicBlock b = bb.split(instrucionIndex);
						if(basicBlockIndex==basicBlocks.size()){
							basicBlocks.add(b);
						}
						else{
							basicBlocks.add(basicBlockIndex, b);
						}
					}
				}
			}
		}
		return;
	}
}
