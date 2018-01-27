import java.util.*;

public class Workman extends Employee {
    private int numProducedCandlesSmall = 0;
    private int numProducedCandlesLarge = 0;

    public Workman(Factory factory, double idleTimeout, double preferenceForLarge) {
        super(factory, idleTimeout, preferenceForLarge);
    }

    public Workman(Factory factory) {
        super(factory);
    }

    protected synchronized void tick() {
        Candle candle = this.produceCandle();

        while (this.isAlive() && !this.storeCandle(candle)) {
            try {
                this.wait(10);
            } catch (InterruptedException e) {
                return; // Thread.terminate() was called
            }
        }
    }

    public List<String> status() {
        List<String> status = super.status();

        status.add(String.format("%d gr. K", this.numProducedCandlesLarge));
        status.add(String.format("%d kl. K", this.numProducedCandlesSmall));

        return status;
    }

    protected Candle produceCandle() {
        this.idle();

        if (Math.random() < this.preferenceForLarge()) {
            this.numProducedCandlesLarge++;
            return new Candle.Large();
        } else {
            this.numProducedCandlesSmall++;
            return new Candle.Small();
        }
    }

    protected boolean storeCandle(Candle candle) {
        for (Storage.Shelf<Candle> shelf: this.factory.storage()) {
            try {
                shelf.put(candle);
                return true;
            } catch (Storage.Exception e) {
                // No fitting space
            }
        }

        return false;
    }
}
