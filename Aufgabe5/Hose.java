/**
 * Ein Objekt von Hose (mit geeigneten Typparameterersetzungen Untertyp von Connection) stellt einen Wasserschlauch als
 * Verbindung von zwei Objekten des Typs WaterSupply dar.
 */
public class Hose implements Connection<WaterSupply> {
    private WaterSupply source = null;
    private WaterSupply sink = null;
    private float diameter;

    /**
     * @param diameter Durchmesser in Zoll
     * @param source Anfangspunkt der Verbindung (oder null wenn der Anfang nicht verbunden ist)
     * @param sink Endpunkt der Verbindung (oder null wenn das Ende nicht verbunden ist)
     */
    public Hose(float diameter, WaterSupply source, WaterSupply sink) {
        if (diameter <= 0)
            throw new IllegalArgumentException("Diameter needs to be greater than zero");

        this.source = source;
        this.sink = sink;
        this.diameter = diameter;
    }

    /**
     * @return Anfangspunkt der Verbindung (oder null wenn der Anfang nicht verbunden ist)
     */
    public WaterSupply source() {
        return this.source;
    }

    /**
     * @return Endpunkt der Verbindung (oder null wenn das Ende nicht verbunden ist)
     */
    public WaterSupply sink() {
        return this.sink;
    }

    /**
     * @return Durchmesser in Zoll
     */
    public float diameter() {
        return this.diameter;
    }
}
