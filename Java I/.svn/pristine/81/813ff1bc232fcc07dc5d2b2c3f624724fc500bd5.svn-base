package imageprocessor;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class ImageProcessorTest {

	@Test
	public void darkerTest() {
		for(int i=0; i<1000; i++){
			int randomInt = (int) (255*Math.random());
			int darker= Filters.darker(randomInt); //pixelComponent after the darker operation has been performed.
			if(randomInt!=0){
				// if expression is false -> error message 
				assertTrue("Pixel did not get any darker", randomInt> darker); 
			}
		}
		assertEquals(0, Filters.darker(0));  //Black shouldn't get any blacker
	}

	@Test
	public void combineTest() {  //FIXME might delete this
		for(int i=0; i<1000; i++){
			int randomInt = (int) (255*Math.random());
			int randomInt2= (int) (255*Math.random());
			assertEquals((randomInt + randomInt2), Filters.combine(randomInt, randomInt2));
		}
	}

	@Test
	public void purplish(){
		for(int i=0 ; i<1000; i++){
			Color c1= genColor();
			assertEquals(0, Filters.purplish(c1).getGreen());
		}
	}

	@Test
	public void copy(){
		for(int i=0; i<1000; i++){
			int randomInt = (int) (255*Math.random());
			assertEquals(randomInt, Filters.copy(randomInt));
		}
	}

	@Test
	public void composite(){
		for(int i=0; i<1000; i++){
			int randomInt = (int) (255*Math.random());
			int randomInt2= (int) (255*Math.random());
			assertEquals((randomInt + randomInt2)/2, Filters.composite(randomInt, randomInt2));
		}
	}

	@Test
	public void negative(){
		for(int i=0; i<1000; i++){
			int randomInt = (int) (255*Math.random());
			int negative = Filters.negative(randomInt);
			if(randomInt<=127)
				assertTrue( "Must invert pixelComponent", negative>=127);
			if(randomInt>=128)
				assertTrue( "Must invert pixelComponent", negative<=128);
		}
	}

	@Test
	public void posterize(){
		for(int i=0; i<1000; i++){
			int randomInt = (int) (255*Math.random());
			int posterize= Filters.posterize(randomInt);
			assertTrue("Can only return a value of 0 or 255",posterize==0 || posterize==255);
		}
	}

	@Test
	public void brighter(){
		for(int i=0 ; i<1000; i++){
			Color c1= genColor();
			Color brighter= Filters.brighter(c1);
			int red=c1.getRed();
			int blue=c1.getBlue();
			int green=c1.getGreen();
			int brighterRed=brighter.getRed();
			int brighterGreen=brighter.getGreen();
			int brighterBlue=brighter.getBlue();

			if(red<255){
				assertTrue("Red should be brighter after the method is called.", brighterRed>red);
			}
			else
				assertEquals(red, brighterRed);
			if(green<255){
				assertTrue("Green should be brighter after the method is called.", brighterGreen>green);
			}
			else
				assertEquals(green, brighterGreen);
			if(blue<255){
				assertTrue("Blue should be brighter after the method is called.", brighterBlue>blue) ;
			}
			else
				assertEquals(blue, brighterBlue);
		}
	}

	@Test
	public void grayscale(){
		for(int i=0 ; i<1000; i++){
			Color c1= genColor();
			Color grayscale= Filters.grayscale(c1);
			int newRed = grayscale.getRed();
			int newGreen = grayscale.getGreen();
			int newBlue= grayscale.getBlue();

			assertTrue( "Color components should be equal", newRed== newGreen && newGreen== newBlue);
		}
	}

	@Test
	public void blackAndWhite(){
		for(int i=0 ; i<1000; i++){
			Color c1= genColor();
			Color bw = Filters.blackAndWhite(c1);
			int newRed= bw.getRed();
			int newGreen = bw.getGreen();
			int newBlue = bw.getBlue();
			boolean isBlack = (newRed==0 && newGreen ==0 && newBlue==0);
			boolean isWhite= (newRed==255 && newGreen ==255 && newBlue==255);
			assertTrue("Must return either black or white", isBlack || isWhite) ;

		}

	}

	@Test
	public void combineBrigher(){
		for(int i=0 ; i<1000; i++){
			Color c1= genColor();
			Color c2= genColor();
			Color comBri = Filters.combineBrighter(c1, c2);
			int brightestRed= comBri.getRed();
			int brightestGreen = comBri.getGreen();
			int brightestBlue = comBri.getBlue();

			if(c1.getRed()>c2.getRed()){
				assertTrue("Should retain brighter component",brightestRed==c1.getRed()) ;} 
			else{
				assertTrue("Should retain brighter component", brightestRed==c2.getRed());}

			if(c1.getBlue()>c2.getBlue()){
				assertTrue("Should retain brighter component" ,brightestBlue==c1.getBlue());}
			else{
				assertTrue("Should retain brighter component" ,brightestBlue==c2.getBlue());}

			if(c1.getGreen()>c2.getGreen()){
				assertTrue("Should retain brighter component" ,brightestGreen==c1.getGreen());}
			else{
				assertTrue("Should retain brighter component" ,brightestGreen==c2.getGreen());}
		}
	}

	@Test
	public void bgSubtract(){
		for(int i=0 ; i<1000; i++){
			Color c1= genColor();
			Color c2= genColor();

			assertTrue("If colors are the same, bgSubtract should return blue",(Filters.bgSubtract(c1, c1).equals(Color.BLUE) && Filters.bgSubtract(c2, c2).equals(Color.BLUE)));
		}
	}

	@Test
	public void bgReplace(){
		for(int i=0 ; i<1000; i++){
			Color c1 = genColor();
			Color c2 = genColor();

			if(Math.random()<.3){
				c1=Color.blue;
			}
			if(c1.getRed()==0 && c1.getGreen()==0 && c1.getBlue()==255){
				assertTrue("When color1 is blue, bgReplace should return the color from Image 2", 
						Filters.bgReplace(c1, c2).equals(c2)) ;
			}
			else
				assertTrue( "When color1 is not blue, bgReplace should return the color from Image 1",
						Filters.bgReplace(c1, c2).equals(c1));
		}
	}

	public Color genColor(){
		int red= (int)Math.random()*255;
		int blue= (int)Math.random()*255;
		int green= (int)Math.random()*255;
		return new Color(red, green, blue);
	}
}
