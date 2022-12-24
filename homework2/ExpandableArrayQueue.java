import java.util.NoSuchElementException;

public class ExpandableArrayQueue implements Queue {
    private static final int MINIMUM_CAPACITY = 16;
    private Object[] elements;
    private int size = 0;
    private int head = 0;
    private int tail = 0;

    public ExpandableArrayQueue() {
        elements = new Object[MINIMUM_CAPACITY];
    }

    public void enqueue(Object item) {
        if (isFull()) {
            Object[] expandedElements = new Object[2 * capacity()];
            System.arraycopy(elements, head, expandedElements, 0, size - head);
            System.arraycopy(elements, 0, expandedElements, size - head, head);
            elements = expandedElements;
            head = 0;
            tail = size();
        }

        elements[tail] = item;
        tail = (tail + 1) % elements.length;
        size++;
    }

    public Object dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from empty queue");
        }

        if (capacity() > MINIMUM_CAPACITY && size <= capacity() / 4) {
            Object[] shrinkedElements = new Object[capacity() / 2];
            System.arraycopy(elements, head, shrinkedElements, 0, size);
            elements = shrinkedElements;
            head = 0;
        }

        var item = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        
        return item;
    }

    public Object peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot peek into empty queue");
        }

        return elements[size - 1];
    }

    public int size() {
        return size;
    }

    public boolean isFull() {
        return size == elements.length;
    }

    public int capacity() {
        return elements.length;
    }

}