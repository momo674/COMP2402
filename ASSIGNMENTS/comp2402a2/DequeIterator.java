package comp2402a2;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class DequeIterator<E> implements Iterator<E> {
    private ArrayList<E> frontStack;
    private ArrayList<E> rearStack;
    private int currentIndex;
    
    public DequeIterator(ArrayList<E> frontStack, ArrayList<E> rearStack) {
        this.frontStack = frontStack;
        this.rearStack = rearStack;
        this.currentIndex = 0;
    }
    
    @Override
    public boolean hasNext() {
        return currentIndex < frontStack.size() || !rearStack.isEmpty();
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        if (currentIndex < frontStack.size()) {
            return frontStack.get(currentIndex++);
        } else {
            // Rear stack is non-empty, pop and return elements from it
            return rearStack.remove(rearStack.size() - 1);
        }
    }

    // You can implement the remove() method if needed, but it's optional.
}
