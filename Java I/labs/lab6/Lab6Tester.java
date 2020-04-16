package lab6;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * A test suite for all of the functions in CSE131 lab 6.
 * @author dshook and ron cytron
 *
 */
public class Lab6Tester {

	private Random random = new Random(0);
	
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
			
			int calcExpt = Methods.expt(n, k);
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
			check = Methods.isPalindrome(palindromes[i]);
			if (!check) {
				printError("isPalindrome error when s = " + palindromes[i], true, check);
			}
			assertEquals(true, check);
			check = Methods.isPalindrome(notPalindromes[i]);
			if (check) {
				printError("isPalindrome error when s = " + notPalindromes[i], false, check);
			}
			assertEquals(false, check);
		}
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
