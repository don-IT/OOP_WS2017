import java.util.*;

//GOOD : Verwendung der abstrakten Klasse zur Vermeidung der Initialisierung

public abstract class Produzent implements Simulierbar {
    private HashSet<Speicher> empfänger = new HashSet<Speicher>();

    /**NOTE:
     * @return An welche Speicher dieser Produzent seine Menge abgiebt
     */
    public Set<Speicher> empfänger() {
        return this.empfänger;
    }

    /** NOTE:
     * @param wetter Aktuelles Wetter
     * @param zahlengenerator Zufallszahlengenerator
     * @return Fehlstand (neg: es musste nachgeholt werden, pos: es konnte abgegeben werden)
     */
    public float simuliere(Wetter wetter, Random zahlengenerator) {
        float menge = this.produziere(wetter, zahlengenerator);
        float fehlstand = this.verteile(menge);

        return fehlstand;
    }

    /**NOTE:
     * Setze das Objekt für eine neue Simulation zurück
     */
    public void zurücksetzen() {}

    /** NOTE:
     * @param wetter Aktuelles Wetter
     * @param zahlengenerator Zufallszahlengenerator
     * @return Wieviel produziert wird
     */
    protected abstract float produziere(Wetter wetter, Random zahlengenerator);

    /**NOTE:
     * @param menge Wieviel produziert wird
     */
    // Precondition:  Fehlstand, d.h. wieviel ins öff. Netz abgegeben wurde (größer/gleich 0)
    // BAD: Objekt-Kopplung ist stark, weil im laufenden System Variablenzugriffe zwi-
    // schen unterschiedlichen Objekten(Speicher) häufig auftreten

    protected float verteile(float menge) {
        if ((menge > 0) && (this.empfänger.size() > 0)) {
            Speicher[] empfänger = this.empfänger.toArray(new Speicher[this.empfänger.size()]);

            float[] frei = new float[empfänger.length];
            float summeFrei = 0;

            for (int i = 0; i < frei.length; i++) {
                frei[i] = Math.max(0, empfänger[i].frei());
                summeFrei += frei[i];
            }

            for (int i = 0; i < empfänger.length; i++) {
                if (summeFrei != 0) {
                    empfänger[i].empfange(menge * (frei[i] / summeFrei));
                } else {
                    empfänger[i].empfange(menge / empfänger.length);
                }
            }

            return 0;
        } else {
            return menge; // NOTE: Menge wird ins öffentliche Netz abgegeben (positiv)
        }
    }
}
