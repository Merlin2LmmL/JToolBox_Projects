
/**
 * @author Mustafa Bozkurt
 */
public class AutoMu extends Kraftfahrzeug
{
    // instance variables - replace the example below with your own
    private int x,y;
    private Dreieck d1,d2;
    private Rechteck v1,v2,v3,v4,v5,v6,v7,v8;
    private Kreis r1,r2,r3;
    private String bezeichnung, farbe, typ; 
    private int ps, laenge;

    /**
     * Constructor for objects of class AutoMu
     */

    public AutoMu(String _bezeichnung, int _ps,int xPos, int yPos)
    {
        super(_bezeichnung,_ps,xPos,yPos);

        
        zeichnen();
    }

    public AutoMu(int xPos, int yPos) {
        super("Bus",300,xPos,yPos);
        
        zeichnen();
    }

    private void zeichnen() {
        v1=new Rechteck (10,60,120,35);
        v1.setzeFarbe("grau");
        v2=new Rechteck (21,70,10,9);
        v2.setzeFarbe("cyan");
        v3=new Rechteck (31,70,10,9);
        v3.setzeFarbe("cyan");
        v4=new Rechteck (41,70,10,9);
        v4.setzeFarbe("cyan");
        v5=new Rechteck (51,70,10,9);
        v5.setzeFarbe("cyan");
        v6=new Rechteck (61,70,10,9);
        v6.setzeFarbe("cyan");
        v7=new Rechteck (71,70,10,9);
        v7.setzeFarbe("cyan");
        v8=new Rechteck (120,65,10,20);
        v8.setzeFarbe("cyan");
        r1=new Kreis (20,86,7);
        r1.setzeFarbe("schwarz");
        r2=new Kreis (37,86,7);
        r2.setzeFarbe("schwarz");
        r3=new Kreis (100,86,7);
        r3.setzeFarbe("schwarz");

        auto.hinzufuegen(v1);
        auto.hinzufuegen(v2);
        auto.hinzufuegen(v3);
        auto.hinzufuegen(v4);
        auto.hinzufuegen(v5);
        auto.hinzufuegen(v6);
        auto.hinzufuegen(v7);
        auto.hinzufuegen(v8);
        auto.hinzufuegen(r1);
        auto.hinzufuegen(r2);
        auto.hinzufuegen(r3);
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