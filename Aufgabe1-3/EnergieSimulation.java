public abstract class EnergieSimulation extends Simulation {
    public static EnergieSimulation erzeugeEinfamilienhausSimulation() {
        return new EnergieSimulation() {
            protected void initialisiere() {
                // Speicher...

                Speicher[] batterien = new Speicher[5];
                for (int i = 0; i < batterien.length; i++) {
                    batterien[i] = new Speicher(1000);
                    this.add(batterien[i]);
                }

                // Produzenten...

                SolarPanel dach = new SolarPanel(0.1f, 250);
                this.add(dach);


                Windkraftanlage windrad = new Windkraftanlage(10);
                this.add(windrad);


                // Verbraucher...

                Heizung heizung = new Heizung(10);
                this.add(heizung);

                Kühlschrank kühlschrank = new Kühlschrank(0.25f);
                this.add(kühlschrank);

                Licht licht = new Licht(10);
                this.add(licht);


                // Verbindungen...

                for (Speicher batterie : batterien) {
                    dach.empfänger().add(batterie);
                    windrad.empfänger().add(batterie);

                    heizung.herkunft().add(batterie);
                    kühlschrank.herkunft().add(batterie);
                    licht.herkunft().add(batterie);
                }
            }
        };
    }

    public String formatiereMenge(float menge) {
        return String.format("%+f kWh", menge);
    }
}
