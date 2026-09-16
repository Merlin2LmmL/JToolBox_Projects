public class Player {
    private int x, y; // Weltkoordinaten (in Pixeln, nicht Blöcken)
    private Bild sprite;

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.sprite = new Bild("spieler.png"); // Dein Sprite
        this.sprite.setzePosition(x, y);
        this.sprite.sichtbarMachen();
    }

    public void bewege(int dx, int dy) {
        x += dx;
        y += dy;
        sprite.setzePosition(x, y);
        sprite.getBasisComponente().repaint();
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public void update() {
        // Logik pro Tick (z. B. Animation, Gravitation)
    }
}
