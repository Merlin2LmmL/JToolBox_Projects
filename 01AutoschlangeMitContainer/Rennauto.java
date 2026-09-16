
/**
 * Der Rennwagen von K. Hartmann 
 */
public class Rennauto
{
    private int x, y;
    private String bezeichnung, farbe, typ; 
    private int ps, laenge;
    private Kreis rad1, rad2, kopf;
    private Dreieck motor;
    private Rechteck mitte;
    private Ellipse dach, scheibe;
    private Behaelter auto;

    /**
     * Konstruktor für Objekte der Klasse Auto
     */
    public Rennauto(String _bezeichnung, int _ps, int xPos, int yPos)
    {
        
        x = xPos;
        y = yPos;
        bezeichnung = _bezeichnung;
        ps = _ps;
        auto = new Behaelter(x, y, 140, 100);
        
        dach = new Ellipse(15, 55, 55, 35);
        dach.setzeFarbe("magenta");
        
        scheibe = new Ellipse(20, 60, 45, 30);
        scheibe.setzeFarbe("gelb");
        kopf = new Kreis(48, 64, 3);
        kopf.setzeFarbe("schwarz");
        motor = new Dreieck(0, 70, 140, 20);
        motor.setzeFarbe("magenta");
        mitte = new Rechteck(10, 70, 60, 20);
        mitte.setzeFarbe("magenta");
        rad1 = new Kreis(20, 80, 10);
        rad1.setzeFarbe("schwarz");
        rad2 = new Kreis(80, 80, 10);
        rad2.setzeFarbe("schwarz");
        
        auto.hinzufuegen(dach);
        auto.hinzufuegen(scheibe);
        auto.hinzufuegen(kopf);
        auto.hinzufuegen(motor);
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
    
//     public void autoNeuZeichnen(){
//         dach.setzePosition(15, 55);
//         scheibe.setzePosition(20, 60);
//         kopf.setzePosition(48, 64);
//         motor.setzePosition(x, 70);
//         mitte.setzePosition(10, 70);
//         rad1.setzePosition(20, 80);
//         rad2.setzePosition(80, 80);
//     }

}
