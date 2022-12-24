import java.util.NoSuchElementException;


public class ExpandableArrayStack implements Stack {
    private static final int MINIMUM_CAPACITY = 16;
    private int size = 0;
    private Object[] elements;

    public ExpandableArrayStack() {
        elements = new Object[MINIMUM_CAPACITY];
    }

    public void push(Object item) {
        if (isFull()) {
            Object[] expandedElements = new Object[2 * elements.length];
            System.arraycopy(elements, 0, expandedElements, 0, size);
            elements = expandedElements;
        }
        
        elements[size++] = item;
    }

    public Object pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot pop from empty stack");
        }

        if (capacity() > MINIMUM_CAPACITY && size <= capacity() / 4) {
            Object[] shrinkedElements = new Object[elements.length / 2];
            System.arraycopy(elements, 0, shrinkedElements, 0, size);
            elements = shrinkedElements;
        }

        var poppedElement = elements[size - 1];
        elements[--size] = null;
        
        return poppedElement;
    }

    public Object peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot peek into empty stack");
        }

        return elements[size - 1];
    }

    public boolean isFull() {
        return size == elements.length;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return elements.length;
    }

}