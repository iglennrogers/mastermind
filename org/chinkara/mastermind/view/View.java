package org.chinkara.mastermind.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import org.chinkara.mastermind.Mastermind;
import org.chinkara.mastermind.model.AnswerCode;
import org.chinkara.mastermind.model.CodeColour;
import org.chinkara.mastermind.model.Mark;
import org.chinkara.mastermind.model.Model;


public class View {

	public enum EntryType {
		
		HIDDEN,
		EMPTY,
		FULL
	}
	
	public static final int SPACING = 3;
	public static final int SPOT = 40;
	
	private final Mastermind controller;
	private final Map<Integer,EntryPanel> entry = new HashMap<Integer,EntryPanel>();
	private final Map<String,Action> commands = new HashMap<String,Action>();
	
	private ComponentAdapter resizeListener = new ComponentAdapter() {
		
	    public void componentResized(ComponentEvent e){
	    	
	        if (e.getID() != ComponentEvent.COMPONENT_RESIZED) return;
	        
	        JFrame view = (JFrame)e.getSource();
	        Dimension sz = view.getContentPane().getSize();
	        int h = (sz.height - (Model.getMaxGuesses() + 3)*SPACING)/(Model.getMaxGuesses() + 2);
	        
	        for (Component entry: view.getContentPane().getComponents()) {
	        	
	        	try {
	        		
		        	EntryPanel panel = (EntryPanel)entry;
		        	panel.setSize(sz.width, h);
	        	}
	        	catch (ClassCastException ex) {
	        	}
	        }
	    }
	};
	
	public View(Mastermind controller) {
		
		this.controller = controller;
		
        JFrame f = new JFrame();
        f.setTitle("Mastermind");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createCommands();
        
        JMenuBar menu = new JMenuBar();
        menu.add(createMenu());
        f.setJMenuBar(menu);
        
        JPanel contents = createGamePanel();
        f.setContentPane(contents);
		
        f.pack();
        f.setVisible(true);
		f.addComponentListener(resizeListener);
		
		newGame();
	}
	
	@SuppressWarnings("serial")
	private void createCommands() {
		
		{
			Action actionNew = new AbstractAction("New") {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					onGameNew();
				}
			};
			actionNew.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
			actionNew.putValue(Action.SHORT_DESCRIPTION, "New game");
			commands.put("New", actionNew);
		}
		{
			Action actionSuggest = new AbstractAction("Suggest") {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					onGameSuggest();
				}
			};
			actionSuggest.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
			actionSuggest.putValue(Action.SHORT_DESCRIPTION, "Ask the computer to try a guess");
			commands.put("Suggest", actionSuggest);
		}
		{
			Action actionSolve = new AbstractAction("Solve") {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					onGameSolve();
				}
			};
			actionSolve.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
			actionSolve.putValue(Action.SHORT_DESCRIPTION, "Get the computer to solve the code");
			commands.put("Solve", actionSolve);
		}
		{
			Action actionExit = new AbstractAction("Exit") {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					onGameExit();
				}
			};
			actionExit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
			actionExit.putValue(Action.SHORT_DESCRIPTION, "Exit the game");
			commands.put("Exit", actionExit);
		}
		{
			Action actionGuess = new AbstractAction("Guess") {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					onGuessButtonPressed();
				}
			};
			actionGuess.putValue(Action.SHORT_DESCRIPTION, "Guess the next colour code");
			commands.put("Guess", actionGuess);
		}
	}
	
	private JPanel createGamePanel() {
		
		JPanel contents = new JPanel();
        contents.setLayout(new BoxLayout(contents, BoxLayout.PAGE_AXIS));
        contents.setBackground(Color.BLUE);
    	contents.add(Box.createVerticalStrut(SPACING));
        
        for (int i = 0; i < Model.getMaxGuesses() + 1; i++) {
        	
        	int rowId = Model.getMaxGuesses() - i;
        	EntryPanel currEntry = new EntryPanel(this, rowId);
        	entry.put(rowId, currEntry);
        	
        	contents.add(currEntry);
        	contents.add(Box.createVerticalStrut(SPACING));
        }
    	contents.add(createButton());
    	contents.add(Box.createVerticalStrut(SPACING));
		return contents;
	}

	private JPanel createButton() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.setBackground(Color.BLUE);
		panel.add(Box.createHorizontalGlue());
		panel.add(new JButton(commands.get("Guess")));
		panel.add(Box.createHorizontalGlue());
		
		Dimension d = new Dimension(5*SPACING + (Model.getPlaces() + 1)*SPOT, SPOT);
        panel.setSize(d);
        panel.setMaximumSize(d);
        panel.setMinimumSize(d);
        panel.setPreferredSize(d);
        return panel;
	}
	
	private JMenu createMenu() {
		
		JMenu menu = new JMenu("Game");
		
		menu.add(new JMenuItem(commands.get("New")));
		menu.add(new JSeparator());
		menu.add(new JMenuItem(commands.get("Suggest")));
		menu.add(new JMenuItem(commands.get("Solve")));
		menu.add(new JSeparator());
		menu.add(new JMenuItem(commands.get("Exit")));
		
		return menu;
	}
	
	protected void onGameExit() {

		controller.exit();
	}

	protected void onGameNew() {
		
		controller.newGame();
	}

	protected void onGameSolve() {
		
		controller.solve();
		commands.get("New").setEnabled(true);
	}

	protected void onGameSuggest() {
		
		controller.suggest();
	}

	void onGuessButtonPressed() {
	
		controller.guess();
		commands.get("New").setEnabled(true);
	}
	
	void notifyEntry(int rowId, int colId, CodeColour colour) {

		int currentGuess = controller.getGuessNumber();
		if (rowId != currentGuess) {
			
			String s = "Unexpected row setting for colour : " + Integer.toString(rowId) + " should be " + Integer.toString(currentGuess);
			throw new IllegalArgumentException(s);
		}

		controller.set(colId, colour);
	}
	
	public void allowMarking(boolean allow) {
		
		commands.get("Guess").setEnabled(allow);
	}
	
	public void setGuess(int rowId, int colId, CodeColour col) {
		
		EntryPanel panel = entry.get(rowId);
		panel.setGuess(colId, col);
	}
	
	public void activate(int rowId, boolean isActive) {
		
		EntryPanel panel = entry.get(rowId);
		panel.activate(isActive);
		commands.get("Suggest").setEnabled(isActive);
		commands.get("Solve").setEnabled(isActive);
	}

	public void setMark(int rowId, Mark mark) {
		
		EntryPanel panel = entry.get(rowId);
		panel.setMark(mark);
	}

	public void setAnswer(AnswerCode answer, boolean isVisible) {
		
		EntryPanel panel = entry.get(Model.getMaxGuesses());
		if (isVisible) {
			
			panel.setAnswer(answer, View.EntryType.FULL);
		}
		else {
			
			panel.setAnswer(answer, View.EntryType.HIDDEN);
		}
	}

	public void newGame() {
		
		for (EntryPanel panel: entry.values()) {
			
			panel.clear();
		}
		commands.get("New").setEnabled(false);
	}

	public void close() {
		
		for (Frame frame: Frame.getFrames()) {
			
			frame.dispose();
		}
	}
}
