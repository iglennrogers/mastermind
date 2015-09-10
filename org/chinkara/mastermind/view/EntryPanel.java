package org.chinkara.mastermind.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.chinkara.mastermind.model.AnswerCode;
import org.chinkara.mastermind.model.CodeColour;
import org.chinkara.mastermind.model.ColourCode;
import org.chinkara.mastermind.model.Mark;
import org.chinkara.mastermind.model.Model;


@SuppressWarnings("serial")
public class EntryPanel extends JPanel {

	private final View view;
	private final ColourCodePanel codepanel;
	private final MarkPanel markpanel;
	
	private ComponentAdapter resizeListener = new ComponentAdapter() {
		
	    public void componentResized(ComponentEvent e) {
	    	
	        if (e.getID() != ComponentEvent.COMPONENT_RESIZED) return;
	        
	        EntryPanel panel = (EntryPanel)e.getSource();
	        Dimension sz = panel.getSize();
	        int w = (int) ((sz.width - 5*View.SPACING)/(Model.getPlaces() + ((Model.getPlaces() + 1)/2)/2.0));
	        int h = sz.height;
	        panel.codepanel.setSize(Math.min(w, h));
	        panel.markpanel.setSize(Math.min(w, h));
	    }
	};

	EntryPanel(View view, int i) {

		this.view = view;
		codepanel = new ColourCodePanel(i);
		markpanel = new MarkPanel();
		
        setBackground(Color.LIGHT_GRAY);

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(Box.createHorizontalGlue());
        add(codepanel);
        if (i != Model.getMaxGuesses()) {
        	
	        add(Box.createHorizontalStrut(View.SPACING));
	        add(markpanel);
        }
        add(Box.createHorizontalGlue());
		addComponentListener(resizeListener);
	}
	
	public void setCode(ColourCode colcode) {
		
		codepanel.set(colcode);
	}
	
	public void setMark(Mark colmark) {
		
		markpanel.set(colmark);
	}
	
	public void activate(boolean isActive) {
	
		if (isActive) {
			
			setBackground(Color.DARK_GRAY);
		}
		else {
			
			setBackground(Color.LIGHT_GRAY);
		}
		codepanel.activate(isActive);
	}
	
	public void notifyEntry(int rowId, int colId, CodeColour colour) {
		
		view.notifyEntry(rowId, colId, colour);
	}

	public void setGuess(int colId, CodeColour col) {
		
		codepanel.setGuess(colId, col);
	}

	public void setAnswer(AnswerCode answer, View.EntryType visibility) {
		
		codepanel.set(answer, visibility);
	}

	public void clear() {

		for (int i = 0; i < Model.getPlaces(); i++) {
			
			codepanel.setGuess(i, null);
		}
		markpanel.set(null);
	}
}
