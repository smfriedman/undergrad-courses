package studio2;

	import cse131.ArgsProcessor;

		public class GCcounter {

			public static void main(String[] args) {

			ArgsProcessor ap = new ArgsProcessor(args);
			
			int stringLength = ap.nextInt("How long is the DNA strand?");
			int i = 0;
			int cgCount = 0;
			double n;
			String strand = "";
			
			while ( i < stringLength ) {
					i++;
					n = Math.random();
				if ( n < 0.25 ) {
					strand = strand + "A";
				}
				else if ( n < 0.5 ) {
					strand = strand + "T";
				}
				else if ( n < 0.75 ) {
					strand = strand + "C";
					cgCount++;
				}
				else {
					strand = strand + "G";
					cgCount++;
				}
			}
				double pct = Math.round((cgCount*100/stringLength)*10.0)/10.0;
				System.out.println(strand);
				System.out.println("There are " + cgCount + " C's and G's in this string,");
				System.out.println("which is " + pct + "% of the total string.");
			
			}

}