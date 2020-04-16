package lists;

public class SinglyLinkedListComplete implements List<Integer> {

	private ListNode head;

	public SinglyLinkedListComplete() {
		head = null;
	}

	@Override
	public void addFirst(Integer x) {
		ListNode p = new ListNode(x);
		if (head == null) {
			head = p;
		} else {
			p.next = head;
			head = p;
		}
	}

	@Override
	public void addLast(Integer x) {
		ListNode p = new ListNode(x);
		if (head == null) {
			head = p;
		} else {
			ListNode q = head;
			while (q.next != null) {
				q = q.next;
			}
			q.next = p;
		}
	}

	@Override
	public boolean remove(Integer x) {
		if (head == null) {
			return false;
		} else if (head.value == x) {
			head = head.next;
			return true;
		}
		ListNode p = head;
		while (p.next != null) {
			if (p.next.value == x) {
				p.next = p.next.next;
				return true;
			}
		}
		return false;
	}

	@Override
	public Integer get(int i) throws IndexOutOfBoundsException {
		ListNode p = head;
		int current = 0;
		while (p != null) {
			if (i == current) {
				return p.value;
			} else {
				p = p.next;
			}
		}
		throw new IndexOutOfBoundsException(String.valueOf(i));
	}

	@Override
	public int size() {
		ListNode p = head;
		int size = 0;
		while (p != null) {
			size++;
			p = p.next;
		}
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

}
