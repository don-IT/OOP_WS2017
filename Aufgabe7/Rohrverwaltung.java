import java.util.Comparator;

/**
 * Programm zur Verwaltung der Rohre im Lager eines Installateurs und der Rohre von Anlagen.
 */
public class Rohrverwaltung {
    private Lager lager;

    /**
     * Erstellt eine neue Instanz mit einem neuen Lager
     */
    public Rohrverwaltung() {
        this(new Lager());
    }

    /**
     * Erstellt eine neue Instanz
     *
     * @param lager Das zu verwendende Lager
     */
    public Rohrverwaltung(Lager lager) {
        if (null == lager)
            // https://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html#preconditions
            throw new NullPointerException("Lager muss ungleich NULL sein");

        this.lager = lager;
    }

    /**
     * Gibt das Lager, das dieser Instanz zugewiesen ist, zurück.
     *
     * @return das Lager
     */
    public Lager lager() {
        return this.lager;
    }

    /**
     * Lagert ein Rohr im Lager des Installateurs ein.
     *
     * @param rohr Das einzulagernde Rohr
     * @return Das Rohr, soweit es eingelagert werden konnte, sonst NULL
     */
    public Rohr lagere(Rohr rohr) {
        return this.lager().hinzufuege(rohr);
    }

    /**
     * Entnimmt das günstigste für die Anlage passendes Rohr aus dem Lager und montiert es
     * in der Anlage.
     *
     * Falls kein passendes Rohr mit kleinem Temperaturbereich gelagert ist, darf auch ein passendes Rohr mit großem
     * Temperaturbereich verwendet werden. Falls dann noch immer kein passendes Rohr gefunden wird, wird NULL
     * zurückgegeben.
     *
     * @param anlage Die Anlage, in die das Rohr montiert werden soll
     * @return Das montierte Rohr oder NULL, wenn kein passendes Rohr gefunden wurde
     */
    public Rohr montiere(Anlage anlage) {
        return this.montiere(anlage, 0);
    }

    /**
     * Entnimmt das günstigste für die Anlage passendes Rohr mit gegebener Mindestlänge aus dem Lager, kürzt dieses und
     * montiert es in der Anlage. Der Abfall, der beim eventuellen Kürzen entsteht, wird ins Lager zurückgelegt.
     *
     * Falls kein passendes Rohr mit kleinem Temperaturbereich gelagert ist, darf auch ein passendes Rohr mit großem
     * Temperaturbereich verwendet werden. Falls dann noch immer kein passendes Rohr gefunden wird, wird NULL
     * zurückgegeben.
     *
     * @param anlage Die Anlage, in die das Rohr montiert werden soll
     * @param laenge Länge des zu verwendenden Rohres in Zentimentern, oder 0, wenn irgendein Rohr verwendet werden soll
     * @return Das montierte Rohr oder NULL, wenn kein passendes Rohr gefunden wurde
     */
    public Rohr montiere(Anlage anlage, double laenge) {
        if (null == anlage) {
            // https://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html#preconditions
            throw new NullPointerException("Anlage muss ungleich NULL sein");
        } else if (laenge < 0) {
            // https://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html#preconditions
            throw new IllegalArgumentException("Länge muss größer/gleich 0 sein");
        }

        // Im Idealfall gibt es ein Rohr mit kleinem Temperaturbereich (günstiger)...
        for (Rohr rohr: this.lager().rohre(Comparator.comparingDouble(Rohr::wert))) // Günstige Rohre zuerst
            if (anlage.istRohrKompatibel(rohr) && !rohr.hatGrossenTemperaturbereich() && (rohr.laenge() >= laenge))
                return this.montiere(anlage, rohr, laenge);

        // Wenn kein günstiges Rohr da ist (siehe oben), suche nach passendem Rohr mit großem Temperaturbereich (teurer)
        for (Rohr rohr: this.lager().rohre(Comparator.comparingDouble(Rohr::wert))) // Günstige Rohre zuerst
            if (anlage.istRohrKompatibel(rohr) && rohr.hatGrossenTemperaturbereich() && (rohr.laenge() >= laenge))
                return this.montiere(anlage, rohr, laenge);

        // Kein passendes Rohr gefunden
        return null;
    }

    /**
     * Entnimmt das angegebene Rohr aus dem Lager, kürzt es und montiert es in der Anlage. Der Abfall, der beim
     * eventuellen Kürzen entsteht, wird ins Lager zurückgelegt.
     *
     * @requires laenge <= rohr.laenge
     *
     * @param anlage Die Anlage, in die das Rohr montiert werden soll
     * @param rohr Das zu montierende Rohr
     * @param laenge Auf welche Länge in Zentimetern das Rohr gekürzt werden soll bevor es montiert wird, oder 0, wenn
     *               nicht gekürzt werden soll
     * @return Das eventuell gekürzte Rohr, soweit es montiert werden konnte, oder NULL, wenn das Rohr im Lager nicht
     *         gefunden wurde
     */
    private Rohr montiere(Anlage anlage, Rohr rohr, double laenge) {
        assert laenge <= rohr.laenge();

        if (this.lager().rohre().contains(rohr)) {
            this.lager().entferne(rohr);

            if ((laenge > 0) && (laenge < rohr.laenge()))
                this.lager().hinzufuege(rohr.kuerze(laenge));

            anlage.hinzufuege(rohr);
            return rohr;
        } else {
            return null;
        }
    }

    /**
     * Entfernt ein Rohr aus einer Anlage und lagert dieses im Lager des Installateurs ein.
     *
     * @param anlage Die Anlage, aus der das Rohr entfernt werden soll
     * @param rohr Das Rohr, das entfernt werden soll
     * @return Das entfernte Rohr oder NULL, wenn das angegebene Rohr nicht in der Anlage demontiert werden konnte
     */
    public Rohr demontiere(Anlage anlage, Rohr rohr) {
        if (null == anlage)
            // https://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html#preconditions
            throw new NullPointerException("Anlage muss ungleich NULL sein");

        return this.lager().hinzufuege(anlage.entferne(rohr));
    }

    /**
     * Zeigt die Summe aller Preise der Rohre im Lager auf dem Bildschirm an.
     */
    public void lagerwert() {
        System.out.println(this.lager().rohrwert());
    }

    /**
     * Zeigt die Summe aller Preise der Rohre einer Anlage auf dem Bildschirm an.
     *
     * @param anlage Die Anlage, über die Informationen angezeigt werden sollen
     */
    public void anlagenwert(Anlage anlage) {
        if (null == anlage)
            // https://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html#preconditions
            throw new NullPointerException("Anlage muss ungleich NULL sein");

        System.out.println(anlage.rohrwert());
    }

    /**
     * Zeigt alle Rohre des Lagerbestands mit allen Informa- tionen auf dem Bildschirm an.
     */
    public void lagerliste() {
        System.out.println(this.lager().rohrliste());
    }

    /**
     * Zeigt alle Rohre einer Anlage mit allen Informationen auf dem Bildschirm an.
     *
     * @param anlage Die Anlage, über die Informationen angezeigt werden sollen
     */
    public void anlagenliste(Anlage anlage) {
        if (null == anlage)
            // https://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html#preconditions
            throw new NullPointerException("Anlage muss ungleich NULL sein");

        System.out.println(anlage.rohrliste());
    }
}
