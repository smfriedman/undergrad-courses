public class MyThing {
    
    public void count(int x) 
    {
	// Print every odd number between 1 and x (inclusive) in the form
	// 1...
        // 3...
	// <etc>
	// x!
    	for (int i = 1; i <= x; i = i+2){
    		System.out.print(i);
    		if (i == x || i == x-1) System.out.println("!");
    		else System.out.println("...");
    	}
    }
}
