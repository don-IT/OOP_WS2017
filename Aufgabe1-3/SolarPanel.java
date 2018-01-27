import java.util.Random;

public class SolarPanel extends Produzent {
    private float effizienz;
    private float größe;

    /**NOTE:
     * @param effizienz Effizienz des Panels in kWh/qm
     * @param größe Größe des Panels in Quadratmetern
     */


    public SolarPanel(float effizienz, float größe) {
        if (effizienz < 0) {
            throw new IllegalArgumentException("Effizienz muss größer/gleich Null sein.");
        } else if (größe < 0) {
            throw new IllegalArgumentException("Größe muss größer/gleich Null sein.");
        }

        this.effizienz = effizienz;
        this.größe = größe;
    }

    /**NOTE:
     * @return Effizienz des Panels in kWh/qm
     */
    public float effizienz() {
        return this.effizienz;
    }

    /**NOTE:
     * @return Größe des Panels in Quadratmetern
     */
    public float größe() {
        return this.größe;
    }

    /**NOTE:
     * @param wetter Aktuelles Wetter
     * @param zahlengenerator Zufallszahlengenerator
     * @return Wieviele kWh produziert werden
     */

    protected float produziere(Wetter wetter, Random zahlengenerator) {
        return wetter.sonnenschein() * this.größe * this.effizienz;
    }
}
