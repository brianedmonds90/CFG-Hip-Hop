package CFG;

import java.util.ArrayList;

import com.sun.swing.internal.plaf.basic.resources.basic;
/**
 * This class contains all the information of a CFG: function name, nodes, edges, entry, exit,
 * definition, uses, and kills.
 */
public class CFG {
	private String name; // Function name
	ArrayList<BasicBlock> nodes;//Nodes are basic blocks
	ArrayList<Edge> edges;
	BasicBlock entry;
	String fileName;
	Function function;
	private ArrayList<BasicBlock> exitNodes;
	public ArrayList<Defs_Uses> definitions;
	private ArrayList<Defs_Uses> uses;
	private ArrayList<Defs_Uses> gen_set;
	private ArrayList<KillSet> killSet;
	private ArrayList<Defs_Uses> kills;
	BasicBlock exitNode;
	
	public CFG(){
		nodes = new ArrayList<BasicBlock>();
		edges = new ArrayList<Edge>();
		exitNodes = new ArrayList<BasicBlock>();
		definitions = new ArrayList<Defs_Uses>();
		uses = new ArrayList<Defs_Uses>();

		killSet = new ArrayList<KillSet>();
		gen_set = new ArrayList<Defs_Uses>();
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
	
	public void addEdge(BasicBlock bi, BasicBlock bj, String label) {
		edges.add(new Edge(bi, bj, label));
	}
	
	public void setFileName(String str){
		fileName = str;
	}
	
	public void setFunction(Function f) {
		function = f;
	}


	public void setEntryNode(){
		//entry = nodes.get(0);
		entry = new BasicBlock();
		entry.setBlockNo(-2);
		Edge e = new Edge(entry, nodes.get(0), "ENTRY");
		edges.add(e);
	}
	
	public void setExitNodes(){
		BasicBlock a,b;
		for(int i =0;i<nodes.size();i++){
			a = nodes.get(i);
			for(int j = 0;j<edges.size();j++){
				b = edges.get(j).getU();
				if(a.getBlockNo()==b.getBlockNo()){
					break;
				}
				else if(j == edges.size()-1){
					exitNodes.add(a);
					a.setAsExitNode();
				}
			}
		}
		exitNode = new BasicBlock();
		int block_no = -1;
		exitNode.setBlockNo(block_no);
		for(BasicBlock e: exitNodes){
			edges.add(new Edge(e,exitNode, "EXIT"));
		}
		
		return;
	}
	
	public void setExitNodes(ArrayList<BasicBlock> exitNodes) {
		this.exitNodes = exitNodes;
	}
	

	public ArrayList<BasicBlock> getExitNodes() {
		return exitNodes;

	}
	
	public ArrayList<BasicBlock> getNodes() {
		return nodes;
	}
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	public String getFileName(){
		int index = fileName.indexOf(".");
		return fileName.substring(0,index);
	}
	
	public String getFunctionName() {
		return function.getName();
	}

	public void getUses(ArrayList<BasicBlock> basicBlocks){
		for(BasicBlock bb: basicBlocks){
			for(Instruction inst : bb.getInstructions()){
				if(inst.use){
					uses.add(new Defs_Uses(bb,inst.line,Integer.parseInt((inst.getArgs()[0]))));
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
						definitions.add(new Defs_Uses(bb,inst.line,Integer.parseInt((inst.getArgs()[0]))));
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
					if (isAPath(di.basicBlock, dj.basicBlock))
						kills.add(new Defs_Uses(dj.basicBlock, dj.php_line_no, dj.variable_location));
				}
			}
		}
		System.out.print(":::KILLS::: There are "+kills.size()+" number of kills.\n");
		for (Defs_Uses k : kills)
			System.out.print(k.toString()+"\n");
	}
	
	public void getKills_Brian() {
		if(definitions==null)
			return;
		
		
//		for(BasicBlock b: nodes){
//			KillSet k = new KillSet(b);
//			for (Defs_Uses di: definitions) {
//				if(di.basicBlock.getBlockNo()==b.getBlockNo()){
//					
//				}
//			}
//		}
		
		for (int i = definitions.size()-1; i >= 0; i--) {
			Defs_Uses di = definitions.get(i);
			KillSet k = new KillSet(di.basicBlock);
			for (int j = definitions.size()-2; j >0; j--) {
				Defs_Uses dj = definitions.get(j);
				if (dj.variable_location==di.variable_location) {
					boolean path = isAPath(dj.basicBlock,di.basicBlock);
					if (path)
						k.addKill(dj.basicBlock, dj.variable_location,dj.php_line_no);
				}
			}
			killSet.add(k);
		}
		System.out.print(":::KILLS::: There are "+killSet.size()+" number of kills.\n");
		for (KillSet k : killSet)
			if(k.basicBlocks.size()>0){
				System.out.println(k);
			}
	}
	
	public boolean isAPath(BasicBlock a, BasicBlock b) {
		ArrayList<BasicBlock> neighbors = new ArrayList<BasicBlock>();
		neighbors = findNeighbors(a, neighbors);
		if(neighbors.size()==0)
			return false;
		ArrayList<BasicBlock> visitedNode = new ArrayList<BasicBlock>();
		while(neighbors.size()>0) {
			BasicBlock temp = neighbors.remove(0);
			if(!visitedNode.contains(temp))	
				neighbors = findNeighbors(temp, neighbors);
			if(temp.equals(b)) // If the neighbor is b, return true
				return true;
			visitedNode.add(temp);
		}
		return false;
	}
	
	public ArrayList<BasicBlock> findNeighbors(BasicBlock b, ArrayList<BasicBlock> neighbors) {
		for(Edge e : edges) 			
			if(e.getU().equals(b))
				neighbors.add(e.getV());
		return neighbors;
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
	
	public String toDot(){
		String ret = "";
		for(BasicBlock bb: nodes){
			ret+=bb.toDot()+"\n";
		}
		
		for(Edge e: edges){
			ret+=e.toDot()+"\n";
		}
		ret+= entry.getBlockNo()+" [fillcolor = green, style = filled]";
		
		
		ret+= exitNode.getBlockNo()+" [fillcolor= yellow, style = filled] ";

		
		
		for(Defs_Uses dd: definitions){
			ret+= dd.toDotDefs()+"\n";
		}
		
		for(Defs_Uses use: uses){
			ret+= use.toDotUses()+"\n";
		}
		
		ret+=function.toDot();
		
//		for(KillSet kk : killSet){
//			ret+= kk.toDot()+ "\n";
//		}
//		
		return ret;
	}
}
