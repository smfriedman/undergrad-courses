package inputoutput;

import java.awt.Color;

import sedgewick.StdDraw;
import cse131.ArgsProcessor;

public class Sierp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 
		// represent the x and y coordinates of the vertices
		// 
		double xc[]  = { 0, 0.5, 1.0 };
		double yc[]  = { 0, 1.0, 0.0 };
		Color[] colors = { Color.RED, Color.BLUE, Color.green };
		
		ArgsProcessor ap = new ArgsProcessor(args);
		int N = ap.nextInt("What is N?");
		
		double currx = 0.0;
		double curry = 0.0;
		StdDraw.setPenRadius(0.005);
		for (int i=0; i < N; ++i) {
			//
			// pick a vertex by choosing random int
			//   0  <= r < 3
			//
			int r = (int)(Math.random()*3);
			double halfx = (currx + xc[r])/2;
			double halfy = (curry + yc[r])/2;
			StdDraw.setPenColor(colors[r]);
			StdDraw.point(halfx, halfy);
			
			// (halfx,halfy) becomes our current point
			currx = halfx;
			curry = halfy;
			
		}

	}

}
