import java.util.*;

public class Salesperson extends Employee {
    private int numSoldCandlesSmall = 0;
    private int numSoldCandlesLarge = 0;

    public Salesperson(Factory factory, double idleTimeout, double preferenceForLarge) {
        super(factory, idleTimeout, preferenceForLarge);
    }

    public Salesperson(Factory factory) {
        super(factory);
    }

    protected synchronized void tick() {
        Class<? extends Candle> type = this.sellCandle();

        while (this.isAlive() && (null == this.findCandle(type))) {
            try {
                this.wait(10);
            } catch (InterruptedException e) {
                return; // Thread.terminate() was called
            }
        }

        if (this.isAlive())
            this.factory.plotStorage();
    }

    public List<String> status() {
        List<String> status = super.status();

        status.add(String.format("%d gr. K", this.numSoldCandlesLarge));
        status.add(String.format("%d kl. K", this.numSoldCandlesSmall));

        return status;
    }

    protected Class<? extends Candle> sellCandle() {
        this.idle();

        if (Math.random() < this.preferenceForLarge()) {
            this.numSoldCandlesLarge++;
            return Candle.Large.class;
        } else {
            this.numSoldCandlesSmall++;
            return Candle.Small.class;
        }
    }

    protected Candle findCandle(Class<? extends Candle> type) {
        for (Storage.Shelf<Candle> shelf: this.factory.storage()) {
            if (type.isInstance(shelf.peek())) {
                try {
                    return shelf.take();
                } catch (Storage.Exception e) {
                    // Was taken in the meantime, continue...
                }
            }
        }

        return null;
    }
}
