/**
 * Die Klasse Steuerung erstellt in BlueJ eine Spielfläche (Flaeche) und einen Schieberegler (Schieberegler),
 * der bestimmt, wie oft pro Sekunde die Methode vermehren() aufgerufen werden soll.
 * 
 * Hierbei wird:
 * - Ein Spielfeld (10x10 Felder, Feldgröße 20) erzeugt,
 * - Ein Schieberegler im Bereich 0 bis 100 eingesetzt (0 bewirkt einen Stopp der Aufrufe),
 * - Ein Taktgeber verwendet, der die methode vermehren() in regelmäßigen Abständen aufruft.
 * 
 * Die Klasse implementiert ITuWas, um als Callback sowohl für den Schieberegler als auch den Taktgeber zu dienen.
 */
public class Steuerung implements ITuWas {

    // Spielfeld: Flaeche, das über die Methode vermehren() gesteuert wird.
    private Flaeche spielfeld;
    // Schieberegler, der den Frequenzwert (Aufrufe pro Sekunde) bestimmt.
    private Schieberegler slider;
    // Taktgeber, der in definierten Intervallen (in ms) vermehrung() auslöst.
    private Taktgeber takt;

    /**
     * Konstruktor: Erstellt das Spielfeld, den Schieberegler und den Taktgeber.
     */
    public Steuerung(int breite, int hoehe, int groesse) {
        // Erzeuge eine Fläche mit 10 Spalten und 10 Reihen; jedes Feld hat 20 Pixel Seitenlänge.
        spielfeld = new Flaeche(breite, hoehe, groesse);

        // Erzeuge einen horizontalen Schieberegler an einer gewünschten Position und Größe.
        // Hier: Position (50, 50), Größe 300x50, Bereich 0 bis 100, Initialwert 10.
        slider = new Schieberegler('H', 0, hoehe*groesse, 300, 50, 0, 100, 10);
        // Verknüpfe den Schieberegler mit diesem Objekt; hier erhält der Callback-ID 0.
        slider.setzeLink(this, 0);

        // Erzeuge den Taktgeber; hier wird die Callback-ID 1 vergeben, damit wir unterscheiden können.
        takt = new Taktgeber(this, 1);
        // Initialer Start des Taktgebers: 
        // Hier wird in updateTaktgeber() die Periode anhand des initial eingestellten Sliderwerts (10) berechnet.
        updateTaktgeber();
    }

    /**
     * ITuWas Callback-Methode.
     * Je nach übergebener ID unterscheiden wir:
     * - ID 0: Der Schieberegler hat sich verändert. Dann wird die Aufruffrequenz (Taktgeberperiode) 
     *         neu berechnet.
     * - ID 1: Der Taktgeber hat ein Taktsignal ausgelöst und es folgt ein Simulationsschritt,
     *         d.h. spielfeld.vermehren() wird aufgerufen.
     */
    @Override
    public void tuWas(int id) {
        if (id == 0) {
            // Slider hat sich verändert -> Update der Aufruffrequenz
            updateTaktgeber();
        } else if (id == 1) {
            // Taktgeber-Tick: Aufruf der vermehrung()-Methode, um die nächste Generation zu berechnen.
            spielfeld.vermehren();
        }
    }

    /**
     * Lest den aktuellen Wert des Schiebereglers und passt die Timer-Periode entsprechend an.
     * - Ist der Frequenzwert 0, wird der Taktgeber angehalten.
     * - Andernfalls wird die Periode (in Millisekunden) als 1000 / Frequenz berechnet
     *   und der Taktgeber auf "endlos" (wiederholend) gestellt.
     */
    private void updateTaktgeber() {
        int frequency = slider.leseIntWert(); // Anzahl Aufrufe pro Sekunde
        if (frequency <= 0) {
            takt.stop();
        } else {
            // Berechne den Delay in Millisekunden: 1000 ms / Anzahl Aufrufe pro Sekunde.
            int delay = 1000 / frequency;
            takt.setzteAnfangsZeitverzoegerung(delay);
            takt.setzteZeitZwischenAktionen(delay);
            // Starte den Taktgeber, wenn er noch nicht läuft.
            if (!takt.laufend()) {
                takt.endlos();
            }
        }
    }

    public void zufall(double zufallswert) {
        spielfeld.zufall(zufallswert);
    }
}
