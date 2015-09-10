package org.chinkara.mastermind.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.chinkara.mastermind.model.CodeColour;



@SuppressWarnings("serial")
public class ColourPanel extends JPanel implements ActionListener {

	private final int rowId;
	private final int colId;
	
	private CodeColour code;
	private View.EntryType type;
	
	private final JPopupMenu menu;
	private final MouseAdapter mouseAction;
	
	ColourPanel(int rowId, int colId) {

		this.rowId = rowId;
		this.colId = colId;
		
		menu = new ColourMenu(this);
		mouseAction = new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				
				if (e.getButton() == MouseEvent.BUTTON1) {
					
					onMouseClick();
				}
			}
		};
		
		setSize(View.SPOT);
        setBackground(Color.LIGHT_GRAY);
        setOpaque(false);
        type = View.EntryType.EMPTY;
	}

	void setSize(int size) {
		
		Dimension d = new Dimension(size, size);
        setSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
	}

	int getColumn() {
		
		return colId;
	}
	
	void set(CodeColour code, View.EntryType type) {
		
		this.code = code;
        this.type = type;
        repaint();
	}
	
	void set(CodeColour code) {
		
		set(code, View.EntryType.FULL);
	}
	
	void clear() {
		
		set(null, View.EntryType.EMPTY);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);

		if (type != View.EntryType.HIDDEN) {
			
			Point pt = getLocation();
			Rectangle rc = getBounds();
			
			int x = rc.x - pt.x;
			int y = rc.y - pt.y;
			int w = (rc.width - 1);
			int h = (rc.height - 1);
			
			if (code != null) {
				
				float[] dist = {0, 1};
				if (code != CodeColour.Black) {
					Color[] cols = {code.colour(), code.colour().darker()};
					RadialGradientPaint gradient = new RadialGradientPaint(x + w/2, y + h/2, w/2, dist, cols);
					((Graphics2D)g).setPaint(gradient);
				}
				else {
					Color[] cols = {Color.DARK_GRAY, code.colour()};
					RadialGradientPaint gradient = new RadialGradientPaint(x + w/2, y + h/2, w/2, dist, cols);
					((Graphics2D)g).setPaint(gradient);
				}
				g.fillOval(x, y, w, h);
			}
			
			g.setColor(Color.ORANGE);
			g.drawOval(x, y, w, h);
		}
	}

	public void activate(boolean isActive) {

		if (isActive) {
			
	    	setComponentPopupMenu(menu);
	    	addMouseListener(mouseAction);
		}
		else {

	    	setComponentPopupMenu(null);
	    	removeMouseListener(mouseAction);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		CodeColour colour = null;
		try {
			
			colour = CodeColour.valueOf(e.getActionCommand());
		}
		catch (IllegalArgumentException ex) {// 'Clear'
		}
		
		ColourCodePanel parent = (ColourCodePanel)getParent();
		parent.notifyEntry(rowId, colId, colour);
	}

	protected void onMouseClick() {

		CodeColour newColour = null;
		if (code == null) {
			
			newColour = CodeColour.values()[0];
		}
		else if ((code.ordinal() + 1) != CodeColour.size()) {
			
			newColour = CodeColour.values()[code.ordinal() + 1];
		}
		
		ColourCodePanel parent = (ColourCodePanel)getParent();
		parent.notifyEntry(rowId, colId, newColour);
	}

}

