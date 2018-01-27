import java.util.Random;

public class Speicher implements Simulierbar {
    private float verfügbar = 0;
    private float kapazität;


    // Precondition: kapazität Größe des Speichers muss >=0 sein
    public Speicher(float kapazität) {
        if (kapazität < 0)
            throw new IllegalArgumentException("Kapazität muss größer/gleich Null sein.");

        this.kapazität = kapazität;
    }

    /**
     * @param wetter Aktuelles Wetter
     * @param zahlengenerator Zufallszahlengenerator
     * @return Fehlstand (neg: es musste nachgeholt werden, pos: es konnte abgegeben werden)
     */
    //GOOD: Objektkopplung schwach und Klassenzusammenhalt stark (Methodenaufrufe nur innerhalb von Speicher, keine
    //Methoden von anderen Klassen werden aufgerufen, Methoden arbeiten groesstenteils mit Variablen innerhalb von Speicher)
    public float simuliere(Wetter wetter, Random zahlengenerator) {
        if (this.verfügbar > this.kapazität) {
            //NOTE: Speicher ist übervoll, es muss etwas ins öffentliche Netz abgegeben werden
            float fehlstand = this.verfügbar - this.kapazität; // positiv!

            this.verfügbar = this.kapazität;
            return fehlstand;
        } else if (this.verfügbar < 0) {
            //NOTE: Speicher ist überleer, es muss etwas aus dem öffentlichen Netz aufgenommen werden
            float fehlstand = this.verfügbar; // negativ!

            this.verfügbar = 0;
            return fehlstand;
        } else {
            //NOTE: Alles okay, kein Fehlstand
            return 0;
        }
    }

    /** NOTE:
     * Setze das Objekt für eine neue Simulation zurück
     */
    public void zurücksetzen() {
        this.verfügbar = 0;
    }


    // BAD: Starke Objektkopplung: kapazität(getter) nach außen sichtbar
    // werden überhaupt nicht verwendet -> sollte gelöscht werden
    public float kapazität() {
        return this.kapazität;
    }

    /**NOTE:
     * @return Verfügbare Menge in diesem Speicher
     */
    public float verfügbar() {
        return this.verfügbar;
    }

    /** NOTE:
     * @return Freier Platz in diesem Speicher
     */
    public float frei() {
        return this.kapazität - this.verfügbar;
    }

    /**NOTE:
     * Diese Methode wird von den Produzenten aufgerufen
     *
     * @param menge Wieviel im Speicher zusätzlich aufgenommen werden soll
     */

    public void empfange(float menge) {
        if (menge < 0)
            throw new RuntimeException("Menge muss größer/gleich Null sein.");

        this.verfügbar += menge;
    }

    /**NOTE:
     * Diese Methode wird von den Verbrauchern aufgerufen
     *
     * @param menge Wieviel aus dem Speicher entfernt werden soll
     */
    // Precondition : Menge sollte >=0 sein
    // Postcondition: Verfugbar ändert sich
    public void entferne(float menge) {
        if (menge < 0)
            throw new RuntimeException("Menge muss größer/gleich Null sein.");

        this.verfügbar -= menge;
    }
}