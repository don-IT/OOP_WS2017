import java.util.*;

/**
 * Aufgabenverteilung:
 * - Omar: Design der Typhierarchie/Generics, alle Supplies und Connections, ein paar Tests
 * - Wolfi: Ensemble, Composition, ein paar Tests
 * - Diama: kontrolliert
 *
 *
 * Unserer Meinung nach kann die Datenstruktur aus der Aufgabenstellung
 *   Composition<Composition<WaterSupply, Hose>, Composition<PowerSupply, Cable>>
 * nicht korrekt/valide sein. Zur Erklärung hier Bezeichnungen:
 *   Let WasserNetzwerk = Composition<WaterSupply, Hose>
 *   Let StromNetzwerk = Composition<PowerSupply, Cable>
 *   Let X = Composition<WasserNetzwerk, StromNetzwerk>
 *
 * StromNetzwerk ist eine Sammlung von PowerSupplies verbunden durch Cables. Nehmen wir an, dass diese Sammlung
 * auch eine Verbindung zwischen PowerSupplies darstellt (*1), d.h. implements Connection<PowerSupply>. Dann müssen alle
 * Objekte, die in X gesammelt sind, durch StromNetzwerke (oder Untertypen) verbunden sein. X sammelt WasserNetzwerke,
 * Stromnetze (oder Untertypen) verbinden aber keine Wassernetzwerke, sondern PowerSupplies (siehe *1). Widerspruch!
 *
 * Anders ist die Sache, wenn StromNetzwerk weiterhin eine Verbindung darstellt, allerdings nicht beschränkt auf Power-
 * Supplies. StromNetzwerk ist einfach irgendeine Verbindung. Dann können natürlich WasserNetzwerke durch StromNetzwerke
 * verbunden sein. Genauso wie man zwei Wasserhähne mit einem Kabel zusammenbinden kann. Oder wie man einen Garten-
 * schlauch an zwei Steckdosen kleben kann. Dies ist aber nicht die Natur dieser Verbindunsobjekte und schwächt die
 * Definition von Connection ungemein (*2).
 *
 * Eine derartige Funktionalität (siehe *2) kann leicht erzielt werden, in dem die Typparameter der Composition von
 *   public class Composition<Type, ConnectionType extends Connection<Type>>
 * zu
 *   public class Composition<Type, ConnectionType extends Connection>
 * geändert werden. Dann könnte "Composition implements Connection<Type>" gesetzt werden.
 */
public class Test {
    public static void main(String[] args) {
        try {
            System.out.print("Testing Ensemble<String>... ");

            Ensemble<String> ensembleOfStrings = new Ensemble<>();
            String[] someStrings = { "one", "two", "three", "four", "five" };

            testEnsemble(ensembleOfStrings, someStrings, "zero");

            System.out.println("Pass");
        } catch (RuntimeException e) { System.out.println("Fail"); throw e; }



        try {
            System.out.print("Testing Composition<Integer, Negator>... ");

            Composition<Integer, Negator> compositionOfIntegers = new Composition<>();
            Integer[] someIntegers = { 1, 2, 3, 4, 5 };
            Negator[] someNegators = {
                    new Negator(7),
                    new Negator(13),
                    new Negator(17)
            };

            testEnsemble(compositionOfIntegers, someIntegers, 0);
            testComposition(compositionOfIntegers, someNegators);

            System.out.println("Pass");
        } catch (RuntimeException e) { System.out.println("Fail"); throw e; }



        try {
            System.out.print("Testing Composition<WaterSupply, Hose>... ");

            Composition<WaterSupply, Hose> compositionOfWaterSupplies = new Composition<>();
            WaterSupply[] someWaterSupplies = { new WaterSupply(), new WaterSupply(), new WaterSupply(), new WaterSupply() };
            Hose[] someHoses = {
                    new Hose(1f, someWaterSupplies[0], someWaterSupplies[1]),
                    new Hose(2f, someWaterSupplies[1], someWaterSupplies[2]),
                    new Hose(3f, someWaterSupplies[2], someWaterSupplies[3])
            };

            testEnsemble(compositionOfWaterSupplies, someWaterSupplies, new WaterSupply());
            testComposition(compositionOfWaterSupplies, someHoses);
            testSumOfDiameters(compositionOfWaterSupplies, someHoses);

            System.out.println("Pass");
        } catch (RuntimeException e) { System.out.println("Fail"); throw e; }



        try {
            System.out.print("Testing Composition<PowerSupply, Cable>... ");

            Composition<PowerSupply, Cable> compositionOfPowerSupplies = new Composition<>();
            PowerSupply[] somePowerSupplies = { new PowerSupply(), new PowerSupply(), new PowerSupply(), new PowerSupply() };
            Cable[] someCables = {
                    new Cable(7, somePowerSupplies[0], somePowerSupplies[1]),
                    new Cable(33, somePowerSupplies[1], somePowerSupplies[2]),
                    new Cable(12, somePowerSupplies[1], somePowerSupplies[2]), // double connection
                    new Cable(51, somePowerSupplies[2], somePowerSupplies[3])
            };

            testEnsemble(compositionOfPowerSupplies, somePowerSupplies, new PowerSupply());
            testComposition(compositionOfPowerSupplies, someCables);
            testSumOfStrands(compositionOfPowerSupplies, someCables);

            System.out.println("Pass");
        } catch (RuntimeException e) { System.out.println("Fail"); throw e; }



        // Weitere Tests
        testEnsemble2();
        testComposition2();
    }

