/**
 * Ein Rohr
 */
public abstract class Rohr {
    private double laenge;
    private int preis;

    /**
     * Erzeugt ein neues Rohr
     *
     * @param laenge Länge des Rohres in Zentimenter
     * @param preis Preis des Rohres in Cents pro Zentimeter
     */
    public Rohr(double laenge, int preis) {
        if (laenge <= 0) {
            throw new IllegalArgumentException("Länge muss größer als 0 sein");
        } else if (preis <= 0) {
            throw new IllegalArgumentException("Preis muss größer als 0 sein");
        }

        this.laenge = laenge;
        this.preis = preis;
    }

    /**
     * @return Länge des Rohres in Zentimenter
     */
    public double laenge() {
        return this.laenge;
    }

    /**
     * @return Preis des Rohres in Cents pro Zentimeter
     */
    public int preis() {
        return this.preis;
    }

    /**
     * @return Wert des Rohres in Euro (= Länge * Preis/cm * 0.01)
     */
    public double wert() {
        return this.laenge() * this.preis() * 0.01;
    }

    /**
     * @return True, wenn und nur wenn dieses Rohr säurebeständig ist
     */
    public abstract boolean istSaeurebestaendig();

    /**
     * @return True, wenn und nur wenn dieses Rohr einen großen Temperaturbereich aufweist
     */
    public abstract boolean hatGrossenTemperaturbereich();

    /**
     * Kürzt dieses Rohr auf die angegebene Länge und gibt den "Abfall" zurück.
     *
     * @requires neueLaenge < this.laenge
     *
     * @param neueLaenge Länge in Zentimetern, auf die gekürzt werden soll
     * @return Abfall
     */
    protected Rohr kuerze(double neueLaenge) {
        assert neueLaenge < this.laenge;

        Rohr abfall = this.dupliziere();

        abfall.laenge = this.laenge() - neueLaenge;
        this.laenge = neueLaenge;

        return abfall;
    }

    /**
     * Erstellt ein Duplikat dieses Rohres.
     * Wäre mit clone() viel eleganter, aber wir dürfen ja keine Casts/Ausnahmen verwenden :/
     *
     * @return Duplikat dieses Rohres
     */
    protected abstract Rohr dupliziere();

    /**
     * Prüft, ob dieses Rohr mit einer Heizungsanlage kompatibel ist.
     *
     * @param heizungsanlage Die Heizungsanlage
     * @return True, wenn und nur wenn dieses Rohr mit der Heizungsanlagen kompatibel ist
     */
    protected boolean istKompatibelMitHeizungsanlage(Heizungsanlage heizungsanlage) {
        return this.hatKompatiblenTemperaturbereichMitAnlage(heizungsanlage) && !this.istSaeurebestaendig();
    }

    /**
     * Prüft, ob dieses Rohr mit einer Industrieanlage kompatibel (d.h. u.a. säurebeständig) ist.
     *
     * @param industrieanlage Die Industrieanlage
     * @return True, wenn und nur wenn dieses Rohr mit der Industrieanlagen kompatibel ist
     */
    protected boolean istKompatibelMitIndustrieanlage(Industrieanlage industrieanlage) {
        return this.hatKompatiblenTemperaturbereichMitAnlage(industrieanlage) && this.istSaeurebestaendig();
    }

    /**
     * Prüft, ob dieses Rohr kompatiblen Temperaturbereich mit der Anlage hat, d.h. entweder, das Rohr hat großen
     * Temperaturbereich, oder die Anlage benütigt eh keinen großen (d.h. nur kleinen) Temperaturbereich.
     *
     * @param anlage Die Anlage
     * @return True, wenn und nur wenn dieses Rohr kompatiblen Temperaturbereich mit der Anlage hat
     */
    protected boolean hatKompatiblenTemperaturbereichMitAnlage(Anlage anlage) {
        return this.hatGrossenTemperaturbereich() || !anlage.arbeitetInGrossemTemperaturbereich();
    }
}
