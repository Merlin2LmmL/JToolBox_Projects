import javax.swing.JComponent;

public class Game implements ITastatur {
    private Snake snake;
    private Tastatur tastatur;

    public Game() {
        // Erstelle dein Snake-Objekt (das intern das Spielfeld und die Kreise erzeugt)
        snake = new Snake();
        
        // Erstelle das Tastatur-Objekt. Hier wird standardmäßig Zeichnung.gibZeichenflaeche() als Ziel genutzt.
        tastatur = new Tastatur();
        
        // Setze den Link, sodass Tastendrucke an die tastenAktion-Methode weitergeleitet werden.
        tastatur.setzeLink(this);
        
        // Registriere die Richtungstasten (LEFT, RIGHT, UP, DOWN).
        tastatur.tstRichtung();
    }
    
    // Diese Methode wird aufgerufen, wenn eine Taste aus der Tastatur-Klasse gedrückt wird.
    @Override
    public void tastenAktion(String rueckgabe) {
        // Mapping: 0 = ↓, 1 = ↑, 2 = →, 3 = ←
        // Passe das Mapping je nach deinen Bedürfnissen an.
        switch (rueckgabe) {
            case "UP":
                snake.move(1);
                break;
            case "DOWN":
                snake.move(0);
                break;
            case "RIGHT":
                snake.move(2);
                break;
            case "LEFT":
                snake.move(3);
                break;
            default:
                System.out.println("Nicht registrierte Taste: " + rueckgabe);
                break;
        }
    }
}