    /**
     * Implementiert Testfall 3 aus der Angabe:
     * alle übergebenen Objekte werden als
     *   Ensemble<Supply>
     * ja sogar als
     *   Ensemble<?>
     * behandelt.
     */
    private static <Type> void testEnsemble(Ensemble<Type> ensemble, Type[] items, Type nonExisting) {
        if (ensemble.size() != 0) {
            throw new IllegalArgumentException("Ensemble to test is not empty");
        } else if (items.length < 2) {
            throw new IllegalArgumentException("Items to test Ensemble needs to be length 2 or more");
        } else if (items.length != Arrays.stream(items).distinct().count()) {
            throw new IllegalArgumentException("Items to test Ensemble with are not unique");
        } else if (Arrays.stream(items).anyMatch(value -> value == nonExisting)) {
            throw new IllegalArgumentException("Items to test Ensemble with contain the non-existing item provided");
        }

        // Add all items
        for (Type item : items)
            ensemble.add(item);

        // Expect size equal items.length
        if (items.length != ensemble.size())
            throw new RuntimeException("#0001");

        // Expect same order or items
        int i = 0;
        for (Iterator<Type> it = ensemble.iterator(); it.hasNext(); )
            if (it.next() != items[i++])
                throw new RuntimeException("#0002");

        // Remove first item by value
        ensemble.remove(items[0]);

        // Expect size equal items.length - 1
        if (items.length != ensemble.size() + 1)
            throw new RuntimeException("#0003");

        // Re-Add first item
        ensemble.add(items[0]);

        // Expect size equal items.length
        if (items.length != ensemble.size())
            throw new RuntimeException("#0004");

        // Remove first item with iterator
        Iterator<Type> it = ensemble.iterator();
        it.next();
        it.remove();

        // Expect size equal items.length - 1
        if (items.length != ensemble.size() + 1)
            throw new RuntimeException("#0005");

        // Add item twice
        ensemble.add(items[1]);
        ensemble.add(items[1]);

        // Expect size equal items.length, as duplicate items will be ignored
        if (items.length != ensemble.size())
            throw new RuntimeException("#0006");

        // Add NULL
        ensemble.add(null);

        // Expect size equal items.length, as NULL will be ignored
        if (items.length != ensemble.size())
            throw new RuntimeException("#0007");

        // Remove NULL
        ensemble.remove(null);

        // Remove non-existing
        ensemble.remove(nonExisting);

        // Expect size equal items.length, as NULL and non-existing will be ignored
        if (items.length != ensemble.size())
            throw new RuntimeException("#0008");

        // Clear
        ensemble.clear();

        // Expect size equal 0
        if (0 != ensemble.size())
            throw new RuntimeException("#0009");

        // Add all items again
        for (Type item : items)
            ensemble.add(item);

        // Remove items one by one, directly
        for (Iterator<Type> it2 = ensemble.iterator(); it2.hasNext(); )
            ensemble.remove(it2.next());

        // Expect size equal 0
        if (0 != ensemble.size())
            throw new RuntimeException("#0010");

        // Add all items again
        for (Type item : items)
            ensemble.add(item);

        // Remove items one by one, via iterator
        for (Iterator<Type> it3 = ensemble.iterator(); it3.hasNext(); ) {
            it3.next();
            it3.remove();
        }

        // Expect size equal 0
        if (0 != ensemble.size())
            throw new RuntimeException("#0011");
    }

