import java.awt.Color;
/**
 * A Formula One car design by
 *
 * @author Merlin2LmmL
 * 
 * template by
 * @coauther K. Hartmann
 * @version 0.0.2
 */

public class sportsCar {
    private String name, typ, color;
    private Linie wheelLineRight1, wheelLineRight2, wheelLineRight3, wheelLineLeft1, wheelLineLeft2, wheelLineLeft3, halo_upper, halo_lower;
    private Rechteck bounding_box;
    private Kreis wheelLeft, wheelRight, helmet;
    private Dreieck frontWing, spoilerUpper, rearRoof;
    private Rechteck frontWingLower, spoilerLower, body, bodyFront, helmetVisor;
    private Behaelter car;
    private boolean animateWheels;
    private int wheelDegrees;
    private int scale;

    /**
     * @param _name the name of the car
     * @param xPos the inital x position
     * @param yPos the inital y position
     * @param _color the car's color
     * @param _animateWheels weather wheel animation is enabled
     * @param _scale the scale factor of the car
     */
    public sportsCar(String _name, int xPos, int yPos, String _color, boolean _animateWheels, int _scale) {
        name = _name;
        color = _color;
        animateWheels = _animateWheels;
        scale = _scale;

        car = new Behaelter(
            xPos * scale,
            yPos * scale,
            140  * scale,
            100  * scale
        );
        /*
        // Debugging. Showing us the zone, the car has to fill
        bounding_box = new Rechteck(
        car,
        0   * scale,
        0   * scale,
        140 * scale,
        100 * scale
        );
        bounding_box.setzeFarbe("grau");
         */
        spoilerUpper = new Dreieck(
            car,
            0  * scale,
            60 * scale,
            20 * scale,
            12 * scale
        );
        // For some reason setting the direction to 
        // intermediate cardinal directions (yes, i did
        // just google that) makes a right-angled triangle
        spoilerUpper.setzeAusrichtung("SW");
        spoilerUpper.setzeFarbe(color);

        spoilerLower = new Rechteck(
            car,
            8  * scale,
            70 * scale,
            10 * scale,
            20 * scale
        );
        spoilerLower.setzeFarbe(color);

        rearRoof = new Dreieck(
            car,
            8  * scale,
            55 * scale,
            42 * scale,
            26 * scale
        );
        rearRoof.setzeAusrichtung("SO");
        rearRoof.setzeFarbe(color);

        helmet = new Kreis(
            car,
            48 * scale,
            64 * scale,
            5  * scale
        );
        helmet.setzeFarbe("orange");

        helmetVisor = new Rechteck(
            car,
            53 * scale,
            66 * scale,
            5  * scale,
            4  * scale
        );
        helmetVisor.setzeFarbe("schwarz");

        body = new Rechteck(
            car,
            16 * scale,
            74 * scale,
            50 * scale,
            16 * scale
        );
        body.setzeFarbe(color);

        bodyFront = new Rechteck(
            car,
            64 * scale,
            72 * scale,
            30 * scale,
            15 * scale
        );
        bodyFront.setzeFarbe(color);

        frontWing = new Dreieck(
            car,
            92 * scale,
            71 * scale,
            42 * scale,
            14 * scale
        );
        frontWing.setzeAusrichtung("SW");
        frontWing.setzeFarbe(color);

        frontWingLower = new Rechteck(
            car,
            52 * scale,
            83 * scale,
            80 * scale,
            7  * scale
        );
        frontWingLower.setzeFarbe(color);

        wheelLeft = new Kreis(
            car,
            20 * scale,
            80 * scale,
            9  * scale
        );
        wheelLeft.setzeFarbe("schwarz");

        wheelRight = new Kreis(
            car,
            95 * scale,
            80 * scale,
            9  * scale
        );
        wheelRight.setzeFarbe("schwarz");

        halo_lower = new Linie(
            car,
            65 * scale,
            60 * scale,
            80 * scale,
            75 * scale
        );
        halo_lower.setzeLinienDicke(2 * scale);
        halo_lower.setzeFarbe(color);

        halo_upper = new Linie(
            car,
            45 * scale,
            60 * scale,
            65 * scale,
            60 * scale
        );
        halo_upper.setzeLinienDicke(2 * scale);
        halo_upper.setzeFarbe(color);

        if (animateWheels) {
            wheelLineRight1 = new Linie(car);
            wheelLineRight1.setzeLinienDicke(2 * scale);
            wheelLineRight1.setzeFarbe("weiss");

            wheelLineRight2 = new Linie(car);
            wheelLineRight2.setzeLinienDicke(2 * scale);
            wheelLineRight2.setzeFarbe("weiss");

            wheelLineRight3 = new Linie(car);
            wheelLineRight3.setzeLinienDicke(2 * scale);
            wheelLineRight3.setzeFarbe("weiss");

            wheelLineLeft1 = new Linie(car);
            wheelLineLeft1.setzeLinienDicke(2 * scale);
            wheelLineLeft1.setzeFarbe("weiss");

            wheelLineLeft2 = new Linie(car);
            wheelLineLeft2.setzeLinienDicke(2 * scale);
            wheelLineLeft2.setzeFarbe("weiss");

            wheelLineLeft3 = new Linie(car);
            wheelLineLeft3.setzeLinienDicke(2 * scale);
            wheelLineLeft3.setzeFarbe("weiss");

            // Whithout this, the "wheel lines" wouldn't
            // even get drawn at initialization, because
            // no start- and endpoints were set
            updateWheelAnimation();
        }
        
        // We dont need to call Behaelter.hinzufuegen(),
        // because you can already attach
        // the objects when initalizing them
    }

