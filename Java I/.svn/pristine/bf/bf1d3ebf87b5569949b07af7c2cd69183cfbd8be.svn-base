package conway;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class CellViz extends JLabel {
		
	public CellViz(final Cell c, int cellWidth) {
		this.setSize(cellWidth, cellWidth);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setOpaque(true);
		
		c.getPCS().addPropertyChangeListener(
				"live", 
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						boolean alive = (Boolean) evt.getNewValue();
						if (alive)
							setBackground(Color.BLACK);
						else
							setBackground(Color.WHITE);
					}
					
				}
			);
		
		this.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						c.flipAlive();
					}
				}
			);

	}
			
}
