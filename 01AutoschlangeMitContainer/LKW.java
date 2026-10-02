/**
 * Der wagen von Minh Hoang 
 */
public class LKW extends Kraftfahrzeug
{

    private Kreis rad1, rad2, rad3, kopf;
    private Rechteck mitte, ladung, kabine, fenster;

    /**
     * Konstruktor für Objekte der Klasse Auto
     */
    public LKW(String _bezeichnung, int _ps, int xPos, int yPos)
    {
        super(_bezeichnung, _ps, xPos, yPos);
        zeichnen();

    }
    public LKW(int xPos, int yPos)
    {
        super("LKW", 100, xPos, yPos);

        zeichnen();

    }

    public LKW()
    {
        super("LKW", 100, 50, 100);
        zeichnen();

    }

    public void zeichnen(){
        rad1 = new Kreis(15, 80, 10);
        rad1.setzeFarbe("schwarz");
        rad2 = new Kreis(55, 80, 10);
        rad2.setzeFarbe("schwarz");
        rad3 = new Kreis(85, 80, 10);
        rad3.setzeFarbe("schwarz");

        mitte = new Rechteck(10, 80, 70, 10);
        mitte.setzeFarbe("grau");
        ladung = new Rechteck(15, 55, 60, 25);
        ladung.setzeFarbe("braun");
        kabine = new Rechteck(80, 50, 30, 40);
        kabine.setzeFarbe("blau");
        fenster = new Rechteck(85, 55, 25, 20);
        fenster.setzeFarbe("gelb");

        kopf = new Kreis(90, 65 , 5);
        kopf.setzeFarbe("schwarz");

        auto.hinzufuegen(rad1);
        auto.hinzufuegen(rad2);
        auto.hinzufuegen(rad3);
        auto.hinzufuegen(mitte);
        auto.hinzufuegen(ladung);
        auto.hinzufuegen(kabine);
        auto.hinzufuegen(fenster);
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