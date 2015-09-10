package org.chinkara.mastermind.solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.chinkara.mastermind.Mastermind;
import org.chinkara.mastermind.model.AnswerCode;
import org.chinkara.mastermind.model.CodeColour;
import org.chinkara.mastermind.model.Guess;
import org.chinkara.mastermind.model.Mark;
import org.chinkara.mastermind.model.Model;


public class Solver {

	private static final Random RandomColour = new Random();
	
	private final Set<AnswerCode> possibilities;
	private final Mastermind controller;

	private List<int[]> colourCounts;
	
	
	public Solver(Mastermind controller) {
		
		this.controller = controller;
		possibilities = new HashSet<AnswerCode>();
		
		int places = Model.getPlaces();
		AnswerCode curr = firstCode(places);
		do {
			
			possibilities.add(new AnswerCode(curr));
		} while (nextCode(places, curr));
		
		colourCounts = createColourCounts(possibilities);
	}

	public void solve() {
		
		do {
			//reportCurrentPossibilities();
			
			suggest();
			controller.guess();
		} while (!controller.hasEnded());
	}
	
	public void suggest() {
		
		AnswerCode suggestion = createSuggestion();
		//System.out.println("Suggestion : " + suggestion.toString());
		for (int c = 0; c < Model.getPlaces(); c++) {
			
			controller.set(c, suggestion.get(c));
		}
	}
	
	public void filterPossibilitiesOnMark(Guess guess) {
		
		Iterator<AnswerCode> iter = possibilities.iterator();
		while (iter.hasNext()) {
			
			AnswerCode possAnswer = iter.next();
			Mark mark = possAnswer.check(guess.code());
		    if (!mark.isEqual(guess.mark())) {
		    	
		        iter.remove();
		    }
		}
		colourCounts = createColourCounts(possibilities);
	}

	@SuppressWarnings("unused")
	private void reportCurrentPossibilities() {
		
		System.out.println("~~~~~");
		System.out.println("Possibilities : " + Integer.toString(possibilities.size()));
		
		reportColourCount(colourCounts);
	}
	
	private AnswerCode createSuggestion()
	{
		boolean suitable = true;
		AnswerCode suggestion = null;
		
		do {
			
			suggestion = new AnswerCode(Model.getPlaces());
			Set<AnswerCode> currentPossibilities = new HashSet<AnswerCode>(possibilities);
			
			for (int i = 0; i < Model.getPlaces(); i++) {
				
				SuggestedPosition next = getNextPlace(suggestion, currentPossibilities);
				suggestion.set(next.getPosition(), next.getColour());
				suitable = filterPossibilitiesOnSuggestion(currentPossibilities, suggestion);
				if (!suitable) break;
			}
		} while (!suitable);
		return suggestion;
	}
	
	private static boolean filterPossibilitiesOnSuggestion(Set<AnswerCode> currentPossibilities, AnswerCode suggestion) {
		
		Iterator<AnswerCode> iter = currentPossibilities.iterator();
		while (iter.hasNext()) {
			
			AnswerCode possAnswer = iter.next();
			if (!possAnswer.matches(suggestion)) {
				
				iter.remove();
			}
		}
		return (currentPossibilities.size() != 0);
	}
	
	private static SuggestedPosition getNextPlace(AnswerCode suggestion, Set<AnswerCode> currentPossibilities) {
		
		List<SuggestedPosition> list = new ArrayList<SuggestedPosition>();
		
		int currLimit = Integer.MIN_VALUE;
		
		List<int[]> currCounts = createColourCounts(currentPossibilities);
		for (int i = 0; i < Model.getPlaces(); i++) {
			
			if (suggestion.get(i) != null) continue;
			
			int[] counts = currCounts.get(i);
			for (CodeColour col = CodeColour.first(); col != null; col = col.next()) {
				
				int count = counts[col.ordinal()];
				if (count > 0) {
					
					if (currLimit < count) {
						
						currLimit = count;
						list.clear();
					}
					if (currLimit == count) {
						
						list.add(new SuggestedPosition(i, col));
					}
				}
			}
		}
		
		int s = RandomColour.nextInt(list.size());
		return list.get(s);
	}
	
	private static List<int[]> createColourCounts(Set<AnswerCode> possibilities)
	{
		int codeplaces = Model.getPlaces();
		List<int[]> colourCounts = new ArrayList<int[]>(codeplaces);
		
		for (int place = 0; place < codeplaces; place++) {
			
			int[] placeCounts = new int[CodeColour.size()];
			for (AnswerCode code: possibilities) {
				
				CodeColour col = code.get(place);
				placeCounts[col.ordinal()]++;
			}
			colourCounts.add(placeCounts);
		}
		return colourCounts;
	}
	
	private static AnswerCode firstCode(int places) {
	
		AnswerCode curr = new AnswerCode(places);
		for (int i = 0; i < places; i++) {
			
			curr.set(i, CodeColour.first());
		}
		return curr;
	}
	
	private static boolean nextCode(int places, AnswerCode curr) {
		
		for (int i = 0; i < places; i++) {
			
			CodeColour col = curr.get(i).next();
			if (col != null) {
				
				curr.set(i, col);
				return true;
			}
			else {
				
				curr.set(i, CodeColour.first());
			}
		}
		return false;
	}
	
	private static void reportColourCount(List<int[]> colourCounts) {
		
		for (int place = 0; place < Model.getPlaces(); place++) {
			
			System.out.print("Position : " + Integer.toString(place) + " :");
			
			int[] placeCounts = colourCounts.get(place);
			for (CodeColour col = CodeColour.first(); col != null; col = col.next()) {
				
				System.out.print(" " + col.toString() + " : " + placeCounts[col.ordinal()] + " :");
			}
			System.out.println();
		}
		System.out.println();
	}
}
