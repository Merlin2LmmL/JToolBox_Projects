/**
 * Der Rennwagen von K. Hartmann 
 */


public class Bully extends Kraftfahrzeug
{
    private int x, y;
    private String bezeichnung, farbe, typ; 
    private int laenge;
    private Kreis rad1, rad2,felge1,felge2;
    private Dreieck heck,front,front_fenster;
    private Rechteck oben_mitte,hinten_fester,unten_mitte,mitte_fester,forne_fester,griff1,griff2,bumper;
    private Ellipse dach, scheibe;
    private Behaelter auto;

    //if(StaticTools.leseBekannteFarben(farbname)== hellblau){
    //    Color tColor = new Color(139,69,19);new Color (139,69,19)
    public Bully(int xPos,int yPos)
    {
        super("Bully",150,xPos,yPos);
        x = xPos;
        y = yPos;
        //bezeichnung = "Bully";
        
        auto = new Behaelter(x, y, 140, 100);
        Zeichnen();
    }

 
    
    //public Bully(String _bezeichnung, int xPos, int yPos)
   //{
      //  x = xPos;
      //  y = yPos;
       // _bezeichnung = "Bully";      
       // auto = new Behaelter(x, y, 140, 100);
       // Zeichnen();
   // }

   
    private void Zeichnen(){ 
        
        StaticTools.setzeFarbe("Hellblau",51,133,215);
        
        oben_mitte = new Rechteck(32, 11, 88, 35);
        oben_mitte.setzeFarbe("weiss");
        unten_mitte = new Rechteck(18, 45, 120, 45);
        unten_mitte.setzeFarbe("hellblau");
        heck = new Dreieck(21,11, 25, 35);
        heck.setzeFarbe("weiss");
        front = new Dreieck(107,11, 25, 35);
        front.setzeFarbe("weiss");
        rad1 = new Kreis(32, 76, 12);
        rad1.setzeFarbe("schwarz");
        felge1 = new Kreis(38, 82, 6);
        felge1.setzeFarbe("grau");
        felge2 = new Kreis(103, 82, 6);
        felge2.setzeFarbe("grau");
        rad2 = new Kreis(97, 76, 12);
        rad2.setzeFarbe("schwarz");
        front_fenster = new Dreieck(111, 26, 14, 16);
        front_fenster.setzeFarbe("hellgrau");
        hinten_fester = new Rechteck(37, 25, 26, 16);
        hinten_fester.setzeFarbe("hellgrau");
        mitte_fester = new Rechteck(67, 25, 26, 16);
        mitte_fester.setzeFarbe("hellgrau");
        forne_fester = new Rechteck(97, 25, 20, 16);
        forne_fester.setzeFarbe("hellgrau");
        griff1 = new Rechteck(80, 50, 10, 4);
        griff1.setzeFarbe("schwarz");
        griff2 = new Rechteck(98, 50, 10, 4);
        griff2.setzeFarbe("schwarz");
        bumper= new Rechteck(135, 82, 8, 8);
        bumper.setzeFarbe("schwarz");

        auto.hinzufuegen(unten_mitte);
        auto.hinzufuegen(oben_mitte);
        auto.hinzufuegen(rad1);
        auto.hinzufuegen(rad2);
        auto.hinzufuegen(felge1);
        auto.hinzufuegen(felge2);
        auto.hinzufuegen(front);
        auto.hinzufuegen(heck);
        auto.hinzufuegen(hinten_fester);
        auto.hinzufuegen(mitte_fester);
        auto.hinzufuegen(forne_fester);
        auto.hinzufuegen(front_fenster);
        auto.hinzufuegen(griff1);
        auto.hinzufuegen(griff2);
        auto.hinzufuegen(bumper);
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
