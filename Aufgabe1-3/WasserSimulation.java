public abstract class WasserSimulation extends Simulation {
    public static WasserSimulation erzeugeEinfamilienhausSimulation() {
        return new WasserSimulation() {
            public void initialisiere() {
                // Speicher...

                Speicher großeRegentonne = new Speicher(5000);
                this.add(großeRegentonne);

                Speicher kleineRegentonne = new Speicher(1000);
                this.add(kleineRegentonne);


                // Produzenten...

                Wassersammler hausdach = new Wassersammler(100);
                hausdach.empfänger().add(großeRegentonne);
                hausdach.empfänger().add(kleineRegentonne);
                this.add(hausdach);

                Wassersammler garagendach = new Wassersammler(25);
                garagendach.empfänger().add(kleineRegentonne);
                this.add(garagendach);


                // Konsumenten...

                Bewässerungsanlage großesBlumenbeet = new Bewässerungsanlage(50);
                großesBlumenbeet.herkunft().add(großeRegentonne);
                this.add(großesBlumenbeet);

                Bewässerungsanlage kleinesBlumenbeet = new Bewässerungsanlage(20);
                kleinesBlumenbeet.herkunft().add(großeRegentonne);
                this.add(kleinesBlumenbeet);

                Toilette toilette = new Toilette(3, 3);
                toilette.herkunft().add(kleineRegentonne);
                this.add(toilette);
            }
        };
    }

    public static WasserSimulation erzeugeBürogebäudeSimulation() {
        return new WasserSimulation() {
            protected void initialisiere() {
                // Speicher...

                Speicher zisterne = new Speicher(10000);
                this.add(zisterne);


                // Produzenten

                Wassersammler dach = new Wassersammler(500);
                dach.empfänger().add(zisterne);
                this.add(dach);


                // Konsumenten

                for (int i = 0; i < 30; i++) {
                    Toilette toilette = new Toilette(10, 2.5f);
                    toilette.herkunft().add(zisterne);
                    this.add(toilette);
                }
            }
        };
    }

    public String formatiereMenge(float menge) {
        return String.format("%+f Liter", menge);
    }
}
