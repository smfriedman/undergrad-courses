package morelists;

import java.util.Iterator;

public class SinglyLinkedListIterator implements Iterator {

	private List list;
	private int i;

	public SinglyLinkedListIterator(List list) {
		this.list = list;
		i = 0;
	}

	@Override
	public boolean hasNext() {
		return i < list.size();
	}

	@Override
	public Object next() {
		Object x = list.get(i);
		i+=1;
		return x;
	}

	@Override
	public void remove() {
		list.remove(i);
	}

}
