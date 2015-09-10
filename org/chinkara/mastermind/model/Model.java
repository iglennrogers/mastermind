package org.chinkara.mastermind.model;

import java.util.ArrayList;
import java.util.List;


public class Model {

	private static final int MAX_GUESSES = 15;
	private static final int PLACES = 5;
	
	public static int getMaxGuesses() {
		
		return MAX_GUESSES;
	}
	
	public static int getPlaces() {

		return PLACES;
	}
	
	private final AnswerCode answer = new AnswerCode(PLACES);
	private final List<Guess> guesses = new ArrayList<Guess>(MAX_GUESSES);
	
	
	public Model() {
	}
	
	public void begin() {

		answer.randomise();
		guesses.clear();
		for (int i = 0; i < MAX_GUESSES; i++) {
			
			guesses.add(new Guess());
		}
	}

	public boolean hasWon(int guessNumber) {
		
		return (guesses.get(guessNumber).hasWon());
	}
	
	public boolean hasEnded(int guessNumber) {
		
		return (guessNumber == (MAX_GUESSES - 1)) || hasWon(guessNumber);
	}
	
	public AnswerCode getAnswer() {
		
		return answer;
	}
	
	public Guess getGuess(int index) {
		
		return guesses.get(index);
	}
	
}
