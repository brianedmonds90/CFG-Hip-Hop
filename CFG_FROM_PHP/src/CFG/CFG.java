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
	private ArrayList<Defs_Uses> kills;
	
	public CFG(){
		nodes = new ArrayList<BasicBlock>();
		edges = new ArrayList<Edge>();
		exitNodes = new ArrayList<BasicBlock>();
		definitions = new ArrayList<Defs_Uses>();
		uses = new ArrayList<Defs_Uses>();
		kills = new ArrayList<Defs_Uses>();
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
							for(int i=basicBlockIndex+1; i<basicBlocks.size(); i++)
								basicBlocks.get(i).setBlockNo(basicBlocks.get(i).getBlockNo()+1);
						}
					}
				}
			}
		}
		System.out.print(name+":::Definitions::: There are "+definitions.size()+" number of definitions.\n");
		return;
	}
	
	public void getKills() {
		// For each definition, check the next place that it gets kill
		if(definitions==null)
			return;
		for (Defs_Uses di : definitions) {
			for (Defs_Uses dj : definitions) {
				if (di.variable_location==dj.variable_location) {
					/*System.out.println("Same variable");
					System.out.println(di.toString());
					System.out.println(dj.toString());
					System.out.println("Is there a path? "+isAPath(di.basicBlock, dj.basicBlock)+"\n");
					System.out.println("--------------");*/
					if (isAPath(di.basicBlock, dj.basicBlock))
						kills.add(new Defs_Uses(dj.basicBlock, dj.php_line_no, dj.variable_location));
				}
			}
		}
		System.out.print(":::KILLS::: There are "+kills.size()+" number of kills.\n");
		for (Defs_Uses k : kills)
			System.out.print(k.toString());
	}
	
	public boolean isAPath(BasicBlock a, BasicBlock b) {
		ArrayList<BasicBlock> neighbors = new ArrayList<BasicBlock>();
		neighbors = findNeighbors(a, neighbors);
		if(neighbors.size()==0) // If it is an exit node, return false
			return false;
		ArrayList<BasicBlock> visitedNode = new ArrayList<BasicBlock>();
		BasicBlock temp = neighbors.remove(0);
		if(visitedNode.contains(temp)) // If visited already, return false
			return false;
		if(temp.equals(b)) // If the neighbor is b, return true
			return true;
		neighbors = findNeighbors(temp, neighbors);
		visitedNode.add(temp);
		return false;
	}
	
	public ArrayList<BasicBlock> findNeighbors(BasicBlock b, ArrayList<BasicBlock> neighbors) {
		for(Edge e : edges) 			
			if(e.u.equals(b))
				neighbors.add(e.v);
		return neighbors;
	}
}
