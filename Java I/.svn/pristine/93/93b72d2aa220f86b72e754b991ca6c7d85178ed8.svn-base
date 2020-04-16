package lab10;

import java.awt.Color;

import sedgewick.StdDraw;

/**
 * 
 * @author Steven Friedman, steven.friedman
 * @author Vishal Vijay, vvijay
 * Creates a new object Goal that implements Drawable with behavior draw
 */
public class Goal implements Drawable{
	private double posX;
	private double posY;
	private double halfHeight;
	private double halfWidth;
	private Color color;
	private boolean isActive;
	private int value;			//how much this goal is worth
	
	/**
	 * creates a new goal
	 * @param posX
	 * @param posY
	 */
	public Goal(double posX, double posY){
		this.posX = posX;
		this.posY = posY;
		this.halfHeight = .05;
		this.halfWidth = .05;
		this.color = Color.WHITE;
		this.isActive = true;
		this.value = 10;
	}
	
	/**
	 * gets position x
	 * @return position x
	 */
	public double getPosX() {
		return posX;
	}

	/**
	 * sets position x
	 * @param posX
	 */
	public void setPosX(double posX) {
		this.posX = posX;
	}

	/**
	 * gets position y
	 * @return position y
	 */
	public double getPosY() {
		return posY;
	}

	/**
	 * sets position y
	 * @param posY
	 */
	public void setPosY(double posY) {
		this.posY = posY;
	}

	/**
	 * gets half height
	 * @return half height
	 */
	public double getHalfHeight() {
		return halfHeight;
	}

	/**
	 * sets half height
	 * @param halfHeight
	 */
	public void setHalfHeight(double halfHeight) {
		this.halfHeight = halfHeight;
	}

	/**
	 * gets half width
	 * @return half width
	 */
	public double getHalfWidth() {
		return halfWidth;
	}

	/**
	 * sets half width
	 * @param halfWidth
	 */
	public void setHalfWidth(double halfWidth) {
		this.halfWidth = halfWidth;
	}

	/**
	 * gets color
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * sets color
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * gets truth value of isActive
	 * @return truth value of isActive
	 */
	public boolean getActive() {
		return isActive;
	}

	/**
	 * sets truth value of isActive
	 * @param isActive
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * gets value
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * sets value
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * draws goal
	 */
	public void draw(){
		StdDraw.setPenColor(color);
		StdDraw.filledRectangle(posX, posY, halfWidth, halfHeight);
	}

}
