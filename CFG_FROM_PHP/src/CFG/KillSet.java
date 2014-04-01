package CFG;

import java.util.ArrayList;

public class KillSet {
	BasicBlock b;
	ArrayList<BasicBlock> basicBlocks;
	ArrayList<Integer> variable_nums;
	public KillSet(BasicBlock ba){
		basicBlocks = new ArrayList<BasicBlock>();
		variable_nums = new ArrayList<Integer>();
		b = ba;
	}
	
	public void addKill(BasicBlock bb, int var_num){
		basicBlocks.add(bb);
		variable_nums.add(var_num);
	}
	
	
}
