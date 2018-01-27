import java.util.Iterator;

/**
 * A Mapping from key to value. NULL is not valid as key. Setting a NULL value removes the key.
 */
public class Map {
    private final Set keys = new Set();
    private final Storage storage = new Storage();

    /**
     * @return Number of keys in this Map
     */
    public int size() {
        return this.keys.size();
    }

    /**
     * Sets the value stored at key. If value is NULL, delete the key.
     *
     * @param key Key to store the value with
     * @param value Value to store
     */
    public void set(Object key, Object value) {
        if (null == key)
            throw new IllegalArgumentException("Key cannot be NULL");

        if (null == value) {
            this.keys.remove(key);
            this.storage.find(key.hashCode()).value = null;
        } else {
            this.keys.add(key);
            this.storage.find(key.hashCode()).value = value;
        }
    }

    /**
     * @param key Key to look for
     * @return True if and only if there is a value stored with this key
     */
    public boolean has(Object key) {
        return this.keys.contains(key);
    }

    /**
     * Retreives the value stored at key.
     *
     * @param key Key to look for
     * @return Value stored with this key, or NULL if nothing stored with this key
     */
    public Object get(Object key) {
        if (!this.has(key)) {
            return null;
        } else {
            return this.storage.find(key.hashCode()).value;
        }
    }

    /**
     * Deletes the value stores with key
     *
     * @param key Key to delete
     */
    public void delete(Object key) {
        this.set(key, null);
    }

    /**
     * Removes all keys and values.
     */
    public void clear() {
        this.keys.clear();
        this.storage.clear();
    }

    /**
     * Finds the first key with which the value is stored.
     *
     * @param value Value to look for
     * @return First key with which the value was stored, or NULL if value was not stored within this map
     */
    public Object find(Object value) {
        for (Iterator it = this.keys(); it.hasNext(); ) {
            Object key = it.next();

            if (this.get(key) == value)
                return key;
        }

        return null;
    }

    /**
     * @return An iterator over all keys in this Map
     */
    public Iterator keys() {
        return this.keys.iterator();
    }

    /**
     * @return An iterator over all values in this Map
     */
    public Iterator values() {
        return new ValueIterator(this);
    }

    private class Storage {
        private Object value = null;

        private Storage zero = null;
        private Storage one = null;

        public void clear() {
            this.value = null;
            this.zero = null;
            this.one = null;
        }

        public Storage find(int key) {
            if (key == 0) {
                return this;
            } else {
                if (0 == (key & 1)) {
                    if (null == this.zero)
                        this.zero = new Storage();

                    return this.zero.find(key >>> 1);
                } else {
                    if (null == this.one)
                        this.one = new Storage();

                    return this.one.find(key >>> 1);
                }
            }
        }
    }

    private class ValueIterator implements Iterator {
        private final Map map;
        private final Iterator keys;

        public ValueIterator(Map map) {
            this.map = map;
            this.keys = map.keys();
        }

        public boolean hasNext() {
            return this.keys.hasNext();
        }

        public Object next() {
            return this.map.get(this.keys.next());
        }
    }
}
