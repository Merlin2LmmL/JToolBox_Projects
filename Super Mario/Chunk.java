import java.awt.Graphics;

public class Chunk {
    private Block[][] blocks;

    public Chunk(int width, int height) {
        blocks = new Block[width][height];
    }

    // Setzt den übergebenen Block an der Position (x, y) im Chunk.
    public void setBlock(int x, int y, Block block) {
        blocks[x][y] = block;
    }

    public Block[][] getBlock() {
        return blocks;
    }

    /**
     * Rendert alle Blöcke dieses Chunks.
     * @param g Das Graphics-Objekt.
     * @param offsetX Globaler X-Versatz in Pixeln.
     * @param offsetY Globaler Y-Versatz in Pixeln.
     * @param tileSize Die Größe eines Tiles in Pixeln.
     */
    public void render(int offsetX, int offsetY) {
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[x].length; y++) {
                Block block = blocks[x][y];
                if (block != null) {
                    block.render(offsetX, offsetY);
                }
            }
        }
    }
}
