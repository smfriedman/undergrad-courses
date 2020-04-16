package conway;

import java.beans.PropertyChangeSupport;

/**
 * Given to you, no charge
 * Keeps track of the life of a Cell
 * @author cytron
 *
 */
public class Cell {
	
	private boolean nextStepLive;
	private final PropertyChangeSupport pcs;
	private boolean alive;

	public Cell() {
		this.nextStepLive = false;
		//
		// The line below establishes the pcs object, which is used to broadcast
		//   changes to this Cell, specifically when it changes from
		//   live to dead, or vice versa
		//
		this.pcs = new PropertyChangeSupport(this);
	}
	
	/**
	 * This method is needed for the visualization to work
	 * @return the PropertyChangeSupport instance for this Cell
	 */
	public PropertyChangeSupport getPCS() {
		return this.pcs;
	}

	/**
	 * 
	 */
	public void flipAlive() {
		setCurrentLiveness( !isCurrentlyAlive() );
	}
	
	
	/**
	 * Sets the status of the cell in the next generation, specifically whether it will be living or dead
	 * @param willBeAlive - a boolean as to whether the cell will be alive (true) or dead (false)
	 */
	public void setNextStepLiveness(boolean willBeAlive) {
		this.nextStepLive = willBeAlive;
	}
	
	/**
	 * Moves the cell into the next generation.
	 * The cell's current liveness is established based on the value
	 *    saved for the next step.
	 */
	public void evolve() {
		setCurrentLiveness(nextStepLive);
		this.nextStepLive = false;
	}
	


	/**
	 * A setter which sets whether the cell is alive or not.
	 * @param isAlive - a boolean as to whether the cell is alive (true) or dead (false)
	 */
	public final void setCurrentLiveness(boolean isAlive) {
		boolean old = this.alive;
		this.alive = isAlive;
		//
		// The following line will broadcast a message to listeners
		//   * only if * the two values differ (there is a change in liveness)
		//
		pcs.firePropertyChange("live", old, this.alive);
	}

	/**
	 * Is this Cell currently alive?
	 * @return a boolean whether the cell is alive (true) or dead (false)
	 */
	public boolean isCurrentlyAlive() {
		return this.alive;
	}
	
	/**
	 * Not much to say -- all we know is whether this Cell is live or dead.
	 */
	public String toString() {
		if (isCurrentlyAlive())
			return "This cell is alive";
		else return "This cell is dead";
	}

	
}
