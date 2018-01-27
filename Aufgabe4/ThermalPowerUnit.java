/**
 * A unit that provides thermal power
 *
 * @invariant ThermalPowerUnit provides thermal energy
 */
public interface ThermalPowerUnit extends Unit {
    /**
     * @return Average energy output (thermal) in kWh per year, not including waste heat
     *
     * @ensures energyOutput is an average
     * @ensures energyOutput is in kWh per year
     * @ensures energyOutput is for thermal energy only
     * @ensures energyOutput does not include waste heat
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


}
