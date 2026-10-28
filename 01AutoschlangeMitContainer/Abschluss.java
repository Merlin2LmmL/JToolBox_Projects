/**
 * Write a description of class Abschluss here.
 *
 * @author Merlin Ortner
 * @version 1.0.1
 */
public class Abschluss extends Listenelement {    
    public Listenelement suchen(String bezeichnung) { return null; }
    
    public Listenelement getKnoten(int position) { return null; }
    
    public Knoten amEndeEinfügen(Datenelement dneu) {
        Knoten kneu = new Knoten(dneu, this);
        return kneu;
    }
    
    public int getLength(int length) { return length; }
    
    public Listenelement removeElement(String bezeichnung) { return this; }
    
    public Datenelement getDatenelement() { throw new Error("Abschluss has no data"); }
    
    public Listenelement anPosEinfügen(Datenelement dneu, int pos) { throw new Error("Cannot create Knoten after Abschluss"); }
}