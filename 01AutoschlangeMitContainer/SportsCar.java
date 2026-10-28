import java.awt.Color;

/**
 * A Formula One car design by
 *
 * @author Merlin2LmmL
 * 
 * template by
 * @coauther K. Hartmann
 * @version 1.0.6
 */

public class SportsCar extends Kraftfahrzeug {
    private Linie wheelLineRight1, wheelLineRight2, wheelLineRight3, wheelLineLeft1, wheelLineLeft2, wheelLineLeft3, halo_upper, halo_lower;
    private Kreis wheelLeft, wheelRight, helmet;
    private Dreieck frontWing, rearRoof;
    private Linie spoilerUpper;
    private Rechteck frontWingLower, spoilerLower, body, bodyFront, helmetVisor;
    private Rechteck flameOuter, flameInner, brakeLight;
    private Behaelter car;
    private boolean animateWheels;
    private int wheelDegrees;
    private int scale;
    private final int raceNumber;

    private final String lightToneName, darkToneName, helmetToneName, goldToneName, silverToneName, deepToneName;
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
        raceNumber = RaceNumber.pick();

        int id = instanceCounter++;
        lightToneName  = "lightTone"  + id;
        darkToneName   = "darkTone"   + id;
        helmetToneName = "helmetTone" + id;
        goldToneName   = "goldTone"   + id;
        silverToneName = "silverTone" + id;
        deepToneName   = "deepTone"   + id;

        Color base = StaticTools.getColor(baseColor);
        StaticTools.setzeFarbe(lightToneName, adjustColor(base, 40, 40, 40));
        StaticTools.setzeFarbe(darkToneName, adjustColor(base, -30, -30, -30));
        StaticTools.setzeFarbe(helmetToneName, getHelmetColor(base));
        StaticTools.setzeFarbe(deepToneName, adjustColor(base, -80, -80, -80));
        StaticTools.setzeFarbe(goldToneName, 255, 200, 40);
        StaticTools.setzeFarbe(silverToneName, 205, 210, 220);

        car = new Behaelter(
            xPos * scale,
            yPos * scale,
            140  * scale,
            100  * scale
        );

        // Ground shadow (bottom layer)
        Ellipse shadow = new Ellipse(car, 4 * scale, 94 * scale, 132 * scale, 6 * scale);
        shadow.setzeFarbe("dunkelgrau");

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

        // Rear spoiler with some depth: the body extended to the rear, two struts and
        // an angled main plane (trailing edge up) with a gold top edge,
        // a dark underside and an endplate at the rear end
        spoilerLower = new Rechteck(car, 8 * scale, 74 * scale, 10 * scale, 16 * scale);
        spoilerLower.setzeFarbe(baseColor);

        Rechteck endplate = new Rechteck(car, 0, 49 * scale, 3 * scale, 14 * scale);
        endplate.setzeFarbe(darkToneName);
        Rechteck endplateLight = new Rechteck(car, 0, 49 * scale, scale, 14 * scale);
        endplateLight.setzeFarbe(lightToneName);

        Linie strutRear = new Linie(car, 11 * scale, 58 * scale, 11 * scale, 74 * scale);
        strutRear.setzeLinienDicke(2 * scale);
        strutRear.setzeFarbe(deepToneName);
        Linie strutFront = new Linie(car, 16 * scale, 59 * scale, 16 * scale, 74 * scale);
        strutFront.setzeLinienDicke(2 * scale);
        strutFront.setzeFarbe(deepToneName);

        Linie spoilerUnder = new Linie(car, 2 * scale, 58 * scale, 25 * scale, 63 * scale);
        spoilerUnder.setzeLinienDicke(scale);
        spoilerUnder.setzeFarbe(deepToneName);

        spoilerUpper = new Linie(car, 2 * scale, 56 * scale, 25 * scale, 61 * scale);
        spoilerUpper.setzeLinienDicke(4 * scale);
        spoilerUpper.setzeFarbe(lightToneName);

        Linie spoilerEdge = new Linie(car, 2 * scale, 54 * scale, 25 * scale, 59 * scale);
        spoilerEdge.setzeLinienDicke(scale);
        spoilerEdge.setzeFarbe(goldToneName);

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

        // Racing stripe along the whole body
        Rechteck stripe = new Rechteck(car, 8 * scale, 77 * scale, 86 * scale, 3 * scale);
        stripe.setzeFarbe("weiss");

        // Race number on a white roundel
        Kreis roundel = new Kreis(car, 29 * scale, 67 * scale, 5 * scale);
        roundel.setzeFarbe("weiss");
        RaceNumber.draw(car, raceNumber, 34, 72, scale);

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

        addRim(29, 89);
        addRim(104, 89);

        // Exhaust flames and brake light, hidden until the car drives
        flameOuter = new Rechteck(car, 0, 78 * scale, scale, 4 * scale);
        flameOuter.setzeFarbe("orange");
        flameOuter.unsichtbarMachen();

        flameInner = new Rechteck(car, 0, 79 * scale, scale, 2 * scale);
        flameInner.setzeFarbe("gelb");
        flameInner.unsichtbarMachen();

        brakeLight = new Rechteck(car, 9 * scale, 72 * scale, 3 * scale, 6 * scale);
        brakeLight.setzeFarbe("rot");
        brakeLight.unsichtbarMachen();

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

