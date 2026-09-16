public class Spiel {
    /**
     * Level-Auswahl Konstruktors
     * 
     * @param level     Wähle das Level aus, dass du spielen möchtest!
     * 
     * Hinweis: Bis jetzt gibt es 2 Level.
     */
    public Spiel(int level) {
        TiledMap map = new TiledMap("level" + level + ".csv", "tileSet.png", 16, 16, 10 * 16, 10 * 16);
        Figur figur = new Figur(map);
    }
}
