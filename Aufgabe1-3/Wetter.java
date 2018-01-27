import java.io.*;
import java.util.*;

public class Wetter {
    /** NOTE:
     * Liest eine CSV-Datei mit Wetterdaten ein
     *
     * @param pfad Pfad zur CSV-Datei
     * @return Das eingelesene Wetter
     */
    public static Wetter[] liesCSV(String pfad) {


        try (BufferedReader reader = new BufferedReader(new FileReader(pfad))) {
            Vector<Wetter> daten = new Vector<Wetter>();

            reader.readLine(); // Kopfzeile überspringen

            String zeile;
            while ((zeile = reader.readLine()) != null) {
                String[] teile = zeile.split(",");

                daten.add(new Wetter(
                        Float.parseFloat(teile[0]),
                        Float.parseFloat(teile[1]),
                        Float.parseFloat(teile[2]),
                        Float.parseFloat(teile[3]),
                        Float.parseFloat(teile[4])
                ));
            }

            return daten.toArray(new Wetter[daten.size()]);
        } catch (IOException e) {
            throw new RuntimeException("Konnte die Daten im angegebenen Pfad nicht einlesen.", e);
        }
    }

    private float temperatur;
    private float sonnenschein;
    private float bewölkung;
    private float windgeschwindigkeit;
    private float niederschlag;

    /**  NOTE:
     * @param temperatur Durchschnittstemperatur
     * @param sonnenschein Sonnenscheindauer in Stunden
     * @param bewölkung Durchschnittliche Bewölkung auf der Skala 0 (klar) bis 10 (kein Blau sichtbar)
     * @param windgeschwindigkeit Windgeschwindigkeit in Metern/Sekunde
     * @param niederschlag Niederschlagsmenge in Millimetern
     */


    public Wetter(float temperatur, float sonnenschein, float bewölkung, float windgeschwindigkeit, float niederschlag) {
        if (sonnenschein < 0) {
            throw new RuntimeException("Sonnenschein kann nicht kleiner Null sein.");
        } else if (bewölkung < 0) {
            throw new RuntimeException("Bewölkung kann nicht kleiner Null sein.");
        } else if (bewölkung > 10) {
            throw new RuntimeException("Bewölkung kann nicht größer 10 sein.");
        } else if (windgeschwindigkeit < 0) {
            throw new RuntimeException("Windgeschwindigkeit kann nicht kleiner Null sein.");
        } else if (niederschlag < 0) {
            throw new RuntimeException("Niederschlag kann nicht kleiner Null sein.");
        }

        this.temperatur = temperatur;
        this.sonnenschein = sonnenschein;
        this.bewölkung = bewölkung;
        this.windgeschwindigkeit = windgeschwindigkeit;
        this.niederschlag = niederschlag;
    }

    /** NOTE:
     * @return Durchschnittstemperatur
     */
    public float temperatur() {
        return this.temperatur;
    }

    /** NOTE:
     * @return Sonnenscheindauer in Stunden
     */
    public float sonnenschein() {
        return this.sonnenschein;
    }

    /**
     * @return Durchschnittliche Bewölkung auf der Skala 0 (klar) bis 10 (kein Blau sichtbar)
     */
    // BAD: Starke Objektkopplung: bewölkung(getter) nach außen sichtbar
    // werden überhaupt nicht verwendet -> sollte gelöscht werden
    public float bewölkung() {
        return this.bewölkung;
    }

    /** NOTE:
     * @return Windgeschwindigkeit in Metern/Sekunde
     */
    public float windgeschwindigkeit() {
        return this.windgeschwindigkeit;
    }

    /** NOTE:
     * @return Niederschlagsmenge in Millimetern
     */
    public float niederschlag() {
        return this.niederschlag;
    }
}
