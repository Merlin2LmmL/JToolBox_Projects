public class Person implements Datenelement
{
    private String name;
    private int x, y;
    private final int offX = 40, offY = 15;
    private Kreis kopf;
    private RechteckMitRundenEcken koerper;
    private Linie armL, armR, beinL, beinR;
    private String farbe;
    private Behaelter personGrafik;
    
    public Person(String neuerName, int _x, int _y)
    {
        name = neuerName;
        // Offsets to fit the cars container bounds
        x = _x + offX;
        y = _y + offY;
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
     * Lässt die Person gehen (auch diagonal): Arme und Beine schwingen gegengleich,
     * der ganze Körper wippt bei jedem Schritt auf und ab.
     */
    public void bewegen(int dx, int dy){
        int schritte = Math.max(Math.abs(dx), Math.abs(dy));
        int bisherX = 0, bisherY = 0;
        int bounce = 0;

        for (int i = 1; i <= schritte; i++) {
            double phase = i * 0.25;
            int swing = (int) Math.round(Math.sin(phase) * 9);
            int newBounce = (int) Math.round(Math.abs(Math.sin(phase)) * 4);

            beinL.setzeEndpunkte(27, 60, 24 + swing, 80);
            beinR.setzeEndpunkte(33, 60, 36 - swing, 80);
            armL.setzeEndpunkte(25, 35, 15 - swing, 50);
            armR.setzeEndpunkte(35, 35, 45 + swing, 50);

            int zielX = Math.round((float) dx * i / schritte);
            int zielY = Math.round((float) dy * i / schritte);
            personGrafik.horizontalBewegen(zielX - bisherX);
            personGrafik.vertikalBewegen((zielY - bisherY) + (bounce - newBounce));
            bisherX = zielX;
            bisherY = zielY;
            bounce = newBounce;

            StaticTools.warte(10);
        }

        // Ruhepose wiederherstellen
        personGrafik.vertikalBewegen(bounce);
        beinL.setzeEndpunkte(27, 60, 24, 80);
        beinR.setzeEndpunkte(33, 60, 36, 80);
        armL.setzeEndpunkte(25, 35, 15, 50);
        armR.setzeEndpunkte(35, 35, 45, 50);

        x = x + dx;
        y = y + dy;
    }
    
        public void setzePosition(int neuesX, int neuesY) {
        x = neuesX + offX;
        y = neuesY + offY;
        personGrafik.setzePosition(x, y);
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
