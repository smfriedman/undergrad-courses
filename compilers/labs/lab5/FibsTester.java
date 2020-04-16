package lab5;
public class FibsTester {
   public static int fib(int n) {
      if (n <= 0) return 0;
      if (n == 1) return 1;
      int ans = fib(n-1) + fib(n-2);
      return ans;
   }
   public static void fibs() {
     for (int i=0; i < 47; ++i) {
 		System.out.print("The fib of ");
 		System.out.print(i);
 		System.out.print(" is ");
 		System.out.print(fib(i));
 		System.out.print("\n");    
 		}
   }
   public static void main(String[] args) {
      fibs();
   }
}
