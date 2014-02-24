package CFG;

import java.util.ArrayList;

public class Function {
	ArrayList<Line> lines; 
	String name;
	ArrayList<String> fpis;
	public Function(){
		lines = new ArrayList<Line>();
		fpis = new ArrayList<String>();
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
		StringBuilder sb = new StringBuilder("Function: " + name +"\n");
		for(String s: fpis){
			sb.append("FPIS: "+s+"\n");
		}
		for (Line l: lines)
			sb.append(l);
		return sb.toString();
	}

	public void addLine(int str) {
		lines.add(new Line(str));
		
	}

	public void addFPI(String next) {
		fpis.add(next);
	}
}
