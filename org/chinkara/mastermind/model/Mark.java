package org.chinkara.mastermind.model;

import java.awt.Color;


public class Mark {
	
	public enum MarkColour {
		
		BLACK(Color.BLACK),
		WHITE(Color.WHITE);
		
		private final Color col;
		
		private MarkColour(Color col) {
		
			this.col = col;
		}
		
		public Color colour() {
		
			return col;
		}
	}
	
	public final int size;
	public final int correct;
	public final int wrong;
	
	public Mark(final int s, final int c, final int w)
	{
		size = s;
		correct = c;
		wrong = w;
	}

	public MarkColour get(int i) {
	
		if (i < correct) {
			
			return MarkColour.BLACK;
		}
		else if (i < correct + wrong) {
			
			return MarkColour.WHITE;
		}
		else {
			
			return null;
		}
	}
	
	public Boolean hasWon()
	{
		return (correct == size);
	}
	
	public boolean isEqual(Mark other) {
		
		if (this.correct != other.correct) return false;
		if (this.wrong != other.wrong) return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return ((Integer)correct).toString() + " correct, " + ((Integer)wrong).toString() + " wrong";
	}
}
