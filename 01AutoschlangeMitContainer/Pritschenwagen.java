
/**
 * @author Yannik 
 * @version 2.1
 */
public class Pritschenwagen extends Kraftfahrzeug
{
    private int x, y;
    private String bezeichnung, typ; 
    private int ps, laenge;
    private Kreis rad1, rad2, rad3, kopf, radkappe1, radkappe2, radkappe3;
    //private Dreieck ;
    private Rechteck aero, scheibe, lade, kabine, susp, text;
    private Ellipse sand, licht;
    private Behaelter auto;

    
    public Pritschenwagen(String _bezeichnung, int _ps, int xPos, int yPos)
    {
        super(_bezeichnung, _ps, xPos, yPos);
        
        auto = new Behaelter(x, y, 140, 100);
               
        zeichnen();        
    }
        
    public Pritschenwagen(int xPos, int yPos)
    {
        super("", 0, xPos, yPos);
        
        x = xPos;
        y = yPos;        
        auto = new Behaelter(x, y, 140, 100);
               
        zeichnen();        
    }
    
    public void zeichnen()
    {
        sand = new Ellipse(2, 28, 88, 35);
        sand.setzeFarbe("orange");
        
        
        kopf = new Kreis(122, 34, 4);
        kopf.setzeFarbe("schwarz");
        
        lade = new Rechteck(0, 40, 140, 35);
        lade.setzeFarbe("blau");
        
        text = new Rechteck(20, 48, 50, 18);
        text.setzeFarbe("weiss");
        
        susp = new Rechteck(5, 73, 140, 12);
        susp.setzeFarbe("grau");
        
        aero = new Rechteck(90, 10, 20, 69);
        aero.setzeFarbe("rot");
        
        kabine = new Rechteck(110, 15, 40, 75);
        kabine.setzeFarbe("rot");
        
        licht = new Ellipse(130, 77, 8, 8);
        licht.setzeFarbe("gelb");
        
        rad1 = new Kreis(10, 70, 15);
        rad1.setzeFarbe("schwarz");
        radkappe1 = new Kreis(19, 79, 7);
        radkappe1.setzeFarbe("hellgrau");
        
        rad2 = new Kreis(44, 70, 15);
        rad2.setzeFarbe("schwarz");
        radkappe2 = new Kreis(54, 79, 7);
        radkappe2.setzeFarbe("hellgrau");
        
        rad3 = new Kreis(95, 70, 15);
        rad3.setzeFarbe("schwarz");
        radkappe3 = new Kreis(104, 79, 7);
        radkappe3.setzeFarbe("hellgrau");
        
        scheibe = new Rechteck(115, 25, 20, 30);
        scheibe.setzeFarbe("cyan");
        
        auto.hinzufuegen(sand);
        auto.hinzufuegen(susp);
        auto.hinzufuegen(lade);
        auto.hinzufuegen(text);
        auto.hinzufuegen(aero);
        auto.hinzufuegen(kabine);
        auto.hinzufuegen(rad1);
        auto.hinzufuegen(rad2);
        auto.hinzufuegen(rad3);
        auto.hinzufuegen(radkappe1);
        auto.hinzufuegen(radkappe2);
        auto.hinzufuegen(radkappe3);
        auto.hinzufuegen(scheibe);
        auto.hinzufuegen(licht);
        auto.hinzufuegen(kopf);
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
