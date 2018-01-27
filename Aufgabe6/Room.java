import java.util.Iterator;

/**
 * @inv All elements of this.lamps are always of type Lamp or subclasses
 */
public class Room {
    private final String name;
    private final Map lamps = new Map();

    /**
     * @pre name needs to be unique!
     *
     * @param name Unique name
     */
    public Room(String name) {
        if (null == name)
            throw new IllegalArgumentException("Please enter a name");

        this.name = name;
    }

    /**
     * @return Unique name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param lamp Lamp to addRoom to this room
     */
    public void addLamp(Lamp lamp) {
        if (lamp == null)
            throw new IllegalArgumentException("Lamp cannot be NULL");

        this.lamps.set(lamp.getId(), lamp);
    }

    /**
     * @param id ID of the lamp to removeRoom from this room
     */
    public void removeLamp(int id) {
        this.lamps.delete(id);
    }

    /**
     * @return Iterator over all lamps within this room
     */
    public Iterator lamps() {
        return this.lamps.values();
    }

    /**
     * @return Number of lamps within this room
     */
    public int numLamps() {
        return this.lamps.size();
    }

    /**
     * @param id ID of a lamp
     * @return Lamp with this ID, if and only if this lamp is within this room
     */
    public Lamp getLamp(int id) {
        return (Lamp) this.lamps.get(id);
    }

    /**
     * @param id ID of a lamp
     * @return Lamp with this ID, if and only if this lamp is within this room and is instance of DimmedLamp
     */
    private DimmedLamp getDimmedLamp(int id) {
        Lamp lamp = this.getLamp(id);

        if (lamp instanceof DimmedLamp) {
            return (DimmedLamp) lamp;
        } else {
            return null;
        }
    }

    /**
     * @param id ID of a lamp
     * @return Lamp with this ID, if and only if this lamp is within this room and is instance of SimpleLamp
     */
    private SimpleLamp getSimpleLamp(int id) {
        Lamp lamp = this.getLamp(id);

        if (lamp instanceof SimpleLamp) {
            return (SimpleLamp) lamp;
        } else {
            return null;
        }
    }

    /**
     * Changes the Leuchtmittel of a lamp, if and only is this lamp is within this room
     *
     * @param id ID of a lamp
     * @param l neues Leuchtmittel
     */
    public void changeLeuchtmittel(int id, Leuchtmittel l) {
        Lamp toBeChanged = this.getLamp(id);
        if (toBeChanged != null)
            toBeChanged.changeLeuchtmittel(l);
    }

    /**
     * Changes the period of a lamp, if and only if this lamp is within this room
     *
     * @param id ID of a lamp
     * @param period neue Einschaltzeit in Stunden
     */
    public void changePeriod(int id, int period) {
        Lamp lamp = this.getLamp(id);
        if (lamp != null)
            lamp.changePeriod(period);
    }

    /**
     * Changes the Prozent, wieviel eine Lampe gedimmt ist, if and only if this lamp is within this room
     *
     * @param id ID of the lamp
     * @param perc New percentage (0..1 inclusive)
     */
    public void changeDimmedPerc(int id, float perc) {
        //dimmed or simple...nur dimmedLanmp
        DimmedLamp l = this.getDimmedLamp(id);
        if (l != null)
            l.setDimmedPerc(perc);
    }

    /**
     * @return Representation of this
     */
    public String toString() {
        return String.format("%s \"%s\"", this.getClass().getSimpleName(), this.getName());
    }

    /**
     * @param indent What to put in front of each line
     * @return Detailled representation of this
     */
    public String toString(String indent) {
        String out = String.format("%s mit folgenden %d Lampen:", this.toString(), this.numLamps());

        for (Iterator it = this.lamps.values(); it.hasNext(); )
            out += indent + ((Lamp) it.next()).toString(indent + "\t");

        return out;
    }

    /**
     * @return Durchschnittliche Einschaltdauer aller Lampen im Raum, in Stunden
     */
    public float getAveragePeriodOfLamps() { return this.getAveragePeriod(Lamp.class, Leuchtmittel.class); }

    /**
     * @return Durchschnittliche Einschaltdauer aller einfachen Lampen im Raum, in Stunden
     */
    public float getAveragePeriodOfSimpleLamps() { return this.getAveragePeriod(SimpleLamp.class, Leuchtmittel.class); }

    /**
     * @return Durchschnittliche Einschaltdauer aller dimmbaren Lampen im Raum, in Stunden
     */
    public float getAveragePeriodOfDimmedLamps() { return this.getAveragePeriod(DimmedLamp.class, Leuchtmittel.class); }

    /**
     * @return Durchschnittliche Einschaltdauer aller Lampen mit LED Leuchtmittel im Raum, in Stunden
     */
    public float getAveragePeriodOfLampsWithLED() { return this.getAveragePeriod(Lamp.class, LED.class); }

