package CFG;

import java.util.ArrayList;

/**
 * This class represents an individual function.
 * 
 */
public class Function {
	private ArrayList<Line> lines;
	private String name;
	ArrayList<String> fpis;
	private int numParams;
	ArrayList<String> ehTable;

	/**
	 * Initializes variables.
	 */
	public Function() {
		lines = new ArrayList<Line>();
		fpis = new ArrayList<String>();
		numParams = 0;
		ehTable = new ArrayList<String>();
	}

	/**
	 * Initializes variables and gives it a name
	 * 
	 * @param n
	 */
	public Function(String n) {
		this();
		name = n;
	}

	/**
	 * Computers the number of parameters this function has
	 * 
	 * @return Number of parameters
	 */
	public int calcNumParams() {
		int numVars = 0;
		int minSetL = 999999999;
		int temp1, temp2;
		for (Line l : lines) {
			for (Instruction i : l.getInstructions()) {
				if (i.type == i.set) {
					temp2 = Integer.parseInt(i.getArgs()[0]);
					if (temp2 < minSetL) {
						minSetL = temp2;
					}
				}
			}
		}
		if(minSetL>=999999999)
			return 0;
		else
			return minSetL;
	}

	public void addEHTable(String str) {
		ehTable.add(str);
	}

	public void addLine(int str) {
		lines.add(new Line(str));
	}

	public void addFPI(String next) {
		fpis.add(next);
	}

	public void setNumParams(int numParams2) {
		numParams = numParams2;
	}

	public void setLines(ArrayList<Line> lines) {
		this.lines = lines;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Line> getLines() {
		return lines;
	}

	public String getName() {
		return name;
	}

	/**
	 * Returns a string representation
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("Function: " + name
				+ " num params: " + numParams + "\n");
		for (String s : fpis) {
			sb.append("FPIS: " + s + "\n");
		}
		for (Line l : lines)
			sb.append(l);
		return sb.toString();
	}

	/**
	 * @return The appropriate graphviz syntax that represents this function
	 */
	public String toDot() {
		String ret = "";
		ret += "-2 [label=\"" + numParams + " params\"];";
		return ret;
	}
}
