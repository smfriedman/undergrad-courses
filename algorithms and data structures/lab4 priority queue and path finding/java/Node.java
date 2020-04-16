
public class Node<T> {
	private int key;
	private T value;
	private Handle handle;
	
	public Node(int key, T value, Handle handle){
		this.key = key;
		this.value = value;
		this.handle = handle;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Handle getHandle() {
		return handle;
	}

	public void setHandle(Handle handle) {
		this.handle = handle;
	}
	
	
}
