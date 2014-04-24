package CFG;
import java.util.ArrayList;

/**
 * Represents a line of code in the original php file and
 * contains the corresponding bytecode.
 *
 */
public class Line {
	int line_no;
	ArrayList<Instruction> instructions;
	String code;
	
	/**
	 * Initializes instructions to empty array
	 */
	public Line(){
		instructions= new ArrayList<Instruction>();
	}
	
	/**
	 * Set line number
	 * @param l line number in php file
	 */
	public Line(int l){
		this();
		line_no = l;
	}

	/**
	 * Add the corresponding bytecode
	 * @param str instruction
	 * @return Instruction object
	 */
	public Instruction addInstruction(String str) {
		Instruction ret = new Instruction(str);
		instructions.add(ret);
		return ret;
	}

	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}

	/**
	 * Returns a string representation:
	 * 		Line Number: [line_no]
	 * 		[instruction[0]]
	 * 			....
	 * 		[instruction[n]]
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("	Line Number: "+line_no+"\n");
		for (int i=0; i<instructions.size(); i++)
			sb.append(instructions.get(i));
		return sb.toString();
	}
}
