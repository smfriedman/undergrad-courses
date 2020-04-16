package lab9;/*
*TA:DanielD
*-1 no lab header
*99/100
*
*
*/


public class ListItem {
	//
	// Important:  Do NOT make these instance variables private
	// Our testing needs to be able to access them, so leave their
	//   declarations as you see them below.
	//
	int number;
	ListItem next;

	/**
	 * Creates a single list item.
	 * @param number the value to be held in the item
	 * @param next a reference to the next item in the list
	 */
	ListItem(int number, ListItem next) {
		this.number = number;
		this.next   = next;
	}

	/**
	 * Return a copy of this list using recursion.  No
	 * credit if you use any iteration!  All existing lists should remain
	 * intact -- this method must not mutate anything.
	 * @return a new ListItem at the beginning of a copy of this list
	 */
	public ListItem duplicate() {
		if (this.next == null) return new ListItem (this.number, null); //base case: final ListItem
		return new ListItem(this.number, this.next.duplicate());
	}

	/**
	 * Recursively compute the number of elements in the list. No
	 * credit if you use any iteration!  All existing lists should remain
	 * intact.
	 * @return the number of ListItems in the list as an int
	 */
	public int length() {
		if (this.next == null) return 1; //base case: final ListItem
		return 1 + this.next.length();
	}

	/**
	 * Return a new list, like duplicate(), but every element
	 * appears n times instead of once.  See the web page for details.
	 * You must do this method iteratively.  No credit
	 * if you use any recursion!
	 * @param n a positive (never 0) number specifying how many times to copy each element
	 * @return the ListItem at the beginning of the new stretched list
	 */
	public ListItem stretch(int n) {
		//duplicate the list and create pointer that points to first
		ListItem first = this.duplicate();
		ListItem current = first;
		//consider special case for the first ListItem
		for (int i = 0; i < n-1; i++){
			first = new ListItem(first.number, first);
		}
		//iterate through the rest of the ListItems using a pointer and a second pointer, "third" to hold the place of the next item
		while (current.next != null){
			ListItem third = current.next;
			for (int j = 0; j < n-1; j++){
				current.next = new ListItem(current.next.number, current.next);
			}
			current = third;
		}
		return first;
	}

	/**
	 * Return the first ListItem, looking from "this" forward,
	 * that contains the specified number.  No lists should be
	 * modified as a result of this call.  You may do this recursively
	 * or iteratively, as you like.
	 * @param n
	 * @return the first ListItem that contains n; if no ListItem contains n, return null
	 */
	public ListItem find(int n) {
		ListItem current = this.duplicate();
		for (int i = 0; i < this.length(); i++){
			if (current.number == n) return current;
			current = current.next;
		}
		return null;
	}

	/**
	 * Return the maximum number contained in the list
	 * from this point forward.  No lists should be modified
	 * as a result of this call.  You may do this method recursively
	 * or iteratively,as you like.
	 * @return the maximum number contained in the list
	 */
	public int max() {
		ListItem current = this.duplicate();
		int max = this.number;
		for (int i = 0; i < this.length(); i++){
			if (current.number > max) max = current.number;
			current = current.next;
		}
		return max;
	}

	/**
	 * Returns a copy of the list beginning at ls, but containing
	 * only elements that are even.
	 * @param ls
	 * @return a new list containing only even elements of ls
	 */
	public static ListItem evenElements(ListItem ls) {
		if (ls == null) return null;	//base case: end of list ([last ListItem].next)
		if (ls.number % 2 == 0) return new ListItem(ls.number, evenElements(ls.next));
		else return evenElements(ls.next);
	}	


	/**
	 * Returns a string representation of the values reachable from
	 * this list item.  Values appear in the same order as the occur in
	 * the linked structure.  Leave this method alone so our testing will work
	 * properly.
	 */
	public String toString() {
		if (next == null)
			return ("" + number);
		else
			return (number + " " + next); // calls next.toString() implicitly
	}
	
	//beginning of extension 9.1
	
	public static ListItem pairwiseSum(ListItem ls1, ListItem ls2){
		if(ls1 == null) return null;
		return new ListItem(ls1.number + ls2.number, pairwiseSum(ls1.next, ls2.next));
	}
	
	public static ListItem smallElements(ListItem ls, int n){
		ListItem small = ls.duplicate();
		while(small.number > n) small = small.next;
		ListItem current = small.next;
		for(int i = 0; i < small.length() - 1; i++){
			while (current.next.number > n) current.next = current.next.next;
			current = current.next;
		}
		return small;
	}
	
	public static void scale(ListItem ls, int n){
		ls.number = ls.number*n;
		ListItem current = ls.next;
		for(int i = 0; i < ls.length() - 1; i++){
			current.number = current.number*n;
			current = current.next;
		}
	}
	
	public static void insertAfter(ListItem ls, int i, int j){ //put i after every j
		if(ls == null) return;
		if(ls.number == i) ls.next = new ListItem(j, ls.next);
		insertAfter(ls.next, i, j);
	}
	
	//begining of extension 9.2
	
	public ListItem addFirst(ListItem ls, int first){ //helps with reverse recurse
		return new ListItem(first, ls);
	}
	
	public ListItem reverseRecurse(){
		if(this.next == null) return null;
		return addFirst(this.next.reverseRecurse(), this.next.number);
	}
	
	public ListItem reverseLoop(){ //needs work
		ListItem current = this;
		for(int i = 0; i < this.length(); i++){
			ListItem third = this.next;
			current.next.next = current.next;
		}
		return this;
	}
	
	//lecture
	public void dup(){
		ListItem current = this;
		while(current != null){
			ListItem copy = new ListItem(current.number, current.next);
			current.next = copy;
			current = current.next.next;
		}
	}
	
	


}
