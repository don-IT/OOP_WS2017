import java.util.Random;

public class Toilette extends Konsument {
    private int personen;
    private float verbrauchProSpülung;

    /**NOTE:
     * @param personen Wieviele Personen diese Toilette verwenden
     * @param verbrauchProSpülung Verbrauch pro Spülung in Litern
     */
    public Toilette(int personen, float verbrauchProSpülung) {
        if (personen < 0) {
            throw new IllegalArgumentException("Personen muss größer/gleich Null sein.");
        } else if (verbrauchProSpülung < 0) {
            throw new IllegalArgumentException("Verbrauch/Spülung muss größer/gleich Null sein.");
        }

        this.personen = personen;
        this.verbrauchProSpülung = verbrauchProSpülung;
    }

    /**NOTE:
     * @return Wieviele Personen diese Toilette verwenden
     */
    public int personen() {
        return this.personen;
    }

    /**NOTE:
     * @return Verbrauch pro Spülung in Litern
     */
    public float verbrauchProSpülung() {
        return this.verbrauchProSpülung;
    }

    /**NOTE:
     * @param wetter Aktuelles Wetter
     * @param zahlengenerator Zufallszahlengenerator
     * @return Wieviele Liter verbraucht werden
     */

    protected float konsumiere(Wetter wetter, Random zahlengenerator) {
        int verbrauch = 0;

        for (int i = 0; i < this.personen; i++)
            verbrauch += zahlengenerator.nextInt(5) * this.verbrauchProSpülung;

        return verbrauch;
    }
}
