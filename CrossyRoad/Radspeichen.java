/**
 * Rotating spokes for a wheel, so cars without their own wheel animation can spin.
 *
 * @author Merlin Ortner
 * @version 1.0.0
 */
public class Radspeichen {
    private final Linie[] speichen;
    private final int mitteX, mitteY, radius;
    private int grad;

    /**
     * @param behaelter the container the wheel lives in
     * @param _mitteX the x center of the wheel
     * @param _mitteY the y center of the wheel
     * @param _radius the length of the spokes (from the center)
     * @param farbe the spoke color (a StaticTools color name)
     */
    public Radspeichen(Behaelter behaelter, int _mitteX, int _mitteY, int _radius, String farbe) {
        mitteX = _mitteX;
        mitteY = _mitteY;
        radius = _radius;
        speichen = new Linie[2];
        for (int i = 0; i < speichen.length; i++) {
            speichen[i] = new Linie(behaelter);
            speichen[i].setzeLinienDicke(2);
            speichen[i].setzeFarbe(farbe);
        }
        drehen(0);
    }

    /**
     * Rotates the spokes further.
     */
    public void drehen(int degrees) {
        grad = (grad + degrees) % 360;
        for (int i = 0; i < speichen.length; i++) {
            double radians = Math.toRadians(grad + i * 90);
            int dx = (int) (Math.cos(radians) * radius);
            int dy = (int) (Math.sin(radians) * radius);
            speichen[i].setzeEndpunkte(mitteX + dx, mitteY + dy, mitteX - dx, mitteY - dy);
        }
    }

    /**
     * Rotates a whole set of wheels.
     */
    public static void drehen(Radspeichen[] raeder, int degrees) {
        for (Radspeichen rad : raeder) {
            rad.drehen(degrees);
        }
    }
}
