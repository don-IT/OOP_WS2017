/**
 * Ein Objekt von Negator (dient nur zum Testen, ist mit geeigneten Typparameterersetzungen Untertyp von Connection)
 * verbindet zwei Zahlen vom Typ Integer, wobei der von sink zurückgegebene Wert die Negation des von source
 * zurückgegebenen Werts ist.
 */
public class Negator implements Connection<Integer> {
    private Integer source = null;

    /**
     * @param source Anfangspunkt der Verbindung (oder null wenn der Anfang nicht verbunden ist)
     */
    public Negator(Integer source) {
        this.source = source;
    }

    /**
     * @return Anfangspunkt der Verbindung (oder null wenn der Anfang nicht verbunden ist)
     */
    public Integer source() {
        return this.source;
    }

    /**
     * @return Bitweise Negation des Anfangspunkts der Verbindung (oder null wenn der Anfang nicht verbunden ist)
     */
    public Integer sink() {
        return (null != this.source) ? ~this.source : null;
    }
}
