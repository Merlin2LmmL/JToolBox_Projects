

public class Wohnmobil extends Kraftfahrzeug
{
    private int x, y;
    private String bezeichnung, farbe, typ; 
    private int ps, laenge;
    private Kreis rad1, rad2;
    private Rechteck mitte, dach, motor, cockpit2, fenster, fenster2;
    private Behaelter auto;
    private Dreieck cockpit;

    
    public Wohnmobil(String _bezeichnung, int _ps, int xPos, int yPos)
    {
        super(_bezeichnung,  _ps, xPos, yPos);
        x = xPos;
        y = yPos;
        auto = new Behaelter(x, y, 140, 100);
        wohnmobil_zeichnen();
    }
    
    public Wohnmobil(int xPos, int yPos)
    {
        super("cooles Wohnmobil",  150, xPos, yPos);
        x = xPos;
        y = yPos;
        auto = new Behaelter(x, y, 140, 100);
        wohnmobil_zeichnen();
    }
    
    public Wohnmobil()
    {
        super("cooles Wohnmobil",  150, 100, 100);
        x = 100;
        y = 100;
        auto = new Behaelter(x, y, 140, 100);
        wohnmobil_zeichnen();
    }
    
    private void wohnmobil_zeichnen()
    {

        dach = new Rechteck(100, 30, 40, 20);
        dach.setzeFarbe("hellgrau");
        motor = new Rechteck(100, 70, 30, 20);
        motor.setzeFarbe("hellgrau");
        cockpit2 = new Rechteck(100, 50, 15, 20);
        cockpit2.setzeFarbe("hellgrau");
        mitte = new Rechteck(10, 30, 90, 60);
        mitte.setzeFarbe("hellgrau");

        cockpit = new Dreieck(100, 50, 30, 20);
        cockpit.setzeFarbe("hellgrau");

        rad1 = new Kreis(20, 80, 10);
        rad1.setzeFarbe("schwarz");
        rad2 = new Kreis(90, 80, 10);
        rad2.setzeFarbe("schwarz");

        fenster = new Rechteck(20, 40, 25, 14);
        fenster.setzeFarbe("dunkelgrau");
        fenster2 = new Rechteck(102, 52, 11, 16);
        fenster2.setzeFarbe("dunkelgrau");

        auto.hinzufuegen(mitte);
        auto.hinzufuegen(dach);
        auto.hinzufuegen(motor);
        auto.hinzufuegen(cockpit);
        auto.hinzufuegen(cockpit2);
        auto.hinzufuegen(fenster);
        auto.hinzufuegen(fenster2);
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
