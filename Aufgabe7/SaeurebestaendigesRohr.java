/**
 * Für Industrieanlagen werden nur säurebeständige Rohre verwendet.
 */
public abstract class SaeurebestaendigesRohr extends Rohr {
    /**
     * Erzeugt ein neues säurebeständiges Rohr
     *
     * @param laenge Länge des Rohres in Zentimenter
     * @param preis Preis des Rohres in Cents
     */
    public SaeurebestaendigesRohr(double laenge, int preis) {
        super(laenge, preis);
    }

    /**
     * @return True, wenn und nur wenn dieses Rohr säurebeständig ist
     */
    public boolean istSaeurebestaendig() {
        return true;
    }
}
