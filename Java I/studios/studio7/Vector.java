package studio7;

public class Vector {
	private final double deltaX;
	private final double deltaY;
	
	public Vector(double deltaX, double deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}
	
	public String toString(){
		return "[" + deltaX + " " + deltaY + "]";
	}

	public double getDeltaX() {
		return deltaX;
	}

//	public void setDeltaX(double deltaX) {
//		this.deltaX = deltaX;
//	}

	public double getDeltaY() {
		return deltaY;
	}

//	public void setDeltaY(double deltaY) {
//		this.deltaY = deltaY;
//	}
	
	public double magnitude(){
		return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
	}
	
	public Vector deflectX(){
		return new Vector(-deltaX, deltaY); //can also say this.deltaX/deltaY
	}
	
	public Vector deflectY(){
		return new Vector(deltaX, -deltaY);
	}
	
	public Vector plus(Vector other){
		return new Vector(other.deltaX + deltaX, other.deltaY + deltaY);
		//can also do other.getDeltaX or also this.deltaX
	}
	
	public Vector minus(Vector other){
		return new Vector(deltaX - other.deltaX, deltaY - other.deltaY);
		//can also call deflectX and deflectY
	}
	
	public Vector scale(double factor){
		return new Vector(factor*deltaX, factor*deltaY);
	}
	
	public Vector rescale(double magnitude){
		double scaleFactor = magnitude/magnitude();
		return scale(scaleFactor);
	}
}
