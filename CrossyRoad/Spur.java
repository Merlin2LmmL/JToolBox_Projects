import java.awt.Container;

/**
 * One row of the Crossy Road playfield: either a grass row with trees
 * or a road row on which a few vehicles drive to the right.
 * All rows live inside one big "world" container, so the camera only has to
 * move that single container.
 *
 * Every vehicle has its own speed: a base speed that depends on the kind of
 * vehicle (race cars are fast, trucks and campers slow), a random factor for
 * that vehicle and a little slow noise over time. A vehicle slows down
 * behind a slower one instead of driving through it.
 *
 * @author Merlin Ortner
 * @version 1.2.0
 */
public class Spur {
    public static final int BREITE = 780, ZEILE = 80, SPALTEN = 13, SPALTE = 60;
    /** How many rows the world container can hold. */
    public static final int MAXZEILEN = 20000;
    private static final int AUTOBREITE = 140;
    // lifts the vehicle containers so their wheels touch the bottom of the row
    private static final int FAHRZEUGOFFSET = 26;
    // the part of the row (counted from its bottom) in which a vehicle can hit the player
    private static final int HIT_UNTEN = 8, HIT_OBEN = 74;
    /** Draws the hitboxes of all vehicles (and the player). */
    public static boolean hitboxenZeigen;
    // the solid part of a tree trunk (relative to the middle of its column and the bottom of the row)
    private static final int BAUM_HALBE = 18, BAUM_VON = 4, BAUM_BIS = 36;
    // distance (front to back) at which a vehicle starts to follow the one in front
    private static final int FOLGEABSTAND = AUTOBREITE + 70, MINDESTABSTAND = AUTOBREITE + 25;

    // vehicle kinds: SportsCar, FormulaOne, Wohnmobil, LKW, Bully, AutoMu
    // base speed in px/s and the solid part of the 140 px wide container
    private static final int[] TEMPO = {270, 340, 105, 120, 185, 140};
    private static final int[] TREFFER_VON = {8, 8, 12, 12, 20, 12};
    private static final int[] TREFFER_BIS = {132, 132, 138, 110, 140, 130};

    static {
        StaticTools.setzeFarbe("cr_gras1", 112, 196, 82);
        StaticTools.setzeFarbe("cr_gras2", 102, 184, 74);
        StaticTools.setzeFarbe("cr_gras_dunkel", 86, 164, 62);
        StaticTools.setzeFarbe("cr_gras_hell", 140, 214, 104);
        StaticTools.setzeFarbe("cr_asphalt", 58, 61, 70);
        StaticTools.setzeFarbe("cr_asphalt_fleck", 70, 73, 82);
        StaticTools.setzeFarbe("cr_strich", 232, 232, 222);
        StaticTools.setzeFarbe("cr_bordstein", 200, 200, 196);
        StaticTools.setzeFarbe("cr_bordstein_kante", 150, 150, 148);
        StaticTools.setzeFarbe("cr_stamm", 112, 76, 46);
        StaticTools.setzeFarbe("cr_krone", 44, 130, 58);
        StaticTools.setzeFarbe("cr_krone_hell", 74, 164, 76);
        StaticTools.setzeFarbe("cr_baumschatten", 80, 150, 62);
        StaticTools.setzeFarbe("cr_blume_rosa", 244, 140, 180);
        StaticTools.setzeFarbe("cr_blume_gelb", 255, 214, 64);
        StaticTools.setzeFarbe("cr_blume_weiss", 250, 250, 250);
        StaticTools.setzeFarbe("cr_schatten", 36, 38, 44);
    }

    private final int zeile;
    private final boolean strasse;
    private final Behaelter welt;
    private final Behaelter hintergrund;
    private final int weltY;
    private final boolean[] baum = new boolean[SPALTEN];
    private final int richtung;
    private Rechteck[] boxen;

    private Kraftfahrzeug[] fahrzeuge = new Kraftfahrzeug[0];
    private int[] art;
    private double[] fahrzeugX, basis, faktor, phase, geschwindigkeit;
    private double zeit;
    private int periode;

