public class LightBulb extends Leuchtmittel {
    private int maxTemp; // die maximale Temperatur in Grad Celsi- us (ganze Zahl)

    /**
     * @param maxTemp Maximale Temperatur in Grad Celsius, die die Glühbirne im Betrieb erreichen kann
     * @param ratedCapacity Durchschnittliche Nennleistung in Watt
     */
    public LightBulb(int maxTemp, int ratedCapacity) {
        super(ratedCapacity);

        if (maxTemp < 0)
            throw new IllegalArgumentException("maxTemp has to be >= 0");

        this.maxTemp = maxTemp;
    }

    /**
     * @return Maximale Temperatur in Grad Celsius, die die Glühbirne im Betrieb erreichen kann
     */
    public int getMaxTemperature() {
        return this.maxTemp;
    }

    /**
     * @param indent What to put in front of each line
     * @return Detailled Representation of this
     */
    public String toString(String indent) {
        String out = super.toString(indent);

        out += indent + String.format("Maximale Temperatur: %d °C", this.getMaxTemperature());

        return out;
    }
}
