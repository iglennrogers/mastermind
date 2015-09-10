package org.chinkara.mastermind.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;

import org.chinkara.mastermind.model.Mark;
import org.chinkara.mastermind.model.Model;


@SuppressWarnings("serial")
public class MarkPanel extends JPanel {

	private final double widthFactor;
	private Mark mark;
	private View.EntryType type;
	
	
	MarkPanel() {
		
		widthFactor = ((Model.getPlaces() + 1)/2)/2.0;
        setSize(View.SPOT);
        type = View.EntryType.HIDDEN;
        setBackground(Color.MAGENTA);
	}

	void setSize(int size) {
		
		Dimension d = new Dimension((int)(size*widthFactor), size);
        setSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
	}

	void set(Mark mark) {
		
		this.mark = mark;
		if (mark != null) {
			
			type = View.EntryType.FULL;
		}
		else {
			
			type = View.EntryType.EMPTY;
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);

		if (type != View.EntryType.HIDDEN) {
			
			Point pt = getLocation();
			Rectangle rc = getBounds();
			
			int x = rc.x - pt.x;
			int y = rc.y - pt.y;
			int w = (rc.width)/((Model.getPlaces() + 1)/2);
			int h = (rc.height)/2;

			if (type == View.EntryType.FULL) {
				
				for (int i = 0; i < Model.getPlaces(); i++) {
					
					Mark.MarkColour col = mark.get(i);
					if (col != null) {
						
						g.setColor(col.colour());
						g.fillOval(x + (i/2)*w, y + (i%2)*h, w, h);
					}
				}
			}
			
			for (int i = 0; i < Model.getPlaces(); i++) {
				
				g.setColor(Color.ORANGE);
				g.drawOval(x + (i/2)*w, y + (i%2)*h, w, h);
			}
		}
	}
}
