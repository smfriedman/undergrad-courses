package morelists;

public class SinglyLinkedListSentinel implements List<Integer> {

	private ListNode head;

	public SinglyLinkedListSentinel() {
		head = new ListNode();
	}

	@Override
	public void addFirst(Integer x) {
		ListNode p = new ListNode(x);

		p.next = head.next;
		head.next = p;

	}

	@Override
	public void addLast(Integer x) {
		ListNode p = new ListNode(x);

		ListNode q = head.next;
		while (q.next != null) {
			q = q.next;
		}
		q.next = p;

	}

	@Override
	public boolean remove(Integer x) {
		ListNode p = head.next;
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
		ListNode p = head.next;
		int current = 0;
		while (p != null) {
			if (i == current) {
				return p.value;
			} else {
				p = p.next;
				current += 1;
			}
		}
		throw new IndexOutOfBoundsException(String.valueOf(i));
	}

	@Override
	public int size() {
		ListNode p = head.next;
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
