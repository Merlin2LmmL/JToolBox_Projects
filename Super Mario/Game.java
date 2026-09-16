import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {
    private World world;
    private Player player;
    private Thread gameThread;
    private final int FPS = 60;

    public Game() {
        // Welt und Spieler initialisieren (Startpositionen anpassen)
        world = new World();
        player = new Player(100, 200); // Beispielwerte für x und y

        // Optional: Fokus und KeyListener für die Steuerung des Spielers hinzufügen
        setFocusable(true);
        requestFocusInWindow();
    }

    // Startet den Game-Loop in einem separaten Thread
    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Der Game-Loop
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerFrame = 1_000_000_000.0 / FPS;

        while (true) {
            long now = System.nanoTime();
            while (now - lastTime > nsPerFrame) {
                updateGame();
                lastTime += nsPerFrame;
            }
            repaint(); // ruft paintComponent auf
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Update-Methode: Hier werden Spiellogik und Spielerbewegungen verarbeitet
    private void updateGame() {
        // Beispiel: Spieler-Update (hier könntest du auch Eingaben abfragen)
        player.update();

        // Wenn der Spieler sich bewegt, kannst du z. B. auch den Welt-Offset neu berechnen
        // world.update() könnte zusätzliche Logik beinhalten, falls nötig.
    }

    // Paint-Methode: Hier werden die Welt und der Spieler gezeichnet
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Rendere die Welt (hier wird der Spieler als Referenz übergeben)
        world.render(g, player);
        
        // Zusätzlich kannst du den Spieler separat rendern, falls nötig
        // (Beachte, dass in deinem Code der Spieler bereits ein Bild-Objekt hat)
    }

    // Hauptmethode zum Starten des Spiels
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mein Spiel");
        Game gamePanel = new Game();
        frame.add(gamePanel);
        frame.setSize(800, 600); // Fenstergröße anpassen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        gamePanel.startGame();
    }
}
