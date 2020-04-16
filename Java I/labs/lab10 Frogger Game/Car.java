package lab10;

import java.awt.Color;

import sedgewick.StdDraw;

/**
 * 
 * @author Steven Friedman, steven.friedman
 * @author Vishal Vijay, vvijay
 * Creates a new object Car that implements Drawable with behaviors draw and move
 */
public class Car implements Drawable{
	private double posX;
	private double posY;
	private double halfWidth;
	private double halfHeight;
	private double speed;
	private Color color;
	
	/**
	 * creates a new car
	 * @param posX
	 * @param posY
	 * @param speed
	 */
	public Car(double posX, double posY, double speed){
		this.posX = posX;
		this.posY = posY;
		this.halfWidth = .05;
		this.halfHeight = .025;
		this.speed = speed;
		this.color = Color.RED;
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
	 * gets speed
	 * @return speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * sets speed
	 * @param speed
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
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
	 * draws the car
	 */
	public void draw(){
		StdDraw.setPenColor(color);
		StdDraw.filledRectangle(posX, posY, halfWidth, halfHeight);
	}
	
	/**
	 * moves the car by speed,
	 * if the car reaches either end of the screen (depending on direction),
	 * it will loop around to the other side again
	 */
	public void move(){
		if (this.speed > 0){
			if (posX < 1.4)	this.posX = this.posX + speed;
			else this.posX = -.2;
		} else {
			if (posX > -.2)	this.posX = this.posX + speed;
			else this.posX = 1.4;
		}
	}

}
