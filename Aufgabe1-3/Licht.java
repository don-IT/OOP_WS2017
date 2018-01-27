import java.util.Random;

public class Licht extends Konsument {
    private float maximalerVerbrauch;

    /**NOTE:
     * @param maximalerVerbrauch Maximaler Verbrauch des Lichts in kWh
     */
    public Licht(float maximalerVerbrauch) {
        if (maximalerVerbrauch < 0)
            throw new IllegalArgumentException("Maximaler Verbrauch muss größer/gleich Null sein.");

        this.maximalerVerbrauch = maximalerVerbrauch;
    }

    /**NOTE:
     * @return Maximaler Verbrauch des Lichts in kWh
     */
    // BAD: Starke Objektkopplung: maximalVerbrauch(getter) nach außen sichtbar
    // werden überhaupt nicht verwendet -> sollte gelöscht werden
    public float maximalerVerbrauch() {
        return this.maximalerVerbrauch;
    }

    /**NOTE:
     * @param wetter Aktuelles Wetter
     * @param zahlengenerator Zufallszahlengenerator
     * @return Wieviele kWh verbraucht werden
     */

    protected float konsumiere(Wetter wetter, Random zahlengenerator) {
        return (zahlengenerator.nextFloat() + 0.5f) * this.maximalerVerbrauch;
    }
}
