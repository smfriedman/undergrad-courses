package studio4;

import java.awt.Color;

import cse131.ArgsProcessor;
import sedgewick.StdAudio;
import sedgewick.StdDraw;
import sedgewick.StdIn;

public class Flag {


	public static void main(String[] args) {
		//
		//  Add code for your studio below here.
		//
		



			StdDraw.setPenColor(Color.BLACK);
			StdDraw.filledRectangle(.5, .5,.6, .43);
			StdDraw.setPenColor((new Color(0, 150 , 0)));
			StdDraw.filledRectangle(.5, .5, .5, .33);
			StdDraw.setPenColor(Color.YELLOW);
			double [] x = {.05, .5 , .95 , .5};
			double [] y = {.5, .75 , .5 , .25};		
			StdDraw.filledPolygon(x, y);
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.filledCircle(.5, .5, .17);
			StdDraw.setPenColor(Color.white);
			StdDraw.filledRectangle(.5, .55, .17, .02);
			StdDraw.setPenColor((new Color( 0, 150, 0)));
			StdDraw.text(.5, .55, "Ordem E Progresso");
			StdDraw.setPenColor(Color.white);
			StdDraw.filledCircle(.45, .48, .005);
			StdDraw.filledCircle(.40, .47, .005);
			StdDraw.filledCircle(.38, .49, .005);
			StdDraw.filledCircle(.42, .44, .005);
			StdDraw.filledCircle(.45, .41, .005);
			StdDraw.filledCircle(.55, .49, .005);
			StdDraw.filledCircle(.57, .44, .005);
			StdDraw.filledCircle(.6, .41, .005);
			StdDraw.filledCircle(.5, .44, .005);
			StdDraw.filledCircle(.51, .41, .005);
			double a = 0.0;
			double b = .9;
			double diffa = 0.125;
			double diffb = -.1;
			while (a <= 1) {
				StdDraw.setPenColor(Color.MAGENTA);
				StdDraw.text(a, b, "Brazil");
				a = a + diffa;
				b = b + diffb;
				StdDraw.show(150);
			}
			
			ArgsProcessor.useStdInput("music");
			while (!StdIn.isEmpty()) {

				// read in the pitch, where 0 = Concert A (A4)
				int pitch = StdIn.readInt();

				// read in duration in seconds
				double duration = StdIn.readDouble();

				// build sine wave with desired frequency
				double hz = 440 * Math.pow(2, pitch / 12.0);
				int N = (int) (StdAudio.SAMPLE_RATE * duration);
				double[] a1 = new double[N+1];
				for (int i = 0; i <= N; i++) {
					a1[i] = Math.sin(2 * Math.PI * i * hz / StdAudio.SAMPLE_RATE);
				}

				// play it using standard audio
				StdAudio.play(a1);
			}
			
		

		}

	}