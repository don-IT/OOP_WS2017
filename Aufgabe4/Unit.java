/**
 * A power unit that has some energy input and some energy output
 */
public interface Unit {
    /**
     * @return Investment costs in Euro
     *
     * @ensures investmentCosts is in Euro
     */
    public int investmentCosts();

    /**
     * @return Average running costs in Euro per year
     *
     * @ensures runningCosts as an average
     * @ensures runningCosts is in Euro per year
     */
    public int runningCosts();

    /**
     * @return Average energy output in kWh per year
     *
     * @ensures energyOutput is an average
     * @ensures energyOutput is in kWh per year
     */
    public int energyOutput();

    /**
     * @return Average energy input in kWh per year
     *
     * @ensures energyInput is an average
     * @ensures energyInput is in kWh per year
     */
    public int energyInput();
}
