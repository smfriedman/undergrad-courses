package conway;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class ConwayGridPanel extends JPanel {

	public ConwayGridPanel(Conway conway, int cellWidth) {
		int rows = conway.getNumRows();
		int cols = conway.getNumCols();
		setLayout(new GridLayout(rows, cols));


		for (int i=0; i<rows; ++i) {
			for (int j=0; j<cols; ++j) {
				CellViz cell = new CellViz(conway.getCell(i, j), cellWidth);
				this.add(cell);
			}
		}
	}

}
