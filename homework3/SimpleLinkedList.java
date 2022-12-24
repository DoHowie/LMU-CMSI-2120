import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * A simple linked list class, implemented completely from scratch, using doubly
 * linked nodes and a cool "header" node to greatly simplify insertion and
 * deletions on the end of the list.
 */
public class SimpleLinkedList {

    /**
     * Doubly-linked node class, completely private to the List class, as clients
     * don't care about the implementation of the list. This is a regular class and
     * not a record because nodes are mutable.
     */
    private class Node {
        Object item;
        Node next;
        Node previous;

        Node(Object item, Node next, Node previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    /**
     * The list itself maintains only a reference to its "header" node. The header
     * is a node that does not store any data. Its 'next' field points to the first
     * item in the list and its 'previous' field points to the last item. This makes
     * all insertions and deletions uniform, even at the beginning and the end of
     * the list!
     */
    private Node header = new Node(null, null, null);

    /**
     * The number of items in the list, stored to make size() O(1).
     */
    private int size = 0;

    public SimpleLinkedList() {
        // The empty list is made by pointing the header to itself.
        header.next = header.previous = header;
    }

    public int size() {
        // The size stored as a field for easy lookup
        return size;
    }

    public void addFirst(Object item) {
        // The first node is the one right after the header.
        addBefore(item, header.next);
    }

    public void addLast(Object item) {
        // The last node is the one right before the header.
        addBefore(item, header);
    }

    public void add(int index, Object item) {
        // This one is trickier, as there is a special case. If the index is equal
        // to the size, we are just adding the new element at the end. Otherwise
        // we use the special nodeAt() utility to point to the existing node at the
        // desired position. Adding before this node does the trick.
        addBefore(item, (index == size ? header : nodeAt(index)));
    }

    public void remove(int index) {
        // The utility methods do all the work here.
        remove(nodeAt(index));
    }

    public Object get(int index) {
        // Many thanks to the nodeAt() utility!
        return nodeAt(index).item;
    }

    public void set(int index, Object item) {
        // nodeAt() is sure cool, innit?
        nodeAt(index).item = item;
    }

    public int indexOf(Object item) {
        // Ah the old "linear search" pattern: a for-loop with a return in
        // the middle. Note that to iterate the actual nodes, we start at
        // header.next, since header does not hold a list item. The loop
        // terminates when we get back to the header.
        var index = 0;
        for (var node = header.next; node != header; node = node.next) {
            if (node.item.equals(item)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public void forEach(Consumer<Object> consumer) {
        // Iterate from the first node (the one after the header).
        for (var node = header.next; node != header; node = node.next) {
            consumer.accept(node.item);
        }
    }

    /**
     * Get the node at a given index.
     */
    private Node nodeAt(int index) {
        // The only legal indexes are 0, 1, ... size-1.
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + " for size " + size);
        }
        // Only way to get by index in a simple linked list is to walk and
        // count. There are other kinds of lists that give quicker access by
        // position, but those structures are for another time.
        Node node = header;
        for (var i = 0; i <= index; i++) {
            node = node.next;
        }
        return node;
    }

    private void addBefore(Object item, Node node) {
        var newNode = new Node(item, node, node.previous);
        newNode.previous.next = newNode;
        newNode.next.previous = newNode;
        size++;
    }

    /**
     * Removes the referenced node by making its neighbors point around it.
     */
    private void remove(Node node) {
        // Just in case
        if (node == header) {
            throw new NoSuchElementException();
        }
        node.previous.next = node.next;
        node.next.previous = node.previous;
        size--;
    }

    public void take(int n) {
        if (n < 0 || n > size) {
            throw new IllegalArgumentException("Cannot take that many");
        }

        if (n == 0) {
            header.next = header.previous = header;
        }

        else {
            var newLastNode = nodeAt(n - 1);
            newLastNode.next = header;
            header.previous = newLastNode;
        }
        size = n;
    }

    public void drop(int n) {
        if (n < 0 || n > size) {
            throw new IllegalArgumentException("Cannot drop that many");
        }

        if (n == size) {
            header.next = header.previous = header;
        }

        else {
            var newFirstNode = nodeAt(n);
            newFirstNode.previous = header;
            header.next = newFirstNode;
        }
        size -= n;
    }

    public void reverse() {
        var node = header;
        for (var i = 0; i <= size(); i++) {
            var oldNext = node.next;
            node.next = node.previous;
            node = node.previous = oldNext;
        }
    }

    public void append(SimpleLinkedList other) {
        if (this == other) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < other.size(); i++) {
            addLast(other.get(i));
        }
    }

    public void map(UnaryOperator<Object> f) {
        for (var node = header.next; node != header; node = node.next) {
            node.item = f.apply(node.item);
        }
    }

    public void filter(Predicate<Object> p) {
        for (var node = header.next; node != header; node = node.next) {
            if (!p.test(node.item)) {
                remove(node);
            }
        }
    }

    public Object last() {
        for (var node = header.next; node != header; node = node.next) {
            if (node.next == header) {
                return node.item;
            }
        }
        throw new NoSuchElementException();
    }

    public boolean every(Predicate<Object> p) {
        for (var node = header.next; node != header; node = node.next) {
            if (!p.test(node.item)) {
                return false;
            }
        }
        return true;
    }

    public boolean some(Predicate<Object> p) {
        for (var node = header.next; node != header; node = node.next) {
            if (p.test(node.item)) {
                return true;
            }
        }
        return false;
    }

    public static SimpleLinkedList of(Object... items) {
        var list = new SimpleLinkedList();
        for (var item : items) {
            list.addLast(item);
        }
        return list;
    }
}