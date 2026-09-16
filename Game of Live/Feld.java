import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;

/**
 * Die Klasse Feld erweitert die vorhandene Klasse Quadrat und
 * erbt alle Basisfunktionen (z. B. Färben, Füllen, Positionieren).
 * Zusätzlich werden MouseListener und MouseMotionListener implementiert, 
 * um beim Gedrückt-Halten (Drag) dafür zu sorgen, dass jedes Feld,
 * über das der Mauszeiger fährt, genau einmal seine Farbe ändert.
 */
public class Feld extends Quadrat implements MouseListener, MouseMotionListener {

    // Statische Variablen zur Steuerung des globalen Drag-Zustandes.
    private static boolean dragActive = false; // Liefert an, ob aktuell ein Drag mit gedrückter Maustaste aktiv ist.
    private static int dragId = 0;             // Identifiziert die aktuelle Drag-Session.

    // Pro Feld merken wir, ob es in der aktuellen Drag-Session bereits getoggelt wurde.
    private int toggledDragId = -1;

    /**
     * Konstruktor: Erstellt ein Feld an der übergebenen Position mit der gegebenen Seitenlänge.
     * Es wird der Konstruktor der Superklasse Quadrat aufgerufen.
     * 
     * @param xPos  X-Position des Feldes
     * @param yPos  Y-Position des Feldes
     * @param seite Seitenlänge (Größe) des Feldes
     */
    public Feld(int xPos, int yPos, int seite) {
        super(xPos, yPos, seite);  // ruft den Konstruktor von Quadrat auf
        // Registriere diesen Feld als MouseListener und MouseMotionListener an der zugrundeliegenden Komponente.
        JComponent comp = (JComponent) getBasisComponente();
        comp.addMouseListener(this);
        comp.addMouseMotionListener(this);
    }

    /**
     * Diese Methode wird aufgerufen, um das Feld umzuschalten.  
     * Wenn es aktuell "schwarz" ist, wird es auf "weiss" gesetzt und umgekehrt.
     */
    public void onKlick() {
        // Verwende equals() zum Vergleich von Strings
        if (farbe.equals("schwarz")) {
            setzeFarbe("weiss");
        } else {
            setzeFarbe("schwarz");
        }
    }
    
    /* ---------------------------
       MouseListener-Implementierung
       --------------------------- */
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            // Starte eine neue Drag-Session, falls noch nicht aktiv.
            if (!dragActive) {
                dragActive = true;
                dragId++; // Erhöhe die Drag-ID für die neue Session.
            }
            Point p = e.getPoint();
            if (p.x >= 0 && p.x <= seite && p.y >= 0 && p.y <= seite) {
                // Falls das Feld in der aktuellen Drag-Session noch nicht getoggelt wurde, toggle es.
                if (toggledDragId != dragId) {
                    onKlick();
                    toggledDragId = dragId;
                }
            }
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            // Ende der Drag-Session – global zurücksetzen.
            dragActive = false;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // Kann leer bleiben – wir bearbeiten den Toggle über press/entered.
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        // Wird aufgerufen, wenn der Mauszeiger in das Feld eintritt.
        // Prüfe, ob während des Dragging die linke Maustaste gedrückt ist.
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
            // Nur toggeln, wenn in der aktuellen Drag-Session dieses Feld noch nicht getoggelt wurde.
            if (toggledDragId != dragId) {
                onKlick();
                toggledDragId = dragId;
            }
        }
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        // Optional: Man könnte hier den toggled-Status zurücksetzen, wenn gewünscht,
        // damit ein erneutes Betreten des gleichen Feldes in einer fortgesetzten Drag-Session
        // (z. B. bei versehentlichem mehrmaligem Überfahren) nicht zu einem weiteren Toggle führt.
        // Wird hier nicht getan, damit jedes Feld nur einmal pro Drag-Session toggelt.
    }
    
    /* ---------------------------
       MouseMotionListener-Implementierung
       --------------------------- */
    
    @Override
    public void mouseDragged(MouseEvent e) {
        // Da mouseEntered beim erstmaligen Betreten bereits reagiert,
        // ist hier nichts weiter nötig.
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        // Nicht benötigt – keine Aktion beim einfachen Bewegen der Maus.
    }
}
