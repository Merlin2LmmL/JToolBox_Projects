/**
 * Verkettete Listen
 *
 * @author Merlin Ortner
 * @version 1.1.1
 */
public class Liste {
    private Knoten anfang;
    public int knotenAnzahl = 0;

    public Liste() {
        anfang = null;
    }
    
    public void amEndeEinfügen(Kraftfahrzeug fneu) {
        ++knotenAnzahl;
        if (anfang != null) {
            anfang.amEndeEinfügen(fneu);
        } else {
            Knoten knotenNeu = new Knoten(fneu, null);
            anfang = knotenNeu;
        }
    }
    
    // public void anPositionEinfügen(Kraftfahrzeug fneu, int position) {
        // ++knotenAnzahl;
        // Knoten knotenNeu = new Knoten(fneu, getKnoten(position), getKnoten(position + 1));
        // getKnoten(position - 1).ändereNachfolger(knotenNeu);
    // }
    
    public Knoten getKnoten(int position) {
        Knoten knoten = anfang;
        for (int i = 0; i < position; ++i) {
            knoten = knoten.getNachfolger();
            if (knoten == null) return null;
        }
        return knoten;
    }
    
    public Knoten getKnotenByName(String bezeichnung) {
        Knoten aktuellerKnoten = anfang;
        while (aktuellerKnoten.getNachfolger() != null) {
            if (aktuellerKnoten.getKraftfahrzeug().bezeichnung.equals(bezeichnung))
                return aktuellerKnoten;
            else
                aktuellerKnoten = aktuellerKnoten.getNachfolger();
        }
        return null;
    }
    
    public Knoten getKnotenByNameRecursive(String bezeichnung) {
        return anfang.getKnotenByNameRecursive(bezeichnung);
    }
    
    public int countKnoten() {
        if (anfang == null) return 0;
        return anfang.countNachfoler();
    }
}