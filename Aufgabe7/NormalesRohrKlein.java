/**
 * Normales Rohr, kleiner Temperaturbereich.
 * Rohre mit kleinem Temperaturbereich sind preiswerter als Rohre mit großem Temperaturbereich.
 */
public class NormalesRohrKlein extends NormalesRohr {
    /**
     * Erzeugt ein neues normales Rohr mit kleinem Temperaturbereich
     *
     * @param laenge Länge des Rohres in Zentimenter
     * @param preis Preis des Rohres in Cents
     */
    public NormalesRohrKlein(double laenge, int preis) {
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
    protected NormalesRohrKlein dupliziere() {
        return new NormalesRohrKlein(this.laenge(), this.preis());
    }
}
