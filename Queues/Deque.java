import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private int count;
	private Node first;
	private Node last;
	
	public Deque() {
		// construct an empty deque
		count = 0;
	}

	public boolean isEmpty() {
		// is the deque empty?
		return count==0;
	}

	public int size() {
		// return the number of items on the deque
		return count;
	}

	public void addFirst(Item item) {
		// insert the item at the front
		if (item == null) throw new NullPointerException();
		
		count++;
		
		Node firstNode = new Node();
		firstNode.item = item;
		if (count > 1) {
			firstNode.next = first;
			first.previous = firstNode;
		} else {
			last = firstNode;
		}
		first = firstNode;
	}

	public void addLast(Item item) {
		// insert the item at the end
		if (item == null) throw new NullPointerException();

		count++;

		Node lastNode = new Node();
		lastNode.item = item;
		if (count > 1) {
			lastNode.previous = last;
			last.next = lastNode;
		} else {
			first = lastNode;
		}
		last = lastNode;	
	}

	public Item removeFirst() {
		// delete and return the item at the front
		if (count == 0) throw new NoSuchElementException();
		count--;
		Item target = first.item;
		Node nextNode = first.next;
		first = null;
		if (count > 0) {
			first = nextNode;
		} 
		return target;
	}

	public Item removeLast() {
		// delete and return the item at the end
		if (count == 0) throw new NoSuchElementException();
		count--;
		Item target = last.item;
		Node previousNode = last.previous;
		last = null;
		if (count > 0) {
			last = previousNode;
		}
		
		return target;
	}

	public Iterator<Item> iterator() {
		// return an iterator over items in order from front to end
		 return new ListIterator();
	}

	private class ListIterator implements Iterator<Item> {
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() { 
			throw new UnsupportedOperationException();
			/* not supported */
		}

		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	
	private class Node {
		Node next;
		Node previous;
		Item item;
	}
}