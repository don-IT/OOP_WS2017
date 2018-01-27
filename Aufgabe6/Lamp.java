public abstract class Lamp {
    private static int  IDcounter = 0; // every lamp different id, calling constructor each time another number

    private final int id;               //can not be changed anymore
    private Leuchtmittel leuchtmittel; // what Leuchtmittel has it
    private int period;                  //how long was it on

    /**
     * @param leuchtmittel What Leuchtmittel is there in the lamp
     * @param period How time how long was it on
     */
    public Lamp(Leuchtmittel leuchtmittel, int period) {
        this.id = ++IDcounter;

        this.changeLeuchtmittel(leuchtmittel);
        this.changePeriod(period);
    }

    /**
     * @param leuchtmittel What Leuchtmittel is there in the lamp
     */
    public Lamp(Leuchtmittel leuchtmittel) {
        this(leuchtmittel, 0); // Call public Lamp(Leuchtmittel leuchtmittel, int period) constructor ^^
    }

    /**
     * @return Unique ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return How time how long was it on
     */
    public int getPeriod() {
        return this.period;
    }

    /**
     * @return What Leuchtmittel is there in the lamp
     */
    public Leuchtmittel getLeuchtmittel(){
        return this.leuchtmittel;
    }

    /**
     * @return Verbrauch in kWh
     */
    public float getVerbrauch() {
        return this.getPeriod() * this.getLeuchtmittel().getRatedCapacity() * 0.001f;
    }

    /**
     * Change how long was it on
     *
     * @param period Neue Einschaltdauer in Stunden
     */
    public void changePeriod(int period) {
        if (period >= 0) {
            this.period = period;
        } else {
            throw new IllegalArgumentException("Please, insert number >= 0");
        }
    }

    /**
     * Ändern des Leuchtmittels einer Lampe, wobei die Informationen über die
     * Einschaltdauer des vorhergehenden Leuchtmittels verloren gehen.
     *
     * @param newLeuchtMittel Neues Leuchtmittel
     *
     * @post Setzt die Einschaltdauer (period) der Lampe zurück
     */
    public void changeLeuchtmittel(Leuchtmittel newLeuchtMittel) {
        if (newLeuchtMittel == null)
            throw new IllegalArgumentException("Leuchtmittel cannot be NULL");

        this.leuchtmittel = newLeuchtMittel;
        this.period = 0;
    }

    /**
     * @return Representation of this
     */
    public String toString() {
        return String.format("%s #%d", this.getClass().getSimpleName(), this.getId());
    }

    /**
     * @param indent What to put in front of each line
     * @return Detailled Representation of this
     */
    public String toString(String indent) {
        String out = String.format("%s mit folgenden Werten:", this.toString());

        out += indent + String.format("Leuchtmittel: %s", this.getLeuchtmittel().toString(indent + "\t"));
        out += indent + String.format("Einschaltdauer: %d h", this.getPeriod());

        return out;
    }
}
