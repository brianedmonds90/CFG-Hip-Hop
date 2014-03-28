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
}
