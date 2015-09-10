package org.chinkara.mastermind.model;



public class Guess {

	private final ColourCode code = new ColourCode(Model.getPlaces());
	private Mark mark;
	
	public Guess() {
	}
	
	public void apply(AnswerCode answer) {
		
		mark = answer.check(code);
	}
	
	public boolean hasWon()
	{
		return mark.hasWon();
	}

	public Mark mark()
	{
		return mark;
	}

	public ColourCode code() {
	
		return code;
	}
	
	public void set(int colId, CodeColour col) {
	
		code.set(colId, col);
	}
	
	public void clear(int index) {
		
		code.clear(index);
	}
	
	public boolean isGuessable() {
		
		return code.isGuessable();
	}
	
	@Override
	public String toString()
	{
		StringBuilder build = new StringBuilder();
		build.append(code.toString());
		if (mark != null)
		{
			build.append("= ");
			build.append(mark.toString());
		}
		return build.toString();
	}
	
}
