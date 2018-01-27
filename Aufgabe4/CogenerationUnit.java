/**
 * A unit that uses conventional forms of energy only and provides on-demand electricity and heat
 *
 * @invariant CogenerationUnit provides electric energy
 * @invariant CogenerationUnit provides thermal energy
 * @invariant CogenerationUnit works on-demand
 * @invariant CogenerationUnit uses conventional forms of energy
 */
public interface CogenerationUnit extends Unit {
    /**
     * @return Average energy output (electric, heat) in kWh per year, not including waste heat
     *
     * @ensures energyOutput is an average
     * @ensures energyOutput is in kWh per year
     * @ensures energyOutput is for electric and thermal energy only
     * @ensures energyOutput does not include waste heat
     */
    public int energyOutput();

    /**
     * @return Average energy input in kWh per year
     *
     * @ensures energyInput is an average
     * @ensures energyInput is in kWh per year
     * @ensures energyInput is a total of any forms
     */
    public int energyInput();
}
