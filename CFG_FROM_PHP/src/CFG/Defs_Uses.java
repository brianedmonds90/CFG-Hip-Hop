package CFG;

/**
 * This class represents a definition or a use in a basic block and
 * contains which basic block it belongs it, which line number it represents, 
 * and which variable it is.
 * 
 */
public class Defs_Uses {
	public BasicBlock basicBlock;
	public int php_line_no;
	public int variable_location;
	
	/**
	 * @param b Basic block that the definition/use belongs to
	 * @param p The line number that the definition/use appears in
	 * @param var_loc The variable location number represented in the bytecode
	 */
	public Defs_Uses(BasicBlock b, int p, int var_loc){
		basicBlock = b;
		php_line_no = p;
		variable_location = var_loc;
	}
	
	/**
	 * Returns a string representation:
	 * Basic Block : [Basic block number]
	 * PHP line no.: [PHP line number]
	 * Var location: [Variable location number]
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Basic Block : "+basicBlock.getBlockNo()+"\n");
		sb.append("PHP line no.: "+php_line_no+"\n");
		sb.append("Var location: "+variable_location+"\n");
		return sb.toString();
	}
	
	/**
	 * @return The appropriate graphviz syntax that represents a definition
	 */
	public String toDotDefs() {
		
		String ret ="subgraph cluster_g_"+basicBlock.getBlockNo()+" { style=filled; color=lightgrey; node [style=filled,color=white]; " +
				"g_"+basicBlock.getBlockNo()+"[label= \"PHP Line #"+php_line_no+" : "+variable_location+"\"];" +
				"label = \"DEFS\";}";
		ret+="\n"+basicBlock.getBlockNo() +" -> "+"g_"+basicBlock.getBlockNo()+" [style=\"dashed\"];";
		return ret;
	}	

	/**
	 * @return The appropriate graphviz syntax that represents a kill
	 */
	public String toDotKillSet() {
		String ret ="subgraph cluster_k_"+basicBlock.getBlockNo()+ //cluster name-> kill set
				" { style=filled; " +
				"color=lightgrey; " +
				"node [style=filled,color=white]; " +
				"k_"+basicBlock.getBlockNo()+"[label= \"PHP Line #"+php_line_no+" : "+variable_location+"\"];" +
				"label = \"Kill Set\";}";
		ret+="\n"+basicBlock.getBlockNo() +" -> "+"k_"+basicBlock.getBlockNo()+" [style=\"dashed\"];";
		return ret;
	}
	
	/**
	 * @return The appropriate graphviz syntax that represents an use
	 */
	public String toDotUses(){
		String ret = "subgraph cluster_use_"+basicBlock.getBlockNo()+ //cluster name-> defs
				" { style=filled; " +
				"color=lightgrey; " +
				"node [style=filled,color=white]; " +
				"use_"+basicBlock.getBlockNo()+"_"+variable_location+"[label= \"PHP Line #"+php_line_no+" : "+variable_location+"\"];" +
				"label = \"Uses\";}";
		ret+="\n"+basicBlock.getBlockNo() +" -> "+"use_"+basicBlock.getBlockNo()+"_"+variable_location+" [style=\"dashed\"];";
		return ret;
		
	}

}
