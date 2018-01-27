/**
 * A wind turbine uses wind (plus some electricity to operate) to provide electricity
 *
 * @invariant WindTurbine uses free forms of energy plus some electricity to run
 * @invariant WindTurbine provides electric energy (is-a usable form)
 */
public class WindTurbine extends EnergyGenerator implements ElectricPowerUnit, Testable {
    /**
     * @return Average energy output (electric) in kWh per year
     *
     * @ensures energyOutput is an average
     * @ensures energyOutput is in kWh per year
     * @ensures energyOutput is for electric energy only
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
    public int quality() {
        return 0;
    }

    public final boolean usesFreeFormsOfEnergy() { return true; }
    public final boolean usesConventionalFormsOfEnergy() { return false; }

    public final boolean providesElectricEnergy() { return true; }
    public final boolean providesThermalEnergy() { return false; }

    public final boolean worksOnDemand() { return this.quality() > 0; }
}
