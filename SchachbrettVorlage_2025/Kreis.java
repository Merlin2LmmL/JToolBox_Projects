import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 * Klasse Kreis – zeichnet einen Kreis, der separat gefüllt und umrandet werden kann.
 * Mit der Methode setBorderWidth() kann die Linienstärke des Randes angepasst werden.
 */
public class Kreis implements IComponente {

    private CKreis obj;
    protected int radius = 0;
    protected int xPos = 0;
    protected int yPos = 0;
    protected boolean sichtbar = true;
    
    // Momentan gesetzte Farbe (wird von fuellen() oder rand() übernommen)
    protected String farbe = StaticTools.leseNormalfarbe();
    
    // Status für Füllung und Rand; diese Werte werden an das Grafikobjekt weitergegeben.
    protected boolean fillEnabled = false;
    protected boolean borderEnabled = false;
    protected String fillColor = StaticTools.leseNormalfarbe();
    protected String borderColor = StaticTools.leseNormalfarbe();
    
    public Kreis() {
        this(Zeichnung.gibZeichenflaeche());
    }
    
    public Kreis(int neuerRadius) {
        this(Zeichnung.gibZeichenflaeche(), 0, 0, neuerRadius);
    }
    
    public Kreis(int neuesX, int neuesY, int neuerRadius) {
        this(Zeichnung.gibZeichenflaeche(), neuesX, neuesY, neuerRadius);
    }
    
    public Kreis(IContainer behaelter) {
        this(behaelter, 0, 0, 50);
    }
    
    public Kreis(IContainer behaelter, int neuesX, int neuesY, int neuerRadius) {
        obj = new CKreis();
        behaelter.add(obj, 0);
        setzeDimensionen(neuesX, neuesY, neuerRadius);
        behaelter.validate();
    }
    
    /**
     * Liefert die BasisComponente (wird für Container-Operationen benötigt).
     */
    public BasisComponente getBasisComponente() {
        return obj;
    }
    
    public void sichtbarMachen() {
        sichtbar = true;
        obj.sichtbarMachen();
    }
    
    public void unsichtbarMachen() {
        sichtbar = false;
        obj.unsichtbarMachen();
    }
    
    /**
     * Setzt den Radius (Größe) des Kreises.
     */
    public void setzeGroesse(int neuerRadius) {
        radius = neuerRadius;
        obj.setzeGroesse(radius * 2, radius * 2);
    }
    
    /**
     * Setzt die Position des Kreises (linke obere Ecke der Bounding-Box).
     */
    public void setzePosition(int neuesX, int neuesY) {
        xPos = neuesX;
        yPos = neuesY;
        obj.setzePosition(xPos, yPos);
    }
    
    /**
     * Setzt Position und Radius des Kreises.
     */
    public void setzeDimensionen(int neuesX, int neuesY, int neuerRadius) {
        xPos = neuesX;
        yPos = neuesY;
        radius = neuerRadius;
        obj.setzeDimensionen(xPos, yPos, radius * 2, radius * 2);
    }
    
    /**
     * Setzt die aktuell verwendete Farbe. Diese wird dann bei fuellen() oder rand() übernommen.
     */
    public void setzeFarbe(String neueFarbe) {
        farbe = neueFarbe;
    }
    
    /**
     * Aktiviert die Füllung des Kreises mit der momentan gesetzten Farbe.
     */
    public void fuellen() {
        fillEnabled = true;
        fillColor = farbe;
        obj.setFill(fillEnabled, fillColor);
    }
    
    /**
     * Aktiviert den Rand des Kreises mit der momentan gesetzten Farbe.
     */
    public void rand() {
        borderEnabled = true;
        borderColor = farbe;
        obj.setBorder(borderEnabled, borderColor);
    }
    
    /**
     * Passt die Linienstärke (Randdicke) des Kreises an.
     * @param width Die gewünschte Randdicke (in Pixeln)
     */
    public void setBorderWidth(float width) {
        obj.setBorderWidth(width);
    }
    
    public void entfernen() {
        if (obj != null) {
            obj.ausContainerEntfernen();
            obj = null;
        }
    }
    
    @Override
    protected void finalize() {
        if (!Zeichnung.verweistesGUIElementEntfernen) return;
        if (obj != null) {
            entfernen();
        }
    }
}

/**
 * Innere Klasse CKreis – übernimmt das tatsächliche Zeichnen des Ovals.
 */
@SuppressWarnings("serial")
class CKreis extends BasisComponente {
    
    // Lokale Zustände für Füllung und Rand
    protected boolean fillEnabled = false;
    protected boolean borderEnabled = false;
    protected String fillColor = StaticTools.leseNormalfarbe();
    protected String borderColor = StaticTools.leseNormalfarbe();
    protected float borderWidth = 3; // Standard-Randdicke (3 Pixel)
    
    public CKreis() {
        // ggf. weitere Initialisierungen
    }
    
    /**
     * Setzt den Füllstatus und die Füllfarbe, dann fordert er ein Repaint an.
     */
    public void setFill(boolean enabled, String color) {
        fillEnabled = enabled;
        fillColor = color;
        repaint();
    }
    
    /**
     * Setzt den Randstatus und die Randfarbe, dann fordert er ein Repaint an.
     */
    public void setBorder(boolean enabled, String color) {
        borderEnabled = enabled;
        borderColor = color;
        repaint();
    }
    
    /**
     * Setzt die Randdicke und fordert ein Repaint an.
     * @param width Gewünschte Randdicke in Pixeln.
     */
    public void setBorderWidth(float width) {
        borderWidth = width;
        repaint();
    }
    
    /**
     * Zeichnet den Kreis: zuerst die Füllung (falls aktiviert), danach den Rand (falls aktiviert).
     */
    @Override
    public void paintComponentSpezial(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int breite = getSize().width;
        int hoehe  = getSize().height;
        
        // Füllung zeichnen
        if (fillEnabled) {
            g2.setColor(StaticTools.getColor(fillColor));
            g2.fillOval(0, 0, breite, hoehe);
        }
        
        // Rand zeichnen mit angepasster Linienstärke
        if (borderEnabled) {
            g2.setColor(StaticTools.getColor(borderColor));
            g2.setStroke(new BasicStroke(borderWidth));
            g2.drawOval(0, 0, breite, hoehe);
        }
    }
}