        // Gold hub caps on top of the spokes
        Kreis hubRear = new Kreis(car, 27 * scale, 87 * scale, 2 * scale);
        hubRear.setzeFarbe(goldToneName);
        Kreis hubFront = new Kreis(car, 102 * scale, 87 * scale, 2 * scale);
        hubFront.setzeFarbe(goldToneName);

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

    /**
     * Drives with a full show: the car accelerates and brakes smoothly,
     * the wheels spin up with the speed, the suspension bounces, the
     * exhaust spits flames while accelerating and the brake light
     * lights up while slowing down.
     */
    @Override
    public void fahren(int dx, int dy) {
        // Extracted from Behaelter.langsamHorizontalBewegen()
        // and Behaelter.horizontalBewegen()
        int faktor = Math.abs(scale);
        int schritte = Math.max(Math.abs(dx), Math.abs(dy));
        int bisherX = 0, bisherY = 0;
        int bounce = 0;
        boolean forwards = dx > 0;
        double horizontalShare = (double) Math.abs(dx) / (Math.abs(dx) + Math.abs(dy));

        for (int i = 1; i <= schritte; i++) {
            // 0..1: ramps up during the first 25 %, down during the last 25 %
            double speed = Math.min(1.0, Math.min(i, schritte - i + 1) / (0.25 * schritte));
            boolean braking = i > 0.75 * schritte;

            int zielX = Math.round((float) dx * i / schritte);
            int zielY = Math.round((float) dy * i / schritte);

            // Suspension: the faster, the more the car vibrates
            int newBounce = (int) Math.round(Math.sin(i * 0.9) * faktor * speed);

            car.xPos += (zielX - bisherX) * faktor;
            car.yPos += (zielY - bisherY) * faktor + (newBounce - bounce);
            bisherX = zielX;
            bisherY = zielY;
            bounce = newBounce;
            car.setzePosition(car.xPos, car.yPos);

            if (animateWheels) {
                updateWheelAnimation((int) (3 + 9 * speed));
            }

            updateExhaust(forwards && !braking ? speed * horizontalShare : 0);
            setBrakeLight(braking);

            StaticTools.warte((int) (14 - 10 * speed));
        }

        // Back to the resting state
        car.yPos -= bounce;
        car.setzePosition(car.xPos, car.yPos);
        updateExhaust(0);
        setBrakeLight(false);

        // keep inherited x in sync with the actual on-screen position
        x = car.xPos;
        y = car.yPos;
    }

    /**
     * Revs the engine on the spot: the wheels spin at full speed,
     * the car shakes and the exhaust spits fire.
     * 
     * @param frames the length of the burnout (one frame is ~15 ms)
     */
    public void burnout(int frames) {
        for (int i = 0; i < frames; i++) {
            int shake = (i % 2 == 0 ? 1 : -1) * scale;
            car.setzePosition(car.xPos + shake, car.yPos + (i % 3 == 0 ? shake : 0));

            if (animateWheels) {
                updateWheelAnimation(40);
            }
            updateExhaust(Math.min(1.0, i / 10.0 + 0.4));
            StaticTools.warte(15);
        }

        car.setzePosition(car.xPos, car.yPos);
        updateExhaust(0);
    }

    /**
     * @param power 0 hides the flames, 1 is the longest flame
     */
    private void updateExhaust(double power) {
        if (power <= 0) {
            flameOuter.unsichtbarMachen();
            flameInner.unsichtbarMachen();
            return;
        }

        // Random flicker, the flame never grows into the spoiler (8 * scale)
        double length = Math.min(8, 2 + power * 5 + Math.random() * 2);
        flameOuter.setzeFarbe(Math.random() < 0.5 ? "orange" : "rot");
        flameOuter.setzeDimensionen(0, 78 * scale, Math.max(1, (int) (length * scale)), 4 * scale);
        flameInner.setzeDimensionen(0, 79 * scale, Math.max(1, (int) (length * 0.6 * scale)), 2 * scale);
        flameOuter.sichtbarMachen();
        flameInner.sichtbarMachen();
    }

    private void setBrakeLight(boolean on) {
        if (on) brakeLight.sichtbarMachen();
        else brakeLight.unsichtbarMachen();
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

    public int getRaceNumber() { return raceNumber; }

    /**
     * Layers a red sidewall ring, black rubber and a silver rim
     * on top of the plain black tyre.
     */
    private void addRim(int centerX, int centerY) {
        int[] radius = {8, 7, 6};
        String[] color = {"rot", "schwarz", silverToneName};

        for (int i = 0; i < radius.length; i++) {
            Kreis ring = new Kreis(
                car,
                (centerX - radius[i]) * scale,
                (centerY - radius[i]) * scale,
                radius[i] * scale
            );
            ring.setzeFarbe(color[i]);
        }
    }

    public void updateWheelAnimation() {
        updateWheelAnimation(5);
    }

    private void updateWheelAnimation(int degrees) {
        updateWheelLine(wheelLineRight1, 29, 89, 0);
        updateWheelLine(wheelLineRight2, 29, 89, 120);
        updateWheelLine(wheelLineRight3, 29, 89, 240);

        updateWheelLine(wheelLineLeft1, 104, 89, 90);
        updateWheelLine(wheelLineLeft2, 104, 89, 210);
        updateWheelLine(wheelLineLeft3, 104, 89, 330);

        wheelDegrees = (wheelDegrees + degrees) % 360;
    }

    private void updateWheelLine(Linie line, int centerX, int centerY, int angleOffset) {
        double radians = Math.toRadians(wheelDegrees + angleOffset);

        int radius = 5 * scale;
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