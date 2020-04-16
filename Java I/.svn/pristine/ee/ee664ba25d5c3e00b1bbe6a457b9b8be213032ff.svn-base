package studio3;

import cse131.ArgsProcessor;

public class Dice {

	public static void main(String[] args) {
	
		ArgsProcessor ap = new ArgsProcessor(args);
		int D = ap.nextInt("How many dice are thrown?");
		int T = ap.nextInt("How many times are they thrown?");
		
		//creates 6 sided die
		int die[][] = new int [D][T];
		int side;
		for(int n = 0; n < D; n++) {
			for(int i = 0; i < T; i++) {
				side = (int)(Math.random()*6) + 1;
				die[n][i] = side;
			}	
		}
		//prints out array
		for(int n = 0; n < D; n++) {
			System.out.print("Die" + (n+1) + ": ");
			for(int i = 0; i < die[n].length; i++) {
				System.out.println(die[n][i]);
			}
		}	
		
		//how often dice repeat value
		int roll;
		int repeat = 0;
		for (int i = 0; i < T; i++) {
			for (int n = 0; n < D; n++) {
			roll = die[n][i];
			
		
	}

}
