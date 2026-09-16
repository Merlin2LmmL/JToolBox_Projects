public class Apple {
    Kreis apple;
    public Apple(int x, int y, int size) {
        apple = new Kreis(x, y, size);
        apple.setzeFarbe("rot");
        apple.fuellen();
        apple.sichtbarMachen();
    }
    
    public void remove() {
        apple.getBasisComponente().ausContainerEntfernen();
    }
}