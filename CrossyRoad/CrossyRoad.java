import java.awt.Toolkit;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 * A Crossy Road style game: walk freely over an endless, scrolling field
 * of grass and roads and don't get run over. The player is the Person,
 * the traffic are the car sprites of this project.
 *
 * Controls: arrow keys or W/A/S/D to walk, H to show the hitboxes,
 * Space or Enter to restart.
 *
 * The game loop runs on the Swing thread: all changes of one frame happen in
 * a single step, so the screen never shows a half-moved frame.
 *
 * @author Merlin Ortner
 * @version 2.0.0
 */
public class CrossyRoad {
    private static final int BREITE = Spur.BREITE, HOEHE = 720, ZEILE = Spur.ZEILE;
    // the player starts in the middle of the 3rd row; (px, py) are the player's feet,
    // py counts upwards from the bottom of the world
    private static final double STARTX = BREITE / 2.0, STARTY = 2.5 * ZEILE;
    private static final double TEMPO = 230, BESCHLEUNIGUNG = 18;
    // the Person is 60 px wide, its feet are 95 px below the container's top
    private static final int PERSONOFFSET_X = 70, PERSONOFFSET_Y = 95;
    private static final int FUSS_B = 11, FUSS_H = 6;

    private final ArrayList<Spur> spuren = new ArrayList<Spur>();
    private int ersteZeile;
    private Behaelter welt;
    private Person person;
    private Ellipse schatten;
    private Behaelter anzeige, tafel;

    // input, written by the key listener and read by the game loop (both on the Swing thread)
    private boolean links, rechts, hoch, runter, neustart, hGehalten;
    private Rechteck spielerBox;

    private double px, py, vx, vy, maxPy, laufPhase;
    private int punkte, rekord, angezeigtePunkte;
    private double kamera, kameraMin;
    private boolean gestartet, tot;
    private String todesgrund;
    private long zuletzt;

    // how the next rows are generated
    private boolean naechsteStrasse;
    private int reststrecke;

    public static void main(String[] args) {
        new CrossyRoad().spielen();
    }

    public CrossyRoad() {
        Zeichnung.setzeFenstergroesse(BREITE + 16, HOEHE + 39);
        Zeichnung.setzeScrollbar(false);
        Zeichnung.setzeTitel("Crossy Road");
        tastenAnmelden();
    }

