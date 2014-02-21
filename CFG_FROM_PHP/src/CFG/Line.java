package CFG;

import java.util.ArrayList;

public class Line {
	int line_no;
	ArrayList<Instruction> instructions;
	String code;
	
	public Line(){
		instructions= new ArrayList<Instruction>();
	}
	
	public Line(int l){
		this();
		line_no = l;
	}
	
	public String toString() {
		String str = line_no + ": ";
		for (int i=0; i<instructions.size(); i++)
			str += instructions.get(i).toString();
		return str;
	}
}
