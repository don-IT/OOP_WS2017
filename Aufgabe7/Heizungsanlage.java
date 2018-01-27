/**
 * Für Heizungssysteme werden nur normale Rohre verwendet.
 */
public class Heizungsanlage extends Anlage {
    /**
     * Erzeugt eine neue Industrieanlage die in kleinem Temperaturbereich arbeitet
     */
    public Heizungsanlage() {
        this(false);
    }

    /**
     * Erzeugt eine neue Heizungsanlage
     *
     * @param arbeitetInGrossemTemperaturbereich Ob die Anlage in großem Temperaturbereich arbeitet
     */
    public Heizungsanlage(boolean arbeitetInGrossemTemperaturbereich) {
        super(arbeitetInGrossemTemperaturbereich);
    }

    /**
     * Prüft, ob ein Rohr zu dieser Heizungsanlage hinzugefügt werden kann, d.h. kompatibel ist, hineinpasst, o.Ä.
     *
     * @param rohr Das Rohr
     * @return True, wenn und nur wenn das gegebene Rohr zu dieser Anlage hinzugefügt werden kann
     */
    public boolean istRohrKompatibel(Rohr rohr) {
        return (null != rohr) && rohr.istKompatibelMitHeizungsanlage(this);
    }
}
