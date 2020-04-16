package watermelons;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class WatermelonsTest {
	
	@Test
	public void singleTest() {
		int[] in = { 1, 2, 3 };
		int[] ans = Watermelons.allPairSums(in);
		int[] expect = { 3, 4, 5};
		verifySize(in, ans);
		Arrays.sort(ans);
		if (!Arrays.equals(ans, new int[] { 3, 4, 5})) {
			System.out.println("For input " + Arrays.toString(in));
			System.out.println("Expected " + Arrays.toString(expect));
			System.out.println("But got " + Arrays.toString(ans));
			fail("Wrong result, see output");
		}		
	}

	@Test
	public void testPows() {
		for (int i=1; i < 10; ++i) {
			testPow(i);
		}
		
	}
	
	private void testPow(int size) {
		int[] array = genArray(size);
		permute(array);
		int ans[] = Watermelons.allPairSums(array);
		//
		//  Expect size choose 2
		//      = size!  /  (size-2 !  2!)
		//
		verifySize(array, ans);
		for (int e : ans) {
			if (numBitsTrue(e) != 2) {
				fail(" Entry " + e + " not the sum of two elements");
			}
		}
		
	}
	
	private void verifySize(int[] in, int[] out) {
		int size = in.length;
		assertEquals("Wrong size of result", size*(size-1)/2, out.length);

	}
	
	private static int[] genArray(int size) {
		int num = 1;
		int[] ans = new int[size];
		for (int i=0; i < size; ++i) {
			ans[i] = num;
			num = num << 1;
		}
		return ans;
	}
	
	private static int numBitsTrue(int n) {
		int ans = 0;
		while (n != 0) {
			if ((n & 0x00000001) == 1) {
				++ans;
			}
			n = n >>> 1;
		}
		return ans;
	}
	
	private static void permute(int[] array) {
		for (int i=array.length-1; i > 0; --i) {
			int loc = (int)(Math.random() * (i+1));
			int t = array[loc];
			array[loc] = array[i];
			array[i] = t;
		}
		
	}
	
	public final static int[] expected = { 20, 22, 23, 24, 25, 26, 27, 28, 30, 31 };
	
	public static boolean checkPuzzleSolution(int[] soln) {
		int[] allsums = Watermelons.allPairSums(soln);
		Arrays.sort(expected);
		Arrays.sort(allsums);
		return Arrays.equals(expected, allsums);
	}
	
	@Test
	public void testPuzzleSolution() {
		int[] soln = Watermelons.getSolution();
		int[] allsums = Watermelons.allPairSums(soln);
		verifySize(soln, allsums);
		if (!checkPuzzleSolution(soln)) {
			Arrays.sort(soln);
			Arrays.sort(expected);
			Arrays.sort(allsums);
			System.out.println("Your solution does not add up!");
			System.out.println("   Your solution: " + Arrays.toString(soln));
			System.out.println("   Your sums: " + Arrays.toString(allsums));
			System.out.println("   Expected: " + Arrays.toString(expected));
			fail("Wrong solution, see output");
		}
		
	}

}