    /**
     * @param _welt the world container all rows are drawn in
     * @param _zeile the number of the row (0 is the bottom one)
     * @param _strasse true for a road, false for grass
     * @param bauemeErlaubt whether trees may grow on a grass row
     * @param untenStrasse whether the row below is a road, too
     * @param letzteStrasse whether this is the last row of a road (grass follows)
     * @param _richtung 1 if the vehicles drive to the right, -1 if they drive to the left
     */
    public Spur(Behaelter _welt, int _zeile, boolean _strasse, boolean bauemeErlaubt,
            boolean untenStrasse, boolean letzteStrasse, int _richtung) {
        welt = _welt;
        richtung = _richtung;
        zeile = _zeile;
        strasse = _strasse;
        weltY = weltY(zeile);
        hintergrund = new Behaelter(welt, 0, weltY, BREITE, ZEILE);
        hintergrund.setzeMitRand(false);
        // rows are the bottom layer, so vehicles reaching into the next row aren't covered by it
        Container behaelter = hintergrund.getBasisComponente().getParent();
        behaelter.setComponentZOrder(hintergrund.getBasisComponente(), behaelter.getComponentCount() - 1);

        if (strasse) {
            strasseZeichnen(untenStrasse, letzteStrasse);
            fahrzeugeErzeugen();
        } else {
            graseZeichnen(bauemeErlaubt);
        }
    }

    /**
     * @return the y position of the top of a row inside the world container
     */
    public static int weltY(int zeile) {
        return (MAXZEILEN - zeile - 1) * ZEILE;
    }

    private void strasseZeichnen(boolean untenStrasse, boolean letzteStrasse) {
        Rechteck belag = new Rechteck(hintergrund, 0, 0, BREITE, ZEILE);
        belag.setzeFarbe("cr_asphalt");

        // a few lighter specks in the asphalt
        for (int i = 0; i < 9; i++) {
            Rechteck fleck = new Rechteck(hintergrund, (int) (Math.random() * (BREITE - 6)),
                6 + (int) (Math.random() * (ZEILE - 14)), 3 + (int) (Math.random() * 4), 2);
            fleck.setzeFarbe("cr_asphalt_fleck");
        }

        if (untenStrasse) {
            // dashed divider between two road rows
            for (int i = 0; i < SPALTEN; i++) {
                Rechteck strich = new Rechteck(hintergrund, i * SPALTE + 14, ZEILE - 2, 32, 4);
                strich.setzeFarbe("cr_strich");
            }
        } else {
            kante(ZEILE - 7);
        }
        if (letzteStrasse) {
            kante(0);
        }
    }

    /**
     * A kerb along the border between road and grass.
     */
    private void kante(int y) {
        Rechteck stein = new Rechteck(hintergrund, 0, y, BREITE, 7);
        stein.setzeFarbe("cr_bordstein");
        Rechteck rand = new Rechteck(hintergrund, 0, y == 0 ? 7 : y - 1, BREITE, 1);
        rand.setzeFarbe("cr_bordstein_kante");
    }

    private void graseZeichnen(boolean bauemeErlaubt) {
        Rechteck wiese = new Rechteck(hintergrund, 0, 0, BREITE, ZEILE);
        wiese.setzeFarbe(zeile % 2 == 0 ? "cr_gras1" : "cr_gras2");

        // grass tufts and flowers
        for (int i = 0; i < 6; i++) {
            Rechteck busch = new Rechteck(hintergrund, (int) (Math.random() * (BREITE - 8)),
                8 + (int) (Math.random() * (ZEILE - 16)), 2, 4);
            busch.setzeFarbe(Math.random() < 0.5 ? "cr_gras_dunkel" : "cr_gras_hell");
        }
        String[] blumen = {"cr_blume_rosa", "cr_blume_gelb", "cr_blume_weiss"};
        for (int i = 0; i < 3; i++) {
            if (Math.random() < 0.6) {
                Kreis blume = new Kreis(hintergrund, (int) (Math.random() * (BREITE - 6)),
                    10 + (int) (Math.random() * (ZEILE - 20)), 2);
                blume.setzeFarbe(blumen[(int) (Math.random() * blumen.length)]);
            }
        }

        for (int i = 0; i < SPALTEN; i++) {
            if (bauemeErlaubt && Math.random() < 0.2) {
                baum[i] = true;
                int mitte = i * SPALTE + SPALTE / 2;
                Ellipse schatten = new Ellipse(hintergrund, mitte - 26, 56, 52, 18);
                schatten.setzeFarbe("cr_baumschatten");
                Rechteck stamm = new Rechteck(hintergrund, mitte - 5, 46, 10, 28);
                stamm.setzeFarbe("cr_stamm");
                Kreis krone = new Kreis(hintergrund, mitte - 24, 4, 24);
                krone.setzeFarbe("cr_krone");
                Kreis licht = new Kreis(hintergrund, mitte - 15, 10, 11);
                licht.setzeFarbe("cr_krone_hell");
            }
        }
    }

