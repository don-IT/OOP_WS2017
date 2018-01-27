import java.util.*;
import java.util.stream.Collectors;

/**
 * Ein Ort für Rohre (z.B. Lager, Anlage, etc.). Rohre werden ein- oder ausgelagert, ein- oder ausgebaut und verbleiben
 * sonst an diesem Ort.
 */
public abstract class OrtFuerRohre {
    private HashSet<Rohr> rohre = new HashSet<>();

    /**
     * Prüft, ob ein Rohr zu diesem Ort hinzugefügt werden kann, d.h. kompatibel ist, hineinpasst, o.Ä.
     *
     * @param rohr Das Rohr
     * @return True, wenn und nur wenn das gegebene Rohr zu diesem Ort hinzugefügt werden kann
     */
    public abstract boolean istRohrKompatibel(Rohr rohr);

    /**
     * Gibt das Set an Rohren zurück, die sich derzeit an diesem Ort befinden.
     *
     * @return Read-only view über das Set an Rohren an diesem Ort
     */
    public Set<Rohr> rohre() {
        return Collections.unmodifiableSet(this.rohre);
    }

    /**
     * Gibt eine Liste an Rohren zurück, die sich derzeit an diesem Ort befinden, sortiert mit der angegebenen
     * Vergleichsfunktion.
     *
     * @param vergleichsfunktion Vergleichsfunktion
     * @return Read-only view über die Liste an Rohren an diesem Ort
     */
    public List<Rohr> rohre(Comparator<Rohr> vergleichsfunktion) {
        if (null == vergleichsfunktion)
            // https://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html#preconditions
            throw new NullPointerException("Vergleichsfunktion kann nicht NULL sein");

        return this.rohre.stream().sorted(vergleichsfunktion).collect(Collectors.toList());
    }

    /**
     * Fügt ein Rohr dem Ort hinzu (lagern, einbauen, o.Ä.)
     *
     * @param rohr Das Rohr
     * @return Das Rohr, soweit es hinzugefügt werden konnte, sonst NULL
     */
    protected Rohr hinzufuege(Rohr rohr) {
        if (this.istRohrKompatibel(rohr)) {
            this.rohre.add(rohr);
            return rohr;
        } else {
            return null;
        }
    }

    /**
     * Entfernt ein Rohr von diesem Ort (auslagern, ausbauen, o.Ä.)
     *
     * @param rohr Das Rohr
     * @return Das Rohr, soweit es entfernt werden konnte, sonst NULL
     */
    protected Rohr entferne(Rohr rohr) {
        if (this.rohre.contains(rohr)) {
            this.rohre.remove(rohr);
            return rohr;
        } else {
            return null;
        }
    }

    /**
     * @return Zusammenfassung aller an diesem Ort befindlichen Rohre
     */
    protected String rohrwert() {
        double sumWert = 0;
        double sumLaenge = 0;
        int numSaeurebestaendig = 0;
        int numGrosserTemperaturbereich = 0;

        for (Rohr rohr: this.rohre()) {
            sumWert += rohr.wert();
            sumLaenge += rohr.laenge();

            numSaeurebestaendig += rohr.istSaeurebestaendig() ? 1 : 0;
            numGrosserTemperaturbereich += rohr.hatGrossenTemperaturbereich() ? 1 : 0;
        }

        return  String.format("%55s", "").replace(" ", "-") + "\n" +
                String.format("%8.02f cm | %9s | %6d | %6d | EUR%8.02f",
                        sumLaenge, "", numSaeurebestaendig, numGrosserTemperaturbereich, sumWert);
    }

    /**
     * @return Liste der in an diesem Ort befindlichen Rohre mit Detailwerten
     */
    protected String rohrliste() {
        String out = String.format("%11s | %9s | %6s | %6s | %11s", "Länge", "Preis", "säureb", "gr Tmp", "Wert");

        out += "\n" + String.format("%1$55s", " ").replace(" ", "-");

        for (Rohr rohr: this.rohre(Comparator.comparingDouble(Rohr::laenge).reversed()))
            out += "\n" + String.format("%1$8.02f cm | %2$3d ct/cm | %4$6b | %5$6b | EUR %3$7.02f",
                    rohr.laenge(), rohr.preis(),  rohr.wert(),
                    rohr.istSaeurebestaendig(), rohr.hatGrossenTemperaturbereich());

        return out;
    }
}
