public class Start {
    Graph graph;

    public Start(int graphNr) {
        if (graphNr == 0) {
            graph = new Graph(8);
            // Knoten
            graph.knotenHinzufuegen("A");
            graph.knotenHinzufuegen("F");
            graph.knotenHinzufuegen("O");
            graph.knotenHinzufuegen("S");
            graph.knotenHinzufuegen("SB");
            graph.knotenHinzufuegen("U");
            graph.knotenHinzufuegen("W");
            graph.knotenHinzufuegen("Z");

            // Kanten
            graph.kanteHinzufuegen("A", "F", 4);
            graph.kanteHinzufuegen("A", "O", 10);
            graph.kanteHinzufuegen("A", "SB", 2);
            graph.kanteHinzufuegen("F", "U", 15);
            graph.kanteHinzufuegen("O", "SB", 11);
            graph.kanteHinzufuegen("O", "U", 2);
            graph.kanteHinzufuegen("S", "U", 3);
            graph.kanteHinzufuegen("SB", "W", 6);
            graph.kanteHinzufuegen("W", "Z", 3);
            graph.kanteHinzufuegen("Z", "O", 1);
        } else if (graphNr == 1) {
            graph = new Graph(30);

            // Knoten (30)
            graph.knotenHinzufuegen("Berlin");
            graph.knotenHinzufuegen("Rostock");
            graph.knotenHinzufuegen("Kiel");
            graph.knotenHinzufuegen("Luebeck");
            graph.knotenHinzufuegen("Hamburg");
            graph.knotenHinzufuegen("Bremen");
            graph.knotenHinzufuegen("Hannover");
            graph.knotenHinzufuegen("Braunschweig");
            graph.knotenHinzufuegen("Magdeburg");
            graph.knotenHinzufuegen("Halle");
            graph.knotenHinzufuegen("Leipzig");
            graph.knotenHinzufuegen("Erfurt");
            graph.knotenHinzufuegen("Kassel");
            graph.knotenHinzufuegen("Wuerzburg");
            graph.knotenHinzufuegen("Nuernberg");
            graph.knotenHinzufuegen("Regensburg");
            graph.knotenHinzufuegen("Muenchen");
            graph.knotenHinzufuegen("Augsburg");
            graph.knotenHinzufuegen("Stuttgart");
            graph.knotenHinzufuegen("Mannheim");
            graph.knotenHinzufuegen("Karlsruhe");
            graph.knotenHinzufuegen("Freiburg");
            graph.knotenHinzufuegen("Frankfurt");
            graph.knotenHinzufuegen("Koeln");
            graph.knotenHinzufuegen("Dortmund");
            graph.knotenHinzufuegen("Essen");
            graph.knotenHinzufuegen("Duisburg");
            graph.knotenHinzufuegen("Bochum");
            graph.knotenHinzufuegen("Bielefeld");

            // Kanten (nur direkte, plausible Bahnverbindungen) — Entfernungen in km (grob gerundet)

            // Norden
            graph.kanteHinzufuegen("Kiel", "Hamburg", 100);
            graph.kanteHinzufuegen("Luebeck", "Hamburg", 65);
            graph.kanteHinzufuegen("Rostock", "Hamburg", 200);
            graph.kanteHinzufuegen("Hamburg", "Bremen", 120);
            graph.kanteHinzufuegen("Bremen", "Hannover", 120);

            // Nordost / Mitte
            graph.kanteHinzufuegen("Berlin", "Rostock", 230);
            graph.kanteHinzufuegen("Berlin", "Magdeburg", 150);
            graph.kanteHinzufuegen("Magdeburg", "Braunschweig", 110);
            graph.kanteHinzufuegen("Braunschweig", "Hannover", 55);
            graph.kanteHinzufuegen("Magdeburg", "Halle", 70);
            graph.kanteHinzufuegen("Halle", "Leipzig", 40);

            // Mitteldeutschland / Thuringen
            graph.kanteHinzufuegen("Leipzig", "Erfurt", 140);
            graph.kanteHinzufuegen("Halle", "Erfurt", 100);
            graph.kanteHinzufuegen("Erfurt", "Kassel", 135);
            graph.kanteHinzufuegen("Kassel", "Dortmund", 150);

            // Main-/Südrouten
            graph.kanteHinzufuegen("Kassel", "Wuerzburg", 160);
            graph.kanteHinzufuegen("Wuerzburg", "Nuernberg", 110);
            graph.kanteHinzufuegen("Wuerzburg", "Frankfurt", 120);
            graph.kanteHinzufuegen("Nuernberg", "Regensburg", 110);
            graph.kanteHinzufuegen("Regensburg", "Muenchen", 120);
            graph.kanteHinzufuegen("Nuernberg", "Muenchen", 170);
            graph.kanteHinzufuegen("Muenchen", "Augsburg", 80);
            graph.kanteHinzufuegen("Augsburg", "Stuttgart", 150);

            // Rhein-Main / Oberrheingraben
            graph.kanteHinzufuegen("Frankfurt", "Mannheim", 85);
            graph.kanteHinzufuegen("Mannheim", "Karlsruhe", 70);
            graph.kanteHinzufuegen("Karlsruhe", "Freiburg", 70);
            graph.kanteHinzufuegen("Stuttgart", "Mannheim", 95);

            // West- und Ruhrgebiet (Verdichtung, kurze Distanzen)
            graph.kanteHinzufuegen("Duisburg", "Essen", 10);
            graph.kanteHinzufuegen("Essen", "Bochum", 15);
            graph.kanteHinzufuegen("Bochum", "Dortmund", 20);
            graph.kanteHinzufuegen("Dortmund", "Bielefeld", 80);
            graph.kanteHinzufuegen("Bremen", "Bielefeld", 180);
            
            // Verbindungen zwischen Regionen (Hauptachsen)
            graph.kanteHinzufuegen("Hamburg", "Hannover", 150);
            graph.kanteHinzufuegen("Hannover", "Bielefeld", 120);
            graph.kanteHinzufuegen("Hannover", "Kassel", 150);
            graph.kanteHinzufuegen("Frankfurt", "Koeln", 190);
            graph.kanteHinzufuegen("Frankfurt", "Wuerzburg", 120);
            graph.kanteHinzufuegen("Mannheim", "Stuttgart", 85);
            graph.kanteHinzufuegen("Muenchen", "Stuttgart", 220);
            graph.kanteHinzufuegen("Koeln", "Dortmund", 95);
        }

        graph.knotenAusgeben();
        graph.matrixAusgeben();
    }

    public Graph getGraph() {
        return graph;
    }
}
