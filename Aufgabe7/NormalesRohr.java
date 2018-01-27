/**
 * Für Heizungsanlagen werden nur normale Rohre verwendet.
 */
public abstract class NormalesRohr extends Rohr {
    /**
     * Erzeugt ein neues normales Rohr
     *
     * @param laenge Länge des Rohres in Zentimenter
     * @param preis Preis des Rohres in Cents
     */
    public NormalesRohr(double laenge, int preis) {
        super(laenge, preis);
    }

    /**
     * @return True, wenn und nur wenn dieses Rohr säurebeständig ist
     */
    public boolean istSaeurebestaendig() {
        return false;
    }
}
