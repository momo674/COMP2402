package comp2402a3;

import java.lang.reflect.Array;
import java.util.*;


/**
 * An implementation of skiplists for searching
 *
 *
 * @param <T>
 */
public class SkippityFast<T> implements IndexedSSet<T> {
	protected Comparator<T> c;
	private TreeSet<T> bst;
	

	@SuppressWarnings("unchecked")
	protected static class Node<T> {
		T x;
		Node<T>[] next;
		int[] length;
		public Node(T ix, int h) {
			x = ix;
			next = (Node<T>[])Array.newInstance(Node.class, h+1);
			length = new int[h+1];
		}
		public int height() {
			return next.length - 1;
		}
	}

	/**
	 * This node<T> sits on the left side of the skiplist
	 */
	protected Node<T> sentinel;

	/**
	 * The maximum height of any element
	 */
	int h;

	/**
	 * The number of elements stored in the skiplist
	 */
	int n;

	/**
	 * A source of random numbers
	 */
	Random rand;

	/**
	 * Used by add(x) method
	 */
	protected Node<T>[] stack;
	protected int[] stacki;


	@SuppressWarnings("unchecked")
	public SkippityFast(Comparator<T> c) {
		this.bst = new TreeSet<>(c);

		this.c = c;
		n = 0;
		sentinel = new Node<T>(null, 32);
		stack = (Node<T>[])Array.newInstance(Node.class, sentinel.next.length);
		stacki = new int[sentinel.next.length];
		h = 0;
		rand = new Random();
	}

	public SkippityFast() {
		this(new DefaultComparator<T>());
	}

	/**
	 * Simulate repeatedly tossing a coin until it comes up tails.
	 * Note, this code will never generate a height greater than 32
	 * @return the number of coin tosses - 1
	 */
	protected int pickHeight() {
		int z = rand.nextInt();
		int k = 0;
		int m = 1;
		while ((z & m) != 0) {
			k++;
			m <<= 1;
		}
		return k;
	}

	/**
	 * Find the node<T> u that precedes the value x in the skiplist.
	 *
	 * @param x - the value to search for
	 * @return a node<T> u that maximizes u.x subject to
	 * the constraint that u.x < x --- or sentinel if u.x >= x for
	 * all node<T>s x
	 */
	protected Node<T> findPredNode(T x) {
		Node<T> u = sentinel;
		int r = h;
		while (r >= 0) {
			while (u.next[r] != null && c.compare(u.next[r].x,x) < 0)
				u = u.next[r];   // go right in list r
			r--;               // go down into list r-1
		}
		return u;
	}

	protected class Pair {
		public int i;
		public Node<T> u;

		Pair(int i, Node<T> u) {
			this.i = i;
			this.u = u;
		}
	}

	protected Pair findPredNode2(T x) {
		Node<T> u = sentinel;
		int r = h;
		int i = -1;
		while (r >= 0) {
			while (u.next[r] != null && c.compare(u.next[r].x,x) < 0) {
				i += u.length[r];
				u = u.next[r];   // go right in list r
			}
			r--;               // go down into list r-1
		}
		return new Pair(i, u);
	}

	public T find(T x) {
		Node<T> u = findPredNode(x);
		return u.next[0] == null ? null : u.next[0].x;
	}

	public T findGE(T x) {
		if (x == null) {   // return first node<T>
			return sentinel.next[0] == null ? null : sentinel.next[0].x;
		}
		return find(x);
	}

	public T findLT(T x) {
		if (x == null) {  // return last node<T>
			Node<T> u = sentinel;
			int r = h;
			while (r >= 0) {
				while (u.next[r] != null)
					u = u.next[r];
				r--;
			}
			return u.x;
		}
		return findPredNode(x).x;
	}

