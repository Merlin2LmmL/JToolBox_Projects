public class DameFigur {
    private Kreis[][][] figuren; // [x][y][team], Team 0 = Schwarz, Team 1 = Weiß

    public DameFigur() {
        figuren = new Kreis[8][3][2]; // 8 Spalten, 3 Reihen pro Team, 2 Teams
        platziereFiguren();
        einfaerben();
    }

    private void platziereFiguren() {
        // Team Schwarz (unten)
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 3; y++) {
                if ((x + y) % 2 == 0) { // Nur auf schwarzen Feldern
                    figuren[x][y][0] = new Kreis(x * 50 + 5, (5 + 50 * (y + 5)), 20);
                }
            }
        }

        // Team Weiß (oben)
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 3; y++) {
                if ((x + y) % 2 == 1) { // Nur auf schwarzen Feldern
                    figuren[x][y][1] = new Kreis(x * 50 + 5, (5 + 50 * y), 20);
                }
            }
        }
    }

    public void einfaerben() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 3; y++) {
                for (int team = 0; team < 2; team++) {
                    if (figuren[x][y][team] != null) {
                        if (team == 1) { // Schwarz
                            figuren[x][y][team].setzeFarbe("schwarz");
                            figuren[x][y][team].fuellen();
                            figuren[x][y][team].setzeFarbe("weiss");
                            figuren[x][y][team].rand();
                        } else { // Weiß
                            figuren[x][y][team].setzeFarbe("weiss");
                            figuren[x][y][team].fuellen();
                            figuren[x][y][team].setzeFarbe("schwarz");
                            figuren[x][y][team].rand();
                        }
                        figuren[x][y][team].setBorderWidth(3);
                        figuren[x][y][team].sichtbarMachen();
                    }
                }
            }
        }
    }
}
