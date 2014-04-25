package CFG;

import java.util.ArrayList;

/**
 * This class contains all the information of a CFG: function name, nodes,
 * edges, entry, exit, definition, uses, and kills.
 */
public class CFG {
	private String name; // Function name
	ArrayList<BasicBlock> nodes;// Nodes are basic blocks
	private ArrayList<Edge> edges;
	BasicBlock entry;
	private String fileName;
	private Function function;
	private ArrayList<BasicBlock> exitNodes;
	public ArrayList<Defs_Uses> definitions;
	private ArrayList<Defs_Uses> uses;
	BasicBlock exitNode;

	/**
	 * Initializes variables.
	 */
	public CFG() {
		nodes = new ArrayList<BasicBlock>();
		edges = new ArrayList<Edge>();
		exitNodes = new ArrayList<BasicBlock>();
		definitions = new ArrayList<Defs_Uses>();
		uses = new ArrayList<Defs_Uses>();
		name = null;
	}

	/**
	 * @param blocks
	 *            List of Basic blocks
	 */
	public CFG(ArrayList<BasicBlock> blocks) {
		this();
		nodes = blocks;
	}

	/**
	 * Sets the name of this CFG correspond to the function name.
	 * 
	 * @param name
	 *            Name of the CFG
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the basic block that was just created adds it to this CFG
	 * 
	 * @param b
	 *            Basic block to be added to this CFG
	 * @return The object BasicBlock created
	 */
	public BasicBlock addBasicBlock(BasicBlock b) {
		BasicBlock ret = new BasicBlock();
		ret = b;
		nodes.add(ret);
		return ret;
	}

	/**
	 * Adds an edge to the edge list.
	 * 
	 * @param bi
	 *            Source basic block
	 * @param bj
	 *            Destination basic block
	 * @param label
	 *            True / False / Exit / Entry
	 */
	public void addEdge(BasicBlock bi, BasicBlock bj, String label) {
		edges.add(new Edge(bi, bj, label));
	}

	public void setFileName(String str) {
		fileName = str;
	}

	public void setFunction(Function f) {
		function = f;
	}

	/**
	 * Initializes the entry node and adds an edge between entry node to the
	 * first actual node of the CFG.
	 */
	public void setEntryNode() {
		// entry = nodes.get(0);
		entry = new BasicBlock();
		entry.setBlockNo(-2);
		Edge e = new Edge(entry, nodes.get(0), "ENTRY");
		edges.add(e);
	}

	/**
	 * Compute the exit of this CFG
	 */
	public void setExitNodes() {
		BasicBlock a, b;
		for (int i = 0; i < nodes.size(); i++) {
			a = nodes.get(i);
			for (int j = 0; j < edges.size(); j++) {
				b = edges.get(j).getU();
				if (a.getBlockNo() == b.getBlockNo()) {
					break;
				} else if (j == edges.size() - 1) {
					exitNodes.add(a);
					a.setAsExitNode();
				}
			}
		}
		exitNode = new BasicBlock();
		int block_no = -1;
		exitNode.setBlockNo(block_no);
		for (BasicBlock e : exitNodes) {
			edges.add(new Edge(e, exitNode, "EXIT"));
		}
		return;
	}

	public void setExitNodes(ArrayList<BasicBlock> exitNodes) {
		this.exitNodes = exitNodes;
	}

	public ArrayList<BasicBlock> getExitNodes() {
		return exitNodes;

	}

