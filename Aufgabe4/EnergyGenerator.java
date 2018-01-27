/**
 * A unit that uses freely available forms of energy only
 *
 * @invariant EnergyGenerator provides energy in a usable form
 * @invariant EnergyGenerator uses free forms of energy plus some electricity to run
 */
public abstract class EnergyGenerator extends Asset implements Unit {
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
     * @return Average energy input (electric) in kWh per year
     *
     * @ensures energyInput is an average
     * @ensures energyInput is in kWh per year
     * @ensures energyInput is for electric energy only
     */
    public abstract int energyInput();
}
