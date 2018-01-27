import java.util.Random;

public interface Simulierbar {
    /** NOTE:
     * @param wetter Aktuelles Wetter
     * @param zahlengenerator Zufallszahlengenerator
     * @return Fehlstand (neg: es musste nachgeholt werden, pos: es konnte abgegeben werden)
     */
    public float simuliere(Wetter wetter, Random zahlengenerator);

    /** NOTE:
     * Setze das Objekt für eine neue Simulation zurück
     */
    public void zurücksetzen();
}
