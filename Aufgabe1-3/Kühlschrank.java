import java.util.Random;

public class Kühlschrank extends Konsument {
    private boolean türeOffen = false;
    private float typischerVerbrauch;

    /**NOTE:
     * @param typischerVerbrauch Wieviele kwH dieser Kühlschrank im Normalfall verbraucht
     */

    public Kühlschrank(float typischerVerbrauch) {
        if (typischerVerbrauch < 0)
            throw new IllegalArgumentException("Verbrauch muss größer/gleich Null sein.");

        this.typischerVerbrauch = typischerVerbrauch;
    }

    /**NOTE:
     * @return Wieviele kwH dieser Kühlschrank im Normalfall verbraucht
     */
    // BAD: Starke Objektkopplung: typischerVerbrauch(getter) nach außen sichtbar
    // werden überhaupt nicht verwendet -> sollte gelöscht werden
    public float typischerVerbrauch() {
        return this.typischerVerbrauch;
    }

    /**NOTE:
     * @param wetter Aktuelles Wetter
     * @param zahlengenerator Zufallszahlengenerator
     * @return Wieviele kWh verbraucht werden
     */
    protected float konsumiere(Wetter wetter, Random zahlengenerator) {
        if (this.türeOffen) {
            return this.typischerVerbrauch * 7;
        } else {
            return this.typischerVerbrauch;
        }
    }

    // BAD: Starke Objektkopplung: türeOffen,schließeTüre,türeOffen(setter) nach außen sichtbar
    // werden überhaupt nicht verwendet -> sollte gelöscht werden

    public boolean türeOffen() {
        return this.türeOffen;
    }

    public void öffneTüre() {
        this.türeOffen = true;
    }

    public void schließeTüre() {
        this.türeOffen = false;
    }
}
