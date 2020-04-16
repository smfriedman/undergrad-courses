package lab6;
/**
 * 
 * Steven Friedman
 * @author stevenfriedman
 * Various recursive methods to express different functions and tests as well as a main method
 * 
 */
/*
 * TA: RachelN
 * Grade: 100/100
 */
public class Methods {

	//
	// In this class, implement the f and g functions described on the lab page
	//

	/**
	 * Computes f(x) = x-10 		if x>100
	 * 				 = f(f(x+11)	if x<=100
	 * @param integer x
	 * @return f(x)
	 */
	public static int f(int x){
		if (x>100) return x-10;
		return f(f(x+11));
	}

	/**
	 * Raises one integer to the power of another integer
	 * REQUIRES: positive b
	 * @param integer a
	 * @param integer b
	 * @return a to the b power
	 */
	public static int expt(int a, int b){
		if (b == 0) return 1;
		return a*expt(a, b-1);
	}

	/**
	 * Tests whether or not a string is a palindrome
	 * @param String s
	 * @return truth value of "s is a palindrome"
	 */
	public static boolean isPalindrome(String s){
		if (s.length() == 0 || s.length() == 1) return true;	//base case: "" or "x" where x is a single letter, both palindromes
		else if (s.charAt(0) == s.charAt(s.length()-1)){
			return isPalindrome(s.substring(1, s.length()-1));  //if first and last letters the same, strips them off to test again
		}
		return false;
	}

	/**
	 * Tests some values for method f
	 * @param args
	 */
	public static void main(String[] args) {
		//
		// from here, call f or g with the appropriate parameters
		//

		//Some test values for f(x):
		System.out.println(f(91));
		System.out.println(f(150));
		System.out.println(f(1));
	}

}
