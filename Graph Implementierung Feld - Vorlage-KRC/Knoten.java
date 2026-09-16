/**
 * Implementierung mit knotenFeld (Array)
 * 
 * @author Stoeckle 
 * @version 16.11.22
 */
class Knoten {
    //Attribute:
    private String schluessel;
    private boolean besucht; 
 
    Knoten(String schluessel)
    {
        this.schluessel = schluessel;
    }


    //Setze und Gebe Methoden
    String gibSchluessel()
    {
        return schluessel;
    }

    void schluesselAusgeben(){
        System.out.println(this.schluessel);
    }

    boolean istBesucht()
    {
        return besucht;   
    }

    void setzeBesucht(boolean wert)
    {
        besucht = wert;
    }

}
