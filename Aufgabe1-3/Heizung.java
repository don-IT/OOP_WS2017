import java.util.Random;

public class Heizung extends Konsument {
    private float typischerVerbrauch;

    /**NOTE:
     * @param typischerVerbrauch Typischer Verbrauch der Heizung in kWh
     */
    public Heizung(float typischerVerbrauch) {
        if (typischerVerbrauch < 0)
            throw new IllegalArgumentException("Typischer Verbrauch muss größer/gleich Null sein.");

        this.typischerVerbrauch = typischerVerbrauch;
    }


    /**NOTE:
     * @return Typischer Verbrauch der Heizung in kWh
     */
    // BAD: Starke Objektkopplung: typischerVerbrauch(getter) nach außen sichtbar
    // werden überhaupt nicht verwendet -> sollte gelöscht werden

    public float typischerVerbrauch() {
        return this.typischerVerbrauch;
    }

    /** NOTE:
     * @param wetter Aktuelles Wetter
     * @param zahlengenerator Zufallszahlengenerator
     * @return Wieviele kWh verbraucht werden
     */
    //Precondition: alle Klassenvariablen erfüllen die Invarianten der Klasse

    protected float konsumiere(Wetter wetter, Random zahlengenerator) {
        if (wetter.temperatur() > 20) {
            return this.typischerVerbrauch * 0f;
        } else if (wetter.temperatur() > 10) {
            return this.typischerVerbrauch * 0.5f;
        } else if (wetter.temperatur() > 0) {
            return this.typischerVerbrauch * 1f;
        } else {
            return this.typischerVerbrauch * 2f;
        }
    }
}
