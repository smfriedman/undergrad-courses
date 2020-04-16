package studio9;

public class DoublyLinkedListOfInts implements List<Integer> {

	private ListNode head;
	private ListNode tail;
	private int size;
	
	DoublyLinkedListOfInts(){
		this.size = 0;
		this.head = new ListNode(0, null, null);
		this.tail = new ListNode(0, null, head);
		this.head.next = tail;
	}
	
	@Override
	public void addFirst(Integer x) {
		// TODO Auto-generated method stub
		ListNode first = new ListNode(x, this.head.next, head);
		if (this.head.next == tail) this.tail.prev = first;		//empty list case
		this.head.next = first;
		this.size++;
	}

	@Override
	public void addLast(Integer x) {
		// TODO Auto-generated method stub
		ListNode last = new ListNode(x, tail, this.tail.prev);
		if(this.tail.prev == head) this.head.next = last;		//empty list case
		this.tail.prev = last;
		this.size++;
	}

	@Override
	public boolean remove(Integer x) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer get(int i) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		int size = 0;
		ListNode p = head.next;
		while(p.next != null){
			size++;
		}
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if (head.next == tail && tail.prev == head) return true;
		return false;
	}

}
