package CFG;

public class Edge {
	BasicBlock u,v;
	public String label;
	
	public Edge(BasicBlock a,BasicBlock b, String label){
		u=a;
		v=b;
		this.label = label;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("Edge: "+u.getBlockNo()+" --> "+v.getBlockNo());
		if(label!=null)
			sb.append(" "+label);
		return sb.toString();
	}
}
