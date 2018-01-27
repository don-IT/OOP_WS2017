import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A Set of unique values. Does not accept NULL as value!
 */
public class Set implements Iterable {
    private final Item head = new Item(null);

    /**
     * @param value Value to addLamp
     * @return True if and only if this value was not a duplicate
     */
    public boolean add(Object value) {
        for (Item current = this.head; current.value != value; current = current.next) {
            if (null == current.next) {
                current.next = new Item(value);
                return true;
            }
        }

        return false;
    }

    /**
     * @param value Value to look for
     * @return True if and only if this Set contains the value
     */
    public boolean contains(Object value) {
        for (Iterator it = this.iterator(); it.hasNext(); )
            if (value == it.next())
                return true;

        return false;
    }

    /**
     * @param value Value to removeLamp
     * @return True if and only if this Set contained the value before it was removed
     */
    public boolean remove(Object value) {
        for (
                Item previous = this.head, current = previous.next;
                null != current;
                previous = current, current = current.next
        ) {
            if (current.value == value) {
                previous.next = current.next;
                return true;
            }
        }

        return false;
    }

    /**
     * Removes all values.
     */
    public void clear() {
        this.head.next = null;
    }

    /**
     * @return Number of values in this Set
     */
    public int size() {
        return this.head.remaining();
    }

    /**
     * @return Iterator over all values within this Set
     */
    public Iterator iterator() {
        return new ItemIterator(this.head);
    }

    private class Item {
        private final Object value;
        private Item next = null;

        public Item(Object value) {
            this.value = value;
        }

        public int remaining() {
            return (null == this.next) ? 0 : 1 + this.next.remaining();
        }
    }

    private class ItemIterator implements Iterator {
        private Item current;

        public ItemIterator(Item current) {
            this.current = current;
        }

        public boolean hasNext() {
            return null != this.current.next;
        }

        public Object next() {
            if (!this.hasNext())
                throw new NoSuchElementException("The iteration has no more elements");

            this.current = this.current.next;
            return this.current.value;
        }
    }
}
