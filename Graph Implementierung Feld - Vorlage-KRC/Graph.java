/**
 * Implementierung mit knotenFeld (Array)
 * 
 * @author Stoeckle 
 * @version 16.11.22
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Graph{
    //Attribute:
    private Knoten[] knotenFeld;
    private int[][] adjazenzMatrix;
    private int knotenAnzahl;           //Anzahl Knoten wird beim Hinzufügen um 1 erhöht! 

    // Konstruktor
    public Graph(int maxAnzahlKnoten) {
        knotenFeld = new Knoten[maxAnzahlKnoten];
        adjazenzMatrix = new int[maxAnzahlKnoten][maxAnzahlKnoten];
        knotenAnzahl=0; //Anzahl der Knoten zu Beginn

        //Adjazenzmatrix initialisieren  
        for (int i = 0; i < maxAnzahlKnoten; i++) {
            for (int j = 0; j < maxAnzahlKnoten; j++) {
                if (i != j) adjazenzMatrix[i][j] = -1;
            }
        }
    }

    /** 
     * Hilfsmethode knotenIndex(String suchSchluessel)
     * Gibt den Index zu einem Knoten mit einem bestimmten Schlüssel im knotenFeld
     */
    private int gibIndex(String knotenSchluessel) {
        for (int i = 0; i< knotenAnzahl; i++) {
            if (knotenFeld[i].gibSchluessel().equalsIgnoreCase(knotenSchluessel)) {
                return i;
            }
        }
        return -1; //Wenn nicht gefunden
    }

    /** 
     * Einen Knoten hinzufuegen
     * fügt dem Graphen einen Knoten hinzu
     */
    public void knotenHinzufuegen(String schluessel) {
        if (knotenAnzahl < knotenFeld.length) {
            //Den Knoten an die erste freie Stelle im Feld setzen. 
            knotenFeld[knotenAnzahl] = new Knoten(schluessel);
            knotenAnzahl++;
        } else {
            System.out.println("Es wurde kein Knoten erzeugt, da der Graph schon die maximale Anzahl an Knoten enthält.");
        }
    }

    /** 
     * Eine Kante hinzufuegen 
     * Fügt dem UNGERICHTETEN Graphen eine Kante hinzu (Adjazenzmatrix symmetrisch)
     */
    public void kanteHinzufuegen(String startSchluessel, String zielSchluessel, int bewertung) {
        // Position im Feld wird gesucht
        int startIndex = this.gibIndex(startSchluessel);
        int zielIndex = this.gibIndex(zielSchluessel);
        //hinzufuegen SYMMETRISCH
        if (startIndex >-1 && zielIndex >-1) {
            adjazenzMatrix[startIndex][zielIndex] = bewertung;
            adjazenzMatrix[zielIndex][startIndex] = bewertung;
        } else {
            System.out.println("Keine Kante erzeugt, da Start- oder Zielknoten nicht existieren.");
        }
    }

    /****************************************************************************************************
     * Ausgabe und Darstellung
     ****************************************************************************************************/

    //gibt die Schluessel aller Knoten der Reihe nach aus.
    public void knotenAusgeben() {
        System.out.println("KnotenFeld:");
        for (int i = 0; i < knotenAnzahl; i = i + 1) {
            System.out.print(knotenFeld[i].gibSchluessel() + ", ");
        }
    }

    //gibt Adjazenzmatrix textuell aus
    public void matrixAusgeben() {
        System.out.println("");
        System.out.println("Die Adjazenzmatrix lautet:");
        for (int i = 0; i < knotenAnzahl; i = i + 1) {
            for (int j = 0; j < knotenAnzahl; j = j + 1) {
                if (adjazenzMatrix[i][j] == -1 || adjazenzMatrix[i][j]>9) {
                    System.out.print(adjazenzMatrix[i][j] + ", ");
                } else {
                    System.out.print(" " + adjazenzMatrix[i][j] + ", ");
                }
            }
            System.out.println("");
        }
    }

    public void sucheRoute(String startSchluessel, String zielSchluessel) {
        int startIndex = gibIndex(startSchluessel);
        int zielIndex = gibIndex(zielSchluessel);

        int[] vorgaenger = new int[knotenAnzahl];
        for (int i = 0; i < knotenAnzahl; i++) {
            vorgaenger[i] = -1;
            knotenFeld[i].setzeBesucht(false); // Alle Knoten zurücksetzen
        }

        int[] stack = new int[knotenAnzahl];
        int stackPointer = 0;

        stack[stackPointer++] = startIndex;
        knotenFeld[startIndex].setzeBesucht(true);

        while (stackPointer > 0) {
            int aktuellerIndex = stack[--stackPointer];

            if (aktuellerIndex == zielIndex) {
                // Route zurückverfolgen
                int laenge = 0;
                int temp = zielIndex;
                while (temp != -1) {
                    laenge++;
                    temp = vorgaenger[temp];
                }

                String[] route = new String[laenge];
                temp = zielIndex;
                int distanz;
                for (int i = laenge - 1; i >= 0; i--) {
                    route[i] = knotenFeld[temp].gibSchluessel();
                    temp = vorgaenger[temp];
                }
                System.out.println("Route gefunden: ");
                for (int i = 0; i < route.length; i++) {
                    System.out.print(route[i] + " ");
                }
            }

            // Alle Nachbarn prüfen
            for (int i = 0; i < knotenAnzahl; i++) {
                if (adjazenzMatrix[aktuellerIndex][i] != -1 && !knotenFeld[i].istBesucht()) {
                    stack[stackPointer++] = i;
                    knotenFeld[i].setzeBesucht(true);
                    vorgaenger[i] = aktuellerIndex;
                }
            }
        }
    }

    public void sucheKuerzestenPfad(String startSchluessel, String zielSchluessel) {
        float startzeit = System.currentTimeMillis();
        int startIndex = gibIndex(startSchluessel);
        int zielIndex = gibIndex(zielSchluessel);
        int[] vorgaenger = new int[knotenAnzahl];
        int[] distanz = new int[knotenAnzahl];

        // Initialisierung
        for (int i = 0; i < knotenAnzahl; i++) {
            vorgaenger[i] = -1;
            distanz[i] = Integer.MAX_VALUE; // unendlich
            knotenFeld[i].setzeBesucht(false);
        }

        distanz[startIndex] = 0;

        while (true) {
            // Nicht besuchten Knoten mit minimaler Distanz finden
            int aktuellerIndex = -1;
            int minDist = Integer.MAX_VALUE;
            for (int i = 0; i < knotenAnzahl; i++) {
                if (!knotenFeld[i].istBesucht() && distanz[i] < minDist) {
                    minDist = distanz[i];
                    aktuellerIndex = i;
                }
            }

            if (aktuellerIndex == -1) break; // Alle erreichbaren Knoten besucht
            if (aktuellerIndex == zielIndex) break; // Ziel erreicht

            knotenFeld[aktuellerIndex].setzeBesucht(true);

            // Nachbarn aktualisieren
            for (int i = 0; i < knotenAnzahl; i++) {
                int gewicht = adjazenzMatrix[aktuellerIndex][i];
                if (gewicht >= 0 && !knotenFeld[i].istBesucht()) { // -1 = keine Kante
                    int neueDistanz = distanz[aktuellerIndex] + gewicht;
                    if (neueDistanz < distanz[i]) {
                        distanz[i] = neueDistanz;
                        vorgaenger[i] = aktuellerIndex;
                    }
                }
            }
        }

        // Prüfen, ob ein Pfad existiert
        if (distanz[zielIndex] == Integer.MAX_VALUE) {
            System.out.println("Kein Pfad vom Start zum Ziel gefunden.");
            return;
        }

        // Pfad zurückverfolgen und ausdrucken
        int laenge = 0;
        int temp = zielIndex;
        while (temp != -1) {
            laenge++;
            temp = vorgaenger[temp];
        }

        int[] routeIndices = new int[laenge];
        temp = zielIndex;
        for (int i = laenge - 1; i >= 0; i--) {
            routeIndices[i] = temp;
            temp = vorgaenger[temp];
        }

        // Route und Gesamtdistanz drucken
        System.out.print("Route: ");
        for (int i = 0; i < laenge; i++) {
            System.out.print(knotenFeld[routeIndices[i]].gibSchluessel());
            if (i < laenge - 1) System.out.print(" -> ");
        }
        System.out.println("\nGesamtdistanz: " + distanz[zielIndex]);
        System.out.println("Berechnungsdauer: " + (System.currentTimeMillis() - startzeit));
    }

    private int bewertungAusgeben(String startSchluessel, String zielSchluessel) {
        // Position im Feld wird gesucht
        int startIndex = this.gibIndex(startSchluessel);
        int zielIndex = this.gibIndex(zielSchluessel);

        return adjazenzMatrix[startIndex][zielIndex];
    }
}