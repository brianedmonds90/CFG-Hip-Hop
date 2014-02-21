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
	
}
