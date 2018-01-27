import java.util.*;

//GOOD : Verwendung der abstrakten Klasse zur Vermeidung der Initialisierung

public abstract class Konsument implements Simulierbar {
    private HashSet<Speicher> herkunft = new HashSet<Speicher>();

    /**NOTE
     * @return Aus welchen Speichern dieser Konsument die Menge bezieht
     */
    public Set<Speicher> herkunft() {
        return this.herkunft;
    }

    /**NOTE:
     * @param wetter Aktuelles Wetter
     * @param zahlengenerator Zufallszahlengenerator
     * @return Fehlstand (neg: es musste nachgeholt werden, pos: es konnte abgegeben werden)
     */
    public float simuliere(Wetter wetter, Random zahlengenerator) {
        float menge = this.konsumiere(wetter, zahlengenerator);
        float fehlstand = this.fordereAn(menge);

        return fehlstand;
    }

    /**NOTE:
     * Setze das Objekt für eine neue Simulation zurück
     */
    public void zurücksetzen() {}

    /**NOTE:
     * @param wetter Aktuelles Wetter
     * @param zahlengenerator Zufallszahlengenerator
     * @return Wieviel verbraucht wird
     */
    //History-Constraint(Server-kontrolliert): Random zahlgenerator

    //GOOD: Verwendung der abstrakten Methode - Unterklasse implementieren Methode
    protected abstract float konsumiere(Wetter wetter, Random zahlengenerator);

    /**NOTE:
     * @param menge Wieviel verbraucht wird
     * @return Fehlstand, d.h. wieviel aus dem öffentlichen Netz nachgeholt wurde (kleiner/gleich 0)
     */
    protected float fordereAn(float menge) {
        if ((menge > 0) && (this.herkunft.size() > 0)) {
            Speicher[] herkunft = this.herkunft.toArray(new Speicher[this.herkunft.size()]);

            float[] verfügbar = new float[herkunft.length];
            float summeVerfügbar = 0;

            for (int i = 0; i < verfügbar.length; i++) {
                verfügbar[i] = Math.max(0, herkunft[i].verfügbar());
                summeVerfügbar += verfügbar[i];
            }

            for (int i = 0; i < herkunft.length; i++) {
                if (summeVerfügbar != 0) {
                    herkunft[i].entferne(menge * (verfügbar[i] / summeVerfügbar));
                } else {
                    herkunft[i].entferne(menge / herkunft.length);
                }
            }

            return 0;
        } else {
            return -menge; // NOTE:Menge wird aus öffentlichem Netz nachgeholt (negativ)
        }
    }
}