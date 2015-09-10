package org.chinkara.mastermind.view;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import org.chinkara.mastermind.model.CodeColour;


@SuppressWarnings("serial")
public class ColourMenu extends JPopupMenu {
		
	public ColourMenu(ColourPanel colourPanel) {
		
		for (CodeColour col: CodeColour.values()) {
			
			JMenuItem item = new JMenuItem(col.name());
			item.addActionListener(colourPanel);
			add(item);
		}
		add(new JSeparator());
		JMenuItem item = new JMenuItem("Clear");
		item.addActionListener(colourPanel);
		add(item);
	}

}
