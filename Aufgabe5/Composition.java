import java.util.Iterator;

/**
 * Ein Objekt von Composition (mit geeigneten Typparameterersetzungen Untertyp von Connection, Supply und Ensemble) ist
 * eine Sammlung von Objekten, deren gemeinsamer Typ über Typparameter festgelegt ist und die durch Verbindungsobjekte
 * (von einem Untertyp von Connection) miteinander verbunden sein können.
 */
public class Composition<Type, ConnectionType extends Connection<Type>> extends Ensemble<Type> {
    private final Ensemble<ConnectionType> connections = new Ensemble<>();

    /**
     * Fügt den Parameter x als Verbindung zwischen Objekten hinzu, wobei auch die Objekte x.source() und x.sink()
     * hinzugefügt werden, falls sie ungleich null und noch nicht vorhanden sind. Fügt connect dieselbe Verbindung
     * mehrfach hinzu, ist sie auch mehrfach vorhanden.
     *
     * @param x Verbindung, die eingefügt werden soll
     * @return true, wenn und nur wenn die Verbindung noch nicht vorhanden war und daher eingefügt wurde
     */
    public boolean connect(ConnectionType x) {
        if (this.connections.add(x)) {
            // Value was added, not a duplicate

            if (null != x.source()) this.add(x.source());
            if (null != x.sink()) this.add(x.sink());

            return true;
        } else {
            return false;
        }
    }

    /**
     * Entfernt eine Verbindung (mit derselben Identität) aus der Sammlung
     *
     * @param x Verbindung, die entfernt werden soll
     * @return true, wenn und nur wenn die Verbindung vorhanden war und daher entfernt wurde
     */
    public boolean disconnect(ConnectionType x) {
        return this.connections.remove(x);
    }

    /**
     * Testet, ob eine Verbindung (mit derselben Identität) in der Sammlung enthalten ist.
     *
     * @param x Verbindung mit der getestet wird
     * @return true, wenn und nur wenn die Verbindung in der Sammlung enthalten ist
     */
    public boolean hasConnection(ConnectionType x) {
        return this.connections.contains(x);
    }

    /**
     * Entfernt alle Verbindungen
     */
    public void disconnectAll() {
        this.connections.clear();
    }

    /**
     * Gibt die Anzahl der Verbindungen zurück.
     *
     * @return Anzahl der Verbindungen
     */
    public int numConnections() {
        return this.connections.size();
    }

    /**
     * Entfernt den Parameter aus der Sammlung, sofern er (mit derselben Identität) darin enthalten war.
     * Verbindungen zu und von diesem Objekt, die in dieser Composition enthalten sind, werden ebenfalls entfernt.
     *
     * @param value Objekt, das entfernt werden soll
     * @return true, wenn und nur wenn das Objekt in der Sammlung war und daher entfernt wurde
     */
    public boolean remove(Type value) {
        if (super.remove(value)) {
            // Value was part of set, was removed

            for (ConnectionType con : this.connections)
                if ((con.source() == value) || (con.sink() == value))
                    this.disconnect(con); // Removed value was part of a connection, remove connection from composition

            return true;
        } else {
            return false;
        }
    }

    /**
     * Entfernt alle Objekte und alle Verbindungen
     */
    public void clear() {
        super.clear();
        this.disconnectAll();
    }

    /**
     * Gibt einen Iterator über alle mittels connect eingeführte Verbindungen zwischen Objekten zurück. Dieser Iterator
     * implementiert remove. Ist eine Verbindung mehrfach vorhanden, gibt der Iterator sie auch entsprechend oft zurück.
     *
     * @return Iterator über alle Verbindungen
     */
    public Iterator<ConnectionType> connectionIter() {
        return this.connections.iterator();
    }
}
