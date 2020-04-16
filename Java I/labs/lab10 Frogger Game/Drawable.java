package lab10;

/**
 * 
 * @author Steven Friedman, steven.friedman
 * @author Vishal Vijay, vvijay
 * Creates a new interface Drawable that can be implemented by rectangular drawable objects
 */
public interface Drawable {
	/**
	 * draws object
	 */
	public void draw();				//could include more properties that drawable objects share,
									//like color or setters, but it's unnecessary for collisions
	/**
	 * gets half height
	 * @return half height
	 */
	public double getHalfHeight();

	/**
	 * gets position y
	 * @return position y
	 */
	public double getPosY();

	/**
	 * gets half width
	 * @return half width
	 */
	public double getHalfWidth();

	/**
	 * gets position x
	 * @return position x
	 */
	public double getPosX();
}
