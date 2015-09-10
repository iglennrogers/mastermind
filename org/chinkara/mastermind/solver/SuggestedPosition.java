package org.chinkara.mastermind.solver;

import org.chinkara.mastermind.model.CodeColour;
import org.chinkara.mastermind.model.Model;


class SuggestedPosition {

	private final int position;
	private final CodeColour colour;
	
	public SuggestedPosition() {
		
		this(Model.getPlaces(), null);
	}
	
	public SuggestedPosition(int position, CodeColour colour) {
		
		this.position = position;
		this.colour = colour;
	}
	
	public int getPosition() {
		
		if (position == Model.getPlaces()) throw new IllegalArgumentException("Position should have real value");
		
		return position;
	}
	
	public CodeColour getColour() {
		
		if (colour == null) throw new IllegalArgumentException("Colour should have real value");
		
		return colour;
	}
}
