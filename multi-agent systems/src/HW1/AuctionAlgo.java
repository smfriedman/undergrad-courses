/**
 * 
 * AuctionAlgo.java
 * Implementation of the auction algorithm to solve the assignment problem
 * Assumes # agents = # objects = n
 * a. Given input
 * b. Random input n = 2^(1...8), M = 100, 1000 iterations each
 * c. Random input n = 256, M = 10^(1...7), 100 iterations each
 * 
 */
package HW1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.LinkedList;

public class AuctionAlgo {
	
	public static void main(String args[]){
		/******************Main for part 1b******************/
//		int M = 100;
//		int numruns = 1000;
//		for(int n = 2; n <= 256; n*=2){
//			System.out.println("Starting n = " + n + ": " + (new Date().toString()));
//			double total = 0.0;
//			for(int i = 0; i < numruns; i++){
//				total += auctionAlgo(n, M, false, "");
//			}
//			double avg = total / numruns;
//			System.out.println("Avg. value per agent for n = " + n + ": " + avg);
//		}
		/****************************************************/		
		/******************Main for part 1c******************/
		int n = 256;
		int numruns = 100;
		for(int M = 10; M <= 10000000 ; M *= 10){
			Date start = new Date();
			System.out.println("Starting M = " + M +": " + start.toString());
			for(int i = 0; i < numruns; i++){
				String fileName = "/Users/stevenfriedman/classes/cse516a/input/m" + M + "/" + i + ".txt";
				auctionAlgo(n, M, false, fileName);
			}
			Date finish = new Date();
			System.out.println("Time elapsed for M = " + M + ": " + (finish.getTime() - start.getTime()) + " ms");
			System.out.println("Average run time for M = " + M + ": " + ((finish.getTime() - start.getTime()) / n) + " ms");
		}
		/****************************************************/				
	}
	
	public static double auctionAlgo(int n, int M, boolean verbose, String fileName){
		/*****************Values for part 1a*****************/
//		int[][] valuations = {
//			{89, 42,  0,  2, 24, 20, 40, 37, 30, 77},
//			{66, 75,  9, 59, 69, 66, 52, 14, 85, 36},
//			{82, 68,  0, 81, 36, 25, 48, 53, 11, 68},
//			{ 6, 96, 82, 53, 17, 70, 26, 12, 91, 82},
//			{34, 86, 22, 18, 66, 73, 82, 88, 18, 36},
//			{90, 43, 43, 93, 80, 96, 12, 28, 74, 93},
//			{19, 75, 30, 48, 31, 76, 84, 29, 20, 15},
//			{29, 73, 88,  9, 36, 40, 40, 19,  1, 45},
//			{77, 31,  6, 68, 36, 40, 22, 43, 27, 61},
//			{70, 21,  2, 89, 30, 91, 66, 74, 79, 92}
//		};
//		
//		/* assume same # agents as objects */
//		int n = valuations.length; /* # agents */		
		/****************************************************/		

		/************Random generation for part 1b***********/
//		int[][] valuations = new int[n][n];
//		for(int i = 0; i < n; i++){
//			for (int j = 0; j < n; j++){
//				valuations[i][j] = (int) Math.floor(Math.random() * M);
//			}
//		}
		/****************************************************/		

		/**************File import for part 1c***************/
		int[][] valuations = new int[n][n];
		try{
			String line = "";
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			while((line = br.readLine()) != null){
				if(!line.isEmpty() && line.charAt(0) == 'A'){
					String[] row = line.split(" ");
					int agent = Integer.parseInt(row[0].substring(1));
					for(int i = 0; i < n - 1; i++){
						valuations[agent][i] = Integer.parseInt(row[i + 1]);
					}
					valuations[agent][n - 1] = Integer.parseInt(row[n].split(";")[0]);
				}
			}
			br.close();
		} catch (Exception e){
			System.out.println(e);
		}
		/****************************************************/		
		
		LinkedList<Integer> unassigned = new LinkedList<Integer>();
		int[] assignments = new int[n];
		double[] prices = new double[n];
		double epsilon = 0.01 / n;
		
		//initialize - no assignments, prices = 0
		for(int i = 0; i < n; i++){ 
			prices[i] = 0.0;
			unassigned.add(i);
			assignments[i] = -1;
		}
		
		//begin algorithm
		while(!unassigned.isEmpty()){
			//choose agent
			int agent = unassigned.pop();
			
			//find 2 objects with greatest utilities given prices
			double maxVal = Double.MIN_VALUE;
			int maxObj = -1;
			double secondVal = Double.MIN_VALUE;
			int secondObj = -1;
			
			for(int i = 0; i < n; i++){
				double value = valuations[agent][i] - prices[i];
				if(maxObj == -1 || value > maxVal){
					secondVal = maxVal;
					secondObj = maxObj;
					maxVal = value;
					maxObj = i;
				} else if(secondObj == -1 || value > secondVal){
					secondVal = value;
					secondObj = i;
				}
			}
			
			//find preference to second choice, i.e. max price change for algo
			double utilDiff = maxVal - secondVal + epsilon;
			
			//if another agent is assigned maxObj unassign them
			for(int i = 0; i < n; i++){
				if(assignments[i] == maxObj){
					unassigned.add(i);
					assignments[i] = -1;
				}
			}
			
			//assign agent maxObj and change price
			assignments[agent] = maxObj;
			prices[maxObj] = prices[maxObj] + utilDiff;
			
		}
		
		if(verbose){
			//print result
			int totalValue = 0;
			System.out.println("Assignments:");
			for(int i = 0; i < n; i++){
				System.out.println("A" + (i + 1) + ": O" + (assignments[i] + 1));
				totalValue += valuations[i][assignments[i]];
			}
			System.out.println("");
			System.out.println("Prices:");
			for(int i = 0; i < n; i++){
				System.out.println("O" + (i + 1) + ": " + prices[i]);
			}
			System.out.println("");		
			System.out.println("Total Value: " + totalValue);
		}

		double total = 0;
		for(int i = 0; i < n; i++){
			total += valuations[i][assignments[i]];
		}
		return total / n;
	}
	
}
