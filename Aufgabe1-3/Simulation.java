import java.util.*;

public abstract class Simulation extends HashSet<Simulierbar> implements Simulierbar {
    public Simulation() {
        this.initialisiere();
    }

    public abstract String formatiereMenge(float menge);

    public float simuliere(Wetter wetter, Random zahlengenerator) {
        float abgegeben = 0;
        float nachgeholt = 0;

        for (Simulierbar objekt : this) {
            float fehlstand = objekt.simuliere(wetter, zahlengenerator);

            if (fehlstand > 0) {
                abgegeben += fehlstand;
            } else if (fehlstand < 0) {
                nachgeholt -= fehlstand; // fehlstand ist negativ, neg*neg=pos, nachgeholt wird größer
            }
        }

        return abgegeben - nachgeholt;
    }

    public void zurücksetzen() {
        for (Simulierbar objekt : this)
            objekt.zurücksetzen();
    }

    public float simuliere(Wetter[] wetter, int seed) {
        Random zahlengenerator = new Random(seed);

        float abgegeben = 0;
        float nachgeholt = 0;

        for (int i = 0; i < wetter.length; i++) {
            float fehlstand = this.simuliere(wetter[i], zahlengenerator);

            if (fehlstand > 0) {
                abgegeben += fehlstand;
            } else if (fehlstand < 0) {
                nachgeholt -= fehlstand; // fehlstand ist negativ, neg*neg=pos, nachgeholt wird größer
            }
        }

        this.zurücksetzen();
        return abgegeben - nachgeholt;
    }

    protected abstract void initialisiere();
}
