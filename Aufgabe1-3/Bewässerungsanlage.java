import java.util.Random;

public class Bewässerungsanlage extends Konsument {
    private float typischerVerbrauch;

    /**NOTE:
     * @param typischerVerbrauch Typischer Verbrauch der Anlage in Litern
     */
    public Bewässerungsanlage(float typischerVerbrauch) {
        if (typischerVerbrauch < 0)
            throw new IllegalArgumentException("Typischer Verbrauch muss größer/gleich Null sein.");

        this.typischerVerbrauch = typischerVerbrauch;
    }

    /**NOTE:
     * @return Typischer Verbrauch der Anlage in Litern
     */
    // BAD: Starke Objektkopplung: typischerVerbrauch(getter) nach außen sichtbar
    // werden überhaupt nicht verwendet -> sollte gelöscht werden

    public float typischerVerbrauch() {
        return this.typischerVerbrauch;
    }

    /**NOTE:
     * @param wetter Aktuelles Wetter
     * @param zahlengenerator Zufallszahlengenerator
     * @return Wieviele Liter verbraucht werden
     */


    protected float konsumiere(Wetter wetter, Random zahlengenerator) {
        if ((wetter.niederschlag() > 0) || (wetter.temperatur() < 0)) {
            return 0;
        } else {
            // [0..30] * [0..15] = [0..450]
            return (wetter.temperatur() * wetter.sonnenschein() / 450) * this.typischerVerbrauch;
        }
    }
}
