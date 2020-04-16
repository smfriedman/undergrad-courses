package lab5;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * A test suite for all of the functions in CSE131 lab 5.
 * @author dshook and ron cytron
 *
 */
public class Lab5Tester {

	private Random random = new Random(0);
	
	private void checkMult(int j, int k, int calcProduct) {
		int product = j * k;//actual product
		
		//error
		if(product != calcProduct)
		{
			printError("Mult error for " + j + " * " + k + ": ", product, calcProduct);
		}
		assertEquals(product, calcProduct);
	}
	
	/**
	 * Tests the multPos method using randomly generated input
	 */
	@Test
	public void testMultPos() {
		
		for(int i = 0; i < 100; i++)
		{
			int j = random.nextInt(1000)+1; // j is positive
			int k = random.nextInt(2000)+1; // k is positive
			checkMult(j, k, Lab5Methods.multPos(j,k));
		}
	}
	
	/**
	 * Tests the mult method using randomly generated input
	 */
	@Test
	public void testMultAny() {
		
		for(int i = 0; i < 100; i++)
		{
			int j = random.nextInt((i + 1) * 2) - i; //j can be positive or negative or zero
			int k = random.nextInt((i + 1) * 2) - i; //k can be positive or negative or zero
			checkMult(j,k, Lab5Methods.mult(j,k));
		}
	}
	
	/**
	 * Tests the expt method using randomly generated input
	 */
	@Test
	public void testExpt() {
		
		for(int i = 0; i < 100; i++)
		{
			int n = random.nextInt((9 + 1) * 2) - 10;//n can be positive or negative
			int k = random.nextInt(9 + 1);//k >= 0
			
			if (n==0 && k==0) continue;
			
			int calcExpt = Lab5Methods.expt(n, k);
			int expt = (int)Math.pow(n, k);
			
			//error
			if(expt != calcExpt)
			{
				printError("Expt error when n = " + n + ", and k = " + k + ": Ensure you have the proper base case(s) (k = 0? k = 1?)\n and that you are performing the computation correctly (n^k).", expt, calcExpt);
			}
			assertEquals(expt, calcExpt);
		}
		
	}
	/**
	 * Test the isPalendrome method
	 */
	@Test
	public void testIsPalindrome() {
		boolean check = false;
		String[] palindromes = {"noon", "tacocat", "racecar", "amanaplanacanalpanama"};
		String[] notPalindromes = {"batman", "nono", "racer", "quickbrownfox"};
		for (int i = 0; i < palindromes.length; i++) {
			check = Lab5Methods.isPalindrome(palindromes[i]);
			if (!check) {
				printError("isPalindrome error when s = " + palindromes[i], true, check);
			}
			assertEquals(true, check);
			check = Lab5Methods.isPalindrome(notPalindromes[i]);
			if (check) {
				printError("isPalindrome error when s = " + notPalindromes[i], false, check);
			}
			assertEquals(false, check);
		}
	}
	
	/**
	 * Tests the fibonacci method
	 */
	@Test
	public void testFibonacci() {
		int[] check;
		check = Lab5Methods.fibonacci(1);
		if(!Arrays.equals(check, new int[]{1})) {
			printError("fibonnaci error when n = 1", new int[]{1}, check);
		}
		assertEquals(Arrays.equals(check, new int[]{1}), true);


		check = Lab5Methods.fibonacci(5);
		if(!Arrays.equals(check, new int[]{1, 1, 2, 3, 5})) {
			printError("fibonnaci error when n = 5", new int[]{1, 1, 2, 3, 5}, check);
		}
		assertEquals(Arrays.equals(check, new int[]{1, 1, 2, 3, 5}), true);

		check = Lab5Methods.fibonacci(10);
		if(!Arrays.equals(check, new int[]{1, 1, 2, 3, 5, 8, 13, 21, 34, 55})) {
			printError("fibonnaci error when n = 10", new int[]{1, 1, 2, 3, 5, 8, 13, 21, 34, 55}, check);
		}
		assertEquals(Arrays.equals(check, new int[]{1, 1, 2, 3, 5, 8, 13, 21, 34, 55}), true);
	}
	
