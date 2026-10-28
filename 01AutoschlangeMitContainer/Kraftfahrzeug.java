import java.awt.Color;

/**
 * Abstrct class dictating all car classes.
 *
 * @author Merlin Ortner
 * @version 1.0.1
 */
public abstract class Kraftfahrzeug implements Datenelement {
    protected int x, y, ps, laenge;
    protected String bezeichnung, typ;

    protected Behaelter auto;

    // Constructor
    public Kraftfahrzeug(String _bezeichnung, int _ps, int xPos, int yPos) {
        x = xPos;
        y = yPos;
        bezeichnung = _bezeichnung;
        ps = _ps;
        auto = new Behaelter(x, y, 140, 100);
    }

    public void setzePosition(int neuesX, int neuesY) {
        x = neuesX;
        y = neuesY;
        auto.setzePosition(x, y);
    }

    public abstract void fahren(int dx, int dy);

    public void bewegen(int dx, int dy) { fahren(dx, dy); }

    // Datenelement
    public boolean equalsKey(String key) { return bezeichnung.equals(key); }

    public boolean isSmallerThan(Datenelement vergleich) { return bezeichnung.compareTo(vergleich.getBezeichnung()) < 0; }

    public String getBezeichnung() { return bezeichnung; }

    // Color helpers shared by the car classes
    protected static Color adjustColor(Color base, int deltaRed, int deltaGreen, int deltaBlue) {
        int r = clamp(base.getRed()   + deltaRed);
        int g = clamp(base.getGreen() + deltaGreen);
        int b = clamp(base.getBlue()  + deltaBlue);
        return new Color(r, g, b);
    }

    private static int clamp(int wert) {
        if (wert > 255) return 255;
        if (wert < 0) return 0;
        return wert;
    }

    protected static Color getHelmetColor(Color base) {
        return new Color(
            255 - base.getRed(),
            255 - base.getGreen(),
            255 - base.getBlue()
        );
    }
}