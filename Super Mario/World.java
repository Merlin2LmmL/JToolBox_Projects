import java.awt.Graphics;
import java.util.ArrayList;

public class World {
    // 1-dimensionale Liste für Chunks, die horizontal angeordnet sind.
    private ArrayList<Chunk> chunks;

    // Größe eines Chunks in Blöcken (z. B. 16x32)
    private int chunkWidth = 16;
    private int chunkHeight = 32;
    private int tileSize = 16; // Pixelgröße eines Blocks

    // Render-Radius in Chunk-Einheiten
    private int renderRadius = 3;
    
    public World() {
        chunks = new ArrayList<>();
        generateChunksAround(0, 10);
    }

    /**
     * Liefert den Chunk an der X-Koordinate chunkX.
     * Falls der Chunk noch nicht existiert, wird er generiert.
     */
    public Chunk getChunk(int chunkX) {
        while (chunks.size() <= chunkX) {
            chunks.add(null);
        }
        if (chunks.get(chunkX) == null) {
            generateChunk(chunkX);
        }
        return chunks.get(chunkX);
    }

    /**
     * Generiert einen neuen Chunk an der X-Koordinate chunkX.
     */
    public void generateChunk(int chunkX) {
        Chunk newChunk = new Chunk(chunkWidth, chunkHeight);
        // Stelle sicher, dass die Liste groß genug ist
        while (chunks.size() <= chunkX) {
            chunks.add(null);
        }
        chunks.set(chunkX, newChunk);
        System.out.println("Chunk generiert an Index: " + chunkX);
        generateTerrainInChunk(chunkX);        
    }

    /**
     * Generiert alle Chunks in einem Bereich von startChunkX bis endChunkX.
     */
    public void generateChunksInRange(int startChunkX, int endChunkX) {
        for (int x = startChunkX; x <= endChunkX; x++) {
            getChunk(x);
        }
    }

    /**
     * Generiert alle Chunks innerhalb eines bestimmten Radiuses um eine Weltposition (in Blockkoordinaten).
     * Praktisch, um z. B. die Umgebung des Spielers zu laden.
     */
    public void generateChunksAround(int worldX, int radiusChunks) {
        int centerChunkX = worldX / chunkWidth;
        int start = Math.max(0, centerChunkX - radiusChunks);
        int end = centerChunkX + radiusChunks;
        generateChunksInRange(start, end);
    }

    /**
     * Setzt einen Block in der Welt basierend auf globalen Weltkoordinaten.
     */
    public void setBlock(int worldX, int worldY, int blockID) {
        // Bestimme, in welchem Chunk sich die Blockkoordinate befindet
        int chunkX = worldX / chunkWidth;
        // Lokale Koordinaten im Chunk
        int localX = worldX % chunkWidth;
        int localY = worldY % chunkHeight;

        setBlockInChunk(chunkX, localX, localY, blockID);
    }

    /**
     * Hier berechnen wir den globalen X-Wert für den Block, damit er nicht nur lokal,
     * sondern an der richtigen Stelle in der Welt positioniert wird.
     */
    public void setBlockInChunk(int chunkX, int localX, int localY, int blockID) {
        Chunk chunk = getChunk(chunkX);
        int globalX = chunkX * chunkWidth + localX; // globaler X-Wert (in Blockeinheiten)
        // Erstelle den Block mit globalen Koordinaten (Y bleibt gleich)
        Block block = new Block(globalX, localY, blockID);
        chunk.setBlock(localX, localY, block);
    }

    public void generateTerrainInChunk(int chunkX) {
        // Beispiel: Erzeuge eine "Oberfläche" bei y = 20
        for (int x = 0; x < chunkWidth; x++) {
            setBlockInChunk(chunkX, x, 20, 2);
        }
        // Beispiel: Fülle den Bereich unterhalb der Oberfläche mit BlockID 9
        for (int y = 21; y < chunkHeight; y++) {
            for (int x = 0; x < chunkWidth; x++) {
                setBlockInChunk(chunkX, x, y, 9);
            }
        }
    }

    public ArrayList<Chunk> getChunks() {
        return chunks;
    }
    
    /**
     * Rendert nur die Chunks, die innerhalb eines bestimmten Radiuses um den Spieler liegen.
     * @param g Das Graphics-Objekt zum Zeichnen.
     * @param player Der Spieler, um dessen Position gerendert werden soll.
     */
    public void render(Graphics g, Player player) {
        // Berechne den Chunk-Index, in dem sich der Spieler befindet:
        int playerChunkX = player.getX() / chunkWidth;
        // Lege den Render-Bereich fest (in Chunk-Einheiten)
        int startChunk = Math.max(0, playerChunkX - renderRadius);
        int endChunk = playerChunkX + renderRadius;
        
        // Stelle sicher, dass auch alle benötigten Chunks existieren
        generateChunksInRange(startChunk, endChunk);
        
        // Iteriere über den Bereich und rendere die jeweiligen Chunks:
        for (int chunkX = startChunk; chunkX <= endChunk; chunkX++) {
            Chunk chunk = getChunk(chunkX);
            if (chunk != null) {
                // Berechne den globalen X-Versatz (in Pixeln):
                int offsetX = chunkX * chunkWidth * tileSize;
                // Da die Welt nur horizontal angeordnet ist, verwenden wir hier 0 als Y-Versatz.
                chunk.render(offsetX, 0);;
            }
        }
    }
}
