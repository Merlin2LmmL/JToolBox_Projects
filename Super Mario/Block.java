public class Block {
    final int TILESIZE = 16;
    final int TILEAMOUNTWIDTH = 7;
    final int TILEAMOUNTHEIGHT = 11;

    Tilesheet sheet;
    Bild tile;  // Bild, das den Block repräsentiert
    
    private int blockX; // Globale Block-Koordinaten
    private int blockY;

    public Block(int x, int y, int blockID) {
        this.blockX = x;
        this.blockY = y;
        
        if (blockID <= TILEAMOUNTHEIGHT * TILEAMOUNTWIDTH) {
            sheet = new Tilesheet("tilesheet.png", 16, 16, TILESIZE, TILESIZE);
            tile = sheet.getTile((blockID - 1) / TILEAMOUNTWIDTH, (blockID - 1) % TILEAMOUNTWIDTH);
            tile.setzeGroesse(TILESIZE, TILESIZE); // falls nötig
            tile.sichtbarMachen(); // Initial sichtbar
        }
    }

    /**
     * Aktualisiert die sichtbare Position des Blocks basierend auf dem Welt-Offset.
     */
    public void render(int offsetX, int offsetY) {
        int pixelX = blockX * TILESIZE + offsetX;
        int pixelY = blockY * TILESIZE + offsetY;

        if (tile != null) {
            tile.setzePosition(pixelX, pixelY);
            tile.getBasisComponente().repaint(); // Bildschirm-Update auslösen
        }
    }
}
