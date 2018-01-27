import java.util.Random;

public class Windkraftanlage extends Produzent {
    private float effizienz;

    /**NOTE:
     * @param effizienz Effizienz der Anlage in kWh pro m/s
     */
    public Windkraftanlage(float effizienz){
        if (effizienz < 0)
            throw new IllegalArgumentException("Effizienz muss größer/gleich Null sein.");

        this.effizienz = effizienz;
    }

    /**NOTE:
     * @return Effizienz der Anlage in kWh pro m/s
     */
    public float effizienz() {
        return this.effizienz;
    }

    /**NOTE:
     * @param wetter Aktuelles Wetter
     * @param zahlengenerator Zufallszahlengenerator
     * @return Wieviele kWh produziert werden
     */
    protected float produziere(Wetter wetter, Random zahlengenerator) {
        if (wetter.windgeschwindigkeit() > 50) {
            return 0; //NOTE: Anlage wird bei Sturm abgeschaltet
        } else {
            return wetter.windgeschwindigkeit() * this.effizienz;
        }
    }
}
