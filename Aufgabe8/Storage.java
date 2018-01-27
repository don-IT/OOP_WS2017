import java.util.*;
import java.util.concurrent.locks.*;
import java.util.function.Predicate;

public class Storage<T extends Storage.Storable> implements Iterable<Storage.Shelf<T>> {
    private final Grid<Shelf<T>> shelves;
    private final Collection<List<Integer>> mappings;
    private final ReadWriteLock lock;

    public Storage(List<Integer> size) {
        this.shelves = Grid.create(size);

        for (Grid.Iterator<Shelf<T>> it = this.shelves.iterator(); it.hasNext(); ) {
            List<Integer> address = it.nextAddress();
            this.shelves.put(new Shelf<>(this, address), address);
        }

        Vector<Integer> axis = new Vector<>(size.size());
        for (int d = 0; d < size.size(); d++)
            axis.add(d);

        this.mappings = new Stack<>();
        for (Iterator<List<Integer>> it = Permutations.create(axis); it.hasNext(); )
            this.mappings.add(it.next());

        this.lock = new ReentrantReadWriteLock();
    }

    public List<Integer> size() {
        return this.shelves.size();
    }

    public Shelf<T> get(List<Integer> address) {
        return this.shelves.get(address);
    }

    public Iterator<Shelf<T>> iterator() {
        return this.shelves.iterator();
    }

    protected Lock lockForWrite() {
        Lock lock = this.lock.writeLock();

        lock.lock();
        return lock;
    }

    protected Lock lockForRead() {
        Lock lock = this.lock.readLock();

        lock.lock();
        return lock;
    }

    protected List<Shelf<T>> fit(List<Integer> address, List<Integer> size) {
        // Offset the view
        Grid<Shelf<T>> slice = this.shelves.offset(address);

        // For all possible rotations/mappings
        loop: for (List<Integer> mapping: this.mappings) {
            // Rotate/map the view
            Grid<Shelf<T>> rotated = slice.map(mapping);

            try {
                // Limit the view -> might throw IndexOutOfBoundsException
                Grid<Shelf<T>> cropped = rotated.limit(size);

                // Collect shelves
                List<Shelf<T>> shelves = new Stack<>();
                for (Shelf<T> shelf: cropped) {
                    if (!shelf.isEmpty()) // this is not yet synchronized, just a pre-test
                        continue loop;

                    shelves.add(shelf);
                }

                return shelves;
            } catch (IndexOutOfBoundsException e) {
                // content doesnt fit with this rotation
            }
        }

        return null;
    }





    public static interface Storable {
        public List<Integer> size();
    }




    public static class Exception extends RuntimeException {
        public Exception() {
            super();
        }

        public Exception(String message) {
            super(message);
        }
    }





    public static class Shelf<T extends Storable> {
        private final Storage<T> storage;
        private final List<Integer> address;

        private Box<T> box;

        protected Shelf(Storage<T> storage, List<Integer> address) {
            this.storage = storage;
            this.address = address;
        }

        public List<Integer> address() {
            return Collections.unmodifiableList(this.address);
        }

        public boolean isEmpty() {
            Lock lock = this.storage.lockForRead();

            try {
                return null == this.box;

            } finally {
                lock.unlock();
            }
        }

        public boolean test(Predicate<T> predicate) {
            Lock lock = this.storage.lockForRead();

            try {
                if (!this.isEmpty()) {
                    return predicate.test(this.box.content);
                } else {
                    return false;
                }
            } finally {
                lock.unlock();
            }
        }

        public T peek() {
            Lock lock = this.storage.lockForRead();

            try {
                if (!this.isEmpty()) {
                    return this.box.content;
                } else {
                    return null;
                }
            } finally {
                lock.unlock();
            }
        }

        public T take() {
            Lock lock = this.storage.lockForWrite();

            try {
                if (this.isEmpty())
                    throw new Exception("This shelf is not occupied");

                return this.box.take().content;
            } finally {
                lock.unlock();
            }
        }

        public void put(T content) {
            Lock lock = this.storage.lockForWrite();

            try {
                if (!this.isEmpty())
                    throw new Exception("This shelf is already occupied");

                List<Shelf<T>> shelves = this.storage.fit(this.address, content.size());

                if (null == shelves)
                    throw new Exception("Content does not fit this shelf (and neighbors)");

                Box<T> box = new Box<>(content);
                box.put(shelves);
            } finally {
                lock.unlock();
            }
        }
    }





    protected static class Box<T extends Storable> {
        private final T content;

        private List<Shelf<T>> shelves;

        protected Box(T content) {
            this.content = Objects.requireNonNull(content, "Content cannot be NULL");
        }

        protected Box<T> put(List<Shelf<T>> shelves) {
            Objects.requireNonNull(shelves, "Shelves cannot be NULL");

            if (null != this.shelves)
                throw new RuntimeException("This box is already on a shelf");

            for (Shelf<T> shelf: shelves)
                shelf.box = this;

            this.shelves = shelves;
            return this;
        }

        protected Box<T> take() {
            if (null == this.shelves)
                throw new RuntimeException("This box is not yet put on a shelf");

            List<Shelf<T>> shelves = this.shelves;

            for (Shelf<T> shelf: shelves)
                shelf.box = null;

            this.shelves = null;
            return this;
        }
    }
}
