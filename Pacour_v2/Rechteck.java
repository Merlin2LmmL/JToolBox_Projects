/**
 * Ein Rechteck kann sich selbst zeichnen im Programmfenster Zeichnung
 * 
 * @author Hans Witt
 * 
 * Version 1.1 (14.7.2008)
 *     Hinzufügen von Statusvariablen für Position ...
 * Version: 1.1.1 (17.7.2008) 
 *        Neue Komponenten werden von Unten nach Oben aufgebaut, d.h.vor die alten gesetzt
 * Version: 3 (4.8.2008) 
 *        ergänzt für Containerklasse für GUI-Elemente
 * Version: 3.1 (14.8.2008) 
 *        Konstruktor auf int neuesX, int neuesY , int neueBreite, int neueHoehe angepasst        
 * @version: 3.2 (18.8.2008)
 *        Zustandsvariable auf protected gesetzt 
 * 
 */
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Rechteck implements IComponente {

    private CRechteck obj;
    protected int breite = 0;
    protected int hoehe = 0;
    protected int xPos = 0;
    protected int yPos = 0;
    protected boolean sichtbar = true;
    protected boolean gefuellt = true;
    protected String farbe = StaticTools.leseNormalfarbe();

    /**
     * Konstruktor für Hauptfenster
     */
    public Rechteck() {
        this(Zeichnung.gibZeichenflaeche());
    }

    /**
     * Konstruktor für Hauptfenster
     * 
     * @param neueBreite
     * @param neueHoehe
     */
    public Rechteck(int neueBreite, int neueHoehe) {
        this(Zeichnung.gibZeichenflaeche(), 0, 0, neueBreite, neueHoehe);
    }

    /**
     * Konstruktor für Hauptfenster
     * 
     * @param neuesX
     * @param neuesY
     * @param neueBreite
     * @param neueHoehe
     */
    public Rechteck(int neuesX, int neuesY, int neueBreite, int neueHoehe) {
        this(Zeichnung.gibZeichenflaeche(), neuesX, neuesY, neueBreite,
            neueHoehe);
    }

    /**
     * Konstruktor
     * 
     * @param behaelter
     */
    public Rechteck(IContainer behaelter) {
        this(behaelter, 0, 0, 100, 50);
    }

    /**
     * allgemeiner Konstruktor
     * 
     * @param behaelter
     * @param neueBreite
     * @param neueHoehe
     */
    public Rechteck(IContainer behaelter, int neuesX, int neuesY,
    int neueBreite, int neueHoehe) {
        obj = new CRechteck();
        behaelter.add(obj, 0);
        setzeDimensionen(neuesX, neuesY, neueBreite, neueHoehe);
        behaelter.validate();
    }

    /**
     *  Das Interface IComponente fordert eine Methode die eine BasisComponente zurückliefert.
     *  Sie wird benötigt, um ein Objekt zu einem anderen Behälter hinzuzufügen
     */
    public BasisComponente getBasisComponente() {
        return obj ;
    }

    public void sichtbarMachen() {
        sichtbar = true;
        obj.sichtbarMachen();
    }

    /**
     * Mache unsichtbar. Wenn es bereits unsichtbar ist, tue nichts.
     */
    public void unsichtbarMachen() {
        sichtbar = false;
        obj.unsichtbarMachen();
    }

    /**
     * Bewege horizontal um 'entfernung' Bildschirmpunkte.
     */
    public void nachRechtsBewegen() {
        horizontalBewegen(20);
    }

    /**
     * Bewege einige Bildschirmpunkte nach links.
     */
    public void nachLinksBewegen() {
        horizontalBewegen(-20);
    }

    /**
     * Bewege einige Bildschirmpunkte nach oben.
     */
    public void nachObenBewegen() {
        vertikalBewegen(-20);
    }

    /**
     * Bewege einige Bildschirmpunkte nach unten.
     */
    public void nachUntenBewegen() {
        vertikalBewegen(20);
    }

    /**
     * Bewege vertikal um 'entfernung' Bildschirmpunkte.
     */
    public void langsamVertikalBewegen(int entfernung) {
        int delta;

        if (entfernung < 0) {
            delta = -1;
            entfernung = -entfernung;
        } else {
            delta = 1;
        }

        for (int i = 0; i < entfernung; i++) {
            vertikalBewegen(delta);
            StaticTools.warte(10);
        }
    }

    /**
     * Bewege vertikal um 'entfernung' Bildschirmpunkte.
     */
    public void langsamHorizontalBewegen(int entfernung) {
        int delta;

        if (entfernung < 0) {
            delta = -1;
            entfernung = -entfernung;
        } else {
            delta = 1;
        }

        for (int i = 0; i < entfernung; i++) {
            horizontalBewegen(delta);
            StaticTools.warte(10);
        }
    }

    /**
     * Bewege vertikal um 'entfernung' Bildschirmpunkte.
     */
    public void schnellHorizontalBewegen(int entfernung) {
        int delta;

        if (entfernung < 0) {
            delta = -1;
            entfernung = -entfernung;
        } else {
            delta = 1;
        }

        for (int i = 0; i < entfernung; i++) {
            horizontalBewegen(delta);
            StaticTools.warte(5);
        }
    }

    public void setzeGroesse(int neueBreite, int neueHoehe) {
        breite = neueBreite;
        hoehe = neueHoehe;
        obj.setzeGroesse(breite, hoehe);
    }

    /**
     * neue Position
     * 
     * @param x
     * @param y
     */
    public void setzePosition(int neuesX, int neuesY) {
        xPos = neuesX;
        yPos = neuesY;
        obj.setzePosition(xPos, yPos);
    }

    // Methode nötig zum Hinzufügen mit Anpassung beim Behälter
    // Die Enden werden relativ zur aktuellen position verschoben
    public void verschieben(int dx , int dy ) {
        setzePosition(xPos + dx, yPos + dy );
    }

    /**
     * 
     * @param neuesX
     * @param neuesY
     * @param neueBreite
     * @param neueHoehe
     */
    public void setzeDimensionen(int neuesX, int neuesY, int neueBreite, int neueHoehe) {
        xPos = neuesX;
        yPos = neuesY;
        breite = neueBreite;
        hoehe = neueHoehe;
        obj.setzeDimensionen(xPos, yPos, breite, hoehe);
    }

    /*
     * gültige Farben: "rot", "gelb", "blau", "gruen", "lila" , "schwarz" ,
     * "weiss" , "grau","pink","magenta","orange","cyan","hellgrau"
     */
    public void setzeFarbe(String neueFarbe) {
        farbe = neueFarbe;
        obj.setzeBasisfarbe(neueFarbe);
    }

    /**
     * Bewege horizontal um 'entfernung' Bildschirmpunkte.
     */
    public void horizontalBewegen(int entfernung) {
        xPos += entfernung;
        obj.setzePosition(xPos, yPos);
    }

    /**
     * Bewege vertikal um 'entfernung' Bildschirmpunkte.
     */
    public void vertikalBewegen(int entfernung) {
        yPos += entfernung;
        obj.setzePosition(xPos, yPos);
    }

    public void fuellen() {
        gefuellt = true;
        obj.fuellen();
    }

    public void rand() {
        gefuellt = false;
        obj.rand();
    }

}

class CRechteck extends BasisComponente {
    /**
     * Konstruktor ohne Beschriftung
     */
    public CRechteck() {

    }

    /**
     * Die Darstellung der Komponente wird hier programmiert.
     */

    public void paintComponentSpezial(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Graphik-Abmessungen
        breite = getSize().width;
        hoehe = getSize().height;
        g.setColor(farbe);

        if (gefuellt) {
            g2.fill3DRect(0, 0, breite, hoehe, true);
        } else {

            g2.draw3DRect(0, 0, breite, hoehe, true);
            g2.draw3DRect(1, 1, breite - 2, hoehe - 2, true);
        }
    }

}