	/**
	 * Tests the computeGrade method
	 */
	@Test
	public void testComputeGrade() {
		String check;
		double[] labs = {100};
		double[] quizzes = {100, 100};
		double[] exams = {100};
		boolean studios = true;
		int extensions = 100;
		
		//Labs, quizzes, exams, studios, extensions
		check = Lab5Methods.computeGrade(labs, quizzes, exams, studios, extensions);
		if (!check.equals("A+")) {
			printError("Error in computeGrade when labs = " + Arrays.toString(labs) + ", quizzes = " + Arrays.toString(quizzes) + ", exams = " + Arrays.toString(exams) + ", studios = " + studios + ", and extensions = " + extensions, "A+", check);
		}
		assertEquals(check, "A+");
		
		labs = new double[] {90, 90, 90, 90, 90, 90};
		quizzes = new double[] {90, 90, 90, 90, 90, 90};
		exams = new double[] {90, 90, 90};
		studios = true;
		extensions = 90;
		
		//Labs, quizzes, exams, studios, extensions
		check = Lab5Methods.computeGrade(labs, quizzes, exams, studios, extensions);
		if (!check.equals("A-")) {
			printError("Error in computeGrade when labs = " + Arrays.toString(labs) + ", quizzes = " + Arrays.toString(quizzes) + ", exams = " + Arrays.toString(exams) + ", studios = " + studios + ", and extensions = " + extensions, "A-", check);
		}
		assertEquals(check, "A-");
		
		labs = new double[] {87, 87, 87, 87, 87, 87};
		quizzes = new double[] {87, 87, 87, 87, 87, 87};
		exams = new double[] {87, 87, 87};
		studios = true;
		extensions = 87;
		
		//Labs, quizzes, exams, studios, extensions
		check = Lab5Methods.computeGrade(labs, quizzes, exams, studios, extensions);
		if (!check.equals("B+")) {
			printError("Error in computeGrade when labs = " + Arrays.toString(labs) + ", quizzes = " + Arrays.toString(quizzes) + ", exams = " + Arrays.toString(exams) + ", studios = " + studios + ", and extensions = " + extensions, "B+", check);
		}
		assertEquals(check, "B+");
		
		labs = new double[] {87, 87, 87, 87, 87, 87};
		quizzes = new double[] {87, 87, 0, 87, 87, 87};
		exams = new double[] {87, 87, 87};
		studios = true;
		extensions = 87;
		
		//Labs, quizzes, exams, studios, extensions
		check = Lab5Methods.computeGrade(labs, quizzes, exams, studios, extensions);
		if (!check.equals("B+")) {
			printError("Error in computeGrade when labs = " + Arrays.toString(labs) + ", quizzes = " + Arrays.toString(quizzes) + ", exams = " + Arrays.toString(exams) + ", studios = " + studios + ", and extensions = " + extensions, "A", check);
		}
		assertEquals(check, "B+");
		
		labs = new double[] {87, 87, 87, 87, 87, 87};
		quizzes = new double[] {87, 87, 87, 87, 87, 87};
		exams = new double[] {87, 87, 87};
		studios = false;
		extensions = 87;
		
		//Labs, quizzes, exams, studios, extensions
		check = Lab5Methods.computeGrade(labs, quizzes, exams, studios, extensions);
		if (!check.equals("C+")) {
			printError("Error in computeGrade when labs = " + Arrays.toString(labs) + ", quizzes = " + Arrays.toString(quizzes) + ", exams = " + Arrays.toString(exams) + ", studios = " + studios + ", and extensions = " + extensions, "C+", check);
		}
		assertEquals(check, "C+");
		
		labs = new double[] {87.0, 45.4, 77.9, 98.2, 84.7, 79.9};
		quizzes = new double[] {73.0, 84.9, 99.6, 79.2, 74.4, 98.5};
		exams = new double[] {90.2, 77.4, 81.5};
		studios = false;
		extensions = 78;
		
		//Labs, quizzes, exams, studios, extensions
		check = Lab5Methods.computeGrade(labs, quizzes, exams, studios, extensions);
		if (!check.equals("C")) {
			printError("Error in computeGrade when labs = " + Arrays.toString(labs) + ", quizzes = " + Arrays.toString(quizzes) + ", exams = " + Arrays.toString(exams) + ", studios = " + studios + ", and extensions = " + extensions, "C", check);
		}
		assertEquals(check, "C");
	}
		
	/**
	 * Prints an error message when a comparison between integers fails
	 * @param msg - the error message
	 * @param expected - expected int value
	 * @param actual - actual int value
	 */
	private void printError(String msg, int expected, int actual)
	{
		System.out.println(msg);
		System.out.println("Expected: " + expected);
		System.out.println("Actual: " + actual);
	}
	
	/**
	 * Prints an error message when a comparison between doubles fails
	 * @param msg - the error message
	 * @param expected - expected double value
	 * @param actual = actual double value
	 */
	private void printError(String msg, double expected, double actual)
	{
		System.out.println(msg);
		System.out.println("Expected: " + expected);
		System.out.println("Actual: " + actual);
	}
	
	private void printError(String msg, boolean expected, boolean actual) {
		System.out.println(msg);
		System.out.println("Expected: " + expected);
		System.out.println("Actual: " + actual);
	}
	
	private void printError(String msg, int[] expected, int[] actual) {
		System.out.println(msg);
		System.out.println("Expected: " + expected);
		System.out.println("Actual: " + actual);
	}
	
	private void printError(String msg, String expected, String actual) {
		System.out.println(msg);
		System.out.println("Expected: " + expected);
		System.out.println("Actual: " + actual);
	}
}
