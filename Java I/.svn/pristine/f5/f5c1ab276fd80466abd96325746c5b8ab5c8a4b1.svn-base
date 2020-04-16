package conway;

/**
 * The Model for Conway's Game of Life. Contains a board of cells which can either be default
 * constructed to 10x10 or defined to user preferences. Handles the movement of cells to the
 * next generation and whether they live or die in that next generation.
 * @author briandorne roncytron
 *
 */
public class Conway {


	/**
	 * 
	 * @param rows number of rows for the game
	 * @param cols number of columns for the game
	 */
	public Conway(int rows, int cols) {
		// FIXME
	}
		
	/**
	 * For each Cell in the game, set its liveness to false
	 */
	public void clear() {
		// FIXME
	}

	/**
	 * How many columns are in this game?
	 * @return the number of columns in the game
	 */
	public int getNumCols() {
		return 0;  // FIXME
	}

	/**
	 * How many rows are in this game?
	 * @return the number of rows in the game
	 */
	public int getNumRows() {
		return 0;  // FIXME
	}

	/**
	 * Returns a cell at the given row and column of the board.
	 * @param row - the given number row in the board
	 * @param col - the given column in the board
	 * @return the Cell at the given board location
	 */
	public Cell getCell(int row, int col) {
		return null;  // FIXME
	}

	/**
	 * Is the cell at the specified row and column alive?
	 * If a request is made for a Cell outside the allocated game, then return false
	 * @param row - horizontal row of the cell from top left 
	 * @param col - vertical column of the cell from top left
	 * @return a boolean indicating whether the cell is alive
	 */
	public boolean isAlive(int row, int col) {
		// FIXME
		return false;  // FIXME
	}

	/**
	 * Sets the current liveness of the Cell at the specified row and column.
	 * This method is provided as a convenience:  any code could get the Cell itself
	 *    and set that cell's liveness.
	 * @param isAlive - a boolean representing whether the specified Cell is now alive
	 * @param row - horizontal row of the cell from top left 
	 * @param col - vertical column of the cell from top left
	 */
	public void setLife(boolean isAlive, int row, int col) {
		getCell(row, col).setCurrentLiveness(isAlive);
	}

	/**
	 * Drives Conway's game based on these four rules:
	 * 
	 * 1. Any live cell with fewer than two live neighbors dies, because of under-population.
	 * 2. Any live cell with more than three live neighbors dies, because of overcrowding.
	 * 3. Any live cell with two or three live neighbors lives on to the next generation.
	 * 4. Any dead cell with exactly three live neighbors becomes a live cell.
	 * 
	 * It is important that step A of what is described below is performed on ALL cells before
	 * any of step B is done.
	 * 
	 * A) Establish a Cell's next state by computing whether the Cell will be alive or dead in
	 *    the next state.
	 *    
	 *    Hint: a private countLivingNeighbors() method would help here
	 *    
	 *    Once computed, tell the Cell its next state by
	 *    calling setNextStepLiveness on that cell
	 *    
	 * B) Having computed and set the next state for all cells, now iterate over the Cells again 
	 *    and call .evolve() on each so that it takes on its next state.
	 */
	public void step() {
		// FIXME
	}
	/**
	 * Visually display a text representation of each cell and whether it is alive or dead in the form
	 * of a two-dimensional grid containing a "Y" if the cell is living and a "N" if the cell is dead
	 * for each cell.
	 * @return a String as the text representation of the board
	 */
	public String toString() {
		String result = "";
		for (int r = 0; r <getNumRows(); r++){
			for (int c = 0; c <getNumCols(); c++){
				if (isAlive(r,c))
					result = result + "[X]";
				else result = result + "[ ]";
			}
			result = result + "\n";
		}
		return result;
	}
	
	/**
	 * generates the code for the current living cells on the board
	 */
	public void logAndCapture() {
		System.out.println("Beggining of Log and Capture");
		// FIXME for another extension credit
		System.out.println("End of Log and Capture");
	}
	
	public void blinker() {
		clear();
		if (getNumCols() < 3 || getNumRows() < 3) {
			System.out.println("Grid is too small for premade pattern Blinker. " +
			"Conway must be at least 3x3");
		}
		else {
			for (int i = 0; i < 3; i++) {
				setLife(true,1,i);
			}
		}
	}
	
	public void fourBlinkers() {
		clear();
		if (getNumCols() < 9 || getNumRows() < 9) {
			System.out.println("Grid is too small for premade pattern Four Blinkers. " +
			"Conway must be at least 9x9");
		}
		else {
			for (int i = 3; i < 6; i++) {
				for (int j = 3; j < 6; j++) {
					setLife(true,i,j);
				}
			}
		}
	}
	
	public void glider() {
		clear();
		if (getNumCols() < 10 || getNumRows() < 10) {
			System.out.println("Grid is too small for premade pattern Four Blinkers. " +
			"Conway must be at least 10x10");
		}
		else {
			setLife(true,1,1);
			setLife(true,1,3);
			setLife(true,2,2);
			setLife(true,2,3);
			setLife(true,3,2);
		}
	}
	
	public void gosperGliderGun() {
		clear();
		if (getNumCols() < 50 || getNumRows() < 50) {
			System.out.println("Grid is too small for premade pattern Gosper Glider Gun. " +
			"Conway must be at least 50x50");
		}
		else {
			setLife(true,0,27);
			setLife(true,1,25);
			setLife(true,1,27);
			setLife(true,2,15);
			setLife(true,2,16);
			setLife(true,2,23);
			setLife(true,2,24);
			setLife(true,2,37);
			setLife(true,2,38);
			setLife(true,3,14);
			setLife(true,3,18);
			setLife(true,3,23);
			setLife(true,3,24);
			setLife(true,3,37);
			setLife(true,3,38);
			setLife(true,4,3);
			setLife(true,4,4);
			setLife(true,4,13);
			setLife(true,4,19);
			setLife(true,4,23);
			setLife(true,4,24);
			setLife(true,5,3);
			setLife(true,5,4);
			setLife(true,5,13);
			setLife(true,5,17);
			setLife(true,5,19);
			setLife(true,5,20);
			setLife(true,5,25);
			setLife(true,5,27);
			setLife(true,6,13);
			setLife(true,6,19);
			setLife(true,6,27);
			setLife(true,7,14);
			setLife(true,7,18);
			setLife(true,8,15);
			setLife(true,8,16);
		}
	}
	
	public void yourDesginOne() {
		
	}
	
	public void yourDesignTwo() {
		
	}
}
