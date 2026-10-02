import java.awt.Color;

/**
 * A Formula One car design by
 *
 * @author Merlin2LmmL
 * 
 * template by
 * @coauther K. Hartmann
 * @version 1.0.5
 */

public class SportsCar extends Kraftfahrzeug {
    private Linie wheelLineRight1, wheelLineRight2, wheelLineRight3, wheelLineLeft1, wheelLineLeft2, wheelLineLeft3, halo_upper, halo_lower;
    private Kreis wheelLeft, wheelRight, helmet;
    private Dreieck frontWing, spoilerUpper, rearRoof;
    private Rechteck frontWingLower, spoilerLower, body, bodyFront, helmetVisor;
    private Behaelter car;
    private boolean animateWheels;
    private int wheelDegrees;
    private int scale;

    private final String lightToneName, darkToneName, helmetToneName;

    // Gives every instance its own StaticTools color-name namespace,
    // so two cars on the field never overwrite each other's colors.
    private static int instanceCounter = 0;

    /**
     * @param _name the name of the car
     * @param xPos the inital x position
     * @param yPos the inital y position
     * @param ps the car's horsepower
     * @param baseColor the car's base color (a StaticTools color name)
     * @param _animateWheels weather wheel animation is enabled
     * @param _scale the scale factor of the car
     */
    public SportsCar(String _name, int xPos, int yPos, int ps, String baseColor, boolean _animateWheels, int _scale) {
        super(_name, ps, xPos, yPos);
        animateWheels = _animateWheels;
        scale = _scale;

        int id = instanceCounter++;
        lightToneName  = "lightTone"  + id;
        darkToneName   = "darkTone"   + id;
        helmetToneName = "helmetTone" + id;

        Color base = StaticTools.getColor(baseColor);
        StaticTools.setzeFarbe(lightToneName, adjustColor(base, 40, 40, 40));
        StaticTools.setzeFarbe(darkToneName, adjustColor(base, -30, -30, -30));
        StaticTools.setzeFarbe(helmetToneName, getHelmetColor(base));

        car = new Behaelter(
            xPos * scale,
            yPos * scale,
            140  * scale,
            100  * scale
        );

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
        spoilerUpper.setzeFarbe(darkToneName);

        helmet = new Kreis(
            car,
            48 * scale,
            64 * scale,
            5  * scale
        );
        helmet.setzeFarbe(helmetToneName);

        helmetVisor = new Rechteck(
            car,
            53 * scale,
            66 * scale,
            5  * scale,
            4  * scale
        );
        helmetVisor.setzeFarbe("schwarz");

        halo_lower = new Linie(
            car,
            65 * scale,
            60 * scale,
            80 * scale,
            75 * scale
        );
        halo_lower.setzeLinienDicke(2 * scale);
        halo_lower.setzeFarbe(darkToneName);

        halo_upper = new Linie(
            car,
            45 * scale,
            60 * scale,
            65 * scale,
            60 * scale
        );
        halo_upper.setzeLinienDicke(2 * scale);
        halo_upper.setzeFarbe(darkToneName);

        rearRoof = new Dreieck(
            car,
            8  * scale,
            55 * scale,
            42 * scale,
            26 * scale
        );
        rearRoof.setzeAusrichtung("SO");
        rearRoof.setzeFarbe(baseColor);

        spoilerLower = new Rechteck(
            car,
            8  * scale,
            70 * scale,
            10 * scale,
            20 * scale
        );
        spoilerLower.setzeFarbe(darkToneName);

        frontWingLower = new Rechteck(
            car,
            52 * scale,
            83 * scale,
            80 * scale,
            7  * scale
        );
        frontWingLower.setzeFarbe(lightToneName);

        frontWing = new Dreieck(
            car,
            92 * scale,
            71 * scale,
            42 * scale,
            14 * scale
        );
        frontWing.setzeAusrichtung("SW");
        frontWing.setzeFarbe(lightToneName);

        body = new Rechteck(
            car,
            16 * scale,
            74 * scale,
            50 * scale,
            16 * scale
        );
        body.setzeFarbe(baseColor);

        bodyFront = new Rechteck(
            car,
            64 * scale,
            72 * scale,
            30 * scale,
            18 * scale
        );
        bodyFront.setzeFarbe(baseColor);

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

    /**
     * Creates a quick, random baseColored instance of the car
     */
    public SportsCar() {
        this("Car", 0, 0, 800, getRandomBaseColor(), true, 1);
    }

    /**
     * Constructor for @K. Hartmann
     * 
     * Spawn random baseColored car with set start position.
     * 
     * @param startX Initial x position
     * @param startY Initial y position
     */
    public SportsCar(int startX, int startY) {
        this("Car", startX, startY, 800, getRandomBaseColor(), true, 1);
    }

    @Override
    public void setzePosition(int neuesX, int neuesY) {
        super.setzePosition(neuesX, neuesY); // keeps inherited x, y in sync
        car.xPos = neuesX;
        car.yPos = neuesY;  
        car.setzePosition(car.xPos, car.yPos);
    }

    @Override
    public void fahren(int entfernung) {
        // Extracted from Behaelter.langsamHorizontalBewegen()
        // and Behaelter.horizontalBewegen()
        int delta = 1 * Math.abs(scale);
        if (entfernung < 0) {
            delta = -delta;
            entfernung = -entfernung;
        }

        for (int i = 0; i < entfernung; i++) {
            car.xPos += delta;
            car.setzePosition(car.xPos, car.yPos);
            if (animateWheels) {
                updateWheelAnimation();
            }
            StaticTools.warte(10);
        }

        // keep inherited x in sync with the actual on-screen position
        x = car.xPos;
        y = car.yPos;
    }

    private static String getRandomBaseColor() {
        // 0x80 is the hex value for 256 / 2, which is
        // half the amount of possible R/G/B values
        int r = 0x80 - (80 - (int) (Math.random() * 160));
        int g = 0x80 - (80 - (int) (Math.random() * 160));
        int b = 0x80 - (80 - (int) (Math.random() * 160));

        String colorName = "randomColor" + instanceCounter;
        StaticTools.setzeFarbe(colorName, r, g, b);
        return colorName;
    }

    private static Color adjustColor(Color base, int deltaRed, int deltaGreen, int deltaBlue) {
        int r = clamp(base.getRed()   + deltaRed);
        int g = clamp(base.getGreen() + deltaGreen);
        int b = clamp(base.getBlue()  + deltaBlue);
        return new Color(r, g, b);
    }

    private static int clamp(int wert) {
        if (wert > 255) return 255;
        if (wert < 0) return 0;
        return wert;
    }

    private static Color getHelmetColor(Color base) {
        return new Color(
            255 - base.getRed(),
            255 - base.getGreen(),
            255 - base.getBlue()
        );
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