    private void tastenAnmelden() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            boolean gedrueckt;
            if (e.getID() == KeyEvent.KEY_PRESSED) gedrueckt = true;
            else if (e.getID() == KeyEvent.KEY_RELEASED) gedrueckt = false;
            else return false;

            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:    case KeyEvent.VK_W: hoch = gedrueckt;   break;
                case KeyEvent.VK_DOWN:  case KeyEvent.VK_S: runter = gedrueckt; break;
                case KeyEvent.VK_LEFT:  case KeyEvent.VK_A: links = gedrueckt;  break;
                case KeyEvent.VK_RIGHT: case KeyEvent.VK_D: rechts = gedrueckt; break;
                case KeyEvent.VK_H:
                    if (gedrueckt && !hGehalten) hitboxenUmschalten();
                    hGehalten = gedrueckt;
                    break;
                case KeyEvent.VK_SPACE: case KeyEvent.VK_ENTER: if (gedrueckt) neustart = true; break;
                default: return false;
            }
            return true;
        });
    }

    public void spielen() {
        // the first game is built by the first step of the loop, on the Swing thread
        tot = true;
        neustart = true;
        zuletzt = System.nanoTime();
        new Timer(10, e -> {
            long jetzt = System.nanoTime();
            double dt = Math.min(0.05, (jetzt - zuletzt) / 1e9);
            zuletzt = jetzt;
            try {
                if (tot) {
                    if (neustart) {
                        if (tafel != null) tafel.entfernen();
                        neuesSpiel();
                    }
                } else {
                    bild(dt);
                }
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
            Toolkit.getDefaultToolkit().sync();
        }).start();
    }

    private void neuesSpiel() {
        for (Spur spur : spuren) {
            spur.entfernen();
        }
        spuren.clear();
        if (welt != null) welt.entfernen();
        if (person != null) person.grafikEntfernen();

        welt = new Behaelter(0, 0, BREITE, Spur.MAXZEILEN * ZEILE);
        welt.setzeMitRand(false);
        ersteZeile = 0;
        naechsteStrasse = false;
        reststrecke = 0;
        for (int z = 0; z < 3; z++) {
            spuren.add(new Spur(welt, z, false, false, false, false, 1));
        }

        if (schatten != null) schatten.entfernen();
        schatten = new Ellipse(0, 0, 34, 10);
        schatten.setzeFarbe("cr_schatten");

        person = new Person("Spieler", 0, 0);
        px = STARTX;
        py = STARTY;
        maxPy = STARTY;
        vx = 0;
        vy = 0;
        laufPhase = 0;
        punkte = 0;
        kamera = 0;
        kameraMin = 0;
        gestartet = false;
        tot = false;
        neustart = false;

        zeilenNachladen();
        anzeigeZeichnen();
        bildSchirm(0);
    }

    /**
     * One frame: walking, camera, traffic, collision and drawing.
     */
    private void bild(double dt) {
        gehen(dt);

        // The camera follows the best height, plus a slow push so you can't idle
        if (gestartet) {
            kameraMin += Math.min(60, 20 + punkte * 0.6) * dt;
        }
        double ziel = Math.max(0, maxPy - STARTY);
        kamera = Math.max(kameraMin, kamera + (ziel - kamera) * Math.min(1, dt * 6));
        kameraMin = Math.min(kameraMin, kamera);

        zeilenNachladen();
        if (punkte != angezeigtePunkte) anzeigeZeichnen();

        int kam = (int) Math.round(kamera);
        int untersteSichtbare = kam / ZEILE - 1;
        for (Spur spur : spuren) {
            int z = spur.getZeile();
            spur.aktualisieren(dt, z >= untersteSichtbare && z <= untersteSichtbare + HOEHE / ZEILE + 2);
        }

        // Collision with the traffic of the rows the player's feet touch
        for (Spur spur : spuren) {
            if (spur.istStrasse() && spur.trifft(px, py, FUSS_B, FUSS_H)) {
                tot = true;
                todesgrund = "Überfahren!";
            }
        }
        if (py < kam) {
            tot = true;
            todesgrund = "Zu langsam!";
        }

        bildSchirm(kam);
        if (tot) spielEnde();
    }

    /**
     * Moves the player with the pressed keys (smoothly speeding up and
     * slowing down) and lets trees block the way.
     */
    private void gehen(double dt) {
        double richtungX = (rechts ? 1 : 0) - (links ? 1 : 0);
        double richtungY = (hoch ? 1 : 0) - (runter ? 1 : 0);
        double laenge = Math.sqrt(richtungX * richtungX + richtungY * richtungY);
        if (laenge > 0) {
            richtungX /= laenge;
            richtungY /= laenge;
            gestartet = true;
        }

        double weich = Math.min(1, BESCHLEUNIGUNG * dt);
        vx += (richtungX * TEMPO - vx) * weich;
        vy += (richtungY * TEMPO - vy) * weich;

        double neuX = Math.max(FUSS_B, Math.min(BREITE - FUSS_B, px + vx * dt));
        if (frei(neuX, py)) px = neuX; else vx = 0;

        // may not walk below the screen
        double neuY = Math.max(kamera + FUSS_H + 4, py + vy * dt);
        if (frei(px, neuY)) py = neuY; else vy = 0;

        maxPy = Math.max(maxPy, py);
        punkte = Math.max(0, (int) ((maxPy - STARTY) / ZEILE));

        laufPhase += Math.sqrt(vx * vx + vy * vy) * dt * 0.12;
    }

    private boolean frei(double x, double y) {
        for (Spur spur : spuren) {
            if (spur.baumTrifft(x, y, FUSS_B, FUSS_H)) return false;
        }
        return true;
    }

    /**
     * Moves the camera and the player on the screen. Everything is based on the
     * same whole-pixel camera position, so nothing jitters against each other.
     */
    private void bildSchirm(int kam) {
        welt.setzePosition(0, HOEHE - Spur.MAXZEILEN * ZEILE + kam);

        boolean geht = Math.abs(vx) + Math.abs(vy) > 20;
        int wippen = geht ? (int) Math.round(Math.abs(Math.sin(laufPhase)) * 4) : 0;
        int fussScreenY = (int) Math.round(HOEHE - py) + kam;
        if (spielerBox != null) spielerBox.setzePosition((int) Math.round(px) - FUSS_B, fussScreenY - FUSS_H);
        schatten.setzePosition((int) Math.round(px) - 17, fussScreenY - 6);
        person.setzePosition((int) Math.round(px) - PERSONOFFSET_X, fussScreenY - PERSONOFFSET_Y - wippen);
        if (geht) person.schrittPose((int) Math.round(Math.sin(laufPhase) * 9));
        else person.ruhepose();
    }

    /**
     * Builds rows ahead of the screen and throws away the ones behind it.
     */
    private void zeilenNachladen() {
        int kamZeile = (int) (kamera / ZEILE);
        int sichtbarBis = kamZeile + HOEHE / ZEILE + 2;
        while (ersteZeile + spuren.size() <= sichtbarBis) {
            int z = ersteZeile + spuren.size();
            if (reststrecke == 0) {
                naechsteStrasse = !naechsteStrasse;
                reststrecke = naechsteStrasse ? 1 + (int) (Math.random() * 4) : 1 + (int) (Math.random() * 2);
            }
            reststrecke--;
            boolean untenStrasse = !spuren.isEmpty() && spuren.get(spuren.size() - 1).istStrasse();
            // neighbouring road rows drive in opposite directions
            int richtung = Math.random() < 0.5 ? 1 : -1;
            if (untenStrasse) richtung = -spuren.get(spuren.size() - 1).getRichtung();
            spuren.add(new Spur(welt, z, naechsteStrasse, true, untenStrasse, reststrecke == 0, richtung));
        }
        while (ersteZeile < kamZeile - 2) {
            spuren.remove(0).entfernen();
            ersteZeile++;
        }
    }

    private void hitboxenUmschalten() {
        Spur.hitboxenZeigen = !Spur.hitboxenZeigen;
        if (spielerBox != null) {
            spielerBox.entfernen();
            spielerBox = null;
        }
        if (Spur.hitboxenZeigen) {
            spielerBox = new Rechteck(0, 0, 2 * FUSS_B, 2 * FUSS_H);
            spielerBox.rand();
            spielerBox.setzeFarbe("gelb");
        }
    }

    private void anzeigeZeichnen() {
        if (anzeige != null) anzeige.entfernen();
        anzeige = new Behaelter(10, 10, 240, 34);
        anzeige.setzeHintergrundfarbe("schwarz");
        anzeige.setzeFarbe("weiss");
        anzeige.setzeSchriftgroesse(18);
        anzeige.setzeSchriftStilFett();
        anzeige.setzeBeschreibungstext("Punkte: " + punkte + "   Rekord: " + rekord);
        person.nachVorne();
        Zeichnung.gibZeichenflaeche().setComponentZOrder(anzeige.getBasisComponente(), 0);
        if (spielerBox != null) Zeichnung.gibZeichenflaeche().setComponentZOrder(spielerBox.getBasisComponente(), 1);
        angezeigtePunkte = punkte;
    }

    private void spielEnde() {
        rekord = Math.max(rekord, punkte);
        person.schrittPose(9);
        tafel = new Behaelter(BREITE / 2 - 330, HOEHE / 2 - 20, 660, 40);
        tafel.setzeHintergrundfarbe("schwarz");
        tafel.setzeFarbe("weiss");
        tafel.setzeSchriftgroesse(22);
        tafel.setzeBeschreibungstext(todesgrund + "  Punkte: " + punkte + "  Rekord: " + rekord
            + "   -   Leertaste zum Neustarten");
        Zeichnung.gibZeichenflaeche().setComponentZOrder(tafel.getBasisComponente(), 0);
        neustart = false;
    }
}
