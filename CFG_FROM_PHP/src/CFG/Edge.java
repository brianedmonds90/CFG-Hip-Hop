package CFG;

/**
 * This class represents an edge in the CFG, flows from U to V.
 *
 */
public class Edge {
	private BasicBlock u,v;
	private String label;
	
	/**
	 * Creates an edge that is single flow
	 * @param a Source basic block
	 * @param b Destination basic block
	 */
	public Edge(BasicBlock a, BasicBlock b) {
		u = a;
		v = b;
	}
	
	/**
	 * Creates an unidirectional edge with a label attached
	 * @param a Source basic block
	 * @param b Destination basic block
	 * @param label Label for that edge (i.e. True, False, Entry, Exit)
	 */
	public Edge(BasicBlock a, BasicBlock b, String label){
		this(a, b);
		this.label = label;
	}
	
	/**
	 * @return Source basic block
	 */
	public BasicBlock getU() {
		return u;
	}
	
	/**
	 * @return Destination basic block
	 */
	public BasicBlock getV() {
		return v;
	}
	
	/**
	 * @return String label of this edge or null if none
	 */
	public String getLabel() {
		return label;
	}
	
	
	/**
	 * Returns a string representation:
	 * Edge: [U's basic block number] --> [V's basic block number] [label]
	 * Label is displayed when it is not null
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("Edge: "+u.getBlockNo()+" --> "+v.getBlockNo());
		if(label!=null)
			sb.append(" "+label);
		return sb.toString();
	}
	
	/**
	 * @return The appropriate graphviz syntax that represents this edge
	 */
	public String toDot(){
		String ret = u.getBlockNo()+" -> "+v.getBlockNo();
		if(label!=null){
			ret+="[xlabel=\""+label+"\"];";
		}
		else ret+=";";
		return ret;
	}
}
