public class DimmedLamp extends Lamp {
    private float dimmedPerc; // wie viel Prozent die Lampe gedimmt ist (Gleitkommazahl)

    /**
     * @param leuchtmittel What Leuchtmittel is there in the lamp
     * @param dimmedPerc Wie viel Prozent die Lampe gedimmt ist (0..1)
     */
    public DimmedLamp(Leuchtmittel leuchtmittel, float dimmedPerc) {
        super(leuchtmittel);

        this.setDimmedPerc(dimmedPerc);
    }

    /**
     * @param dimmedPerc Wie viel Prozent die Lampe gedimmt ist (0..1)
     */
    public void setDimmedPerc(float dimmedPerc) {
        if ((dimmedPerc < 0) || (dimmedPerc > 1))
            throw new IllegalArgumentException("Please, insert number >= 0 and <= 1");

        this.dimmedPerc=dimmedPerc;
    }

    /**
     * @return Wie viel Prozent die Lampe gedimmt ist (0..1)
     */
    public float getDimmedPerc() {
        return this.dimmedPerc;
    }

    /**
     * @param indent What to put in front of each line
     * @return Detailled Representation of this
     */
    public String toString(String indent) {
        String out = super.toString(indent);

        out += indent + String.format("Dimmgrad: %.02f lm", this.getDimmedPerc());

        return out;
    }
}
