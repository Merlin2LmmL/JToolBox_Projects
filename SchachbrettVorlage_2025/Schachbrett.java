public class Schachbrett {
    Quadrat[][] felder;
    public Schachbrett() {
        felder = new Quadrat[8][8];
        for (int x=0; x<8; x=x+1) {
            for (int y=0; y<8; y=y+1) {
                felder[x][y] = new Quadrat(x*50,y*50,50);}
        }
        einfaerben();
    }

    public void einfaerben() {
        for (int y=0; y<8; y++) {
            for (int x=0; x<8; x++){
                if ((x+y)%2 == 0) {
                    felder[x][y].setzeFarbe("weiss");
                } else {
                    felder[x][y].setzeFarbe("schwarz");
                }
            }
        }
    }
}
