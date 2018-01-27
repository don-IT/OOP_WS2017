import java.util.*;
import java.util.function.*;

/**
 * Ausgabenverteilung:
 * - Omar und Diana: Typhierarchie, Visitor-Pattern, einige Tests
 * - Wolfi: kontrolliert, deutsche Sprache ausgebessert, mehr Tests
 */
public class Test {
    public static void main(String[] args) {
        new Test(Heizungsanlage::new, NormalesRohrKlein::new, NormalesRohrGross::new).testeGleicherAnlagetyp();
        new Test(Industrieanlage::new, SaeurebestaendigesRohrKlein::new, SaeurebestaendigesRohrGross::new).testeGleicherAnlagetyp();

        testeUnterschiedlicherAnlagetyp();
        testeAusgabe();
    }

    private static void testeAusgabe() {
        Random random = new Random(0);
        Rohrverwaltung app = new Rohrverwaltung();

        System.out.println("\nLagere 5 zufällige Rohre...");

        for (int i = 0; i < 5; i++)
            app.lagere(erzeugeZufaelligesRohr(random));

        System.out.println("Führe app.lagerliste() und app.lagerwert() aus...\n");

        app.lagerliste();
        app.lagerwert();

        System.out.println("\nErstelle eine Heizungsanlage mit kleinem und eine Industrieanlage mit großem Temperaturbereich...");

        Anlage[] anlagen = { new Heizungsanlage(), new Industrieanlage() };

        System.out.println("Montiere jeweils Rohre mit den Längen 50cm, 100cm und 200cm...");

        for (Anlage anlage: anlagen) {
            app.montiere(anlage, 50);
            app.montiere(anlage, 100);
            app.montiere(anlage, 200);
        }

        System.out.println("Führe app.anlagenliste(heizungsanlage) und app.anlagenwert(heizungsanlage) aus...\n");

        app.anlagenliste(anlagen[0]);
        app.anlagenwert(anlagen[0]);

        System.out.println("\nFühre app.anlagenliste(industrieanlage) und app.anlagenwert(industrieanlage) aus...\n");

        app.anlagenliste(anlagen[1]);
        app.anlagenwert(anlagen[1]);

        System.out.println("\nFühre nun wieder app.lagerliste() und app.lagerwert() aus...\n");

        app.lagerliste();
        app.lagerwert();
    }

    private static void testeUnterschiedlicherAnlagetyp() {
        try {
            System.out.print("Teste Nicht-Kompatibilität zwischen Heizungs- und Industrieanlage bzw. " +
                    "normalem und säurebeständigem Rohr... ");

            Rohrverwaltung app = new Rohrverwaltung();

            // Je eine Anlage
            Anlage heizungsanlageKlein = new Heizungsanlage(false);
            Anlage heizungsanlageGross = new Heizungsanlage(true);
            Anlage industrieanlageKlein = new Industrieanlage(false);
            Anlage industrieanlageGross = new Industrieanlage(true);

            // Je ein Rohr
            Rohr normalesRohrKlein = new NormalesRohrKlein(100, 10);
            Rohr normalesRohrGross = new NormalesRohrGross(100, 10);
            Rohr saeurebestaendigesRohrKlein = new SaeurebestaendigesRohrKlein(100, 10);
            Rohr saeurebestaendigesRohrGross = new SaeurebestaendigesRohrGross(100, 10);

            // normale Rohre einlagern
            app.lagere(normalesRohrKlein);
            app.lagere(normalesRohrGross);

            // Versuche, normale Rohre in Industrieanlage zu montieren -> geht ned
            app.montiere(industrieanlageKlein);
            app.montiere(industrieanlageGross);

            if (app.lager().rohre().size() != 2) throw new RuntimeException();

            // normale Rohre in Heizungsanlage montieren -> geht, Lager nun wieder leer
            app.montiere(heizungsanlageKlein);
            app.montiere(heizungsanlageGross);

            if (app.lager().rohre().size() != 0) throw new RuntimeException();

            // säurebeständige Rohre einlagern
            app.lagere(saeurebestaendigesRohrKlein);
            app.lagere(saeurebestaendigesRohrGross);

            // Versuche, säurebeständige Rohre in Heizungsanlage zu montieren -> geht ned
            app.montiere(heizungsanlageKlein);
            app.montiere(heizungsanlageGross);

            if (app.lager().rohre().size() != 2) throw new RuntimeException();

            System.out.println("Pass");
        } catch (RuntimeException e) {
            System.out.println("Fail");
            throw e;
        }
    }

