/**
 * Ein Objekt von Cable (mit geeigneten Typparameterersetzungen Untertyp von Connection) stellt ein Stromkabel als
 * Verbindung zwischen zwei Objekten des Typs PowerSupply dar.
 */
public class Cable implements Connection<PowerSupply> {
    private PowerSupply source = null;
    private PowerSupply sink = null;
    private int strands;

    /**
     * @param strands Anzahl der Litzen
     * @param source Anfangspunkt der Verbindung (oder null wenn der Anfang nicht verbunden ist)
     * @param sink Endpunkt der Verbindung (oder null wenn das Ende nicht verbunden ist)
     */
    public Cable(int strands, PowerSupply source, PowerSupply sink) {
        if (strands <= 0)
            throw new IllegalArgumentException("Strands needs to be greater than zero");

        this.source = source;
        this.sink = sink;
        this.strands = strands;
    }

    /**
     * @return Anfangspunkt der Verbindung (oder null wenn der Anfang nicht verbunden ist)
     */
    public PowerSupply source() {
        return this.source;
    }

    /**
     * @return Endpunkt der Verbindung (oder null wenn das Ende nicht verbunden ist)
     */
    public PowerSupply sink() {
        return this.sink;
    }

    /**
     * @return Anzahl der Litzen
     */
    public int strands() {
        return this.strands;
    }
}
