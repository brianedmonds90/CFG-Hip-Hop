package CFG;

/**
 * This class represents an edge in the CFG, flows from U to V.
 *
 */
public class Edge {
	private BasicBlock u,v;
	private String label;
	
	public Edge(BasicBlock a, BasicBlock b) {
		u = a;
		v = b;
	}
	
	public Edge(BasicBlock a, BasicBlock b, String label){
		this(a, b);
		this.label = label;
	}
	
	public BasicBlock getU() {
		return u;
	}
	
	public BasicBlock getV() {
		return v;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("Edge: "+u.getBlockNo()+" --> "+v.getBlockNo());
		if(label!=null)
			sb.append(" "+label);
		return sb.toString();
	}
	
	public String toDot(){
		String ret = u.getBlockNo()+" -> "+v.getBlockNo();
		if(label!=null){
			ret+="[xlabel=\""+label+"\"];";
		}
		else ret+=";";
		return ret;
	}
}
