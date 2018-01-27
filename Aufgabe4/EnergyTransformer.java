/**
 * A unit that uses conventional forms of energy only
 *
 * @invariant EnergyTransformer provides energy in a usable form
 * @invariant EnergyTransformer uses conventional forms of energy only
 */
public abstract class EnergyTransformer extends Asset implements Unit {
    /**
     * @return Average running costs in Euro per year
     *
     * @ensures runningCosts as an average
     * @ensures runningCosts is in Euro per year
     */
    public int runningCosts() {
        return 0;
    }

    /**
     * @return Average energy output in kWh per year, not including waste heat
     *
     * @ensures energyOutput is an average
     * @ensures energyOutput is in kWh per year
     * @ensures energyOutput does not include waste heat
     */
    public abstract int energyOutput();

    /**
     * @return Average energy input (total of any) in kWh per year
     *
     * @ensures energyInput is an average
     * @ensures energyInput is in kWh per year
     * @ensures energyInput is a total of any forms
     */
    public abstract int energyInput();
}
