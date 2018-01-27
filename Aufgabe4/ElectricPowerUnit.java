/**
 * A unit that provides electric power
 *
 * @invariant ElectricPowerUnit provides electric energy
 */
public interface ElectricPowerUnit extends Unit {
    /**
     * @return Average energy output (electric) in kWh per year
     *
     * @ensures energyOutput is an average
     * @ensures energyOutput is in kWh per year
     * @ensures energyOutput is for electric energy only
     */
    public int energyOutput();

    /**
     * @return Average energy input from tradable sources in kWh per year
     *
     * @ensures energyInput is an average
     * @ensures energyInput is in kWh per year
     * @ensures energyInput is for tradable forms of energy only
     * @ensures energyInput does not include free forms of energy
     */
    public int energyInput();

    /**
     * Returns the quality of service of this unit.
     *
     * A value greater than zero indicates that this unit is able to provide reliable and on-demand electric output for
     * a certain period of time in a certain amount. The higher the return value, the more on-demand output.
     *
     * With a return value smaller or equal to zero, this unit is not able to provide on-demand output.
     *
     * A value smaller than zero indicates that consumers connected to this unit have to receive excess electric output,
     * even if it was not requested. The lower the return value, the more excess output.
     *
     * @return Quality of Service
     */
    public int quality();
}
