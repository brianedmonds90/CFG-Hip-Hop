package CFG;

import java.util.ArrayList;

public class KillSet {
	BasicBlock b;
	ArrayList<BasicBlock> basicBlocks;
	ArrayList<Integer> php_line_nos;
	ArrayList<Integer> variable_nums;
	public KillSet(BasicBlock ba){
		basicBlocks = new ArrayList<BasicBlock>();
		variable_nums = new ArrayList<Integer>();
		php_line_nos = new ArrayList<Integer>();
		b = ba;
	}
	
	public void addKill(BasicBlock bb, int var_num, int line_no){
		basicBlocks.add(bb);
		variable_nums.add(var_num);
		php_line_nos.add(line_no);
	}
	
	public String toDot(){
		String ret="";
		if(basicBlocks.size()>0){
			ret ="subgraph cluster_k_"+b.getBlockNo()+ //cluster name-> kill set
				" { style=filled; " +
				"color=lightgrey; " +
				"node [style=filled,color=white]; ";
				int j=0;
				for(BasicBlock bb: basicBlocks){
					ret+="k_"+b.getBlockNo()+"_"+php_line_nos.get(j);
					ret+="[label= \"PHP Line #"+php_line_nos.get(j)+" : "+variable_nums.get(j)+"\"];"; 
					j++;
					
				}
				
				
				
				
				//"[label= \"PHP Line #"+php_line_no+" : "+variable_location+"\"];" +
			ret+="label = \"Kill Set\";}";
			int i=0;
			for(BasicBlock bb: basicBlocks){
				ret+="\n"+b.getBlockNo() +" -> "+"k_"+b.getBlockNo()+"_"+php_line_nos.get(i)+" [style=\"dashed\"];";
				i++;
			}
			
		}
		
		return ret;
	}
	
	public String toString(){
		String ret = "";
		ret += "Basic Block: "+b.getBlockNo()+" Kills: \n";
		int i=0;
		for(BasicBlock bb: basicBlocks){
			ret += "   blockNo: "+bb.getBlockNo()+"\n";
			ret+= "php_line_no: " +php_line_nos.get(i)+"\n";
			ret += "var_num: "+variable_nums.get(i)+"\n";
			i++;
		}
		return ret;
	}
	
}
