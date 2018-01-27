public class LED extends Leuchtmittel {
    private float luminousFlux; // luminousFlux der Lichtstrom in Lumen (Gleitkommazahl)

    /**
     * @param luminousFlux Lichtstrom in Lumen
     * @param ratedCapacity Durchschnittliche Nennleistung in Watt
     */
    public LED(float luminousFlux, int ratedCapacity) {
        super(ratedCapacity);

        if (luminousFlux < 0)
            throw new IllegalArgumentException("luminousFlux has to be >= 0");

        this.luminousFlux = luminousFlux;
    }

    /**
     * @return Lichtstrom in Lumen
     */
    public float getLuminousFlux() {
        return this.luminousFlux;
    }

    /**
     * @param indent What to put in front of each line
     * @return Detailled Representation of this
     */
    public String toString(String indent) {
        String out = super.toString(indent);

        out += indent + String.format("Lichtstrom: %.02f lm", this.getLuminousFlux());

        return out;
    }
}
