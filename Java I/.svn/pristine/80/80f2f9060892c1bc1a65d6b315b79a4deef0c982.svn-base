package lists;

public class ListNodeDemo3 {

	public static void main(String[] args) {

		ListNode list = new ListNode(42);
		ListNode current = list;

		current.next = new ListNode(43);
		current = current.next;

		current.next = new ListNode(44);

		System.out.println(list.value);
		System.out.println(list.next.value);
		System.out.println(list.next.next.value);
		System.out.println();

		list.next = list.next.next;

		System.out.println(list.value);
		System.out.println(list.next.value);
		System.out.println(list.next.next.value);

	}

}
