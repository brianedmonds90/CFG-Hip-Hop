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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Basic Block : "+basicBlock.getBlockNo()+"\n");
		sb.append("PHP line no.: "+php_line_no+"\n");
		sb.append("Var location: "+variable_location+"\n");
		return sb.toString();
	}
}
