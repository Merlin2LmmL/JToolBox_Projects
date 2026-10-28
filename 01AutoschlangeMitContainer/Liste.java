/**
 * Verkettete Listen
 *
 * @author Merlin Ortner
 * @version 1.2.0
 */
public class Liste {
    private Listenelement anfang;

    public Liste() {
        anfang = new Abschluss();
    }

    public Listenelement amEndeEinfügen(Datenelement dneu) { return anfang = anfang.amEndeEinfügen(dneu); }

    public Listenelement getKnoten(int position) { return anfang.getKnoten(position); }

    public Listenelement suchen(String bezeichnung) { return anfang.suchen(bezeichnung); }

    public int getLänge() { return anfang.getLength(0); }

    public Listenelement amAnfangEinfügen(Datenelement dneu) {
        anfang = new Knoten(dneu, getKnoten(0));
        return anfang;
    }

    public void removeElement(String bezeichnung) {
        anfang = anfang.removeElement(bezeichnung);
    }
}