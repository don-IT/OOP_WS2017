import java.util.*;

/**
 * A Grid is a multi-dimensional array. Unlike common Java arrays, Grid is recursive.
 *
 * @param <T> Type of objects stored within this grid
 */
public abstract class Grid<T> implements Iterable<T>, Cloneable {

    /**
     * Creates a new grid
     *
     * @param size of the grid
     * @param <T> Type of objects within this grid
     * @return the created grid
     */
    public static <T> Grid<T> create(List<Integer> size) {
        Grid<T> current = new Element<>(null);

        for (int d = size.size() - 1; d >= 0; d--)
            current = new Dimension<>(size.get(d), current);

        return current;
    }

    /**
     * @return a shallow clone of this grid
     */
    public abstract Grid<T> clone();

    /**
     * @return the size of the grid
     */
    public abstract List<Integer> size();

    /**
     * @return number of dimensions
     */
    public int dimensions() {
        return this.size().size();
    }

    /**
     * @return an iterator over all objects within the grid
     */
    public Iterator<T> iterator() {
        return new Iterator<>(this);
    }

    /**
     * @param address to check
     * @return true if the address exists
     */
    public boolean has(List<Integer> address) {
        Objects.requireNonNull(address, "Address cannot be NULL");

        return isWithinBounds(address, Collections.nCopies(address.size(), 0), this.size());
    }

    /**
     * @param address to look at
     * @return the object stored at this address or NULL if empty
     */
    public T get(List<Integer> address) {
        if (!this.has(address))
            throw new IndexOutOfBoundsException("Address is out of bounds");

        return this.resolve(address).get();
    }

    /**
     * @param value to store at
     * @param address to store value at
     */
    public void put(T value, List<Integer> address) {
        if (!this.has(address))
            throw new IndexOutOfBoundsException("Address is out of bounds");

        this.resolve(address).set(value);
    }

    /**
     * Creates a slice of this grid, starting offset the specified address and limited by the specified size.
     *
     * Values of the slice reference values of the original grid. If values of the slice change, the corresponding
     * values of the original grid change, and vice-versa.
     *
     * @param offset what address to start the slice (inclusive)
     * @param size of the slice
     * @return a slice of this grid
     */
    public Grid<T> slice(List<Integer> offset, List<Integer> size) {
        return new Slice<>(this, offset, size);
    }

    /**
     * Creates a slice of this grid, starting offset the specified address.
     *
     * Values of the slice reference values of the original grid. If values of the slice change, the corresponding
     * values of the original grid change, and vice-versa.
     *
     * @param offset what address to start the slice (inclusive)
     * @return a slice of this grid
     */
    public Grid<T> offset(List<Integer> offset) {
        return new Slice<>(this, offset);
    }

    /**
     * Creates a slice of this grid, limited by the specified size.
     *
     * Values of the slice reference values of the original grid. If values of the slice change, the corresponding
     * values of the original grid change, and vice-versa.
     *
     * @param size of the slice
     * @return a slice of this grid
     */
    public Grid<T> limit(List<Integer> size) {
        return new Slice<>(this, Collections.nCopies(this.dimensions(), 0), size);
    }

    /**
     * Creates a slice of this grid, mapping the mapping.get(i)-th dimension of the grid to i-th dimension of the slice.
     *
     * Values of the slice reference values of the original grid. If values of the slice change, the corresponding
     * values of the original grid change, and vice-versa.
     *
     * @param mapping of dimensions
     * @return a slice of this grid
     */
    public Grid<T> map(List<Integer> mapping) {
        return new Map<>(this, mapping);
    }

    /**
     * Resolves the element stored at the specified address
     *
     * @param address to look at
     * @return the element
     */
    protected abstract Element<T> resolve(List<Integer> address);





    protected static class Dimension<T> extends Grid<T> {
        private final List<Grid<T>> values;

        protected Dimension(int length, Grid<T> prototype) {
            this(Collections.nCopies(length, prototype));
        }

        protected Dimension(List<Grid<T>> prototypes) {
            this.values = new Vector<>(prototypes);

            for (int i = 0; i < prototypes.size(); i++)
                this.values.set(i, prototypes.get(i).clone());
        }

        public Dimension<T> clone() {
            return new Dimension<>(this.values);
        }

        public List<Integer> size() {
            List<Integer> size = new Stack<>();

            size.add(this.values.size());
            size.addAll(this.values.get(0).size());

            return Collections.unmodifiableList(size);
        }

        protected Element<T> resolve(List<Integer> address) {
            return this.values.get(address.get(0)).resolve(address.subList(1, address.size()));
        }
    }





    protected static class Element<T> extends Grid<T> {
        private T value;

        protected Element(T value) {
            this.value = value;
        }

        public Element<T> clone() {
            return new Element<>(this.value);
        }

        public List<Integer> size() {
            return Collections.unmodifiableList(Collections.emptyList());
        }

        synchronized T get() {
            return this.value;
        }

        synchronized void set(T value) {
            this.value = value;
        }

        protected Element<T> resolve(List<Integer> address) {
            return this;
        }
    }




    /**
     * @param <T> Type of objects within the iteration
     */
    public static class Iterator<T> implements java.util.Iterator<T> {
        private final Grid<T> grid;
        private List<Integer> next;
        private List<Integer> current;

        protected Iterator(Grid<T> grid) {
            this.grid = Objects.requireNonNull(grid, "Grid cannot be NULL");
            this.next = Collections.nCopies(this.grid.dimensions(), 0);
        }

