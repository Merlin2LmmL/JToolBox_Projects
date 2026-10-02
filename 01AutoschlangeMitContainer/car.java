
/**
 * Vincent Scheffers
 */
public class car extends Kraftfahrzeug
{
   private int x, y;
    private String bezeichnung, typ; 
    private int ps, laenge;
    
    private Kreis rad1, rad2, kopf;
    private Rechteck mitte;
    private Ellipse dach, scheibe;
    
    private Behaelter auto;
    

        public car()
    {   
        super("car", 500, 200, 100);
    }
    public car(String _bezeichnung, int _ps, int xPos, int yPos)
    {   
        super("car", 500, 200, 100);
        
        
        bezeichnung = _bezeichnung;
        ps = _ps;
        auto = new Behaelter(x, y, 140, 100);
        x = xPos;
        y = yPos;
        
        dach = new Ellipse(5, 55, 65, 30);
        dach.setzeFarbe("rot");
        
        scheibe = new Ellipse(10, 60, 55, 25);
        scheibe.setzeFarbe("cyan");
        kopf = new Kreis(40, 64, 3);
        kopf.setzeFarbe("schwarz");
        mitte = new Rechteck(0, 70, 105, 15);
        mitte.setzeFarbe("rot");
        rad1 = new Kreis(10, 80, 7);
        rad1.setzeFarbe("schwarz");
        rad2 = new Kreis(80, 80, 7);
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
