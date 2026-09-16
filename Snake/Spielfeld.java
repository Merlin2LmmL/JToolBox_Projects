public class Spielfeld {
    int[] FELD = new int[]{6, 6, 100};
    Quadrat[][] felder;
    public Spielfeld() {
        felder = new Quadrat[FELD[0]][FELD[1]];
        for (int y=0; y<FELD[1]; y++) {
            for (int x=0; x<FELD[0]; x++) {
                felder[x][y] = new Quadrat(x*FELD[2],y*FELD[2],FELD[2]);}
        }
        einfaerben();
    }

    public void einfaerben() {
        for (int y=0; y<FELD[1]; y++) {
            for (int x=0; x<FELD[0]; x++){
                felder[x][y].setzeFarbe("weiss");
                felder[x][y].fuellen();
            }
        }
    }
}
