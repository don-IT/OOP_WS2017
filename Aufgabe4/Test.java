import java.util.function.Supplier;

/**
 * Notizen zu Untertypbeziehungen:
 *
 * - ThermalPowerUnit (liefert Wärme) erstellt, um den Kontrast zur ElectricPowerUnit (liefert elektrischen Strom) zu
 *   verdeutlichen.
 *
 * - EnergyTransformer (nutzt konventionelle Energieträger) erstellt, um den Kontrast zum EnergyGenerator (nutzt freie
 *   Energieträger plus Elektrizität) zu verdeutlichen.
 *
 * - HeatingSystem ist eine ThermalPowerUnit, die on-demand arbeitet. Bei der ElectricPowerUnit wird der Grad der
 *   "on-demand'iness" durch die quality()-Methode abgebildert.
 *
 * - Warum ist CogenerationUnit nicht Untertyp von ElectricPowerUnit und ThermalPowerUnit (interface extends A, B)?
 *   Weil von einer ElectricPowerUnit verlangt wird, dass energyOutput (nur) die gelieferte *elektrische* Energie zurück
 *   gibt, die CogenerationUnit aber Elektrizität *plus* Wärme zurück gibt.
 *
 * - Warum ist ElectricPowerUnit bzw. ThermalPowerUnit nicht Untertyp von CogenerationUnit?
 *   Weil von einer CogenerationUnit verlangt wird, dass sie *konventionelle* Energieformen nutzt, die ElectricPowerUnit
 *   aber mit *diversen* (d.h. auch freien) Energieformen arbeitet.
 *
 * - IncinerationPlant als Untertyp von EnergyTransformer und CogenerationUnit erstellt.
 *
 *
 * Notizen zur Implementierung:
 *
 * Ja, wir hätten fancy Funktionalität in die konkreten Units einbauen können, wie z.B.
 * - someUnit.provide(energyTypes.ELECTRICAL, 10_000) throws IDontProvideElectricalException
 * - someUnit.provideOnDemand(energyTypes.THERMAL, 100) throws ICannotProvideOnDemandException
 * - someUnit.use(energySources.FREE) throws IDontRunOnFreeSourcesException
 *
 * ... aber laut Aufgabenstellung ist das nicht zwingend nötig. Wir testen die Untertypbeziehungen stattdessen über das
 * Testable Interface, welches erst direkt in den konkreten Typen implementiert wird (d.h. deren Funktionalität nicht
 * geerbt wird, genauso wie es bei o.g. fancy Funktionalität der Fall wäre). Dies würde Fehler ebenfalls aufdecken,
 * erspart uns aber viel Implementierungsarbeit.
 *
 *
 * Wer hat was gemacht:
 * - Wolfi: alles design by contract, Testfälle
 * - Diana und Omar: alles kontrolliert
 */

public class Test {
    public static void main(String[] args) throws Exception {
        testEnergyGenerators();
        testEnergyTransformers();

        testElectricPowerUnits();
        testThermalPowerUnits();
        testCogenerationUnits();

        testHeatingSystems();
    }



    /* Es folgen die einzelnen Testfälle */

    private static void testEnergyGenerators() throws Exception {
        // Von EnergyGenerators wird erwartet, dass sie freie Träger, nicht konventionelle, nutzen
        System.out.println("Teste EnergyGenerators...");
        for (EnergyGenerator unit : createEnergyGenerators()) {
            String className = unit.getClass().getName();
            Testable t = (Testable) unit;

            if (! t.usesFreeFormsOfEnergy()) {
                throw new Exception(String.format("%s should use free forms of energy, but does not.", className));
            } else if (t.usesConventionalFormsOfEnergy()) {
                throw new Exception(String.format("%s should not use conventional forms of energy, but does.", className));
            }

            System.out.println(String.format("- %s nutzt %d kWh an freien Energieträgern pro Jahr.", className, unit.energyInput()));
        }
    }

    private static void testEnergyTransformers() throws Exception {
        // Von EnergyTransformers wird erwartet, dass sie konventionelle Träger, nicht freie, nutzen
        System.out.println("Teste EnergyTransformers...");
        for (EnergyTransformer unit : createEnergyTransformers()) {
            String className = unit.getClass().getName();
            Testable t = (Testable) unit;

            if (! t.usesConventionalFormsOfEnergy()) {
                throw new Exception(String.format("%s should use conventional forms of energy, but does not.", className));
            } else if (t.usesFreeFormsOfEnergy()) {
                throw new Exception(String.format("%s should not use free forms of energy, but does.", className));
            }

            System.out.println(String.format("- %s nutzt %d kWh an konventionellen Energieträgern pro Jahr.", className, unit.energyInput()));
        }
    }

