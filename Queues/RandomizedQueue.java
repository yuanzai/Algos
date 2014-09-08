import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int count;
	private Node first;
	public RandomizedQueue() {
		count = 0;
		// construct an empty randomized queue
	}

	public boolean isEmpty() {
		// is the queue empty?
		return count ==0;
	}

	public int size() {
		// return the number of items on the queue
		return count;
	}

	public void enqueue(Item item) {
		// add the item
		if (item == null) throw new NullPointerException();
		
		count++;
		Node newNode = new Node();
		newNode.item = item;
		if (count > 1) {
			newNode.next = first;
		}
		first = newNode;	
	}

	public Item dequeue() {
		// delete and return a random item
		if (count == 0) throw new NoSuchElementException();
		
		int r = StdRandom.uniform(count);
		Node previous = null;
		Node target = first;

		if (count == 1) {
			first = null;
		} else {
			if (r == 0) {
				first = first.next;
			} else {
				while (r > 0) {
					previous = target;
					target = target.next;
					r--;
				}
				if (target.next == null) {
					previous.next = null;
				} else {
					previous.next = target.next;
				}
			}
		}

		count--;

		return target.item;
	}

	public Item sample() {
		// return (but do not delete) a random item
		if (count == 0)
			throw new NoSuchElementException();

		int r = StdRandom.uniform(count);
		Node target = first;

		while (r > 0) {
			target = target.next;
			r--;
		}

		return target.item;
	}

	public Iterator<Item> iterator() {
		// return an independent iterator over items in random order
		 return new ListIterator();
	}

	private class ListIterator implements Iterator<Item> {
		private int current = 0;
		private Integer[] sequence;
		
		public ListIterator () {
			sequence = new Integer[count];
			for (int i = 0; i< count;i++)
				sequence[i] = i;
			StdRandom.shuffle(sequence);
		}
		
		public boolean hasNext() {
			return current <count;
		}

		public void remove() { 
			throw new UnsupportedOperationException();
			/* not supported */
		}

		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			
			int r = sequence[current];
			Node currentNode = first;
			while (r > 0) {
				currentNode = currentNode.next;
				r--;
			}
			current++;
			return currentNode.item;
		}
	}
	
	private class Node {
		Node next;
		Item item;
	}
}