public class Figur implements ITastatur, ITuWas { 
    private Rechteck figur; // Dunstabzugshaube
    private int positionX;
    private int positionY;
    private final static int hoehe = 32;
    private final static int breite = 16;
    private float velX = 0;
    private float velY = 0;
    private boolean istAufDemBoden = false;
    private final static float  bewegungsfaktor = 1.2f;
    private final static float  reibungsfaktor = 0.85f;
    private final static float  sprungkraft = 20f;
    private final static float  gravitation = 0.6f;
    Tastatur tasten;
    private Taktgeber takt;
    private TiledMap map;

    // Merkt, ob die UP-Taste gerade gehalten wird
    private boolean upHeld = false;

    // Jump-Counter:
    private int jumps = 0;
    private final int maxJumps = 2;

    public Figur(TiledMap map) {
        this.map = map;
        positionX = map.spawnX;
        positionY = map.spawnY;

        figur();

        tasten = new Tastatur();
        tasten.setzeLink(this);
        tasten.meldeStandardtastenAn();
        // Um mit Pfeiltasten zu spielen lösche die // unten. Nachteil: Du kannst nichtmehr in der Map scrollen.
        //      tasten.meldeRichtungstastenAn();

        takt = new Taktgeber();
        takt.setzeLink(this);
        takt.setzteZeitZwischenAktionen(10);
        takt.endlos();
    }

    public void figur() {
        figur = new Rechteck(positionX, positionY, breite, hoehe);
        figur.setzeFarbe("rot");
    }

    private void checkeFallsTod() {
        if (map.isInBoundary(positionX, positionY)
        || map.getTileTypeAtPixel(positionX + breite, positionY + hoehe + 1) == 143 || map.getTileTypeAtPixel(positionX, positionY + hoehe + 1) == 143) {
            reset();
        }
    }

    private void reset() {
        positionX = map.spawnX;
        positionY = map.spawnY;
        velX = 0;
        velY = 0;
    }

    public void tastenAktion(String rueckgabe) {
        boolean upNow = rueckgabe.contains("W") || rueckgabe.contains("_") || rueckgabe.contains("<UP>");
        // Nur beim Übergang von nicht-gedrückt zu gedrückt und am Boden springen
        if (upNow && !upHeld && (istAufDemBoden || jumps < maxJumps)) {
            velY += sprungkraft;
            jumps++;
        }
        upHeld = upNow;

        if (rueckgabe.contains("D") || rueckgabe.contains("<RIGHT>")) {
            velX += bewegungsfaktor;
        } else if (rueckgabe.contains("A") || rueckgabe.contains("<LEFT>")) {
            velX -= bewegungsfaktor;
        }
    }

    public void bewegen() {
        // 1. Gravitation: Wenn nicht am Boden, fallen lassen; sonst vertikale Geschwindigkeit auf 0 setzen
        if (!bodenTest()) {
            velY -= gravitation;
        } else {
            velY = 0;
        }
        if (obenTest() && velY > 0) {
            velY = 0;
        }

        // 2. Horizontale Kollision: Wenn links/rechts ein Tile ist und man in diese Richtung drückt, velX = 0
        if (linksTest() && velX < 0) {
            velX = 0;
        }
        if (rechtsTest() && velX > 0) {
            velX = 0;
        }

        // 3. Position aktualisieren
        positionX += velX;
        positionY -= velY;

        istAufDemBoden = bodenTest();

        // 4. Boden‐Snap: Solange bodenTest() true ist, einen Pixel nach oben verschieben
        //    (so landet die Figur nie „unter“ dem Boden)
        while (bodenTest()) {
            while (rechtsTest()) {
                positionX -= 1;
            }
            while (linksTest()) {
                positionX += 1;
            }
            jumps = 0;
            positionY -= 1;
        }

        // 5. Einfache Reibung: Beide Geschwindigkeiten etwas abbauen
        velX *= reibungsfaktor;
        velY *= reibungsfaktor;

        // 6. Figur-Objekt aktualisieren
        figur.setzePosition(positionX, positionY);
    }

    private boolean bodenTest() {
        // Prüft, ob direkt unterhalb der aktuellen Position bereits ein fester Tile ist:
        int testX1 = (positionX + breite);        // rechts
        int testX2 = positionX;                   // links
        int testY = (positionY + hoehe);          // unten
        return istFesterTile(testX1, testY) || istFesterTile(testX2, testY);
    }

    private boolean obenTest() {
        // Prüft, ob direkt oberhalb der aktuellen Position bereits ein fester Tile ist:
        int testX1 = (positionX + breite);        // rechts
        int testX2 =  positionX;                   // links
        int testY =  (positionY - (hoehe / 4));    // oben
        return istFesterTile(testX1, testY) || istFesterTile(testX2, testY);
    }

    private boolean linksTest() {
        int testX =  positionX;                    // links
        int testY = (int) (positionY + (hoehe / 2f));// mitte
        return istFesterTile(testX, testY);
    }

    private boolean rechtsTest() {
        int testX = (positionX + breite);         // rechts
        int testY = (int) (positionY + (hoehe / 2f));   // mitte
        return istFesterTile(testX, testY);
    }

    // Zentraler Pixel‐Checker, der map.getTileTypeAtPixel aufruft und -1 = leer, != -1 = fester Block
    private boolean istFesterTile(int pixelX, int pixelY) {
        return map.getTileTypeAtPixel(pixelX, pixelY) != -1;
    }

    @Override
    public void tuWas(int ID) {
        bewegen();
        checkeFallsTod();
    }
}