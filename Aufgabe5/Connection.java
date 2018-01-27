/**
 * Ein Objekt von Connection stellt eine Verbindung dar.
 */
public interface Connection<Type> {
    /**
     * @return Anfangspunkt der Verbindung (oder null wenn der Anfang nicht verbunden ist)
     */
    public Type source();

    /**
     * @return Endpunkt der Verbindung (oder null wenn das Ende nicht verbunden ist)
     */
    public Type sink();
}
