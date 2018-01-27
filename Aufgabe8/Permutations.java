import java.util.*;

public class Permutations<T> implements Iterator<List<T>>, Iterable<List<T>> {
    private final List<T> source;
    private final Iterator<List<T>> inner;

    private List<T> currentInner;
    private int position;

    public static <T> Iterator<List<T>> create(List<T> source) {
        Objects.requireNonNull(source, "Source cannot be NULL");

        if (source.size() < 2) {
            return Collections.singletonList(source).iterator();
        } else {
            return new Permutations<>(source);
        }
    }

    protected Permutations(List<T> source) {
        this.source = source;
        this.position = 0;

        this.inner = create(source.subList(1, source.size()));
        this.currentInner = this.inner.next();
    }

    public Iterator<List<T>> iterator() {
        return this;
    }

    public boolean hasNext() {
        return (this.position < this.source.size()) || this.inner.hasNext();
    }

    public List<T> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("The iteration has no more elements");
        } else if (this.position == this.source.size()) {
            this.position = 0;
            this.currentInner = this.inner.next();

            return this.next();
        } else {
            List<T> current = new Vector<>(this.currentInner);

            current.add(this.position++, this.source.get(0));
            return current;
        }
    }

}
