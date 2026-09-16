import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.awt.event.KeyEvent;

public class Tastatur {
    private final JComponent ziel;
    private final int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private final Set<String> aktiveTasten = new HashSet<>();
    private ITastatur link;

    private final Timer wiederholTimer = new Timer(30, e -> {
        if (link != null && !aktiveTasten.isEmpty()) {
            link.tastenAktion(String.join("+", aktiveTasten));
        }
    });

    // Mapping für Sondertasten (Name -> Kürzel)
    private final Map<String, String> tasteMapping = new HashMap<>();

    public Tastatur() {
        ziel = Zeichnung.gibZeichenflaeche();
        ziel.setFocusable(true);
        ziel.requestFocusInWindow();

        wiederholTimer.setInitialDelay(0);
        wiederholTimer.start();

        // Kürzel definieren
        tasteMapping.put("SPACE", "_");
        tasteMapping.put("ENTER", "<ENTER>");
        tasteMapping.put("UP", "<UP>");
        tasteMapping.put("DOWN", "<DOWN>");
        tasteMapping.put("LEFT", "<LEFT>");
        tasteMapping.put("RIGHT", "<RIGHT>");
    }

    public void setzeLink(ITastatur link) {
        this.link = link;
    }

    public void meldeAnTaste(char c) {
        String upper = ("" + c).toUpperCase();
        String pressed = "pressed " + upper;
        String released = "released " + upper;

        ziel.getInputMap(condition).put(KeyStroke.getKeyStroke(c, 0, false), pressed);
        ziel.getInputMap(condition).put(KeyStroke.getKeyStroke(c, 0, true), released);

        ziel.getActionMap().put(pressed, new TastenAktion(this, pressed));
        ziel.getActionMap().put(released, new TastenAktion(this, released));
    }

    public void meldeAnTaste(String taste) {
    int keyCode = switch (taste) {
        case "UP" -> KeyEvent.VK_UP;
        case "DOWN" -> KeyEvent.VK_DOWN;
        case "LEFT" -> KeyEvent.VK_LEFT;
        case "RIGHT" -> KeyEvent.VK_RIGHT;
        case "SPACE" -> KeyEvent.VK_SPACE;
        case "ENTER" -> KeyEvent.VK_ENTER;
        default -> -1; // nicht unterstützt
    };

    if (keyCode != -1) {
        String pressed = "pressed " + taste;
        String released = "released " + taste;

        ziel.getInputMap(condition).put(KeyStroke.getKeyStroke(keyCode, 0, false), pressed);
        ziel.getInputMap(condition).put(KeyStroke.getKeyStroke(keyCode, 0, true), released);

        ziel.getActionMap().put(pressed, new TastenAktion(this, pressed));
        ziel.getActionMap().put(released, new TastenAktion(this, released));
    } else {
        System.err.println("Unbekannte Taste: " + taste);
    }
}


    public void meldeRichtungstastenAn() {
        for (String richtung : new String[] {"UP", "DOWN", "LEFT", "RIGHT"}) {
            meldeAnTaste(richtung);
        }
    }

    public void rueckgabe(String rueckgabe) {
        if (link == null) return;

        if (rueckgabe.startsWith("pressed ")) {
            String taste = rueckgabe.substring("pressed ".length());
            aktiveTasten.add(mappedTaste(taste));
        } else if (rueckgabe.startsWith("released ")) {
            String taste = rueckgabe.substring("released ".length());
            aktiveTasten.remove(mappedTaste(taste));
        }

        if (aktiveTasten.isEmpty()) {
            link.tastenAktion(""); // Keine Taste aktiv
        } else {
            link.tastenAktion(String.join("+", aktiveTasten));
        }
    }

    private String mappedTaste(String original) {
        return tasteMapping.getOrDefault(original, original);
    }

    public void meldeStandardtastenAn() {
        meldeRichtungstastenAn();

        for (char c = 'A'; c <= 'Z'; c++) {
            meldeAnTaste(c);
        }
        for (char c = '0'; c <= '9'; c++) {
            meldeAnTaste(c);
        }

        meldeAnTaste("SPACE");
        meldeAnTaste("ENTER");
    }
}