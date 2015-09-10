package org.chinkara.mastermind.model;


public class ColourCode {

	private CodeColour[] code;
	
	public ColourCode(int size) {
		
		code = new CodeColour[size];
	}
	
	public ColourCode(ColourCode other) {
		
		code = new CodeColour[other.size()];
		for (int i = 0; i < code.length; i++) {
			
			code[i] = other.code[i];
		}
	}
	
	public CodeColour get(int index) {
	
		if (index > code.length) throw new IllegalArgumentException("Index too big");
		
		return code[index];
	}
	
	public void set(int index, CodeColour col) {
		
		code[index] = col;
	}
	
	public void clear(int index) {
		
		code[index] = null;
	}
	
	public boolean isGuessable() {
		
		for (CodeColour col: code) {
			
			if (col == null) {
				
				return false;
			}
		}
		return true;
	}
	
	int size() {
		
		return code.length;
	}
	
	@Override
	public String toString()
	{
		StringBuilder build = new StringBuilder();
		for (CodeColour c : code) {
			
			if (c != null) {
				
				build.append(c.toString().toLowerCase());
			}
			else {
				
				build.append("null");
			}
			build.append(" ");
		}
		return build.toString();
	}
}
