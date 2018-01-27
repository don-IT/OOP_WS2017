import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Ein Objekt von Ensemble ist eine Sammlung von Objekten, deren gemeinsamer Typ über Typparameter festgelegt ist.
 */
public class Ensemble<Type> implements Iterable<Type> {
    private final Item head = new Item(null); // Implies: NULL cannot be a part of an Ensemble

    /**
     * @return Anzahl der Objekte in dieser Sammlung
     */
    public int size() {
        return this.head.remaining();
    }

    /**
     * Fügt den Parameter in die Sammlung ein sofern dieses Objekt (mit derselben Identität) noch nicht vorhanden ist.
     *
     * @param value Objekt, dass eingefügt werden soll
     * @return true, wenn und nur wenn das Objekt value noch nicht in der Sammlung war und daher eingefügt wurde
     */
    public boolean add(Type value) {
        for (Item current = this.head; current.value != value; current = current.next) {
            if (null == current.next) {
                current.next = new Item(value);

                return true;
            }
        }

        return false;
    }

    /**
     * Testet, ob das Objekt (mit derselben Identität) in der Sammlung enthalten ist.
     *
     * @param value Objekt, mit dem getestet wird
     * @return true, wenn und nur wenn das Objekt in der Sammlung enthalten ist
     */
    public boolean contains(Type value) {
        for (Iterator it = this.iterator(); it.hasNext(); )
            if (value == it.next())
                return true;

        return false;
    }

    /**
     * Entfernt den Parameter aus der Sammlung, sofern er (mit derselben Identität) darin enthalten war.
     *
     * @param value Objekt, das entfernt werden soll
     * @return true, wenn und nur wenn das Objekt in der Sammlung war und daher entfernt wurde
     */
    public boolean remove(Type value) {
        for (Item previous = this.head, current = previous.next; null != current; current = current.next) {
            if (current.value == value) {
                previous.next = current.next;
                // current.next = null; // DONT break running iterators

                return true;
            }
        }

        return false;
    }

    /**
     * Entfernt alle Objekte aus der Sammlung
     */
    public void clear() {
        this.head.next = null;
    }

    /**
     * Gibt einen neuen Iterator über alle Objekte in der Sammlung zurück, wobei die Objekte in der Reihenfolge des
     * Einfügens zurückgegeben werden. Der Iterator muss remove implementieren ohne eine UnsupportedOperationException
     * zu werfen (siehe java.lang.Iterator).
     *
     * @return Iterator über alle Elemente der Sammlung
     */
    public Iterator<Type> iterator() {
        return new ItemIterator(this);
    }

    private class Item {
        private Type value;
        private Item next = null;

        public Item(Type value) {
            this.value = value;
        }

        public int remaining() {
            return (null == this.next) ? 0 : 1 + this.next.remaining();
        }
    }

    private class ItemIterator implements Iterator<Type> {
        private Ensemble<Type> parent;

        private Item previous = null;
        private Item current;

        public ItemIterator(Ensemble<Type> parent) {
            this.parent = parent;
            this.current = parent.head;
        }

        public boolean hasNext() {
            return null != this.current.next;
        }

        public Type next() {
            if (!this.hasNext())
                throw new NoSuchElementException("The iteration has no more elements");

            this.previous = this.current;
            this.current = this.current.next;

            return this.current.value;
        }

        public void remove() {
            if (null == this.previous)
                throw new IllegalStateException("The next method has not yet been called, or the remove method has already been called after the last call to the next method");

            this.parent.remove(this.current.value);

            this.current = this.previous;
            this.previous = null;
        }
    }
}
