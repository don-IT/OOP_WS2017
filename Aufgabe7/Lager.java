/**
 * Ein Lager ist ein Ort für Rohre, wo Rohren gelagert werden und von wo der Installateur Rohre zur Montate entnehmen kann
 */
public class Lager extends OrtFuerRohre {
    /**
     * Prüft, ob ein Rohr zu diesem Lager hinzugefügt werden kann.
     *
     * @param rohr Das Rohr
     * @return True, wenn und nur wenn das gegebene Rohr zu diesem Lager hinzugefügt werden kann
     */
    public boolean istRohrKompatibel(Rohr rohr) {
        return null != rohr; // Das Lager kann alle Rohre aufnehmen
    }
}
