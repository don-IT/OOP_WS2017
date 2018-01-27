import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;

/**
 * Neben dem Testprogramm soll die Klasse Test.java als Kommentar eine kurze, aber verständliche Beschreibung der
 * Aufteilung der Arbeiten auf die einzelnen Gruppenmitglieder enthalten — wer hat was gemacht.
 *
 * 11.10. (Skype): Git und IntelliJ einrichten (alle)
 * 13.10. (Operngasse): Besprechung der Aufgabenstellung 1, Erstellung von Klassendiagrammen und Stubs (alle)
 *
 * Diana und Omar: Implementierung der Modelklassen
 * Wolfi: Implementierung der Testklassen, Integration
 *
 * 18.10. (Operngasse): Besprechung der Aufgabenstellung 2, Klassendiagramme (alle)
 * 23.10. (Operngasse und Freihaus): Besprechung des Feedbacks, Besprechung mit Tutor (alle)
 * 24.10. (Skype): Klassenhierarchie (alle)
 *
 * Diana und Omar: Modelklassen
 * Wolfi: Simulationsklassen
 *
 * Diana und Omar: alles für Aufgabe 3
 */

public abstract class Test {
    public static void main(String[] args) {
        // Seeds...

        if (args.length == 0)
            args = new String[] {
                    "0",
                    "1",
                    Double.toString(Math.random()).substring(2, 6),
                    Double.toString(Math.random()).substring(2, 6)
        };

        int[] seeds = Arrays.stream(args).mapToInt(arg -> Integer.parseInt(arg)).toArray();




        // Wetterdaten...

        Hashtable<String, Wetter[]> wetterdaten = new Hashtable<String, Wetter[]>();

        wetterdaten.put("Hohe Warte, 2015-07", Wetter.liesCSV(ClassLoader.getSystemClassLoader().getResource("wetterdaten/zamg-hohewarte-2015-07.csv").getPath()));
        wetterdaten.put("Hohe Warte, 2017-09", Wetter.liesCSV(ClassLoader.getSystemClassLoader().getResource("wetterdaten/zamg-hohewarte-2017-09.csv").getPath()));



        // Simulationen...

        Hashtable<String, Simulation> simulationen = new Hashtable<String, Simulation>();

        simulationen.put("Wasser: Einfamilienhaus", WasserSimulation.erzeugeEinfamilienhausSimulation());
        simulationen.put("Wasser: Toiletten in einem Bürogebäude", WasserSimulation.erzeugeBürogebäudeSimulation());
        simulationen.put("Energie: Passivenergie-Einfamilienhaus", EnergieSimulation.erzeugeEinfamilienhausSimulation());



        // Ausführen...

        simulationen.forEach((String simulationsTitel, Simulation simulation) -> {
            System.out.println(String.format("Teste \"%s\" (%d Objekte):", simulationsTitel, simulation.size()));

            wetterdaten.forEach((String wetterTitel, Wetter[] wetter) -> {
                System.out.println(String.format("\tWetterdaten \"%s\" (%d Tage)", wetterTitel, wetter.length));

                for (int seed : seeds) {
                    float fehlstand = simulation.simuliere(wetter, seed);
                    System.out.println(String.format("\t\tFehlstand für Seed %d: %+f", seed, fehlstand));
                }

                System.out.println();
            });

            System.out.println();
        });
    }
}