package deques;

/**
 * A doubly-linked implementation of the {@link Deque} interface.
 *
 * @see Deque
 */
public class LinkedDeque<T> implements Deque<T> {
    /**
     * The sentinel node representing the front of this deque.
     */
    private Node<T> front;
    /**
     * The sentinel node representing the back of this deque.
     */
    private Node<T> back;
    /**
     * The number of elements in this deque.
     */
    private int size;

    /**
     * Constructs an empty deque.
     */
    public LinkedDeque() {
        size = 0;
        // TODO: Replace with your code
        //create two sentinel nodes with value null
        front = new Node<T>(null);
        back = new Node<T>(null);
        //connect the front and back to create an empty linked deque
        front.next = back;
        back.prev = front;
    }

    @Override
    public void addFirst(T item) {
        Node<T> newNode = new Node<T>(item, front, front.next);
        front.next = newNode;
        front.next.next.prev = newNode;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        Node<T> newNode = new Node<T>(item, back.prev, back);
        back.prev = newNode;
        back.prev.prev.next = newNode;
        size += 1;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T result = front.next.value;
        front.next=front.next.next; //set front's next to new first
        front.next.prev=front; //set new first's prev to front
        size -= 1;
        return result;
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T result = back.prev.value;
        back.prev=back.prev.prev; //set back's prev to new last
        back.prev.next=back; //set new last's next to back
        size -= 1;
        return result;
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        Node<T> current = front.next;
        for (int i = 0; i < size; i += 1){
            if (i == index){
                return current.value;
            }
            else current = current.next;
        }
        return null;
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns null if the front and back nodes form a valid linked deque. Otherwise, returns a
     * string describing the error.
     *
     * @return null if this deque is valid, or a description of the error
     */
    private String checkInvariants() {
        if (front == null) {
            return "Unexpected reference: front should reference a sentinel node but was <null>";
        } else if (front.prev != null) {
            return "Unexpected reference: front.prev should be <null> but was <" + front.prev + ">";
        } else if (back == null) {
            return "Unexpected reference: back should reference a sentinel node but was <null>";
        } else if (back.next != null) {
            return "Unexpected reference: back.next should be <null> but was <" + back.next + ">";
        }
        String message = checkNode(front, -1);
        if (message != null) {
            return message;
        }
        int i = 0;
        for (Node<T> curr = front.next; curr != back; curr = curr.next) {
            message = checkNode(curr, i);
            if (message != null) {
                return message;
            }
            i += 1;
        }
        return null;
    }

    /**
     * Returns null if the current node is valid with correct references in both directions.
     * Otherwise, returns a string describing the error.
     *
     * @param node the node to validate
     * @param i the index of the node in this deque
     * @return null if this node is valid, or a description of the error
     */
    private String checkNode(Node<T> node, int i) {
        if (node.next == null) {
            return "Unexpected null reference in node at index " + i + ": <" + node + ">";
        } else if (node.next.prev == node) {
            return null;
        } else if (node.next.prev == null) {
            return "Unexpected null reference in node at index " + (i + 1) + ": <" + node.next + ">";
        } else {
            return "Mismatched references:\n"
                    + "node at index " + i + ": <" + node + ">\n"
                    + "node at index " + (i + 1) + ": <" + node.next + ">";
        }
    }

    /**
     * A doubly-linked node containing a single element.
     *
     * @param <T> the type of element in this node
     */
    private static class Node<T> {
        /**
         * The element data value.
         */
        public final T value;
        /**
         * The previous node in the deque.
         */
        public Node<T> prev;
        /**
         * The next node in the deque.
         */
        public Node<T> next;

        /**
         * Constructs a new node with the given value.
         *
         * @param value the element data value for the new node
         */
        public Node(T value) {
            this(value, null, null);
        }

        /**
         * Constructs a new node with the given value, previous node, and next node.
         *
         * @param value the element data value for the new node
         * @param prev the previous node in the deque, or null
         * @param next the next node in the deque, or null
         */
        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    // ", prev=" + prev +
                    // ", next=" + next +
                    '}';
        }
    }
}