    private static void testElectricPowerUnits() throws Exception {
        // Von ElectricPowerUnits wird erwartet, dass sie elektrische, nicht thermische, Energie produzieren
        System.out.println("Teste ElectricPowerUnits...");
        for (ElectricPowerUnit unit : createElectricPowerUnits()) {
            String className = unit.getClass().getName();
            Testable t = (Testable) unit;

            if (! t.providesElectricEnergy()) {
                throw new Exception(String.format("%s should provide electric energy, but does not.", className));
            } else if (t.providesThermalEnergy()) {
                throw new Exception(String.format("%s should not provide thermal energy, but does.", className));
            }

            System.out.println(String.format("- %s liefert %d kWh elektrische Energie pro Jahr.", className, unit.energyOutput()));
        }
    }

    private static void testThermalPowerUnits() throws Exception {
        // Von ThermalPowerUnits wird erwartet, dass sie thermische, nicht elektrische, Energie produzieren
        System.out.println("Teste ThermalPowerUnits...");
        for (ThermalPowerUnit unit : createThermalPowerUnits()) {
            String className = unit.getClass().getName();
            Testable t = (Testable) unit;

            if (! t.providesThermalEnergy()) {
                throw new Exception(String.format("%s should provide thermal energy, but does not.", className));
            } else if (t.providesElectricEnergy()) {
                throw new Exception(String.format("%s should not provide electric energy, but does.", className));
            }

            System.out.println(String.format("- %s liefert %d kWh thermische Energie pro Jahr.", className, unit.energyOutput()));
        }
    }

    private static void testCogenerationUnits() throws Exception {
        // Von CogenerationUnits wird erwartet, dass sie elektrische und thermische Energie produzieren
        System.out.println("Teste CogenerationUnits...");
        for (CogenerationUnit unit : createCogenerationUnits()) {
            String className = unit.getClass().getName();
            Testable t = (Testable) unit;

            if (! t.providesElectricEnergy()) {
                throw new Exception(String.format("%s should provide electric energy, but does not.", className));
            } else if (! t.providesThermalEnergy()) {
                throw new Exception(String.format("%s should provide thermal energy, but does not.", className));
            }

            System.out.println(String.format("- %s liefert %d kWh elektrische+thermische Energie pro Jahr.", className, unit.energyOutput()));
        }
    }

    private static void testHeatingSystems() throws Exception {
        // Von HeatingSystems wird erwartet, dass sie (immer) on-demand arbeiten
        System.out.println("Teste HeatingSystems...");
        for (HeatingSystem unit : createHeatingSystems()) {
            String className = unit.getClass().getName();
            Testable t = (Testable) unit;

            if (! t.worksOnDemand()) {
                throw new Exception(String.format("%s should work on-demand, but does not.", className));
            }

            System.out.println(String.format("- %s arbeitet immer on-demand.", className));
        }
    }



    /* Es folgen Factories für alle konkreten Typen */

    private static Supplier<IncinerationPlant> incinerationPlantFactory = IncinerationPlant::new;
    private static Supplier<HeatPump> heatPumpFactory = HeatPump::new;
    private static Supplier<SolarThermalSystem> solarThermalSystemFactory = SolarThermalSystem::new;
    private static Supplier<PhotovoltaicSystem> photovoltaicSystemFactory = PhotovoltaicSystem::new;
    private static Supplier<WindTurbine> windTurbineFactory = WindTurbine::new;



    /* Hier erfolgt die Gruppierung der konkreten Klassen (siehe Factories oben) entsprechend ihrer abstrakten Typen
       bzw. Interfaces */

    private static EnergyGenerator[] createEnergyGenerators() {
        return new EnergyGenerator[] {
                heatPumpFactory.get(),
                solarThermalSystemFactory.get(),
                photovoltaicSystemFactory.get(),
                windTurbineFactory.get()
        };
    }

    private static EnergyTransformer[] createEnergyTransformers() {
        return new EnergyTransformer[] {
                incinerationPlantFactory.get()
        };
    }

    private static ElectricPowerUnit[] createElectricPowerUnits() {
        return new ElectricPowerUnit[] {
                photovoltaicSystemFactory.get(),
                windTurbineFactory.get()
        };
    }

    private static ThermalPowerUnit[] createThermalPowerUnits() {
        return new ThermalPowerUnit[] {
                heatPumpFactory.get(),
                solarThermalSystemFactory.get()
        };
    }

    private static CogenerationUnit[] createCogenerationUnits() {
        return new CogenerationUnit[] {
                incinerationPlantFactory.get()
        };
    }

    private static HeatingSystem[] createHeatingSystems() {
        return new HeatingSystem[] {
                heatPumpFactory.get()
        };
    }
}