    private void fahrzeugeErzeugen() {
        int anzahl = 2 + (int) (Math.random() * 3);
        periode = Math.max(BREITE + 300 + (int) (Math.random() * 300), anzahl * (AUTOBREITE + 130));
        double versatz = Math.random() * periode;

        // different kinds of vehicles in one row: shuffle all kinds and take the first few
        int[] sorte = new int[TEMPO.length];
        for (int i = 0; i < sorte.length; i++) sorte[i] = i;
        for (int i = sorte.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            int tausch = sorte[i];
            sorte[i] = sorte[j];
            sorte[j] = tausch;
        }

        fahrzeuge = new Kraftfahrzeug[anzahl];
        art = new int[anzahl];
        fahrzeugX = new double[anzahl];
        basis = new double[anzahl];
        faktor = new double[anzahl];
        phase = new double[anzahl];
        geschwindigkeit = new double[anzahl];
        for (int i = 0; i < anzahl; i++) {
            art[i] = sorte[i];
            fahrzeuge[i] = neuesFahrzeug(art[i]);
            // the vehicle's container moves into the world, so it moves with the camera
            welt.hinzufuegen(fahrzeuge[i].gibGrafik());
            if (richtung < 0) fahrzeuge[i].spiegeln();
            schattenHinzufuegen(fahrzeuge[i].gibGrafik(), art[i]);

            basis[i] = TEMPO[art[i]];
            faktor[i] = 0.85 + Math.random() * 0.3;
            phase[i] = Math.random() * 2 * Math.PI;
            geschwindigkeit[i] = basis[i] * faktor[i];
            fahrzeugX[i] = ((versatz + (double) i * periode / anzahl) % periode) - AUTOBREITE;
            fahrzeuge[i].setzePosition(-AUTOBREITE - 50, weltY - FAHRZEUGOFFSET);
        }
    }

    private static Kraftfahrzeug neuesFahrzeug(int art) {
        switch (art) {
            case 0:  return new SportsCar(0, 0);
            case 1:  return new FormulaOne(0, 0);
            case 2:  return new Wohnmobil(0, 0);
            case 3:  return new LKW(0, 0);
            case 4:  return new Bully(0, 0);
            default: return new AutoMu(0, 0);
        }
    }

    /**
     * SportsCar and FormulaOne draw their own ground shadow, the others get one here
     * (behind everything else in the vehicle).
     */
    private static void schattenHinzufuegen(Behaelter grafik, int art) {
        if (art == 0 || art == 1) return;
        Ellipse schatten = new Ellipse(grafik, 6, 92, art == 3 ? 108 : 128, 7);
        schatten.setzeFarbe("cr_schatten");
        Container eltern = schatten.getBasisComponente().getParent();
        eltern.setComponentZOrder(schatten.getBasisComponente(), eltern.getComponentCount() - 1);
    }

    public int getZeile() { return zeile; }

    public boolean istStrasse() { return strasse; }

    public int getRichtung() { return richtung; }

    // x positions on the screen: the logical x of a vehicle always grows in its driving direction
    private double links(int i) {
        return richtung > 0 ? fahrzeugX[i] : BREITE - fahrzeugX[i] - AUTOBREITE;
    }

    private double trefferVon(int i) {
        return richtung > 0 ? fahrzeugX[i] + TREFFER_VON[art[i]] : BREITE - fahrzeugX[i] - TREFFER_BIS[art[i]];
    }

    private double trefferBis(int i) {
        return richtung > 0 ? fahrzeugX[i] + TREFFER_BIS[art[i]] : BREITE - fahrzeugX[i] - TREFFER_VON[art[i]];
    }

