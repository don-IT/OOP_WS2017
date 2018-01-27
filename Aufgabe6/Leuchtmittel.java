public abstract class Leuchtmittel {
    private int ratedCapacity; // ratedCapacity Nennleistung in Watt (ganze Zahl)

    /**
     * @param ratedCapacity Durchschnittliche Nennleistung in Watt
     */
    public Leuchtmittel(int ratedCapacity) {
        if (ratedCapacity < 0)
            throw new IllegalArgumentException("rated Capacity has to be >= 0");

        this.ratedCapacity = ratedCapacity;
    }

    /**
     * @return Durchschnittliche Nennleistung in Watt
     */
    public int getRatedCapacity(){
        return this.ratedCapacity;
    }

    /**
     * @return Representation of this
     */
    public String toString() {
        return String.format("%s", this.getClass().getSimpleName());
    }

    /**
     * @param indent What to put in front of each line
     * @return Detailled Representation of this
     */
    public String toString(String indent) {
        String out = String.format("%s mit folgenden Werten:", this.toString());

        out += indent + String.format("Nennleistung: %d W", this.getRatedCapacity());

        return out;
    }
}
