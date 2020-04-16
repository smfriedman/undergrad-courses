package lab5;
public class FibsIter {
	static int a = 0;
	static int b = 1;
   public static void fibs() {
     for (int i=0; i < 47; ++i) {
    	 int c = a + b;
    	 if(i == 0) {
    		 c = 0;
    	 }
        System.out.println("The fib of " + i + " is " + c);
        a = b;
        b = c;
     }
   }
   public static void main(String[] args) {
      fibs();
   }
}
