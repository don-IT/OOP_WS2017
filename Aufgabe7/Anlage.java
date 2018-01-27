/**
 * Eine Anlage ist ein Ort für Rohre - die Rohre werden montiert und demontiert.
 *
 * Industrieanlage hat nur säurebeständige Rohren
 * Heizungsanlage hat nur normale Rohren
 */
public abstract class Anlage extends OrtFuerRohre {
    private boolean arbeitetInGrossemTemperaturbereich;

    /**
     * Erzeugt eine neue Anlage
     *
     * @param arbeitetInGrossemTemperaturbereich Ob die Anlage in großem Temperaturbereich arbeitet
     */
    public Anlage(boolean arbeitetInGrossemTemperaturbereich) {
        this.arbeitetInGrossemTemperaturbereich = arbeitetInGrossemTemperaturbereich;
    }

    /**
     * @return True, wenn und nur wenn diese Anlage in großem Temperaturbereich arbeitet
     */
    public boolean arbeitetInGrossemTemperaturbereich() {
        return this.arbeitetInGrossemTemperaturbereich;
    }
}
