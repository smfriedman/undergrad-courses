package calculator;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComputationsTest {

	@Test
	public void doublesTest(){
		double d1= Math.random()*20.0;
		double d2= Math.random()*10.0;
		assertEquals(d1+d2, Computations.addDoubles(d1,d2), .1);
		assertEquals(d1-d2, Computations.subtractDoubles(d1,d2), .1);
		assertEquals(d1/d2, Computations.divideDoubles(d1,d2), .1);
		assertEquals(d1*d2, Computations.multiplyDoubles(d1,d2), .1);
	}
	
	@Test
	public void intTest(){
		int i1= (int) (Math.random()*20);
		int i2= (int) (Math.random()*10);
		assertEquals(i1+i2, Computations.addInts(i1,i2), .1);
		assertEquals(i1-i2, Computations.subtractInts(i1,i2), .1);
		assertEquals(i1/i2, Computations.divideInts(i1,i2), .1);
		assertEquals(i1*i2, Computations.multiplyInts(i1,i2), .1);
	}

	@Test
	public void stringTest(){
		String s1= "an irishman w";
		String s2="alks out of a bar";
		assertEquals(s1+s2, Computations.concatenate(s1, s2));
		assertEquals(s2+s1, Computations.concatenate(s2, s1));
//		assertEquals("an ir", Computations.substring(s1, s2));
//		assertEquals("true", Computations.containsString(s1, "irish"));
//		assertEquals("false", Computations.containsString(s1, "walks"));
//		assertEquals("true", Computations.containsString(s1, " "));
//		assertEquals("true", Computations.containsString(s2, " "));
		
		
	}

	@Test
	public void booleanTest(){
		assertEquals(true, Computations.andBoolean(true, true));
		assertEquals(false, Computations.andBoolean(true, false));
		assertEquals(false, Computations.andBoolean(false, false));
		assertEquals(false, Computations.andBoolean(false, true));
		
		assertEquals(true, Computations.orBoolean(true, false));
		assertEquals(true, Computations.orBoolean(true, true));
		assertEquals(true, Computations.orBoolean(false, true));
		assertEquals(false, Computations.orBoolean(false, false));
	}
}
