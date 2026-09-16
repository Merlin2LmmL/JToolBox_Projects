import java.awt.image.BufferedImage;

/**
 * BilddateiAusschnitt:
 *  - Erzeugt ein JToolbox-kompatibles "Bilddatei"-Objekt direkt aus
 *    einem BufferedImage (z.B. Subimage eines großen Sheets).
 */
public class BilddateiAusschnitt extends BilddateiAbstrakt {

    // Hinweis: "bBild" muss nicht private sein, sondern package-private/protected,
    // damit wir es hier setzen können.
    protected BufferedImage bBild;

    /**
     * Konstruktor: Nimmt ein bereits ausgeschnittenes BufferedImage
     */
    public BilddateiAusschnitt(BufferedImage sub) {
        this.bBild = sub;
    }

    @Override
    public BufferedImage leseBild() {
        return bBild;
    }
}
