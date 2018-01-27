/**
 * Für Industrieanlagen werden nur säurebeständige Rohre verwendet.
 */
public class Industrieanlage extends Anlage {
    /**
     * Erzeugt eine neue Industrieanlage die in großem Temperaturbereich arbeitet
     */
    public Industrieanlage() {
        this(true);
    }

    /**
     * Erzeugt eine neue Industrieanlage
     *
     * @param arbeitetInGrossemTemperaturbereich Ob die Anlage in großem Temperaturbereich arbeitet
     */
    public Industrieanlage(boolean arbeitetInGrossemTemperaturbereich) {
        super(arbeitetInGrossemTemperaturbereich);
    }

    /**
     * Prüft, ob ein Rohr zu dieser Industrieanlage hinzugefügt werden kann.
     *
     * @param rohr Das Rohr
     * @return True, wenn und nur wenn das gegebene Rohr zu dieser Anlage hinzugefügt werden kann
     */
    public boolean istRohrKompatibel(Rohr rohr) {
        return (null != rohr) && rohr.istKompatibelMitIndustrieanlage(this);
    }
}
