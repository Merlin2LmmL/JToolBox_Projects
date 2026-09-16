import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Tilesheet {

    private BufferedImage[][] tileImages;
    private int rows, cols;
    private int tileWidth, tileHeight;
    private int targetWidth, targetHeight;

    /**
     * Konstruktor
     * @param filename Dateiname des Tilesheets
     * @param tileWidth Ursprüngliche Breite eines Tiles im Tilesheet
     * @param tileHeight Ursprüngliche Höhe eines Tiles im Tilesheet
     * @param targetWidth Zielbreite, auf die das Tile skaliert werden soll
     * @param targetHeight Zielhöhe, auf die das Tile skaliert werden soll
     */
    public Tilesheet(String filename, int tileWidth, int tileHeight, int targetWidth, int targetHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;

        // Gesamtes Tilesheet laden
        Bilddatei bd = new Bilddatei(filename);
        BufferedImage entire = bd.leseBild();

        cols = entire.getWidth() / tileWidth;
        rows = entire.getHeight() / tileHeight;

        tileImages = new BufferedImage[rows][cols];

        // Alle Tiles ausschneiden und direkt in der Zielgröße skalieren
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * tileWidth;
                int y = row * tileHeight;

                // Ursprüngliches Tile ausschneiden
                BufferedImage original = entire.getSubimage(x, y, tileWidth, tileHeight);

                // Neues Bild in Zielgröße erzeugen
                BufferedImage copy = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = copy.createGraphics();
                // Hier kannst du den Interpolationsmodus anpassen: 
                // VALUE_INTERPOLATION_NEAREST_NEIGHBOR für einen pixeligen Retro-Look
                // VALUE_INTERPOLATION_BILINEAR für eine weichere Skalierung
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                g2d.drawImage(original, 0, 0, targetWidth, targetHeight, null);
                g2d.dispose();

                tileImages[row][col] = copy;
            }
        }
    }

    /**
     * Liefert ein bestimmtes Tile als neues Bild-Objekt (kopiert)
     * @param row Zeile
     * @param col Spalte
     * @return Ein Bild-Objekt des angeforderten Tiles
     */
    public Bild getTile(int row, int col) {
        BufferedImage img = tileImages[row][col];
        BilddateiAusschnitt ausschnitt = new BilddateiAusschnitt(img);
        Bild tile = new Bild(ausschnitt);
        tile.unsichtbarMachen(); // Standardzustand
        return tile;
    }

    // Getter für Zeilen und Spalten
    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
}
