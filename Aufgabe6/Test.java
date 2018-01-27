import java.util.Iterator;
import java.util.Random;

/**
 * Aufgabenverteilung:
 * - Diana: Typhierarchie, Aggregator-Methoden, Set, einige Tests
 * - Wolfi: Map, einige Tests
 * - Omar: Kontrolliert
 */
public class Test {
    public static void main(String[] args) {
        testSet();
        testMap();

        System.out.println("\n");

        Random random = new Random(0);

        // Create a house with some rooms with some lamps
        Room kitchen = createRandomRoom("Küche", 2, random); // lamp ids 1, 2
        Room livingroom = createRandomRoom("Wohnzimmer", 4, random); // lamp ids 3, 4, 5, 6
        Room bedroom = createRandomRoom("Schlafzimmer", 3, random); // lamp ids 7, 8, 9
        Room attic = createRandomRoom("Dachboden", 3, random); // lamp ids 10, 11, 12

        House myHouse = new House("Mein Haus");
        myHouse.addRoom(kitchen);
        myHouse.addRoom(livingroom);
        myHouse.addRoom(bedroom);
        myHouse.addRoom(attic);

        // Create another house
        House yourHouse = new House("Dein Haus");

        // Move a room from myHouse to yourHouse
        myHouse.removeRoom(attic.getName());
        yourHouse.addRoom(attic);

        // Move a lamp from livingroom to kitchen
        Lamp crazyLamp = myHouse.getRoom(livingroom.getName()).getLamp(6);
        myHouse.getRoom(livingroom.getName()).removeLamp(crazyLamp.getId());
        myHouse.getRoom(kitchen.getName()).addLamp(crazyLamp);


        // Simulate some life
        for (int numLamps = 12, i = 0; i < 100000; i++) {
            int lampId = 1 + random.nextInt(numLamps);

            for (Iterator rooms = myHouse.rooms(); rooms.hasNext(); ) {
                Room room = (Room) rooms.next();
                Lamp lamp = room.getLamp(lampId);

                if (lamp != null) {
                    lamp.changePeriod(lamp.getPeriod() + 1);

                    if (random.nextFloat() < 0.05) {
                        // Change Leuchtmittel
                        lamp.changeLeuchtmittel(createRandomLeuchtmittel(random));
                    }
                }
            }
        }

        // Print overview
        System.out.println(myHouse.toString("\n\t") + "\n");

        // Print stats
        for (Iterator rooms = myHouse.rooms(); rooms.hasNext(); )
            System.out.println(((Room) rooms.next()).getStats("\n") + "\n");

        System.out.println(String.format("Σ Verbrauch aller Lampen in %s: %.2f kWh", myHouse, myHouse.getTotalVerbrauchOfLamps()));
    }

    private static void testSet() {
        System.out.print("Testing Set... ");

        try {
            Set set = new Set();

            // Expect list to be empty
            compareValues(set, new String[] {});

            // Add some values
            set.add("one");
            set.add("two");
            set.add("three");

            // Expect contents of list to be correct size and order
            compareValues(set, "one-two-three".split("-"));

            // Add duplicate
            set.add("three");

            // Expect list to still be size 3
            compareValues(set, "one-two-three".split("-"));

            // Remove last value
            set.remove("three");

            // Expect contents of list to be correct size and order
            compareValues(set, "one-two".split("-"));

            // Re-addLamp "three", removeLamp "two" from the middle
            set.add("three");
            set.remove("two");

            // Expect contents of list to be correct size and order
            compareValues(set, "one-three".split("-"));

            // Re-addLamp "two" (to end), removeLamp "one" from head
            set.add("two"); // order is now one-three-two
            set.remove("one");

            // Expect contents of list to be correct size and order
            compareValues(set, "three-two".split("-"));

            // Clear all
            set.clear();

            // Expect list to be empty
            compareValues(set, new String[] {});

            System.out.println("Pass");
        } catch (Exception e) {
            System.out.println("Fail");
            throw e;
        }
    }

    private static void testMap() {
        System.out.print("Testing Map... ");

        try {
            Map map = new Map();

            // No need to test key-related things, as they were already tested with testSet()
            // That's why we only test storage-related things (object.hashCode, binaryTree) here

            map.set(0, "zero");
            if (map.get(0) != "zero") throw new RuntimeException();

            map.set(1, "one");
            if (map.get(1) != "one") throw new RuntimeException();

            map.set(-1, "negative one");
            if (map.get(-1) != "negative one") throw new RuntimeException();

            if (map.size() != 3) throw new RuntimeException();

            System.out.println("Pass");
        } catch (Exception e) {
            System.out.println("Fail");
            throw e;
        }
    }

    private static void compareValues(Set set, String[] values) {
        if (set.size() != values.length)
            throw new RuntimeException();

        int i = 0;
        for (Iterator it = set.iterator(); it.hasNext(); )
            if (!values[i++].equals(it.next()))
                throw new RuntimeException();
    }

    private static Leuchtmittel createRandomLeuchtmittel(Random random) {
        if (random.nextBoolean()) {
            return new LightBulb(100 + random.nextInt(150), 10 + random.nextInt(90));
        } else {
            return new LED(10f + 90f * random.nextFloat(), 2 + random.nextInt(48));
        }
    }

    private static Lamp createRandomLamp(Random random) {
        if (random.nextBoolean()) {
            float pickColor = random.nextFloat();
            SimpleLamp.Color color;

            if (pickColor < 0.2f) {
                color = SimpleLamp.Color.BLUE; // probability 0.2
            } else if (pickColor < 0.5f) {
                color = SimpleLamp.Color.RED; // probability 0.3
            } else {
                color = SimpleLamp.Color.WHITE; // probability: 0.5
            }

            return new SimpleLamp(createRandomLeuchtmittel(random), color);
        } else {
            return new DimmedLamp(createRandomLeuchtmittel(random), random.nextFloat());
        }
    }

    private static Room createRandomRoom(String name, int numLamps, Random random) {
        Room room = new Room(name);

        for (int i = 0; i < numLamps; i++)
            room.addLamp(createRandomLamp(random));

        return room;
    }

    private static Room createRandomRoom(String name, Random random) {
        return createRandomRoom(name, random.nextInt(4), random);
    }

    private static Room createRandomRoom(Random random) {
        return createRandomRoom(String.format("Room #%d", random.nextInt()), random);
    }

    private static House createRandomHouse(String name, int numRooms, Random random) {
        House house = new House(name);

        for (int i = 0; i < numRooms; i++)
            house.addRoom(createRandomRoom(random));

        return house;
    }

    private static House createRandomHouse(String name, Random random) {
        return createRandomHouse(name, random.nextInt(4), random);
    }

    private static House createRandomHouse(Random random) {
        return createRandomHouse(String.format("House #%d", random.nextInt()), random);
    }
}
