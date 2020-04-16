package inputoutput;

import sedgewick.StdDraw;
import sedgewick.StdIn;

public class MouseDemo {

	public static void main(String[] args) {
		//
		// wait for the mouse to be pressed
		//
		while (!StdDraw.mousePressed()) { // mouse is not pressed
			// do nothing
			StdDraw.pause(100); // pause for 1/10 second
		}
		System.out.println("OK mouse is pressed");
		while (StdDraw.mousePressed()) {  /// mouse is pressed
			StdDraw.pause(100);
		}
		System.out.println("released");

		
	}

}
