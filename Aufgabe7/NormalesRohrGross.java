/**
 * Normales Rohr, großer Temperaturbereich.
 * Rohre mit kleinem Temperaturbereich sind preiswerter als Rohre mit großem Temperaturbereich.
 */
public class NormalesRohrGross extends NormalesRohr {
    /**
     * Erzeugt ein neues normales Rohr mit kleinem Temperaturbereich
     *
     * @param laenge Länge des Rohres in Zentimenter
     * @param preis Preis des Rohres in Cents
     */
    public NormalesRohrGross(double laenge, int preis) {
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
    protected NormalesRohrGross dupliziere() {
        return new NormalesRohrGross(this.laenge(), this.preis());
    }
}
