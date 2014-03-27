package CFG;

import java.util.ArrayList;

public class Function {
	ArrayList<Line> lines; 
	String name;
	ArrayList<String> fpis;
	int numParams;
	
	public Function(){
		lines = new ArrayList<Line>();
		fpis = new ArrayList<String>();
		numParams = 0;
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
		StringBuilder sb = new StringBuilder("Function: " + name +" num params: "+numParams+"\n");
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

	
	public int calcNumParams() {
		int numVars = 0;
		int minSetL = 999999999;
		int temp1,temp2;
		for(Line l: lines){
			for(Instruction i: l.getInstructions()){
				
				if(i.type == i.get){
					temp1 = Integer.parseInt(i.args[0]);
					if(temp1 > numVars){
						numVars= temp1;
					}
				}
				else if(i.type == i.set){
					temp2 = Integer.parseInt(i.args[0]);
					if(temp2 < minSetL){
						minSetL = temp2;
					}
				}
			}
		}
		if(numVars>minSetL){
			return minSetL;
		}
		else 
			return 0;
		
	}

	public void setNumParams(int numParams2) {
		numParams = numParams2;
	}
}
