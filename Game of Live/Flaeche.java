public class Flaeche {
    Feld[][] felder;
    int breite;
    int hoehe;
    public Flaeche(int hoehe, int breite, int groesse) {
        this.breite = breite;
        this.hoehe = hoehe;

        felder = new Feld[breite][hoehe];
        for (int y=0; y<hoehe; y++) {
            for (int x=0; x<breite; x++) {
                felder[x][y] = new Feld(x*groesse,y*groesse,groesse);
                felder[x][y].setzeFarbe("weiss");
                felder[x][y].fuellen();
                felder[x][y].sichtbarMachen();
            }   
        }
    }

    public void vermehren() {
        // Zuerst: In-Place-Berechnung der neuen Zustände als Marker in der Variable 'farbe'
        for (int x = 0; x < breite; x++) {
            for (int y = 0; y < hoehe; y++) {
                int lebendigeNachbarn = 0;

                // Überprüfe alle 8 Nachbarfelder
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx == 0 && dy == 0) continue;  // Das eigene Feld überspringen
                        int nx = x + dx;
                        int ny = y + dy;
                        if (nx >= 0 && nx < breite && ny >= 0 && ny < hoehe) {
                            String nachbarZustand = felder[nx][ny].farbe;
                            // Zähle als lebendig, wenn der Originalzustand lebendig war
                            if (isOriginallyLive(nachbarZustand)) {
                                lebendigeNachbarn++;
                            }
                        }
                    }
                }

                // Aktuellen Zustand holen:
                String aktuellerZustand = felder[x][y].farbe;
                if (isOriginallyLive(aktuellerZustand)) {
                    // Zelle war ursprünglich lebendig ("schwarz")
                    if (lebendigeNachbarn < 2 || lebendigeNachbarn > 3) {
                        // Unter- oder Überbevölkerung: Zelle stirbt.
                        // Markiere den Übergang: lebendig -> tot
                        felder[x][y].farbe = "schwarz->weiss";
                    }
                    // Bei 2 oder 3 Nachbarn bleibt sie lebendig – Zustand bleibt "schwarz"
                } else {
                    // Zelle war ursprünglich tot ("weiss")
                    if (lebendigeNachbarn == 3) {
                        // Exakte Reproduktion: Tote Zelle wird lebendig.
                        felder[x][y].farbe = "weiss->schwarz";
                    }
                }
            }
        }

        // Zweite Schleife: Übergangszustände endgültig umsetzen
        for (int x = 0; x < breite; x++) {
            for (int y = 0; y < hoehe; y++) {
                if (felder[x][y].farbe.equals("schwarz->weiss")) {
                    felder[x][y].setzeFarbe("weiss");
                    felder[x][y].fuellen();
                } else if (felder[x][y].farbe.equals("weiss->schwarz")) {
                    felder[x][y].setzeFarbe("schwarz");
                    felder[x][y].fuellen();
                }
            }
        }
    }

    /**
     * Hilfsmethode, um den ursprünglichen Zustand zu ermitteln.
     * Zustände "schwarz" und "schwarz->weiss" werden als ursprünglich lebendig betrachtet.
     * 
     * @param zustand Der aktuelle Wert von felder[x][y].farbe
     * @return true, wenn die Zelle ursprünglich lebendig war, sonst false.
     */
    private boolean isOriginallyLive(String zustand) {
        return zustand.equals("schwarz") || zustand.equals("schwarz->weiss");
    }
    
    public void zufall(double zufallswert) {
        for (int x = 0; x < breite; x++) {
            for (int y = 0; y < hoehe; y++) {
                if ((double) Math.random() * (double) zufallswert >= 1.0) {
                    felder[x][y].setzeFarbe("schwarz");
                } else {
                    felder[x][y].setzeFarbe("weiss");
                }
                felder[x][y].fuellen();
            }
        }
    }
}
