package main;

public class StateMachine {
	int state;
	int input;
	public final int function = 1;
	public final int line= 2;
	public final int fpi = 4;
	public final int instruction = 3;
	public final int init= 0;
	public final int space= 6;
	public final int eh_state =7;
	public final int excess = 8;
	public StateMachine(){
		state = init;
	}
	
	public void whatState(int input){
		if(state == init){
			if(input == function){
				state = function;
			}
		}
		else if(state == function){
			if(input == line){
				state = line;
			}
			else if(input == fpi){
				state = fpi;
			}
			else if(input == eh_state){
				state = eh_state;
			}
		}
		else if(state == fpi){
			if(input == line){
				state = line;
			}
			else if(input == function){
				state = function;
			}
			else if(input == fpi){
				state = fpi;
			}
		}
		else if(state == line){
			if(input!= space || input!=function||input!=line){
				state = instruction;
			}
		}
		else if(state== instruction){
			if(input == instruction){
				state = instruction;
			}
			else if(input == function){
				state = function;
			}
			else if(input == line){
				state = line;
			}
			else{
				state = space;
			}
		}
//		else if(state== instruction){
//			if(input==instruction){
//				state = instruction;
//			}
//			else if(input == function){
//				state = function;
//			}
//			else if(input == line){
//				state = line;
//			}
//			
//
//		}
		else if(state == space){
			if(input == function) state = function;
			if(input == line) state = line;
			if(input == instruction) state= instruction;
		}
		
		else if(state==eh_state){
			if(input==fpi){
				state=fpi;
			}
			if(input==eh_state){
				state = eh_state;
			}
			
		}
		
	}

	public void handleInput(String str) {
		if(str.contains("Function")||str.contains("Pseudo-main")) whatState(function);
		else if(str.contains("// line")) whatState(line);
		else if(str.contains("FPI")) whatState(fpi);
		else if(str.equals(" ")||str.equals("")) whatState(space);
		else if(str.contains("EH")||str.contains("handle exception")) whatState(eh_state);
		else if(str.contains("::")) 
			whatState(excess);
		else whatState(instruction);
	}

	public int getCurrentState() {
		return state;
	}


}
