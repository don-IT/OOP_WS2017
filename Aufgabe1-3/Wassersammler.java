import java.util.Random;

public class Wassersammler extends Produzent {
    private float größe;

    /**NOTE:
     * @param größe Größe des Wassersammlers in Quadratmetern
     */
    public Wassersammler(float größe) {
        if (größe < 0)
            throw new IllegalArgumentException("Größe muss größer/gleich Null sein.");

        this.größe = größe;
    }

    /**NOTE:
     * @return Größe des Wassersammlers in Quadratmetern
     */
    public float größe() {
        return this.größe;
    }

    /**NOTE:
     * @param wetter Aktuelles Wetter
     * @param zahlengenerator Zufallszahlengenerator
     * @return Wieviele Liter produziert werden
     */
    protected float produziere(Wetter wetter, Random zahlengenerator) {
        return wetter.niederschlag() * this.größe;
    }
}