    /**
     * @return Durchschnittliche Einschaltdauer aller Lampen mit Glühbirnen im Raum, in Stunden
     */
    public float getAveragePeriodOfLampsWithLightBulb() { return this.getAveragePeriod(Lamp.class, LightBulb.class); }

    private float getAveragePeriod(Class matchType, Class matchLeuchtmittelType) {
        float sum = 0;
        int num = 0;

        for (Iterator it = this.lamps(); it.hasNext(); ) {
            Lamp lamp = (Lamp) it.next();
            Leuchtmittel leuchtmittel = lamp.getLeuchtmittel();

            if (matchType.isInstance(lamp) && matchLeuchtmittelType.isInstance(leuchtmittel)) {
                sum += lamp.getPeriod();
                num += 1;
            }
        }

        return (num == 0) ? -1 : sum / num;
    }

    /**
     * @return Durchschinttliche Nennleistung aller Lampen in Watt
     */
    public float getAverageRatedCapacityOfLamps() { return this.getAverageRatedCapacity(Lamp.class, Leuchtmittel.class); }

    /**
     * @return Durchschinttliche Nennleistung aller einfachen Lampen in Watt
     */
    public float getAverageRatedCapacityOfSimpleLamps() { return this.getAverageRatedCapacity(SimpleLamp.class, Leuchtmittel.class); }

    /**
     * @return Durchschinttliche Nennleistung aller dimmbaren Lampen in Watt
     */
    public float getAverageRatedCapacityOfDimmedLamps() { return this.getAverageRatedCapacity(DimmedLamp.class, Leuchtmittel.class); }

    /**
     * @return Durchschinttliche Nennleistung aller Lampen mit LED in Watt
     */
    public float getAverageRatedCapacityOfLampsWithLED() { return this.getAverageRatedCapacity(Lamp.class, LED.class); }

    /**
     * @return Durchschinttliche Nennleistung aller Lampen mit Glühbirne in Watt
     */
    public float getAverageRatedCapacityOfLampsWithLightBulb() { return this.getAverageRatedCapacity(Lamp.class, LightBulb.class); }

    private float getAverageRatedCapacity(Class matchType, Class matchLeuchtmittelType) {
        float sum = 0;
        int num = 0;

        for (Iterator it = this.lamps(); it.hasNext(); ) {
            Lamp lamp = (Lamp) it.next();
            Leuchtmittel leuchtmittel = lamp.getLeuchtmittel();

            if (matchType.isInstance(lamp) && matchLeuchtmittelType.isInstance(leuchtmittel)) {
                sum += leuchtmittel.getRatedCapacity();
                num += 1;
            }
        }

        return (num == 0) ? -1 : sum / num;
    }

    /**
     * @return Durchschnittliche Lichtausbeute aller Lampen mit LED in Lumen pro Watt
     */
    public float getAverageLuminousFluxPerWattOfLampsWithLED() { return this.getAverageLuminousFluxPerWatt(Lamp.class); }

    /**
     * @return Durchschnittliche Lichtausbeute aller einfachen Lampen mit LED in Lumen pro Watt
     */
    public float getAverageLuminousFluxPerWattOfSimpleLampsWithLED() { return this.getAverageLuminousFluxPerWatt(SimpleLamp.class); }

    /**
     * @return Durchschnittliche Lichtausbeute aller dimmbaren Lampen mit LED in Lumen pro Watt
     */
    public float getAverageLuminousFluxPerWattOfDimmedLampsWithLED() { return this.getAverageLuminousFluxPerWatt(DimmedLamp.class); }

    private float getAverageLuminousFluxPerWatt(Class matchType) {
        float sum = 0;
        int num = 0;

        for (Iterator it = this.lamps(); it.hasNext(); ) {
            Lamp lamp = (Lamp) it.next();

            if (matchType.isInstance(lamp) && (lamp.getLeuchtmittel() instanceof LED)) {
                LED led = (LED) lamp.getLeuchtmittel();

                sum += led.getLuminousFlux() / led.getRatedCapacity();
                num += 1;
            }
        }

        return (num == 0) ? -1 : sum / num;
    }

    /**
     * @return Maximale Temperatur aller Lampen mit Glühbirne in Grad Celsius
     */
    public float getMaximumMaxTemperatureOfLampsWithLightBulb() { return this.getMaximumMaxTemperature(Lamp.class); }

    /**
     * @return Maximale Temperatur aller einfachen Lampen mit Glühbirne in Grad Celsius
     */
    public float getMaximumMaxTemperatureOfSimpleLampsWithLightBulb() { return this.getMaximumMaxTemperature(SimpleLamp.class); }

    /**
     * @return Maximale Temperatur aller dimmbaren Lampen mit Glühbirne in Grad Celsius
     */
    public float getMaximumMaxTemperatureOfDimmedLampsWithLightBulb() { return this.getMaximumMaxTemperature(DimmedLamp.class); }

