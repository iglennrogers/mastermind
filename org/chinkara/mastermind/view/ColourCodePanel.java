package org.chinkara.mastermind.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.chinkara.mastermind.model.CodeColour;
import org.chinkara.mastermind.model.ColourCode;
import org.chinkara.mastermind.model.Model;


@SuppressWarnings("serial")
public class ColourCodePanel extends JPanel {

	private final List<ColourPanel> colourPanels = new ArrayList<ColourPanel>(Model.getPlaces());
	
	private ComponentAdapter resizeListener = new ComponentAdapter() {
		
	    public void componentResized(ComponentEvent e) {
	    	
	        if (e.getID() != ComponentEvent.COMPONENT_RESIZED) return;
	        
	        ColourCodePanel panel = (ColourCodePanel)e.getSource();
	        Dimension sz = panel.getSize();
	        int w = sz.width/Model.getPlaces();
	        int h = sz.height;
	        
			for (ColourPanel col: colourPanels) {
				
		        col.setSize(Math.min(w, h));
			}
	        
	    }
	};

	ColourCodePanel(int rowId) {

        setBackground(Color.LIGHT_GRAY);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        for (int i = 0; i < Model.getPlaces(); i++) {
        	
        	ColourPanel col = new ColourPanel(rowId, i);
        	colourPanels.add(col);
        	
	        add(col);
        }
		addComponentListener(resizeListener);
	}

	public void activate(boolean isActive) {

		for (ColourPanel col: colourPanels) {
			
			col.activate(isActive);
		}
	}
	
	void setSize(int size) {
		
		Dimension d = new Dimension(Model.getPlaces()*size, size);
        setSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
	}

	void set(ColourCode code) {
		
		for (ColourPanel col: colourPanels) {

			col.set(code.get(col.getColumn()));
		}
	}
	
	void set(ColourCode code, View.EntryType type) {
		
		for (ColourPanel col: colourPanels) {

			col.set(code.get(col.getColumn()), type);
		}
	}
	
	public void notifyEntry(int rowId, int colId, CodeColour colour) {

		EntryPanel entry = (EntryPanel)getParent();
		entry.notifyEntry(rowId, colId, colour);
	}

	void setGuess(int colId, CodeColour col) {

		if (col != null) {
			
			colourPanels.get(colId).set(col);
		}
		else {
			
			colourPanels.get(colId).clear();
		}
	}
}
