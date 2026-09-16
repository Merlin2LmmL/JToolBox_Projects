import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * TiledMap.java
 * 
 * Erstellt eine visuelle Karte aus einer CSV-Datei und einem Tileset-PNG,
 * wobei die tatsächliche Zeichen-Größe jeder Kachel (blockSize) frei einstellbar ist.
 * Zusätzlich kann per Pixelkoordinaten erfragt werden, welcher Tile-Typ an dieser Stelle liegt.
 * 
 * Autor: Merlin Ortner
 * Version: 09.05.2025
 */
public class TiledMap {
    private int[][] map;                  // Array für die Tile-IDs aus der CSV
    private BufferedImage tilesetImage;   // Das vollständige Tileset-Bild
    private BufferedImage[] tiles;        // Array mit einzelnen Tile-Subimages
    public int tileWidth, tileHeight;    // Originalgröße der Textur-Kachel (z. B. 16×16 px)
    public int cols, rows;               // Anzahl Spalten/Zeilen im CSV
    private int blockSize;                // Gewünschte Zeichen-Größe jeder Kachel (z. B. 50×50 px)
    public int spawnX, spawnY;

    /**
     * Haupt-Konstruktor.
     * 
     * @param csvFile     Pfad zur CSV-Datei (z. B. "level1.csv")
     * @param tilesetFile Pfad zum Tileset-Bild (z. B. "TileSet.png")
     * @param tileSize    Originalgröße der Kacheln im Tileset (z. B. 16 für 16×16-Pixel-Tiles)
     * @param blockSize   Gewünschte Zeichengröße jeder Kachel (z. B. 50 für 50×50-Pixel)
     */
    public TiledMap(String csvFile, String tilesetFile, int tileSize, int blockSize, int spawnX, int spawnY) {
        this.tileWidth  = tileSize;
        this.tileHeight = tileSize;
        this.blockSize  = blockSize;

        try {
            loadMap(csvFile);
            loadTileset(tilesetFile);
            sliceTileset();
            displayMap();
        } catch (Exception e) {
            System.err.println("Fehler beim Laden der TiledMap: " + e.getMessage());
            e.printStackTrace();
        }
        this.spawnX = spawnX;
        this.spawnY = (rows * 16) - spawnY;
    }

    /**
     * Parameterloser Konstruktor mit Standarddateien und Werten:
     * - CSV:       "level1.csv"
     * - Tileset:   "TileSet.png"
     * - tileSize:  16 (Original-Tile-Größe)
     * - blockSize: 16 (Zeichengröße jeder Kachel)
     */
    public TiledMap() {
        this("level1.csv", "tileSet.png", 16, 16, 10 * 16, 10 * 16);
    }

    /**
     * Liest die CSV-Datei ein und befüllt das map-Array mit Tile-IDs.
     */
    private void loadMap(String csvFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            java.util.List<int[]> rowsList = new java.util.ArrayList<>();

            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(",");
                int[] row = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    row[i] = Integer.parseInt(parts[i]);
                }
                rowsList.add(row);
            }

            rows = rowsList.size();
            cols = rowsList.get(0).length;
            map = new int[rows][cols];

            for (int r = 0; r < rows; r++) {
                map[r] = rowsList.get(r);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Fehler beim Lesen der Map-Datei: " + e.getMessage());
        }
    }

    /**
     * Lädt das komplette Tileset-Bild in tilesetImage.
     */
    private void loadTileset(String tilesetFile) {
        try {
            tilesetImage = ImageIO.read(new File(tilesetFile));
        } catch (IOException e) {
            System.err.println("Fehler beim Laden des Tilesets: " + e.getMessage());
        }
    }

    /**
     * Zerlegt das geladene Tileset in einzelne Tile-Subimages,
     * basierend auf tileWidth × tileHeight.
     */
    private void sliceTileset() {
        if (tilesetImage == null) return;

        int tsCols = tilesetImage.getWidth() / tileWidth;
        int tsRows = tilesetImage.getHeight() / tileHeight;
        tiles = new BufferedImage[tsCols * tsRows];
        int idx = 0;

        for (int ty = 0; ty < tsRows; ty++) {
            for (int tx = 0; tx < tsCols; tx++, idx++) {
                tiles[idx] = tilesetImage.getSubimage(
                    tx * tileWidth,
                    ty * tileHeight,
                    tileWidth,
                    tileHeight
                );
            }
        }
    }

    /**
     * Skalierungsmethode: Erzeugt aus einem originalen BufferedImage
     * eine Version in der Größe destSize×destSize.
     */
    private BufferedImage scaleTile(BufferedImage original, int destSize) {
        BufferedImage scaled = new BufferedImage(destSize, destSize, original.getType());
        Graphics2D g = scaled.createGraphics();
        // Bilineare Interpolation, um die Skalierung weich zu gestalten
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(original, 0, 0, destSize, destSize, null);
        g.dispose();
        return scaled;
    }

    /**
     * Zeichnet die gesliceten und skalierten Kacheln auf dem Bildschirm.
     * 
     * Wichtig:
     * - Position: c * blockSize, r * blockSize
     * - Größe:   blockSize × blockSize
     */
    private void displayMap() {
        if (map == null || tiles == null) return;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int gid = map[r][c];
                if (gid >= 0 && gid < tiles.length) {
                    // 1. Rohes Tile aus dem tiles-Array holen
                    BufferedImage rawTile = tiles[gid];
                    // 2. Auf blockSize×blockSize skalieren
                    BufferedImage scaledTile = scaleTile(rawTile, blockSize);
                    // 3. In ein BilddateiAbstrakt packen
                    BilddateiAbstrakt tileFile = new TileImage(scaledTile);
                    // 4. Zeichne das skalierte Tile an Position (c*blockSize, r*blockSize)
                    new Bild(c * blockSize, r * blockSize, blockSize, blockSize, tileFile);
                }
            }
        }
    }

    /**
     * Gibt den Tile-Typ (GID) zurück, der sich an den Pixelkoordinaten (x, y) befindet.
     * 
     * @param x Die x-Koordinate in Pixel (0 = ganz links).
     * @param y Die y-Koordinate in Pixel (0 = ganz oben).
     * @return  Die GID (Tile-ID) aus dem map-Array oder -1, wenn (x,y) außerhalb der Karte liegt.
     */
    public int getTileTypeAtPixel(int x, int y) {
        // Umrechnung von Pixelkoordinaten in Tile-Koordinaten:
        int col = x / blockSize; // Spalte = Ganzzahl-Division
        int row = y / blockSize; // Zeile  = Ganzzahl-Division

        // Prüfen, ob (row,col) innerhalb der Map-Grenzen liegt:
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return -1; // Außerhalb der Karte
        }

        // Ansonsten GID aus dem map-Array zurückgeben
        return map[row][col];
    }

    /**
     * Interne Klasse, um ein BufferedImage an die Bibliothek Bild/BilddateiAbstrakt anzupassen.
     */
    private static class TileImage extends BilddateiAbstrakt {
        private BufferedImage img;

        public TileImage(BufferedImage img) {
            this.img = img;
        }

        @Override
        public BufferedImage leseBild() {
            return img;
        }
    }
    
    public boolean isInBoundary(int x, int y) {
        return (x < 0 || x > cols * tileWidth || y < 0 || y > rows* tileHeight);
    }
}