        /**
         * @return true if there is a current object
         */
        public boolean hasCurrent() {
            return null != this.current;
        }

        /**
         * @return true if there is one more object
         */
        public boolean hasNext() {
            return null != this.next;
        }

        /**
         * @return the value stored at the current currentAddress
         */
        public T current() {
            return this.grid.get(this.current);
        }

        /**
         * @return the next object of this iteration
         */
        public T next() {
            if (!this.hasNext()) {
                this.current = null;

                throw new NoSuchElementException("There are no more elements in the iteration");
            } else {
                this.current = this.next;
                this.next = this.increment(this.current);

                return this.grid.get(this.current);
            }
        }

        /**
         * @return the current currentAddress
         */
        public List<Integer> currentAddress() {
            if (null == this.current)
                throw new IllegalStateException("Iteration is in illegal state");

            return Collections.unmodifiableList(this.current);
        }

        /**
         * @return the next currentAddress
         */
        public List<Integer> nextAddress() {
            this.next();

            return Collections.unmodifiableList(this.current);
        }

        private List<Integer> increment(List<Integer> current) {
            List<Integer> next = new Vector<>(current);

            for (int d = next.size() - 1; d >= 0; d--) {
                next.set(d, next.get(d) + 1);

                if (this.grid.has(next)) {
                    return next;
                } else {
                    for (int i = d; i < next.size(); i++)
                        next.set(i, 0);
                }
            }

            return null;
        }
    }





    protected static abstract class View<T> extends Grid<T> {
        private final Grid<T> parent;

        public View(Grid<T> parent) {
            this.parent = Objects.requireNonNull(parent, "Parent cannot be NULL");
        }

        public Grid<T> parent() {
            return this.parent;
        }
    }





    protected static class Slice<T> extends View<T> {
        private final List<Integer> offset;
        private final List<Integer> size;

        public Slice(Grid<T> parent, List<Integer> offset) {
            this(parent, offset, sum(parent.size(), scale(offset, -1)));
        }

        public Slice(Grid<T> parent, List<Integer> offset, List<Integer> size) {
            super(parent);

            if (!parent.has(offset)) {
                throw new IndexOutOfBoundsException("Offset is out of bounds");
            } else if (!parent.has(sum(offset, size, Collections.nCopies(size.size(), -1)))) {
                throw new IndexOutOfBoundsException("Size is out of bounds");
            }

            this.offset = offset;
            this.size = size;
        }

        public List<Integer> offset() {
            return this.offset;
        }

        public List<Integer> size() {
            return this.size;
        }

        public Grid<T> clone() {
            return new Slice<>(this.parent(), this.offset(), this.size);
        }

        protected Element<T> resolve(List<Integer> address) {
            if (!isWithinBounds(address, Collections.nCopies(address.size(), 0), this.size))
                throw new ArrayIndexOutOfBoundsException("Address is out of bounds");

            return this.parent().resolve(sum(address, this.offset));
        }
    }





    protected static class Map<T> extends View<T> {
        private final List<Integer> mapping;

        public Map(Grid<T> parent, List<Integer> mapping) {
            super(parent);

            List<Integer> axis = new Vector<>(parent.dimensions());
            for (int d = 0; d < parent.dimensions(); d++)
                axis.add(d);

            if (!axis.containsAll(mapping) || !mapping.containsAll(axis))
                throw new IllegalArgumentException("Mapping is not bijective");

            this.mapping = mapping;
        }

        public List<Integer> mapping() {
            return this.mapping;
        }

        public Map<T> clone() {
            return new Map<>(this.parent(), this.mapping());
        }

        public List<Integer> size() {
            List<Integer> size = this.parent().size();

            Vector<Integer> mapped = new Vector<>(this.parent().dimensions());
            mapped.setSize(this.parent().dimensions());

            for (int d = 0; d < size.size(); d++)
                mapped.set(d, size.get(this.mapping.get(d)));

            return mapped;
        }

        public Element<T> resolve(List<Integer> address) {
            Vector<Integer> unmapped = new Vector<>(this.parent().dimensions());
            unmapped.setSize(this.parent().dimensions());

            for (int d = 0; d < address.size(); d++)
                unmapped.set(this.mapping.get(d), address.get(d));

            return this.parent().resolve(unmapped);
        }
    }





    @SafeVarargs // checked: summands does not get modified in any way
    protected static List<Integer> sum(List<Integer>... summands) {
        if (summands.length < 1)
            throw new RuntimeException("Cannot sum zero vectors");

        List<Integer> sum = summands[0];
        for (int i = 1; i < summands.length; i++)
            sum = add(sum, summands[i]);

        return sum;
    }

    protected static List<Integer> add(List<Integer> a, List<Integer> b) {
        if (a.size() != b.size())
            throw new IllegalArgumentException("Length mismatch");

        List<Integer> sum = new Vector<>(a.size());
        for (int d = 0; d < a.size(); d++)
            sum.add(a.get(d) + b.get(d));

        return sum;
    }

    protected static List<Integer> scale(List<Integer> vector, int factor) {
        List<Integer> result = new Vector<>(vector.size());
        for (Integer i: vector)
            result.add(i * factor);

        return result;
    }

    protected static boolean isWithinBounds(List<Integer> x, List<Integer> lower, List<Integer> upper) {
        if ((x.size() != lower.size()) || (x.size() != upper.size()))
            throw new RuntimeException("Length mismatch");

        for (int d = 0; d < x.size(); d++)
            if ((x.get(d) < lower.get(d)) || (x.get(d) >= upper.get(d)))
                return false;

        return true;
    }
}
