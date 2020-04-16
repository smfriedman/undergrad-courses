package TestClasses;

public class Pub {

	public int a, b;
	
	public Pub() {
		this.a = 400;
		this.b = 31;
	}
	public static Pub genPub() {
		return new Pub();
	}

	@Override
	public String toString() {
		return "Pub [a=" + a + ", b=" + b + "]";
	}
}
