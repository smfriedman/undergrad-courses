package studio2;

import cse131.ArgsProcessor;

	public class Pi {

		public static void computePi(int n) {

			double ans = 0.0;
			double x = 0;
			double y = 0;
			int in = 0;
			
			for (int i = 0; i<n; i++) {
				x = Math.random()*0.5;
				y = Math.random()*0.5;
				
				if (Math.sqrt((x)*(x) + (y)*(y))<=0.5) {
					in++;
				}
			}
			
			ans = ((double)in*4)/n;
			
			System.out.println("Our group shows Pi = " + ans);
		}

	public static void main(String[] args) {
			
		ArgsProcessor ap = new ArgsProcessor(args);
			int n = ap.nextInt("How many times does the expert throw the dart?");
			computePi(n);
}

}