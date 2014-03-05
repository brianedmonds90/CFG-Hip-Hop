package CFG;

public class Edge {
	BasicBlock u,v;
	String label;
	
	public Edge(BasicBlock a,BasicBlock b, String label){
		u=a;
		v=b;
		this.label = label;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("Edge: "+u.getBlockNo()+" --> "+v.getBlockNo());
		return sb.toString();
	}
}
