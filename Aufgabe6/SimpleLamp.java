public class SimpleLamp extends  Lamp{
    public enum Color {
        RED, WHITE, BLUE
    }

    private Color color; // Farbe der Lampe

    /**
     * @param leuchtmittel What Leuchtmittel is there in the lamp
     * @param color Farbe der Lampe (Weiß, Rot, Blau)
     */
    public SimpleLamp(Leuchtmittel leuchtmittel, Color color) {
        super(leuchtmittel);

        this.color = color;
    }

    /**
     * @return Farbe der Lampe (Weiß, Rot, Blau)
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * @param indent What to put in front of each line
     * @return Detailled Representation of this
     */
    public String toString(String indent) {
        String out = super.toString(indent);

        out += indent + String.format("Farbe: %s", this.getColor());

        return out;
    }
}