    private static Anlage erzeugeZufaelligeAnlage(Random random) {
        if (random.nextBoolean()) {
            return new Heizungsanlage(random.nextBoolean());
        } else {
            return new Industrieanlage(random.nextBoolean());
        }
    }

    private static Rohr erzeugeZufaelligesRohr(Random random) {
        double laenge = 10 + 390 * random.nextDouble();

        if (random.nextBoolean()) {
            // Normales Rohr
            return (random.nextDouble() < 0.5)
                    ? new NormalesRohrKlein(laenge, 5 + random.nextInt(10))
                    : new NormalesRohrGross(laenge, 15 + random.nextInt(10));
        } else {
            // Säurebeständiges Rohr
            return (random.nextDouble() < 0.5)
                    ? new SaeurebestaendigesRohrKlein(laenge, 25 + random.nextInt(10))
                    : new SaeurebestaendigesRohrGross(laenge, 35 + random.nextInt(10));
        }
    }

    private Function<Boolean, Anlage> erzeugeAnlage;
    private BiFunction<Double, Integer, Rohr> erzeugeRohrKlein;
    private BiFunction<Double, Integer, Rohr> erzeugeRohrGross;

    private Test(
            Function<Boolean, Anlage> erzeugeAnlage,
            BiFunction<Double, Integer, Rohr> erzeugeRohrKlein,
            BiFunction<Double, Integer, Rohr> erzeugeRohrGross
    ) {
        this.erzeugeAnlage = erzeugeAnlage;
        this.erzeugeRohrKlein = erzeugeRohrKlein;
        this.erzeugeRohrGross = erzeugeRohrGross;
    }

    private void testeGleicherAnlagetyp() {
        // Teste Anlage und Rohr aus dem selben Temperaturnereich
        this.testeGleicherTemperaturbereich(false);
        this.testeGleicherTemperaturbereich(true);

        // Teste Anlagen und Rohre aus unterschiedlichem Temperaturbereich
        this.testeUnterschiedlicherTemperaturbereich();

        // Teste dynamische Auswahl von Rohren und automatisches kürzen der Rohre
        this.testeDynamischeRohrauswahlUndKuerzen();
    }

