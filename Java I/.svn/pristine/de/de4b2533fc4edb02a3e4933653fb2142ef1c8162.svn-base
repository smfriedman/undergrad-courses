package lab3;

import cse131.ArgsProcessor;

public class MineSweeper {

	public static void main (String[] args) {

		//
		// Do not change anything between here ...
		//
		ArgsProcessor ap = new ArgsProcessor(args);
		int cols = ap.nextInt("How many columns?");
		int rows = ap.nextInt("How many rows?");
		double percent = ap.nextDouble("What is the probability of a bomb?");
		//
		// ... and here
		//
		//  Your code goes below these comments
		//

		String[][] bomb2 = new String [rows + 2][cols + 2];
		int[][] bomb3 = new int [rows + 2][cols + 2];
		String[][] bomb4 = new String [rows + 2][cols + 2];
		for (int i = 1; i < rows + 1; i++){
			for (int j = 1; j < cols + 1; j++){
				double r = Math.random();
				if (r <= percent) {
					bomb2[i][j] = "*";
					bomb4[i][j] = "*";
				}
				else bomb2[i][j] = ".";	
				System.out.print(bomb2[i][j] + " ");
			}
			System.out.println();
		}	
		System.out.println();
		for (int i = 1; i < rows + 1; i++){
			for (int j = 1; j < cols + 1; j++){
				for (int m = -1; m < 2; m++) {
					for (int n = -1; n < 2; n++){
						if (bomb4[i+m][j+n] == "*") bomb3[i][j]++;
					}
				}
				
				if (bomb4[i][j] != "*"){
					bomb4[i][j] = bomb3[i][j] + "";
				}
				System.out.print(bomb4[i][j] + " ");
			}
			System.out.println();
		}
	}
}
