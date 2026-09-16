
/**
 * Combi von HAR
 */
public class PKW
{
   private int x, y;
    private String bezeichnung, typ, farbe; 
    private int ps, laenge;
    
    private Kreis rad1, rad2, kopf;
    private Rechteck mitte, dach, scheibe;
    
    private Behaelter auto;

    /**
     * Konstruktor für Objekte der Klasse Auto
     */
    public PKW(String _bezeichnung, String _farbe, int _ps, int xPos, int yPos)
    {
        
        x = xPos;
        y = yPos;
        bezeichnung = _bezeichnung;
        farbe = _farbe;
        ps = _ps;
        auto = new Behaelter(x, y, 140, 100);
        
        dach = new Rechteck(5, 55, 65, 35);
        dach.setzeFarbe(farbe);
        
        scheibe = new Rechteck(10, 60, 55, 30);
        scheibe.setzeFarbe("gelb");
        kopf = new Kreis(48, 64, 3);
        kopf.setzeFarbe("schwarz");
        mitte = new Rechteck(0, 70, 110, 25);
        mitte.setzeFarbe(farbe);
        rad1 = new Kreis(10, 80, 10);
        rad1.setzeFarbe("schwarz");
        rad2 = new Kreis(80, 80, 10);
        rad2.setzeFarbe("schwarz");
        
        auto.hinzufuegen(dach);
        auto.hinzufuegen(scheibe);
        auto.hinzufuegen(kopf);
        auto.hinzufuegen(mitte);
        auto.hinzufuegen(rad1);
        auto.hinzufuegen(rad2);
    }
    
    public void setzePosition(int neuesX, int neuesY)
    {
        x = neuesX;
        y = neuesY;
        auto.setzePosition(x, y);
    }
    
    public void fahren(int entfernung)
    {
        x = x + entfernung;       
        auto.langsamHorizontalBewegen(entfernung);
    }
}
