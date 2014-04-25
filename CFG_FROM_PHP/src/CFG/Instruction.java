package CFG;

import java.util.StringTokenizer;

import main.Main;

/**
 * This class represents each instruction line in the bytecode. It contains the
 * type of instruction, the operation code, paramenters, line number in
 * bytecode, and destination (if applicable).
 * 
 */
public class Instruction {

	/************************
	 * TYPES OF INSTRUCTIONS
	 *************************/
	public static final int unidentified = -1;
	public final int basic = 0;
	public final int literal_and_constant = 1;
	public final int operator = 2;
	public final int control_flow = 3;
	public final int get = 4;
	public final int isset_empty_type_querying = 5;
	public final int mutator = 6;
	public static final int call = 7;
	public final int member_operations = 8;
	public final int member = 9;
	public final int iterator = 10;
	public final int include_eval_define = 11;
	public final int miscellaneous = 12;
	public final int continuation_creation_execution = 13;
	public final int set = 14;
	public int type;
	private String[] args;
	public int line;
	private String instruction_text;
	private String bc_line_no;
	private Instruction destination;
	private boolean unconditional;
	public boolean definition;
	public boolean use;
	public boolean global, callSite;
	String instr_text;
	public boolean passAsCell;

	/**
	 * Initializes variables.
	 */
	public Instruction() {
		args = new String[100];

		destination = null;
		unconditional = false;
		callSite = false;
	}

	/**
	 * @param t
	 *            Type of instruction
	 */
	public Instruction(int t) {
		this();
		type = t;
	}

	/**
	 * This constructor takes in an instruction from the bytecode and parses and
	 * stores the operation code and the arguments.
	 * 
	 * @param text
	 *            The actual instruction from bytecode
	 */
	public Instruction(String text) {
		this();
		setInstruction_text(text);
		int index = getInstruction_text().indexOf(" ");

		if (index != -1) {
			setInstruction_text(getInstruction_text().substring(0, index));
		}

		if (Main.cfgInstructions.contains(getInstruction_text())) {
			type = control_flow;
		} else if (Main.useInstructions.contains(getInstruction_text())) {
			type = get;
			use = true;
		} else if (Main.defInstructions.contains(getInstruction_text())) {
			// Mutator instructions
			type = set;
			definition = true;
		} else if (Main.mutator.contains(getInstruction_text())) {
			use = true;
			definition = true;
		} else {
			type = unidentified;
		}

		if (Main.globalInstructions.contains(getInstruction_text())) {
			global = true;
		}
		if (Main.callInstructions.contains(getInstruction_text())) {
			type = call;
			callSite = true;
			if (instruction_text.length() > 5) {
				char cell = instruction_text.charAt(5);
				if (cell == 'C') {
					passAsCell = true;
				}
			}
		}
		/* Parse the instruction */
		boolean instr = true;
		int numArgs = 0;
		StringTokenizer st = new StringTokenizer(text, " ");
		while (st.hasMoreTokens()) {
			if (instr) {
				instr_text = st.nextToken();
				instr = false;
			} else {
				args[numArgs] = st.nextToken();
				numArgs++;
			}
		}
		if (getInstruction_text().equals("Jmp")
				|| getInstruction_text().equals("JmpNS"))
			unconditional = true;
	}

	/**
	 * @param bc_line
	 *            Line number in the bytecode
	 */
	public void setBCLineNo(String bc_line) {
		bc_line_no = bc_line;
	}

	/**
	 * @param destination
	 *            The next instruction that executes after this instruction
	 */
	public void setDestination(Instruction destination) {
		this.destination = destination;
	}

	/**
	 * @param l
	 */
	public void setLine(Line l) {
		line = l.line_no;
	}

	/**
	 * @return A list of arguments
	 */
	public String[] getArgs() {
		return args;
	}

	/**
	 * @return The next instruction that will be executed after this
	 *         instruction.
	 */
	public Instruction getDestination() {
		return destination;
	}

	/**
	 * Used for constructing the edges of a CFG
	 * 
	 * @return True if this instruction is unconditional; false otherwise
	 */
	public boolean getUnconditional() {
		return unconditional;
	}

	/**
	 * @return the line number in bytecode
	 */
	public String getBCLineNO() {
		return bc_line_no;
	}

	/**
	 * Compares the line number in bytecode
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Instruction))
			return false;
		Instruction e = (Instruction) other;
		if (e.getBCLineNO().equals(this.getBCLineNO()))
			return true;
		return false;
	}

	/**
	 * Compares the bytecode line number
	 * 
	 * @param b
	 * @return
	 */
	public int compareTo(Instruction b) {
		if (this.equals(b))
			return 0;
		int a = Integer.parseInt(getBCLineNO());
		int bb = Integer.parseInt(b.getBCLineNO());
		if (a < bb)
			return -1;
		return 1;
	}

	public String getInstruction_text() {
		return instruction_text;
	}

	public void setInstruction_text(String instruction_text) {
		this.instruction_text = instruction_text;
	}

	/**
	 * Returns a string representation: [line number in bytecode]: [operation
	 * code] [args]
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("\t" + bc_line_no + ": "
				+ getInstruction_text() + " ");
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null)
				sb.append(args[i] + " ");
		}

		sb.append("\n");
		return sb.toString();
	}

	public boolean callSite() {

		return callSite;
	}

}