    private float getMaximumMaxTemperature(Class matchType) {
        float max = 0;
        int num = 0;

        for (Iterator it = this.lamps(); it.hasNext(); ) {
            Lamp lamp = (Lamp) it.next();

            if (matchType.isInstance(lamp) && (lamp.getLeuchtmittel() instanceof LightBulb)) {
                LightBulb lightBulb = (LightBulb) lamp.getLeuchtmittel();

                if (lightBulb.getMaxTemperature() > max)
                    max = lightBulb.getMaxTemperature();

                num += 1;
            }
        }

        return (num == 0) ? -1 : max;
    }

    /**
     * @return Gesamtverbrauch aller Lampen in Kilowattstunden
     */
    public float getTotalVerbrauchOfLamps() { return this.getTotalVerbrauch(Lamp.class, Leuchtmittel.class); }

    /**
     * @return Gesamtverbrauch aller einfachen Lampen in Kilowattstunden
     */
    public float getTotalVerbrauchOfSimpleLamps() { return this.getTotalVerbrauch(SimpleLamp.class, Leuchtmittel.class); }

    /**
     * @return Gesamtverbrauch aller dimmbaren Lampen in Kilowattstunden
     */
    public float getTotalVerbrauchOfDimmedLamps() { return this.getTotalVerbrauch(DimmedLamp.class, Leuchtmittel.class); }

    /**
     * @return Gesamtverbrauch aller Lampen mit LED in Kilowattstunden
     */
    public float getTotalVerbrauchOfLampsWithLED() { return this.getTotalVerbrauch(Lamp.class, LED.class); }

    /**
     * @return Gesamtverbrauch aller Lampen mit Glühbirne in Kilowattstunden
     */
    public float getTotalVerbrauchOfLampsWithLightBulb() { return this.getTotalVerbrauch(Lamp.class, LightBulb.class); }

    private float getTotalVerbrauch(Class matchType, Class matchLeuchtmittelType) {
        float sum = 0;
        int num = 0;

        for (Iterator it = this.lamps(); it.hasNext(); ) {
            Lamp lamp = (Lamp) it.next();
            Leuchtmittel leuchtmittel = lamp.getLeuchtmittel();

            if (matchType.isInstance(lamp) && matchLeuchtmittelType.isInstance(leuchtmittel)) {
                sum += lamp.getVerbrauch();
                num += 1;
            }
        }

        return (num == 0) ? -1 : sum;
    }

    /**
     * @param indent What to put in front of each line
     * @return Statistics about this room
     */
    public String getStats(String indent) {
        String out = "";

        out += indent + String.format("%22s || %6s || %6s | %6s || %6s | %6s", this.toString(), "any", "Simple", "Dimmed", "LED", "L.Bulb");
        out += indent + String.format("%70s", " ").replace(" ", "-");

        out += indent + String.format("%22s || %6.02f || %6.02f | %6.02f || %6.02f | %6.02f", "⌀ Nennleistung (W)",
                this.getAverageRatedCapacityOfLamps(),
                this.getAverageRatedCapacityOfSimpleLamps(),
                this.getAverageRatedCapacityOfDimmedLamps(),
                this.getAverageRatedCapacityOfLampsWithLED(),
                this.getAverageRatedCapacityOfLampsWithLightBulb()
        );

        out += indent + String.format("%22s || %6.02f || %6.02f | %6.02f || %6.02f | %6.02f", "⌀ Einschaltdauer (h)",
                this.getAveragePeriodOfLamps(),
                this.getAveragePeriodOfSimpleLamps(),
                this.getAveragePeriodOfDimmedLamps(),
                this.getAveragePeriodOfLampsWithLED(),
                this.getAveragePeriodOfLampsWithLightBulb()
        );

        out += indent + String.format("%22s || %6.02f || %6.02f | %6.02f || %2$6.02f | %6.02f", "⌀ Lichtausbeute (lm/W)",
                this.getAverageLuminousFluxPerWattOfLampsWithLED(),
                this.getAverageLuminousFluxPerWattOfSimpleLampsWithLED(),
                this.getAverageLuminousFluxPerWattOfDimmedLampsWithLED(),
                -1f
        );

        out += indent + String.format("%22s || %6.02f || %6.02f | %6.02f || %6.02f | %2$6.02f", "max. Temperatur (°C)",
                this.getMaximumMaxTemperatureOfLampsWithLightBulb(),
                this.getMaximumMaxTemperatureOfSimpleLampsWithLightBulb(),
                this.getMaximumMaxTemperatureOfDimmedLampsWithLightBulb(),
                -1f
        );

        out += indent + String.format("%22s || %6.02f || %6.02f | %6.02f || %6.02f | %6.02f", "Σ Verbrauch (kWh)",
                this.getTotalVerbrauchOfLamps(),
                this.getTotalVerbrauchOfSimpleLamps(),
                this.getTotalVerbrauchOfDimmedLamps(),
                this.getTotalVerbrauchOfLampsWithLED(),
                this.getTotalVerbrauchOfLampsWithLightBulb()
        );

        return out;
    }
}