    private void testeGleicherTemperaturbereich(boolean grosserTemperaturbereich) {
        try {
            // Anlage erstellen
            Anlage anlage = this.erzeugeAnlage.apply(grosserTemperaturbereich);

            Rohr rohr = grosserTemperaturbereich
                    ? this.erzeugeRohrGross.apply(100.0, 10)
                    : this.erzeugeRohrKlein.apply(100.0, 10);

            System.out.format("Teste einfaches Zusammenspiel von %s und %s... ",
                    anlage.getClass().getSimpleName(), rohr.getClass().getSimpleName());

            Lager lager = new Lager();
            Rohrverwaltung app = new Rohrverwaltung(lager);

            // 100cm Rohr einlagern
            app.lagere(rohr);

            if (lager.rohre().size() != 1) throw new RuntimeException();

            // irgendein (-> 100cm) Rohr montieren
            app.montiere(anlage);

            if (lager.rohre().size() != 0) throw new RuntimeException();
            if (anlage.rohre().size() != 1) throw new RuntimeException();
            if (rohr.laenge() != 100.0) throw new RuntimeException();

            // dieses Rohr wieder demontieren
            app.demontiere(anlage, rohr);

            if (lager.rohre().size() != 1) throw new RuntimeException();
            if (anlage.rohre().size() != 0) throw new RuntimeException();
            if (rohr.laenge() != 100.0) throw new RuntimeException();

            // 50cm montieren -> 50cm Rest bleiben im Lager
            app.montiere(anlage, 50.0);

            if (lager.rohre().size() != 1) throw new RuntimeException();
            if (anlage.rohre().size() != 1) throw new RuntimeException();
            if (rohr.laenge() != 50.0) throw new RuntimeException();

            // dieses Rohr wieder demontieren
            app.demontiere(anlage, rohr);

            if (lager.rohre().size() != 2) throw new RuntimeException();
            if (anlage.rohre().size() != 0) throw new RuntimeException();

            // beide Rohre (das vorher montierte bzw. demontierte sowie der Rest) zusammen 100cm
            if (lager.rohre().stream().mapToDouble(Rohr::laenge).sum() != 100) throw new RuntimeException();

            // Versuch, 100cm zu montieren -> nicht möglich, nur mehr 2x 50cm da
            app.montiere(anlage, 100);

            if (lager.rohre().size() != 2) throw new RuntimeException();
            if (anlage.rohre().size() != 0) throw new RuntimeException();

            System.out.println("Pass");
        } catch (RuntimeException e) {
            System.out.println("Fail");
            throw e;
        }
    }

    private void testeUnterschiedlicherTemperaturbereich() {
        try {
            // Zwei unterschiedliche Anlagen
            Anlage anlageKlein = this.erzeugeAnlage.apply(false);
            Anlage anlageGross = this.erzeugeAnlage.apply(true);

            // Zwei unterschiedliche Rohre
            Rohr rohrKlein = this.erzeugeRohrKlein.apply(100.0, 10);
            Rohr rohrGross = this.erzeugeRohrGross.apply(100.0, 10);

            System.out.format("Teste Zusammenspiel zwischen %s, %s, %s... ",
                    anlageKlein.getClass().getSimpleName(),
                    rohrKlein.getClass().getSimpleName(),
                    rohrGross.getClass().getSimpleName());

            Rohrverwaltung app = new Rohrverwaltung();

            // Beide Rohre einlagern
            app.lagere(rohrKlein);
            app.lagere(rohrGross);

            // dynamisch kleines Rohr montieren
            app.montiere(anlageKlein);

            if (!anlageKlein.rohre().contains(rohrKlein)) throw new RuntimeException();

            // demontieren, dynamisch großes Rohr montieren
            app.demontiere(anlageKlein, rohrKlein);
            app.montiere(anlageGross);

            if (!anlageGross.rohre().contains(rohrGross)) throw new RuntimeException();

            // demontieren, dynamisch beide Rohre montieren (groß als fallback)
            app.demontiere(anlageGross, rohrGross);
            app.montiere(anlageKlein);
            app.montiere(anlageKlein);

            if (anlageKlein.rohre().size() != 2) throw new RuntimeException();

            // demontieren; versuchen, beide Rohre dynamisch zu montieren, fallback greift aber nicht
            app.demontiere(anlageKlein, rohrKlein);
            app.demontiere(anlageKlein, rohrGross);
            app.montiere(anlageGross);
            app.montiere(anlageGross); // --> null

            if (anlageGross.rohre().size() != 1) throw new RuntimeException();

            System.out.println("Pass");
        } catch (RuntimeException e) {
            System.out.println("Fail");
            throw e;
        }
    }

