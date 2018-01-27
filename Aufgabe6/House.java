import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @inv All elements of this.rooms are always of type Room or subclasses
 */
public class House {
    private final String name;
    private final Map rooms = new Map();

    /**
     * @pre name needs to be unique!
     *
     * @param name Unique name of this house
     */
    public House(String name) {
        if (null == name)
            throw new IllegalArgumentException("Please enter a name");

        this.name = name;
    }

    /**
     * @return Unique name of this house
     */
    public String getName() {
        return this.name;
    }

    /**
     * Adds a room to this house
     *
     * @param room Room to add
     */
    public void addRoom(Room room){
        if (room == null)
            throw new IllegalArgumentException("Room cannot be NULL");

        this.rooms.set(room.getName(), room);
    }

    /**
     * Removes a room from this house
     * @param name Name of a room to remove
     */
    public void removeRoom(String name) {
        this.rooms.delete(name);
    }

    /**
     * @param name Name of a room
     * @return The room, if and only if it was within this house
     */
    public Room getRoom(String name) {
        return (Room) this.rooms.get(name);
    }

    /**
     * @return Iterator over all rooms within this house
     */
    public Iterator rooms() {
        return this.rooms.values();
    }

    /**
     * @return Number of rooms within this house
     */
    public int numRooms() {
        return this.rooms.size();
    }

    /**
     * @return Gesamtverbrauch aller Lampen in allen Räumen, in Kilowattstunden
     */
    public float getTotalVerbrauchOfLamps() {
        float total = 0;
        int num = 0;

        for (Iterator rooms = this.rooms(); rooms.hasNext(); ) {
            Room room = (Room) rooms.next();

            total += room.getTotalVerbrauchOfLamps();
            num += 1;
        }

        return (num == 0) ? -1 : total;
    }

    /**
     * @return Representation of this
     */
    public String toString() {
        return String.format("%s \"%s\"", this.getClass().getSimpleName(), this.getName());
    }

    /**
     * @param indent What to put in front of each line
     * @return Detailled representation of this
     */
    public String toString(String indent) {
        String out = String.format("%s mit folgenden %d Räumen:", this.toString(), this.numRooms());

        for (Iterator it = this.rooms.values(); it.hasNext(); )
            out += indent + ((Room) it.next()).toString(indent + "\t");

        return out;
    }
}
