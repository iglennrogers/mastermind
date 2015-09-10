package org.chinkara.mastermind.model;

import java.awt.Color;
import java.util.Random;


public enum CodeColour {

	Black(Color.BLACK),
	White(Color.WHITE),
	Red(Color.RED),
	Green(Color.GREEN),
	Blue(Color.BLUE),
	Yellow(Color.YELLOW),
	Cyan(Color.CYAN),
	Magenta(Color.MAGENTA);	
	
	private static final Random RandomColour = new Random();
	private static final CodeColour[] colList = CodeColour.values();
	
	private final Color col;
	
	private CodeColour(Color col) {
	
		this.col = col;
	}
	
	public Color colour() {
	
		return col;
	}
	
	public static int size() {
	
		return colList.length;
	}
	
	public static CodeColour random() {
		
		return colList[RandomColour.nextInt(colList.length)];
	}
	
	public static CodeColour first() {
		
		return colList[0];
	}
	
	public static CodeColour next(CodeColour curr) {
		
		int nextOrd = (curr.ordinal() + 1) % size();
		if (nextOrd > 0) {
			
			return colList[nextOrd];
		}
		else {
			
			return null;
		}
	}
	
	public CodeColour next() {
		
		return CodeColour.next(this);
	}
}

