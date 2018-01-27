/**
 * Säurebeständiges Rohr, kleiner Temperaturbereich.
 * Rohre mit kleinem Temperaturbereich sind preiswerter als Rohre mit großem Temperaturbereich.
 */
public class SaeurebestaendigesRohrKlein extends SaeurebestaendigesRohr {
    /**
     * Erzeugt ein neues säurebeständiges Rohr mit kleinem Temperaturbereich
     *
     * @param laenge Länge des Rohres in Zentimenter
     * @param preis Preis des Rohres in Cents
     */
    public SaeurebestaendigesRohrKlein(double laenge, int preis) {
        super(laenge, preis);
    }

    /**
     * @return True, wenn und nur wenn dieses Rohr einen großen Temperaturbereich aufweist
     */
    public boolean hatGrossenTemperaturbereich() {
        return false;
    }

    /**
     * Erstellt eine Kopie dieses Rohres
     *
     * @return Kopie dieses Rohres
     */
    protected SaeurebestaendigesRohrKlein dupliziere() {
        return new SaeurebestaendigesRohrKlein(this.laenge(), this.preis());
    }
}
