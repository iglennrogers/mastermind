package org.chinkara.mastermind.model;


public class AnswerCode extends ColourCode {

	public AnswerCode(int size) {
		
		super(size);
	}
	
	public AnswerCode(AnswerCode other) {
		
		super(other);
	}
	
	void randomise() {
		
		for (int i = 0; i < size(); i++) {
			
			set(i, CodeColour.random());
		}
	}
	
	public boolean matches(AnswerCode other) {

		for (int i = 0; i < size(); i++) {
			
			CodeColour c = other.get(i); 
			if (c != null) {
				
				if (get(i) != c) return false;
			}
		}
		return true;
	}
	
	public Mark check(ColourCode guess)
	{
		if (guess.size() != size()) {
			
			throw new IllegalArgumentException("Code should have " + Integer.toString(size()) + " entries only");
		}
		
		int c = 0;
		int w = 0;
	
		int[] possWrongCodes = new int[CodeColour.size()];
		int[] leftoverCodes = new int[CodeColour.size()];
		
		for (int i = 0; i < size(); i++) {
			
			if (get(i) == guess.get(i)) {
				
				c++;
			}
			else {

				possWrongCodes[guess.get(i).ordinal()]++;
				leftoverCodes[get(i).ordinal()]++; 
			}
		}
		
		for (int i = 0; i < CodeColour.size(); i++) {
			
			int wrong = possWrongCodes[i];
			int left = leftoverCodes[i];
			if ((wrong > 0) && (left > 0)) {
				
				w += Math.min(wrong, left);
			}
		}
		
		return new Mark(size(), c, w);
	}
}
