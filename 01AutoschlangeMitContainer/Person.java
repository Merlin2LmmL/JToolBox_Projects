
public class Person implements Datenelement
{
    private String name;
    private int x, y;
    private Kreis kopf;
    private RechteckMitRundenEcken koerper;
    private Linie armL, armR, beinL, beinR;
    private String farbe;
    private Behaelter personGrafik;
    
    public Person(String neuerName, int _x, int _y)
    {
        name = neuerName;
        x = _x;
        y = _y;
        StaticTools.setzeFarbe("farbe1", StaticTools.gibZufall(255), StaticTools.gibZufall(255), StaticTools.gibZufall(255));
        farbe = "farbe1";
        personZeichnen();
    }
    
    public void grafikEntfernen(){
        personGrafik.entfernen();
    }

    public void informationAusgeben(){
        System.out.println("Die Person heißt "+name);
    }
    
    public boolean equalsKey(String schluessel) { return name.equals(schluessel); }
    
    public boolean isSmallerThan(Datenelement vergleich) { return name.compareTo(vergleich.getBezeichnung()) < 0; }
    
    public String getBezeichnung(){
        return name;
    }
    
    /**
     * Lässt die Person gehen: Arme und Beine schwingen gegengleich,
     * der ganze Körper wippt bei jedem Schritt auf und ab.
     */
    public void bewegen(int entf){
        int delta = entf < 0 ? -1 : 1;
        int bounce = 0;

        for (int i = 0; i < Math.abs(entf); i++) {
            double phase = i * 0.25;
            int swing = (int) Math.round(Math.sin(phase) * 9);
            int newBounce = (int) Math.round(Math.abs(Math.sin(phase)) * 4);

            beinL.setzeEndpunkte(27, 60, 24 + swing, 80);
            beinR.setzeEndpunkte(33, 60, 36 - swing, 80);
            armL.setzeEndpunkte(25, 35, 15 - swing, 50);
            armR.setzeEndpunkte(35, 35, 45 + swing, 50);

            personGrafik.vertikalBewegen(bounce - newBounce);
            bounce = newBounce;

            personGrafik.horizontalBewegen(delta);
            StaticTools.warte(10);
        }

        // Ruhepose wiederherstellen
        personGrafik.vertikalBewegen(bounce);
        beinL.setzeEndpunkte(27, 60, 24, 80);
        beinR.setzeEndpunkte(33, 60, 36, 80);
        armL.setzeEndpunkte(25, 35, 15, 50);
        armR.setzeEndpunkte(35, 35, 45, 50);

        x = x + entf;
    }
    
    public void personZeichnen()
    {
      personGrafik = new Behaelter(x, y, 60, 120);
      kopf = new Kreis(22,12,8);
      kopf.setzeFarbe("orange");
      koerper = new RechteckMitRundenEcken(24,30,14,34,5);
      koerper.setzeFarbe(farbe);
      armL = new Linie(25,35,15,50);
      armL.setzeFarbe(farbe);
      armR = new Linie(35,35,45,50);
      armR.setzeFarbe(farbe);
      beinL = new Linie(27,60,24,80);
      beinL.setzeFarbe(farbe);
      beinR = new Linie(33,60,36,80);
      beinR.setzeFarbe(farbe);
      personGrafik.hinzufuegen(kopf);
      personGrafik.hinzufuegen(koerper);
      personGrafik.hinzufuegen(armL);
      personGrafik.hinzufuegen(armR);
      personGrafik.hinzufuegen(beinL);
      personGrafik.hinzufuegen(beinR);
    }
}