	public ArrayList<BasicBlock> getNodes() {
		return nodes;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public String getFileName() {
		int index = fileName.indexOf(".");
		return fileName.substring(0, index);
	}

	public String getFunctionName() {
		return function.getName();
	}

	/**
	 * Go through each nodes and check for uses. If this node contains an use,
	 * add to the list uses.
	 * 
	 * @param basicBlocks
	 *            List of nodes
	 */
	public void getUses(ArrayList<BasicBlock> basicBlocks) {
		for (BasicBlock bb : basicBlocks) {
			for (Instruction inst : bb.getInstructions()) {
				if (inst.use) {
					// some instructions pass variables into functions
					if (inst.callSite) {
						//This instruction uses the second parameter as use variable location
						if (inst.getInstruction_text().equals("FPassL")) {  
							uses.add(new Defs_Uses(bb, inst.line, Integer
									.parseInt((inst.getArgs()[1]))));
						}
						else{
							uses.add(new Defs_Uses(bb, inst.line, Integer
									.parseInt((inst.getArgs()[0]))));
						}
					} else {
						// TODO need to support the global parameter usage
						if (inst.getArgs()[0] != null)
							uses.add(new Defs_Uses(bb, inst.line, Integer
									.parseInt((inst.getArgs()[0]))));
					}
				}
			}
		}
		return;
	}

	/**
	 * Go through each nodes and check for definition. Each node should only
	 * contain ONE definition. If multiple definitions are found, that node will
	 * be split into two.
	 * 
	 * @param basicBlocks
	 *            List of nodes
	 */
	public void getDefinitions(ArrayList<BasicBlock> basicBlocks) {
		boolean bbHasDef;
		int instrucionIndex;
		int basicBlockIndex = 0;
		ArrayList<BasicBlock> splitBlocks = new ArrayList<BasicBlock>();
		for (int j = 0; j < basicBlocks.size(); j++) {
			BasicBlock bb = basicBlocks.get(j);
			bbHasDef = false;
			instrucionIndex = 0;
			basicBlockIndex++;
			ArrayList<Instruction> instructions = bb.getInstructions();
			for (int k = 0; k < instructions.size(); k++) {
				Instruction inst = instructions.get(k);
				instrucionIndex++;
				if (inst.definition) {
					if (!bbHasDef) {
						bbHasDef = true;
						definitions.add(new Defs_Uses(bb, inst.line, Integer
								.parseInt((inst.getArgs()[0]))));
					} else {// Basic Block already has a definition in it
							// split the basic block and add it to the
							// basicBlocks list
						BasicBlock b = bb.split(instrucionIndex);
						if (basicBlockIndex == basicBlocks.size()) {
							basicBlocks.add(b);
						} else {
							basicBlocks.add(basicBlockIndex, b);
							for (int i = basicBlockIndex + 1; i < basicBlocks
									.size(); i++)
								basicBlocks.get(i).setBlockNo(
										basicBlocks.get(i).getBlockNo() + 1);
						}
					}
				}
			}
		}
		basicBlocks.addAll(splitBlocks);
		return;
	}

	/**
	 * Go through each nodes and check for definition. Return a list of
	 * definitions for the given basicBlocks
	 * 
	 * @param basicBlocks
	 *            List of nodes
	 */
	public ArrayList<Defs_Uses> getDefs(ArrayList<BasicBlock> basicBlocks) {
		ArrayList<Defs_Uses> ret = new ArrayList<Defs_Uses>();
		for (BasicBlock bb : basicBlocks) {
			for (Instruction inst : bb.getInstructions()) {
				if (inst.definition) {
					ret.add(new Defs_Uses(bb, inst.line, Integer.parseInt((inst
							.getArgs()[0]))));
				}
			}
		}
		return ret;
	}

	/**
	 * Determines if there is a path between node a to node b
	 * 
	 * @param a
	 *            Source basic block
	 * @param b
	 *            Destination basic block
	 * @return True if there is a path or false if there is not a path
	 */
	public boolean isAPath(BasicBlock a, BasicBlock b) {
		ArrayList<BasicBlock> neighbors = new ArrayList<BasicBlock>();
		neighbors = findNeighbors(a, neighbors);
		if (neighbors.size() == 0)
			return false;
		ArrayList<BasicBlock> visitedNode = new ArrayList<BasicBlock>();
		while (neighbors.size() > 0) {
			BasicBlock temp = neighbors.remove(0);
			if (!visitedNode.contains(temp))
				neighbors = findNeighbors(temp, neighbors);
			if (temp.equals(b)) // If the neighbor is b, return true
				return true;
			visitedNode.add(temp);
		}
		return false;
	}

	/**
	 * Adds all neighbors of a basic block to the list and returns the list
	 * 
	 * @param b
	 *            Basic block that you want to find the neighbor of
	 * @param neighbors
	 *            An existing arraylist of neighbors
	 * @return neighbors
	 */
	public ArrayList<BasicBlock> findNeighbors(BasicBlock b,
			ArrayList<BasicBlock> neighbors) {
		for (Edge e : edges)
			if (e.getU().equals(b))
				neighbors.add(e.getV());
		return neighbors;
	}

	/**
	 * Returns a string representation: ----- CFG: [fileName] -----: *** Block
	 * number : 0 *** Head: [head instruction] ... [instruction]
	 * 
	 * *** Block number: n *** Head: [head instruction] ... [instruction]
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("--------------- CFG: " + fileName
				+ " ---------------\n");
		for (BasicBlock b : nodes) {
			sb.append(b + "\n");
		}
		for (Edge e : edges) {
			sb.append(e + "\n");
		}
		sb.append("------------ End of CFG: " + fileName + " ------------\n");
		return sb.toString();
	}

	/**
	 * @return The appropriate graphviz syntax that represents this CFG
	 */
	public String toDot() {
		String ret = "";
		for (BasicBlock bb : nodes) {
			ret += bb.toDot() + "\n";
		}
		for (Edge e : edges) {
			ret += e.toDot() + "\n";
		}
		ret += entry.getBlockNo() + " [fillcolor = green, style = filled]";
		ret += exitNode.getBlockNo() + " [fillcolor= yellow, style = filled] ";
		for (Defs_Uses dd : definitions) {
			ret += dd.toDotDefs() + "\n";
		}
		for (Defs_Uses use : uses) {
			ret += use.toDotUses() + "\n";
		}
		ret += function.toDot();
		return ret;
	}

	public ArrayList<Defs_Uses> splitDefs() {
		// TODO Auto-generated method stub
		return null;
	}
}
