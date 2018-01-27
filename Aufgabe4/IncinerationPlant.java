/**
 * An incineration plant burns garbage to provide on-demand electricity and heat
 *
 * @invariant CogenerationUnit provides electric energy (is-a energy in a usable form)
 * @invariant CogenerationUnit provides thermal energy (is-a energy in a usable form)
 * @invariant CogenerationUnit works on-demand
 * @invariant CogenerationUnit uses garbage and electricity (is-a conventional forms of energy)
 */
public class IncinerationPlant extends EnergyTransformer implements CogenerationUnit, Testable {
    /**
     * @return Average energy output (electric, heat) in kWh per year, not including waste heat
     *
     * @ensures energyOutput is an average
     * @ensures energyOutput is in kWh per year
     * @ensures energyOutput is for electric and thermal energy only
     * @ensures energyOutput does not include waste heat
     */
    public int energyOutput() {
        return 0;
    }

    /**
     * @return Average energy input in kWh per year
     *
     * @ensures energyInput is an average
     * @ensures energyInput is in kWh per year
     * @ensures energyInput is a total of any forms
     * @ensures energyInput is for energy saved in garbage and electricity
     */
    public int energyInput() {
        return 0;
    }

    public final boolean usesFreeFormsOfEnergy() { return false; }
    public final boolean usesConventionalFormsOfEnergy() { return true; }

    public final boolean providesElectricEnergy() { return true; }
    public final boolean providesThermalEnergy() { return true; }

    public final boolean worksOnDemand() { return true; }
}
