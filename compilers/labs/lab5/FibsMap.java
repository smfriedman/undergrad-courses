package lab5;

import java.util.HashMap;
import java.util.Map;

public class FibsMap {
   public static void fibs() {
	   Map<Integer, Integer> fibMap = new HashMap<Integer,Integer>();
	   fibMap.put(0, 0);
	   fibMap.put(1, 1);
     for (int i=0; i < 47; ++i) {
    	 if(i > 1){
    		 fibMap.put(i, fibMap.get(i - 1) + fibMap.get(i - 2));
    	 }
         System.out.println("The fib of " + i + " is " + fibMap.get(i));
     }
   }
   public static void main(String[] args) {
      fibs();
   }
}
