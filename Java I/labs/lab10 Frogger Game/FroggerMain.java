package lab10;

import cse131.ArgsProcessor;

/**
 * 
 * @author Steven Friedman, steven.friedman
 * @author Vishal Vijay, vvijay
 * Runs Frogger in a main method
 */
public class FroggerMain {
	/**
	 * asks for how many players and creates
	 * and runs a new Frogger with that many players
	 * @param args
	 */
	public static void main(String[] args) {
		ArgsProcessor ap = new ArgsProcessor(args);
		int n = ap.nextInt("How many players? (1 or 2)");
		FroggerGame g = new Frogger(n);
		g.playGame();
		System.exit(0);
	}
}