    /**
     * Drives the vehicles on.
     *
     * @param dt the seconds since the last frame
     * @param sichtbar whether the row is on the screen (otherwise it isn't drawn)
     */
    public void aktualisieren(double dt, boolean sichtbar) {
        zeit += dt;
        for (int i = 0; i < fahrzeuge.length; i++) {
            // wanted speed: base speed, own factor and a little slow noise
            double wunsch = basis[i] * faktor[i] * (1 + 0.08 * Math.sin(zeit * 0.9 + phase[i]));

            // follow the vehicle in front instead of driving through it
            for (int j = 0; j < fahrzeuge.length; j++) {
                if (j == i) continue;
                double abstand = (fahrzeugX[j] - fahrzeugX[i] + periode) % periode;
                if (abstand < MINDESTABSTAND) {
                    wunsch = Math.min(wunsch, geschwindigkeit[j] * 0.7);
                } else if (abstand < FOLGEABSTAND) {
                    wunsch = Math.min(wunsch, geschwindigkeit[j]);
                }
            }
            geschwindigkeit[i] += (wunsch - geschwindigkeit[i]) * Math.min(1, dt * 3);

            fahrzeugX[i] += geschwindigkeit[i] * dt;
            if (fahrzeugX[i] > BREITE) {
                fahrzeugX[i] -= periode;
            }

            // vehicles beyond the screen edges rest until they come back
            if (sichtbar && fahrzeugX[i] > -AUTOBREITE && fahrzeugX[i] < BREITE) {
                int wippen = (int) Math.round(Math.sin(fahrzeugX[i] * 0.2));
                fahrzeuge[i].setzePosition((int) links(i), weltY - FAHRZEUGOFFSET + wippen);
                fahrzeuge[i].animiere((int) Math.round(geschwindigkeit[i] * dt));
                if (boxen != null) {
                    boxen[i].setzePosition((int) trefferVon(i), weltY + ZEILE - HIT_OBEN);
                }
            }
        }

        if (hitboxenZeigen && boxen == null) boxenErzeugen();
        else if (!hitboxenZeigen && boxen != null) boxenEntfernen();
    }

    private void boxenErzeugen() {
        boxen = new Rechteck[fahrzeuge.length];
        for (int i = 0; i < boxen.length; i++) {
            int breite = (int) (trefferBis(i) - trefferVon(i));
            boxen[i] = new Rechteck(welt, (int) trefferVon(i), weltY + ZEILE - HIT_OBEN, breite, HIT_OBEN - HIT_UNTEN);
            boxen[i].rand();
            boxen[i].setzeFarbe("rot");
        }
    }

    private void boxenEntfernen() {
        for (Rechteck box : boxen) {
            box.entfernen();
        }
        boxen = null;
    }

    /**
     * @param mitteX the x center of the player's feet
     * @param fussY the height of the player's feet in the world (counted upwards)
     * @param halbeB half the width of the player's feet area
     * @param halbeH half the height of the player's feet area
     * @return whether a vehicle of this row overlaps the player's feet
     */
    public boolean trifft(double mitteX, double fussY, int halbeB, int halbeH) {
        if (fussY + halbeH <= zeile * ZEILE + HIT_UNTEN || fussY - halbeH >= zeile * ZEILE + HIT_OBEN) {
            return false;
        }
        for (int i = 0; i < fahrzeuge.length; i++) {
            if (mitteX + halbeB > trefferVon(i) && mitteX - halbeB < trefferBis(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param mitteX the x center of the player's feet
     * @param fussY the height of the player's feet in the world (counted upwards)
     * @param halbeB half the width of the player's feet area
     * @param halbeH half the height of the player's feet area
     * @return whether the player would stand inside a tree of this row
     */
    public boolean baumTrifft(double mitteX, double fussY, int halbeB, int halbeH) {
        double unten = zeile * ZEILE + BAUM_VON, oben = zeile * ZEILE + BAUM_BIS;
        if (fussY + halbeH <= unten || fussY - halbeH >= oben) return false;

        for (int i = 0; i < SPALTEN; i++) {
            if (baum[i]) {
                double mitte = i * SPALTE + SPALTE / 2.0;
                if (mitteX + halbeB > mitte - BAUM_HALBE && mitteX - halbeB < mitte + BAUM_HALBE) {
                    return true;
                }
            }
        }
        return false;
    }

    public void entfernen() {
        if (boxen != null) boxenEntfernen();
        for (Kraftfahrzeug fahrzeug : fahrzeuge) {
            fahrzeug.entfernen();
        }
        hintergrund.entfernen();
    }
}
