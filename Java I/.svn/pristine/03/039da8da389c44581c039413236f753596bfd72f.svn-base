package conway;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.Random;
import java.awt.Component;
import java.awt.event.MouseEvent;

import junit.framework.TestCase;

public class ConwayTester extends TestCase {

	private Random random = new Random(0);

	@Test
	public void testcellFunctions()
	{
		//test the cell functions

		//initializing
		Cell c = new Cell();
		c.setCurrentLiveness(true);
		assert(c.isCurrentlyAlive());

		//setAlive method
		c.setCurrentLiveness(false);
		assert(!c.isCurrentlyAlive());
		c.setCurrentLiveness(true);
		assert(c.isCurrentlyAlive());

		//setNextGenAlive and evolve
		c.setNextStepLiveness(false);
		c.evolve();
		assert(!c.isCurrentlyAlive());
		c.setNextStepLiveness(true);
		c.evolve();
		assert(c.isCurrentlyAlive());
	}

	
	@Test
	public void testinit() {
		//test Conway constructor
		Conway cw = new Conway(10,10);
		assertEquals(10, cw.getNumRows());
		assertEquals(10, cw.getNumCols());

		cw = new Conway(15,15);
		assertEquals(15, cw.getNumRows());
		assertEquals(15, cw.getNumCols());
	}


	@Test
	public void testConwayAccessors()
	{
		Conway cw = new Conway(20, 20);

		//just initialized, should be false
		assert(!cw.getCell(12, 5).isCurrentlyAlive());

		//test setlife method
		cw.setLife(true, 2, 17);
		assert(cw.isAlive(2, 17));
		assert(cw.getCell(2, 17).isCurrentlyAlive());

		cw.getCell(2, 17).setCurrentLiveness(false);
		assert(!cw.isAlive(2, 17));
	}

	@Test
	public void testStep() {
		Conway cw = randomBoard();
		Cell[] cells = new Cell[100];
		int[] crow = new int[100];
		int[] ccol = new int[100];
		boolean[] states = new boolean[100];
		for(int i = 0; i < 100; i++) {
			int row = random.nextInt(20);
			int col = random.nextInt(20);

			cells[i] = cw.getCell(row, col);
			crow[i] = row;
			ccol[i] = col;
			int n = checkLivingNeighbors(row, col, cw);
			if(cw.isAlive(row, col))
			{
				if (n > 1 && n < 4)
					states[i] = true;
				else
					states[i] = false;
			}
			else
			{
				if (n == 3)
					states[i] = true;
				else
					states[i] = false;
			}
		}
		String before = cw.toString();
		cw.step();
		String after = cw.toString();
		for(int i = 0; i < 100; i++)
		{
			if (cells[i].isCurrentlyAlive() != states[i]) {
				System.out.println("testStep about to fail.");
				System.out.println("Board before step: ");
				System.out.println(before);
				System.out.println("Failure at row " + crow[i] + " and col " + ccol[i]);
				System.out.println("Alive for test says: " + states[i]);
				System.out.println("Alive for your solution says: " + cells[i].isCurrentlyAlive());
				System.out.println("Board after step: ");
				System.out.println(after);
			}
			assertEquals(cells[i].isCurrentlyAlive(), states[i]);
		}
	}

	@Test
	public void testPrint() {
		Cell c = new Cell();
		System.out.println("Dead cell: " + c);
		c.setCurrentLiveness(true);
		System.out.println("Live cell: " + c);

		Conway cw = new Conway(10,10);
		System.out.println("Fresh board:\n" + cw);

		cw.setLife(true, 5, 7);
		cw.setLife(true, 6, 8);
		cw.setLife(true, 7, 8);
		cw.setLife(true, 7, 7);
		cw.setLife(true, 7, 6);
		System.out.println("Slider:\n" + cw);
	}

	public Conway randomBoard() {
		Conway cw = new Conway(20, 20);
		for(int i = 0; i < cw.getNumRows(); i++) {
			for(int j = 0; j < cw.getNumCols(); j++) {
				int r = random.nextInt(2);
				if(r == 1)
					cw.setLife(true, i, j);
				else
					cw.setLife(false, i, j);
			}
		}
		return cw;
	}
	
	//
	// x is a col here, y is a row
	private int checkLivingNeighbors(int sourceY, int sourceX, Conway cw) {

		int cLN = 0;
		for (int x = sourceX - 1; x <= sourceX + 1; x++){
			for (int y = sourceY - 1; y <= sourceY + 1; y++){
				if (cw.isAlive (y, x) && !(x == sourceX && y == sourceY))
					cLN = cLN + 1;
			}
		}
		return cLN;	
	}
}
