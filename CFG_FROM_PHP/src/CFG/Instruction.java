package CFG;

public class Instruction {
	int type;
	String [] args;
	int line;
	public Instruction(){
		args= new String [10];
	}
	
	public Instruction(int t){
		this();
		type = t;
	}
	
	public String toString() {
		String str = type + " ";
		for (int i=0; i<args.length; i++)
			str += args[i]+" ";
		str += "\n";
		return str;
	}
}