    /**
     * Implementiert Testfall 2 aus der Angabe:
     * alle übergebenen Objekte werden als
     *   Composition<Supply, Connection<Supply>>
     * ja sogar als
     *   Composition<?, ? extends Type>
     * betrachtet.
     */
    private static <Type, ConnectionType extends Connection<Type>> void testComposition(Composition<Type, ConnectionType> composition, ConnectionType[] connections) {
        if (composition.size() != 0) {
            throw new IllegalArgumentException("Composition to test is not empty");
        } else if (connections.length < 2) {
            throw new IllegalArgumentException("Connections to test Composition needs to be length 2 or more");
        } else if (connections.length != Arrays.stream(connections).distinct().count()) {
            throw new IllegalArgumentException("Connections to test Compositions with are not unique");
        }

        // Add all connections, and count unique
        HashSet<Type> set = new HashSet<>();
        for (ConnectionType con : connections) {
            set.add(con.source());
            set.add(con.sink());

            composition.connect(con);
        }

        // Expect size equal to number of unique objects from connections
        if (composition.size() != set.size())
            throw new RuntimeException("#0101");

        // Expect number of connections equal to length of connections
        if (composition.numConnections() != connections.length)
            throw new RuntimeException("#0102");

        // Remove all connections
        composition.disconnectAll();

        // Expect size stay same
        if (composition.size() != set.size())
            throw new RuntimeException("#0103");

        // Add all connections again
        for (ConnectionType con : connections)
            composition.connect(con);

        // Remove objects one by one
        for (Iterator<Type> it = composition.iterator(); it.hasNext(); )
            composition.remove(it.next());

        // Expect size be zero
        if (composition.size() != 0)
            throw new RuntimeException("#0104");

        // Add all connections again
        for (ConnectionType con : connections)
            composition.connect(con);

        // Clear all objects
        composition.clear();

        // Expect size be zero
        if (composition.size() != 0)
            throw new RuntimeException("#0105");

    }

    private static void testSumOfDiameters(Composition<WaterSupply, Hose> composition, Hose[] hoses) {
        composition.clear();

        float expected = 0;
        for (Hose hose : hoses) {
            composition.connect(hose);
            expected += hose.diameter();
        }

        float actual = 0;
        for (Iterator<Hose> it = composition.connectionIter(); it.hasNext(); )
            actual += it.next().diameter();

        if (expected != actual)
            throw new RuntimeException("#1001");
    }

    private static void testSumOfStrands(Composition<PowerSupply, Cable> composition, Cable[] cables) {
        composition.clear();

        int expected = 0;
        for (Cable cable : cables) {
            composition.connect(cable);
            expected += cable.strands();
        }

        int actual = 0;
        for (Iterator<Cable> it = composition.connectionIter(); it.hasNext(); )
            actual += it.next().strands();

        if (expected != actual)
            throw new RuntimeException("#2001");
    }



