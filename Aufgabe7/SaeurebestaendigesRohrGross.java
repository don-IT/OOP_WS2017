/**
 * Säurebeständiges Rohr, großer Temperaturbereich.
 * Rohre mit kleinem Temperaturbereich sind preiswerter als Rohre mit großem Temperaturbereich.
 */
public class SaeurebestaendigesRohrGross extends SaeurebestaendigesRohr {
    /**
     * Erzeugt ein neues säurebeständiges Rohr mit großem Temperaturbereich
     *
     * @param laenge Länge des Rohres in Zentimenter
     * @param preis Preis des Rohres in Cents
     */
    public SaeurebestaendigesRohrGross(double laenge, int preis){
        super(laenge, preis);
    }

    /**
     * @return True, wenn und nur wenn dieses Rohr einen großen Temperaturbereich aufweist
     */
    public boolean hatGrossenTemperaturbereich() {
        return true;
    }

    /**
     * Erstellt eine Kopie dieses Rohres
     *
     * @return Kopie dieses Rohres
     */
    protected SaeurebestaendigesRohrGross dupliziere() {
        return new SaeurebestaendigesRohrGross(this.laenge(), this.preis());
    }
}
