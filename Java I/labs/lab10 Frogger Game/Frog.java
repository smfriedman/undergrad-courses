package lab10;

import java.awt.Color;

import sedgewick.StdDraw;

/**
 * 
 * @author Steven Friedman, steven.friedman
 * @author Vishal Vijay, vvijay
 * Creates a new object Frog with behaviors move, collide, and draw
 */
public class Frog {
	private double posX;
	private double posY;
	private double halfHeight;
	private double halfWidth;
	private Color color;
	private int lives;
	private double speed;
	private int playerNum;
	private int score;
	
	/**
	 * creates a new frog
	 * @param posX
	 * @param posY
	 * @param playerNum, player number: 0 for p1, 1 for p2
	 * @param color, any color from the Color awt
	 */
	public Frog(double posX, double posY, int playerNum, Color color){
		this.posX = posX;
		this.posY = posY;
		this.halfHeight = 0.03;
		this.halfWidth = 0.03;
		this.color = color;
		this.lives = 5;
		this.speed = .01;
		this.playerNum = playerNum;
		this.score = 0;
	}
	
	/**
	 * gets x position
	 * @return x position
	 */
	public double getPosX() {
		return posX;
	}

	/**
	 * sets x position
	 * @param posX
	 */
	public void setPosX(double posX) {
		this.posX = posX;
	}

	/**
	 * gets y position
	 * @return y position
	 */
	public double getPosY() {
		return posY;
	}

	/**
	 * sets y position
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
	 * gets number of lives
	 * @return number of lives
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * sets number of lives
	 * @param lives
	 */
	public void setLives(int lives) {
		this.lives = lives;
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
	 * gets player number (0 for p1, 1 for p2)
	 * @return player number
	 */
	public int getPlayerNum() {
		return playerNum;
	}

	/**
	 * sets player number (0 for p1, 1 for p2)
	 * @param playerNum
	 */
	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	/**
	 * gets score
	 * @return score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * sets score
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * moves the frog by amount speed based on 
	 * the key pressed and the frogs player number,
	 * frog is not able to go off screen,
	 * WASD for p1, up down left right for p2
	 */
	public void move(){																//p1 (0) p2 (1)
		if(ArcadeKeys.isKeyPressed(playerNum,0) && posY < .97) posY = posY + speed;	//W 	 up
		if(ArcadeKeys.isKeyPressed(playerNum,1) && posX > .03) posX = posX - speed;	//A 	 left
		if(ArcadeKeys.isKeyPressed(playerNum,2) && posY > .03) posY = posY - speed;	//S 	 down
		if(ArcadeKeys.isKeyPressed(playerNum,3) && posX < .97) posX = posX + speed;	//D 	 right
	}
	
	/**
	 * tests if the frog has collided with another rectangular Drawable
	 * @param d, a Drawable
	 * @return true if collided, false otherwise
	 */
	public boolean collide(Drawable d){
		if (this.getHalfHeight() + d.getHalfHeight() > Math.abs(this.getPosY() - d.getPosY())
				&& this.getHalfWidth() + d.getHalfWidth() > Math.abs(this.getPosX() - d.getPosX())){
			return true;				
		}
		else return false;
	}
	
	/**
	 * draws the frog
	 */
	public void draw(){
		StdDraw.setPenColor(color);
		StdDraw.filledRectangle(posX, posY, halfWidth, halfHeight);
	}
	
}
