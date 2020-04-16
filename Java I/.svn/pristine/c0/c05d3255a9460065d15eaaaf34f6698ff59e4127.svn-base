package lab4;

import sedgewick.StdDraw;
import cse131.ArgsProcessor;

public class BumpingBalls {
	
	public static void main(String[] args) {

		ArgsProcessor ap = new ArgsProcessor(args);
		int N = ap.nextInt("How many balls are there?");
		int I = ap.nextInt("How many iterations are there?");
		
		StdDraw.setXscale(-1.0, 1.0);
		StdDraw.setYscale(-1.0, 1.0);
		
		double[][] B = new double [N][5];
		//{px,py,vx,vy,r}
		
		for (int i = 0; i < N; i++){
			B[i][0] = ((Math.random())-0.5)*1.5; //px
			B[i][1] = ((Math.random())-0.5)*1.5; //py
			B[i][2] = ((Math.random())-0.5)*0.1; //vx
			B[i][3] = ((Math.random())-0.5)*0.1; //vy
			B[i][4] = ((Math.random())*0.05)+0.05; //r
		}
		
		for (int k = 0; k < I; k++) {
			for (int i = 0; i < N; i++){
				for (int j = 0; j < N; j++){
					double d = Math.sqrt(((B[i][0]-B[j][0])*(B[i][0]-B[j][0]))+((B[i][1]-B[j][1])*(B[i][1]-B[j][1])));
					if ((i != j) && (d < (B[i][4]+B[j][4]))){
						double[] C = new double[2];
						C[0] = B[i][2];
						C[1] = B[i][3];
						B[i][2] = B[j][2];
						B[i][3] = B[j][3];
						B[j][2] = C[0];
						B[j][3] = C[1];
					}
				}
				if (Math.abs(B[i][0] + B[i][2]) > 1.0 - B[i][4]) B[i][2] = -B[i][2];
				if (Math.abs(B[i][1] + B[i][3]) > 1.0 - B[i][4]) B[i][3] = -B[i][3];
				B[i][0] = B[i][0] + B[i][2]; 
			    B[i][1] = B[i][1] + B[i][3];
			    StdDraw.setPenColor(StdDraw.GRAY);
			    StdDraw.filledSquare(0.0, 0.0, 1.0);
				}
			for (int i = 0; i < N; i++){
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.filledCircle(B[i][0], B[i][1], B[i][4]);
			}
			StdDraw.show(20);
		}

	}

}