    private void testeDynamischeRohrauswahlUndKuerzen() {
        try {
            Anlage anlage = this.erzeugeAnlage.apply(false);

            System.out.format("Teste dynamische Rohrauswahl und Kürzen von Rohren mit %s... ",
                    anlage.getClass().getSimpleName());

            Rohrverwaltung app = new Rohrverwaltung();
            Rohr rohrLangTeuer = this.erzeugeRohrKlein.apply(200.0, 20);
            Rohr rohrKurzTeuer = this.erzeugeRohrKlein.apply(50.0, 20);
            Rohr rohrLangBillig = this.erzeugeRohrKlein.apply(200.0, 10);
            Rohr rohrKurzBillig = this.erzeugeRohrKlein.apply(50.0, 10);

            app.lagere(rohrLangTeuer);
            app.lagere(rohrKurzTeuer);
            app.lagere(rohrLangBillig);
            app.lagere(rohrKurzBillig);

            /* Lagerstand:
             * 200cm x 20ct = 40,-
             * 200cm x 10ct = 20,-
             * 50cm x 20ct = 10,-
             * 50cm x 10ct = 5,-
             */

            if (app.montiere(anlage, 30.0) != rohrKurzBillig) // -> 20cm x 10ct = Wert 2,- bleibt im Lager
                throw new RuntimeException();

            app.demontiere(anlage, rohrKurzBillig);

            /* Lagerstand:
             * 200cm x 20ct = 40,- <- rohrLangTeuer
             * 200cm x 10ct = 20,- <- rohrLangBillig
             * 50cm x 20ct = 10,- <- rohrKurzTeuer
             * 30cm x 10ct = 3,- <- rohrKurzBillig
             * 20cm x 10ct = 2,-
             */

            if (app.montiere(anlage, 150.0) != rohrLangBillig) // -> 50cm Wert x 10ct = 5,- bleibt im Lager
                throw new RuntimeException();

            app.demontiere(anlage, rohrLangBillig);

            /* Lagerstand:
             * 200cm x 20ct = 40,- <- rohrLangTeuer
             * 150cm x 10ct = 15,- <- rohrLangBillig
             * 50cm x 20ct = 10,- <- rohrKurzTeuer
             * 50cm x 10ct = 5,-
             * 30cm x 10ct = 3,- <- rohrKurzBillig
             * 20cm x 10ct = 2,-
             */

            if (app.montiere(anlage, 160.0) != rohrLangTeuer) // -> 40cm Wert x 20ct = 8,- bleibt im Lager
                throw new RuntimeException();

            app.demontiere(anlage, rohrLangTeuer);

            /* Lagerstand:
             * 160cm x 20ct = 32,- <- rohrLangTeuer
             * 150cm x 10ct = 15,- <- rohrLangBillig
             * 50cm x 20ct = 10,- <- rohrKurzTeuer
             * 40cm x 20ct = 8,-
             * 50cm x 10ct = 5,-
             * 30cm x 10ct = 3,- <- rohrKurzBillig
             * 20cm x 10ct = 2,-
             */

            // Anonymes Stück 50cm x 10 ct
            app.demontiere(anlage, app.montiere(anlage, 45.0)); // -> 5cm x 10ct = 0,50 bleibt im Lager

            /* Lagerstand:
             * 160cm x 20ct = 32,- <- rohrLangTeuer
             * 150cm x 10ct = 15,- <- rohrLangBillig
             * 50cm x 20ct = 10,- <- rohrKurzTeuer
             * 40cm x 20ct = 8,-
             * 45cm x 10ct = 4,50
             * 30cm x 10ct = 3,- <- rohrKurzBillig
             * 20cm x 10ct = 2,-
             * 5cm x 10ct = 0,50
             */

            if (app.lager().rohre().stream().mapToDouble(Rohr::laenge).sum() != 500) throw new RuntimeException();

            double[] erwarteteWerte = { 32.0, 15.0, 10.0, 8.0, 4.5, 3.0, 2.0, 0.5 };
            double[] tatsaechlicheWerte = app.lager().rohre(Comparator.comparingDouble(Rohr::wert).reversed())
                    .stream().mapToDouble(r -> r.wert()).toArray();

            if (!Arrays.equals(erwarteteWerte, tatsaechlicheWerte)) throw new RuntimeException();

            System.out.println("Pass");
        } catch (RuntimeException e) {
            System.out.println("Fail");
            throw e;
        }
    }
}