    private static void testComposition2(){

        System.out.println("******************************+************+*******************************+************+*");
        System.out.println("TESTCASE 2");
        System.out.println("******************************+************+*******************************+************+*");

        System.out.println("Testing Composition<Integer, Negator>...");

        Composition<Integer, Negator> compositionOfIntegers = new Composition<>();



        Integer num1=2;
        Integer num2=4;
        Integer num3=6;
        Integer num4=8;


        compositionOfIntegers.add(num1);
        compositionOfIntegers.add(num2);
        compositionOfIntegers.add(num3);
        compositionOfIntegers.add(num4);




        System.out.println();
        System.out.println();

        System.out.println("******************************+************+*******************************+************+*");
        System.out.println("TESTCASE 3");
        System.out.println("******************************+************+*******************************+************+*");

        System.out.println("Testing Composition<WaterSupply, Hose>...");

        Composition<WaterSupply, Hose> compositionOfWaterSupplies = new Composition<>();

        WaterSupply teil1= new WaterSupply();
        WaterSupply teil2= new WaterSupply();
        WaterSupply teil3= new WaterSupply();

        compositionOfWaterSupplies.add(teil1);
        compositionOfWaterSupplies.add(teil2);
        compositionOfWaterSupplies.add(teil3);

        Hose connection= new Hose(2.6f,teil1,teil2);
        Hose connection5=new Hose(3.8f,teil3,teil1);

        compositionOfWaterSupplies.connect(connection);

        System.out.println();
        float sumDiameter;
        sumDiameter=connection.diameter()+connection5.diameter();
        System.out.println("Summe von diameter: "+sumDiameter);

        System.out.println("******************************+************+*******************************+************+*");
        System.out.println("TESTCASE 4");
        System.out.println("******************************+************+*******************************+************+*");

        System.out.println("Testing Composition<PowerSupply, Cable>...");

        Composition<PowerSupply, Cable> compositionOfPowerSupplies = new Composition<>();

        PowerSupply elektro1= new PowerSupply();
        PowerSupply elektro2= new PowerSupply();
        PowerSupply elektro3= new PowerSupply();
        PowerSupply elektro4= new PowerSupply();

        Cable connection3= new Cable(20,elektro3,elektro4);
        Cable connection2= new Cable(50,elektro1,elektro2);

        compositionOfPowerSupplies.connect(connection2);

        int sumLitzen;
        sumLitzen=connection2.strands()+connection3.strands();
        System.out.println("Summe von Litzen ist: "+ sumLitzen);

        System.out.println();


    }

    private static void testEnsemble2() {
        System.out.println("******************************+************+*******************************+************+*");
        System.out.println("TESTCASE 1");
        System.out.println("******************************+************+*******************************+************+*");

        String a1= "First";
        String a2= "Second";
        String a3= "Third";
        String a4= "Fourth";

        Ensemble<String> ensembleOfStrings = new Ensemble<String>();

        ensembleOfStrings.add(a1);
        ensembleOfStrings.add(a1);
        ensembleOfStrings.add(a2);
        ensembleOfStrings.add(a2);
        ensembleOfStrings.add(a3);
        ensembleOfStrings.add(a4);

        System.out.println("Nach ensembleOfString Ausfuhrung erwartet ist: First Second Third Fourth  ");
        System.out.println();
        System.out.print("Print ensembleOfString: ");

        for (Iterator<String> iterator = ensembleOfStrings.iterator(); iterator.hasNext();) {
            System.out.print(iterator.next()+" ");
        }
        System.out.println();
        System.out.println();

        ensembleOfStrings.remove(a1);

        System.out.println("Nach ensembleOfString Ausfuhrung erwartet ist: Second Third Fourth  ");
        System.out.println();
        System.out.print("Print ensembleOfString: ");

        for (Iterator<String> iterator = ensembleOfStrings.iterator(); iterator.hasNext();) {
            System.out.print(iterator.next()+" ");
        }
        System.out.println();
        System.out.println();

        System.out.println("Nach ensembleOfString Ausfuhrung erwartet ist: false ");
        System.out.println(ensembleOfStrings.contains(a1));
        System.out.println();

        System.out.println("Nach ensembleOfString Ausfuhrung erwartet ist: true ");
        System.out.println(ensembleOfStrings.contains(a2));
        System.out.println();

        System.out.println("Nach ensembleOfString Ausfuhrung erwartet ist: 3 ");
        System.out.println(ensembleOfStrings.size());
        System.out.println();

        System.out.println("Nach ensembleOfString Ausfuhrung erwartet ist:  ");
        ensembleOfStrings.clear();
        for (Iterator<String> iterator = ensembleOfStrings.iterator(); iterator.hasNext();) {
            System.out.print(iterator.next()+" ");
        }
        System.out.println();


    }
}
