package lab2;

import cse131.ArgsProcessor;

public class RPS {

	public static void main(String[] args) {

		ArgsProcessor ap = new ArgsProcessor(args);
		int n = ap.nextInt("How many games will be played?");
		int i = 0;
		
		double p1;
		String p1String = "";
		int p2 = 0;
		String p2String = "";
		int p1wins = 0;
		int p2wins = 0;
		int draws = 0;
		
		while (i < n) {
			i ++;
			p1 = 3 * Math.random();
		
			if (p1 <= 1.0) {
				p1String = p1String + "R";
			}
			else if (p1 <= 2.0) {
				p1String = p1String + "P";
			}
			else if (p1 <= 3.0) {
				p1String = p1String + "S";
			}	
			
			p2 = p2 + 1;
			if (p2 == 1) {
				p2String = p2String + "R";
			}	
			else if (p2 == 2) {
				p2String = p2String + "P";
			}
			else if (p2 == 3) {
				p2String = p2String + "S";
			}
			else if (p2 == 4) {
				p2 = 1;
				p2String = p2String + "R";
			}
			
			boolean p1R = p1 <= 1.0;
			boolean p1P = p1 <= 2.0 && p1 > 1.0;
			boolean p1S = p1 >2.0;
			
			boolean p2R = p2 == 1;
			boolean p2P = p2 == 2;
			boolean p2S = p2 == 3;
			
			boolean W = (p1R && p2S) || (p1P && p2R) || (p1S && p2P);
			boolean D = (p1R && p2R) || (p1P && p2P) || (p1S && p2S);
			boolean L = (p1S && p2R) || (p1R && p2P) || (p1P && p2S);
			
			if (W == true) {
				p1wins ++;
			}
			else if (L == true) {
				p2wins ++;
			}
			else if (D == true) {
				draws ++;
			}
						
			
		}

		System.out.println("Random Player: " + p1String);
		System.out.println("Ordered Player: " + p2String);
		System.out.print("Out of " + n + " games, the random player won " + p1wins + " games, the ordered player won " + p2wins + " games, and there were " + draws + " draws.");
	}

}