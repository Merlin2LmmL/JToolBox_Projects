/**
 * Knoten, doppelt verkettet durch "Pointer" in einer Liste
 *
 * @author Merlin Ortner
 * @version 1.0.2
 */
public class Knoten {
    public Knoten nachfolger;
    public Knoten vorgänger;
    private Kraftfahrzeug fahrzeug;

    public Knoten(Kraftfahrzeug fahrzeugNeu, Knoten k) {
        this(fahrzeugNeu, null, k);
    }

    public Knoten(Kraftfahrzeug fahrzeugNeu, Knoten v, Knoten n) {
        fahrzeug = fahrzeugNeu;
        vorgänger = v;
        nachfolger = n;
        if (n != null) {
            n.vorgänger = this; // Rückverweis des Nachfolgers
        }
    }

    public void amEndeEinfügen(Kraftfahrzeug fneu) {
        if (nachfolger != null) {
            nachfolger.amEndeEinfügen(fneu);
        } else {
            nachfolger = new Knoten(fneu, this, null);
        }
    }
    
    public Knoten getKnotenByNameRecursive(String bezeichnung) {
        if (fahrzeug.bezeichnung.equals(bezeichnung)) return this;
        return nachfolger.getKnotenByNameRecursive(bezeichnung);
    }
    
    public int countNachfoler() {
        if (nachfolger != null)
            return nachfolger.countNachfoler() + 1;
        else
            return 1;
    }
    
    public Kraftfahrzeug getKraftfahrzeug() { return fahrzeug; }
    public Knoten getNachfolger() { return nachfolger; };
    public Knoten getVorgänger() { return vorgänger; };
    
    public void ändereNachfolger(Knoten NeuNachfolger) { nachfolger = NeuNachfolger; }
    public void ändereVorgänger(Knoten NeuVorgänger) { vorgänger = NeuVorgänger; }
}