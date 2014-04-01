package CFG;

public class Defs_Uses {
	public BasicBlock basicBlock;
	public int php_line_no;
	public int variable_location;
	
	public Defs_Uses(BasicBlock b, int p, int var_loc){
		basicBlock = b;
		php_line_no = p;
		variable_location = var_loc;
	}

	public String toDot() {
		
		String ret ="subgraph cluster_"+basicBlock.getBlockNo()+" { style=filled; color=lightgrey; node [style=filled,color=white]; " +
				"g_"+basicBlock.getBlockNo()+"[label= \"PHP Line #"+php_line_no+" : "+variable_location+"\"];" +
				"label = \"Gen Set\";}";
		ret+="\n"+basicBlock.getBlockNo() +" -> "+"g_"+basicBlock.getBlockNo()+" [style=\"dashed\"];";
		return ret;
	}
	
	
}
