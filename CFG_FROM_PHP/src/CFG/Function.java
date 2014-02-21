package CFG;

import java.util.ArrayList;

public class Function {
	ArrayList<Line> lines; 
	String name;
	public Function(){
		lines = new ArrayList<Line>();
	}
	
	public Function(String n){
		this();
		name = n;
	}

	public ArrayList<Line> getLines() {
		return lines;
	}

	public void setLines(ArrayList<Line> lines) {
		this.lines = lines;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		String str = "Function: " + name + "\n";
		for (int i=0; i<lines.size(); i++)
			lines.toString();
		return str;
	}
}
