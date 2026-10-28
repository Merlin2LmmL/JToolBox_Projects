
/**
 * Write a description of class Listenelement here.
 *
 * @author Merlin Ortner
 * @version 1.0.0
 */
public abstract class Listenelement {
    public abstract Listenelement amEndeEinfügen(Datenelement dneu);
    public abstract Listenelement getKnoten(int position);
    public abstract Listenelement suchen(String bezeichnung);
    public abstract int getLength(int length);
    public abstract Listenelement removeElement(String bezeichnung);
}