    private static final String[] COLORS = {"rot", "blau", "gelb", "gruen", "lila", "magenta", "cyan"};
    
    /**
     * Creates a quick, random colored instance of the car
     */
    public sportsCar() {
        this("Car", 0, 0, COLORS[(int) (Math.random() * COLORS.length)], true, 1);
    }

    public void setzePosition(int neuesX, int neuesY) {
        car.xPos = neuesX;
        car.yPos = neuesY;
        car.setzePosition(car.xPos, car.yPos);
    }

    public void fahren(int entfernung) {
        // Extracted from Behaelter.langsamHorizontalBewegen()
        // and Behaelter.horizontalBewegen()
        int delta;
        if (entfernung < 0) {
            delta = -1 * Math.abs(scale);
            entfernung = -entfernung;
        } else {
            delta = 1;
        }

        for (int i = 0; i < entfernung; i++) {
            car.xPos += delta;
            car.setzePosition(car.xPos, car.yPos);
            if (animateWheels) {
                updateWheelAnimation();
            }
            StaticTools.warte(10);
        }
    }

    public void updateWheelAnimation() {
        updateWheelLine(wheelLineRight1, 28, 88, 0);
        updateWheelLine(wheelLineRight2, 28, 88, 120);
        updateWheelLine(wheelLineRight3, 28, 88, 240);

        updateWheelLine(wheelLineLeft1, 103, 88, 90);
        updateWheelLine(wheelLineLeft2, 103, 88, 210);
        updateWheelLine(wheelLineLeft3, 103, 88, 330);

        wheelDegrees = (wheelDegrees + 5) % 360;
    }

    private void updateWheelLine(Linie line, int centerX, int centerY, int angleOffset) {
        double radians = Math.toRadians(wheelDegrees + angleOffset);

        int radius = 8 * scale;
        int dx = (int) (Math.cos(radians) * radius);
        int dy = (int) (Math.sin(radians) * radius);

        line.setzeEndpunkte(
            centerX * scale + dx,
            centerY * scale + dy,
            centerX * scale - dx,
            centerY * scale - dy
        );
    }
}