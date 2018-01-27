import java.util.*;

public abstract class Employee implements Runnable {
    public static final int MIN_IDLE = 5;
    public static final int MAX_IDLE = 50;

    protected final Factory factory;

    private boolean alive = false;
    private boolean idle = false;

    private double idleTimeoutFactor;
    private double preferenceForLarge;

    public Employee(Factory factory) {
        this(factory, 0.5, 0.5);
    }

    public Employee(Factory factory, double idleTimeoutFactor, double preferenceForLarge) {
        this.factory = Objects.requireNonNull(factory, "Factory cannot be NULL");
        this.idleTimeoutFactor(idleTimeoutFactor);
        this.preferenceForLarge(preferenceForLarge);
    }

    public void run() {
        try {
            for (this.alive = true; this.isAlive(); )
                this.tick();

        } finally {
            this.alive = false;
        }
    }

    protected abstract void tick();

    public void shutdown() {
        this.alive = false;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public List<String> status() {
        Stack<String> status = new Stack<>();

        status.add(this.isIdle() ? "arbeitet (sleep)" : "wartet vorm Lager (wait)");
        status.add(String.format("sleep(%d)", this.idleTimeout()));
        status.add(String.format("P(gr. K.): %.02f", this.preferenceForLarge()));

        return status;
    }

    public boolean isIdle() {
        return this.idle;
    }

    protected boolean idle() {
        try {
            this.idle = true;

            Thread.sleep(this.idleTimeout());

            return false;
        } catch (InterruptedException e) {
            return true;

        } finally {
            this.idle = false;
        }
    }

    public double idleTimeoutFactor() {
        return this.idleTimeoutFactor;
    }

    public double idleTimeoutFactor(double value) {
        if ((value < 0) || (value > 1))
            throw new RuntimeException("Idle timeout needs to be within 0 and 1 (incl)");

        return this.idleTimeoutFactor = value;
    }

    protected int idleTimeout() {
        return MIN_IDLE + (int) Math.round(this.idleTimeoutFactor * (MAX_IDLE - MIN_IDLE));
    }

    public double preferenceForLarge() {
        return this.preferenceForLarge;
    }

    public double preferenceForLarge(double value) {
        if ((value < 0) || (value >= 1))
            throw new IllegalArgumentException("Preference for large needs to be within 0 (incl) and 1 (excl)");

        return this.preferenceForLarge = value;
    }
}
