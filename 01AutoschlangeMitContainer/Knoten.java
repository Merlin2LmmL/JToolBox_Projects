/**
 * Knoten, einfach verkettet durch "Pointer" in einer Liste
 *
 * @author Merlin Ortner
 * @version 1.0.3
 */
public class Knoten extends Listenelement {
    public Listenelement nachfolger;
    private Datenelement data;

    public Knoten(Datenelement dNeu, Listenelement k) {
        data = dNeu;
        nachfolger = k;
    }

    public Knoten(Datenelement dataNeu, Knoten v, Knoten n) {
        data = dataNeu;
        nachfolger = n;
    }

    public Listenelement amEndeEinfügen(Datenelement dneu) {
        nachfolger = nachfolger.amEndeEinfügen(dneu);
        return this;
    }
    
    public Listenelement anPosEinfügen(Datenelement dneu, int pos) {
        if (pos == 0) {
            Knoten kneu = new Knoten(dneu, nachfolger);
            return nachfolger = kneu;
        }
        return nachfolger.anPosEinfügen(dneu, pos - 1);
    }

    public Listenelement suchen(String bezeichnung) {
        if (data.getBezeichnung().equals(bezeichnung)) return this;
        return nachfolger.suchen(bezeichnung);
    }

    public Listenelement getKnoten(int position) {
        if (position == 0) return this;
        return nachfolger.getKnoten(position - 1);
    }
    
    public Listenelement removeElement(String bezeichnung) {
        if (data.equalsKey(bezeichnung)) return nachfolger;
        nachfolger = nachfolger.removeElement(bezeichnung);
        return this;
    }

    public int getLength(int length) { return nachfolger.getLength(length + 1); }

    public Datenelement getDatenelement() { return data; }

    public Listenelement getNachfolger() { return nachfolger; };

    public void ändereNachfolger(Knoten NeuNachfolger) { nachfolger = NeuNachfolger; }
}