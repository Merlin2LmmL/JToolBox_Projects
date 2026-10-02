/**
 * Der Rennwagen von Lana Baumeister 
 */
public class Auto extends Kraftfahrzeug
{
    private Kreis rad1, rad2, kopf;
    private Rechteck mitte;
    private Rechteck vorne;
    private Rechteck fenster;

    /**
     * Konstruktor für Objekte der Klasse Auto
     */
    public Auto(int xPos, int yPos)
    {
        super("auto", 400, xPos, yPos);
        zeichnen();
    }
    
    public Auto()
    {
        super("auto", 400, 50, 50);
        zeichnen();
    }

    public Auto(String _bezeichnung, int _ps, int xPos, int yPos)
    {
        super(_bezeichnung, _ps, xPos, yPos);
        zeichnen();
    }
    
    public void zeichnen()    
    {
            kopf = new Kreis(48, 64, 3);
            kopf.setzeFarbe("schwarz");
            mitte = new Rechteck(10, 40, 100, 50);
            mitte.setzeFarbe("magenta");
            rad1 = new Kreis(20, 80, 10);
            rad1.setzeFarbe("schwarz");
            rad2 = new Kreis(80, 80, 10);
            rad2.setzeFarbe("schwarz");
            vorne = new Rechteck(110, 55, 30, 35);
            vorne.setzeFarbe("magenta");
            fenster = new Rechteck(94, 42, 13, 13);
            fenster.setzeFarbe("cyan");

            auto.hinzufuegen(kopf);
            auto.hinzufuegen(mitte);
            auto.hinzufuegen(rad1);
            auto.hinzufuegen(rad2);
            auto.hinzufuegen(vorne);
            auto.hinzufuegen(fenster);
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
