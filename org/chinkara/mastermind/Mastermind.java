package org.chinkara.mastermind;

import org.chinkara.mastermind.model.CodeColour;
import org.chinkara.mastermind.model.Guess;
import org.chinkara.mastermind.model.Model;
import org.chinkara.mastermind.solver.Solver;
import org.chinkara.mastermind.view.View;


public class Mastermind {

	private final Model model = new Model();
	private final View view = new View(this);
	private	Solver solver;
	
	private int currentGuess;
	private boolean gameEnd;
	
	private Mastermind() {
	}
	
	private void run() {
		
		model.begin();
		
		currentGuess = 0;
		gameEnd = false;
		
		view.allowMarking(false);
		view.activate(currentGuess, true);
		view.setAnswer(model.getAnswer(), false);
		
		solver = new Solver(this);
	}

	public void set(int colId, CodeColour col) {
		
		Guess guess = model.getGuess(currentGuess);
		guess.set(colId, col);
		
		view.setGuess(currentGuess, colId, col);
		view.allowMarking(guess.isGuessable());
	}
	
	public int getGuessNumber() {
	
		return currentGuess;
	}
	
	public boolean hasEnded() {
	
		return gameEnd;
	}
	
	public void guess() {

		view.allowMarking(false);
		view.activate(currentGuess, false);
		
		Guess guess = model.getGuess(currentGuess);
		guess.apply(model.getAnswer());
		
		view.setMark(currentGuess, guess.mark());
		
		if (model.hasEnded(currentGuess)) {
			
			view.setAnswer(model.getAnswer(), true);
			gameEnd = true;
		}
		else {
			
			currentGuess++;
			view.activate(currentGuess, true);
			
			solver.filterPossibilitiesOnMark(guess);
		}
	}

	public void exit() {
		
		System.out.println(model.getAnswer().toString());
		view.close();		
	}

	public void newGame() {
		
		System.out.println(model.getAnswer().toString());
		view.activate(currentGuess, false);
		view.newGame();
		run();
	}
	
	public void solve() {
		
		solver.solve();
	}
	
	public void suggest() {
		
		solver.suggest();
	}
	
	public static void main(String[] args) {

        System.out.println("Mastermind");

        new Mastermind().run();
	}
}

