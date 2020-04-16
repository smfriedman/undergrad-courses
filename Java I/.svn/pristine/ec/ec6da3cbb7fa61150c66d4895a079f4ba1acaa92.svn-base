package inputoutput;

import java.awt.Color;

import sedgewick.StdDraw;

public class MovingBall {

	public static void main(String[] args) {
		StdDraw.setPenRadius(.08);
		double x = 0.0;
		double y = 0.5;
		double diff = 0.02;
		while (true) {  // runs forever
			// first clear canvas
			StdDraw.clear();
			
			x = x + diff; // move 
			if ( x >= 1.0) {
				diff = - diff;
			}
			else if ( x <= 0.0) {
				diff = - diff;
			}
			StdDraw.point(x, y);
			//
			// pause to be nice to other processes
			StdDraw.show(25);
		}

	}

}
