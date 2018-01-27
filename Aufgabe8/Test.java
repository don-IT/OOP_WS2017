/**
 * Arbeitsteilung:
 * - Diana: Prototyp
 * - Wolfi: Grid, Storage, Permutations
 * - Omar: Diana beim Concurrency verstehen geholfen, kontrolliert
 */
public class Test {
    public static void main(String[] args) {
        standard(4, 4);
        slowSalespeople(2, 4);
        moreSmallCandles(2, 2);
    }

    private static Factory createFactory() {
        return new Factory(8, 3);
    }

    private static void standard(int numWorkmen, int numSalespeople) {
        Factory factory = createFactory();

        System.out.format("Fabrik mit %s-Lager, %d Arbeitern und %d Verkäufern: \n\n\n",
                factory.storage().size(), numWorkmen, numSalespeople);

        for (int i = 0; i < numWorkmen; i++)
            factory.employ(new Workman(factory));

        for (int i = 0; i < numSalespeople; i++)
            factory.employ(new Salesperson(factory));

        sleep(1000);
        factory.shutdown();
    }

    private static void slowSalespeople(int numWorkmen, int numSalespeople) {
        Factory factory = createFactory();

        System.out.format("Fabrik mit %s-Lager, %d schnellen Arbeitern und %d langsamen Verkäufern: \n\n\n",
                factory.storage().size(), numWorkmen, numSalespeople);

        for (int i = 0; i < numWorkmen; i++)
            factory.employ(new Workman(factory, 0.33, 0.5));

        for (int i = 0; i < numSalespeople; i++)
            factory.employ(new Salesperson(factory, 0.66, 0.5));

        sleep(1000);
        factory.shutdown();
    }

    private static void moreSmallCandles(int numWorkmen, int numSalespeople) {
        Factory factory = createFactory();

        System.out.format("Fabrik mit %s-Lager, %d Arbeitern (bevorzugen kl. K.) und %d Verkäufern (bevorzugen gr. K): \n\n\n",
                factory.storage().size(), numWorkmen, numSalespeople);

        for (int i = 0; i < numWorkmen; i++)
            factory.employ(new Workman(factory, 0.5, 0.33));

        for (int i = 0; i < numSalespeople; i++)
            factory.employ(new Salesperson(factory, 0.5, 0.66));

        sleep(1000);
        factory.shutdown();
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Interrupted
        }
    }
}
