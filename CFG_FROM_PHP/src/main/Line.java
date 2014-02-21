package main;

import java.util.ArrayList;

public class Line {
	int line_no;
	ArrayList<Instruction> instructions;
	
	public Line(){
		instructions= new ArrayList<Instruction>();
	}
	
	public Line(int l){
		this();
		line_no = l;
	}
}
