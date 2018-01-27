/**
 * A solar-thermal system uses sunlight (plus some electricity to operate) to provide heat
 *
 * @invariant SolarThermalSystem uses free forms of energy plus some electricity to run
 * @invariant SolarThermalSystem provides thermal energy (is-a usable form)
 */
public class SolarThermalSystem extends EnergyGenerator implements ThermalPowerUnit, Testable {
    /**
     * @return Average energy output (heat) in kWh per year, not including waste heat
     *
     * @ensures energyOutput is an average
     * @ensures energyOutput is in kWh per year
     * @ensures energyOutput is for thermal energy only
     * @ensures energyOutput does not include waste heat
     */
    public int energyOutput() {
        return 0;
    }

    /**
     * @return Average energy input (electric) in kWh per year
     *
     * @ensures energyInput is an average
     * @ensures energyInput is in kWh per year
     * @ensures energyInput does not include free forms of energy
     * @ensures energyInput is for electric energy only (is-a tradable forms of energy)
     */
    public int energyInput() {
        return 0;
    }

    public final boolean usesFreeFormsOfEnergy() { return true; }
    public final boolean usesConventionalFormsOfEnergy() { return false; }

    public final boolean providesElectricEnergy() { return false; }
    public final boolean providesThermalEnergy() { return true; }

    public final boolean worksOnDemand() { return false; }
}
