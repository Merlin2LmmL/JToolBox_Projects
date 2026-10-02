
/**
 * Der Rennwagen von Jonas Franzke
 */
public class Pickup extends Kraftfahrzeug
{
    private Kreis rad1, rad2, kopf;
    private Dreieck frontscheibe;
    private Rechteck chassie,kabine;

    /**
     * Konstruktor für Objekte der Klasse Auto
     */
    public Pickup(int xPos, int yPos)
    {
        super ("Pickup",500,xPos ,yPos);
        zeichnen();
    }

    public Pickup()
    {
        super ("Pickup",500,42,42);
        zeichnen();
    }

    public Pickup(String _bezeichnung , int _ps, int xPos, int yPos)
    {
        super (_bezeichnung ,_ps ,xPos ,yPos);
        zeichnen();
    }

    private void zeichnen()
    {

        chassie = new Rechteck(5, 55, 130, 25);
        chassie.setzeFarbe("grau");
        kopf = new Kreis(110, 48, 3);
        kopf.setzeFarbe("schwarz");
        frontscheibe = new Dreieck(118, 27, 15, 30);
        frontscheibe.setzeFarbe("cyan");
        kabine = new Rechteck(75,27, 50, 30);
        kabine.setzeFarbe("grau");
        rad1 = new Kreis(30, 75, 10);
        rad1.setzeFarbe("schwarz");
        rad2 = new Kreis(95, 75, 10);
        rad2.setzeFarbe("schwarz");

        auto.hinzufuegen(kopf);
        auto.hinzufuegen(chassie);
        auto.hinzufuegen(frontscheibe);
        auto.hinzufuegen(kabine);
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
