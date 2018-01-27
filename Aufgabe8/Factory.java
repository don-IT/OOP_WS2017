import java.util.*;
import java.util.concurrent.locks.*;

public class Factory extends ThreadGroup {
    private final Storage<Candle> storage;
    private final Set<Employee> employees;

    public Factory(int storageWidth, int storageHeight) {
        super("Factory");

        this.storage = new Storage<>(Arrays.asList(storageWidth, storageHeight));
        this.employees = new HashSet<>();
    }

    public Storage<Candle> storage() {
        return this.storage;
    }

    public Set<Employee> employees() {
        return Collections.unmodifiableSet(this.employees);
    }

    public void employ(Employee employee) {
        this.employees.add(employee);

        (new Thread(this, employee, employee.toString())).start();
    }

    public void fire(Employee employee) {
        if (this.employees.remove(employee))
            employee.shutdown();
    }

    public void shutdown() {
        StringBuilder out = new StringBuilder();

        out.append("Die Fabrik schließt...\n\n");

        for (Employee employee: this.employees) {
            employee.shutdown();

            out.append(String.format("%s: %s", employee, employee.status()));
            out.append('\n');
        }

        this.interrupt();

        System.out.println(out.toString());

        this.plotStorage();
    }

    protected void finalize() {
        this.plotStorage();
    }

    protected void plotStorage() {
        Lock lock = this.storage.lockForRead();

        try {
            List<Integer> size = this.storage.size();
            StringBuilder out = new StringBuilder();

            for (int y = 0; y < size.get(1); y++) {
                for (int x = 0; x < size.get(0); x++) {
                    Candle candle = this.storage.get(Arrays.asList(x, y)).peek();

                    if (null != candle) {
                        out.append((candle instanceof Candle.Large ? '#' : '+'));
                    } else {
                        out.append('o');
                    }
                }

                out.append('\n');
            }

            System.out.println(out.toString());
        } finally {
            lock.unlock();
        }
    }
}