	protected Node<T> getPred(int i) {
		Node<T> u = sentinel;
		int r = h;
		int j = -1;   // index of the current node in list 0
		while (r >= 0) {
			while (u.next[r] != null && j + u.length[r] < i) {
				j += u.length[r];
				u = u.next[r];
			}
			r--;
		}
		return u;
	}

	
	public boolean add(T x) {
		bst.add(x);
		Node<T> u = sentinel;
		int r = h;
		int comp = 0;
		int rank = -1;
		while (r >= 0) {
			while (u.next[r] != null
			       && (comp = c.compare(u.next[r].x,x)) < 0) {
	 			 	 rank += u.length[r];
					 u = u.next[r];
			}
			if (u.next[r] != null && comp == 0) return false;
			stacki[r] = rank;
			stack[r--] = u;          // going down, store u
		}
		// We made it this far without finding x, so we insert it

		Node<T> w = new Node<T>(x, pickHeight());
		while (h < w.height()) {
			stack[++h] = sentinel;   // height increased
			stacki[h] = -1;
		}

		// length of each edge "over" x increases by 1
		for (int i = 0; i <= h; i++) {
			stack[i].length[i]++;
		}

		rank++;  // now rank = rank(x)
		for (int i = 0; i < w.next.length; i++) {
			// these two lines split w into the L_i
			w.next[i] = stack[i].next[i];
			stack[i].next[i] = w;
			// now we split the length of an edge into two
			w.length[i] = stack[i].length[i] + stacki[i] - rank;
			stack[i].length[i] = rank-stacki[i];
		}
		n++;
		return true;
	}

	public boolean remove(T x) {
				bst.remove(x);

		boolean removed = false;
		Node<T> u = sentinel;
		int r = h;
		int comp = 0;
		while (r >= 0) {
			while (u.next[r] != null
			       && (comp = c.compare(u.next[r].x, x)) < 0) {
				u = u.next[r];
			}
			stack[r] = u;
			r--;
		}
		if (c.compare(x, u.next[0].x) == 0) {
			// we're really removing something
			for (r = h; r >= 0; r--) {
				u = stack[r];
				u.length[r]--;
				if (u.next[r] != null && c.compare(x, u.next[r].x) == 0) {
					u.length[r] += u.next[r].length[r];
					u.next[r] = u.next[r].next[r];
					if (u == sentinel && u.next[r] == null)
						h--;  // height has gone down
				}
			}
			n--;
			return true;
		}
		return false;
	}

	public T get(int i) {
		if (i < 0 || i > n-1) throw new IndexOutOfBoundsException();
		Node<T> p = getPred(i);
		return p.next[0].x;
	}

	public int rangecount(T x, T y) {
		if (c.compare(x, y) > 0) {
			T temp = x;
			x = y;
			y = temp;
		}
	
		int count = 0;
		Node<T> u = sentinel;
		int r = h;
		int rank = -1; // Initialize rank
	
		while (r >= 0) {
			while (u.next[r] != null && c.compare(u.next[r].x, x) < 0) {
				// Update rank while moving to the right
				rank += u.length[r];
				u = u.next[r];
			}
	
			r--; // Go down into list r-1
		}
	
		// Now u is the largest node with u.x < x or the sentinel
		u = u.next[0];
	
		while (u != null && c.compare(u.x, y) <= 0) {
			// If u is within the range [x, y], add its count to the total
			count += u.length[0];
			u = u.next[0];
		}
	
		return count;
	}
	





	


	public void clear() {
		n = 0;
		h = 0;
		Arrays.fill(sentinel.next, null);
	}

	public int size() {
		return n;
	}

	public Comparator<T> comparator() {
		return c;
	}

	/**
	 * Create a new iterator in which the next value in the iteration is u.next.x
	 * @param u
	 * @return
	 */
	protected Iterator<T> iterator(Node<T> u) {
		class SkiplistIterator implements Iterator<T> {
			Node<T> u, prev;
			public SkiplistIterator(Node<T> u) {
				this.u = u;
				prev = null;
			}
			public boolean hasNext() {
				return u.next[0] != null;
			}
			public T next() {
				prev = u;
				u = u.next[0];
				return u.x;
			}
			public void remove() {
				// Not constant time
				SkippityFast.this.remove(prev.x);
			}
		}
		return new SkiplistIterator(u);
	}

	public Iterator<T> iterator() {
		return iterator(sentinel);
	}

	public Iterator<T> iterator(T x) {
		return iterator(findPredNode(x));
	}
}
