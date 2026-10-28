import java.awt.Color;

/**
 * A Formula One car with open wheels, halo, DRS rear wing and a lot of show:
 * start lights, tyre smoke, sparks from the floor and glowing brake discs.
 *
 * @author Merlin Ortner
 * @version 1.0.0
 */
public class FormulaOne extends Kraftfahrzeug {
    private static final String[] TEAM_COLORS = {"rot", "blau", "gruen", "orange", "gelb", "lila", "cyan"};
    // Tyre compounds: soft, medium, hard
    private static final String[] COMPOUNDS = {"rot", "gelb", "weiss"};

    private static final int REAR_X = 28, REAR_Y = 82, REAR_R = 12;
    private static final int FRONT_X = 110, FRONT_Y = 84, FRONT_R = 10;

    // Gives every instance its own StaticTools color-name namespace,
    // so two cars on the field never overwrite each other's colors.
    private static int instanceCounter = 0;

    private final String lightToneName, darkToneName, helmetToneName;
    private final String accentColor;
    private final int raceNumber;

    private Linie drsFlap, rearSpoke1, rearSpoke2, frontSpoke1, frontSpoke2;
    private Kreis rearHub, frontHub;
    private Rechteck brakeLight, lightHousing;
    private Rechteck[] sparks = new Rechteck[5];
    private Kreis[] smoke = new Kreis[3];
    private Kreis[] startLights = new Kreis[5];
    private int wheelDegrees;
    private boolean drsOpen;

    /**
     * @param _name the name of the car
     * @param xPos the inital x position
     * @param yPos the inital y position
     * @param ps the car's horsepower
     * @param baseColor the team color (a StaticTools color name)
     * @param _accentColor the color of the livery details (a StaticTools color name)
     */
    public FormulaOne(String _name, int xPos, int yPos, int ps, String baseColor, String _accentColor) {
        super(_name, ps, xPos, yPos);
        accentColor = _accentColor;
        raceNumber = RaceNumber.pick();

        int id = instanceCounter++;
        lightToneName  = "f1LightTone"  + id;
        darkToneName   = "f1DarkTone"   + id;
        helmetToneName = "f1HelmetTone" + id;

        Color base = StaticTools.getColor(baseColor);
        StaticTools.setzeFarbe(lightToneName, adjustColor(base, 40, 40, 40));
        StaticTools.setzeFarbe(darkToneName, adjustColor(base, -45, -45, -45));
        StaticTools.setzeFarbe(helmetToneName, getHelmetColor(base));

        // Ground shadow and tyre smoke (bottom layer)
        Ellipse shadow = new Ellipse(auto, 4, 94, 132, 6);
        shadow.setzeFarbe("dunkelgrau");

        for (int i = 0; i < smoke.length; i++) {
            smoke[i] = new Kreis(auto, 0, 80, 4);
            smoke[i].setzeFarbe("hellgrau");
            smoke[i].unsichtbarMachen();
        }

        // Rear wing: gearbox, pylon, endplate, main plane and the DRS flap on top
        Rechteck gearbox = new Rechteck(auto, 8, 74, 12, 8);
        gearbox.setzeFarbe(darkToneName);
        Rechteck pylon = new Rechteck(auto, 11, 59, 3, 16);
        pylon.setzeFarbe(darkToneName);

        Rechteck endplate = new Rechteck(auto, 3, 44, 3, 22);
        endplate.setzeFarbe(darkToneName);
        Rechteck endplateLight = new Rechteck(auto, 3, 44, 1, 22);
        endplateLight.setzeFarbe(lightToneName);

        Linie planeUnder = new Linie(auto, 5, 60, 29, 62);
        planeUnder.setzeLinienDicke(1);
        planeUnder.setzeFarbe("schwarz");
        Linie plane = new Linie(auto, 5, 58, 29, 60);
        plane.setzeLinienDicke(3);
        plane.setzeFarbe(lightToneName);

        drsFlap = new Linie(auto, 5, 53, 29, 55);
        drsFlap.setzeLinienDicke(2);
        drsFlap.setzeFarbe(accentColor);

        // Bodywork: floor, chassis, engine cover, sidepod with intake
        Rechteck floor = new Rechteck(auto, 30, 88, 68, 2);
        floor.setzeFarbe(darkToneName);
        Rechteck chassis = new Rechteck(auto, 28, 76, 72, 12);
        chassis.setzeFarbe(baseColor);
        Dreieck engineCover = new Dreieck(auto, 30, 60, 34, 16);
        engineCover.setzeAusrichtung("SO");
        engineCover.setzeFarbe(baseColor);

        Rechteck sidepod = new Rechteck(auto, 44, 78, 40, 10);
        sidepod.setzeFarbe(lightToneName);
        Rechteck intake = new Rechteck(auto, 82, 79, 3, 8);
        intake.setzeFarbe("schwarz");
        Rechteck stripe = new Rechteck(auto, 28, 79, 72, 2);
        stripe.setzeFarbe(accentColor);

        // Race number on a white roundel
        Kreis roundel = new Kreis(auto, 55, 78, 5);
        roundel.setzeFarbe("weiss");
        RaceNumber.draw(auto, raceNumber, 60, 83, 1);

        // Nose cone
        Dreieck nose = new Dreieck(auto, 100, 72, 34, 16);
        nose.setzeAusrichtung("SW");
        nose.setzeFarbe(baseColor);

        // Cockpit with helmet and halo
        Rechteck cockpit = new Rechteck(auto, 62, 72, 30, 4);
        cockpit.setzeFarbe(darkToneName);
        Kreis helmet = new Kreis(auto, 68, 64, 5);
        helmet.setzeFarbe(helmetToneName);
        Rechteck visor = new Rechteck(auto, 74, 66, 5, 3);
        visor.setzeFarbe("schwarz");

        Linie haloTop = new Linie(auto, 66, 62, 84, 62);
        haloTop.setzeLinienDicke(2);
        haloTop.setzeFarbe(darkToneName);
        Linie haloFront = new Linie(auto, 84, 62, 92, 73);
        haloFront.setzeLinienDicke(2);
        haloFront.setzeFarbe(darkToneName);
        Linie haloRear = new Linie(auto, 66, 62, 64, 72);
        haloRear.setzeLinienDicke(2);
        haloRear.setzeFarbe(darkToneName);

        // Front wing
        Rechteck frontWingUpper = new Rechteck(auto, 112, 88, 26, 2);
        frontWingUpper.setzeFarbe(lightToneName);
        Rechteck frontWingLower = new Rechteck(auto, 108, 90, 30, 3);
        frontWingLower.setzeFarbe(accentColor);
        Rechteck frontEndplate = new Rechteck(auto, 134, 84, 3, 10);
        frontEndplate.setzeFarbe(darkToneName);

        // Open wheels with spokes and hubs
        String compound = COMPOUNDS[(int) (Math.random() * COMPOUNDS.length)];
        addTyre(REAR_X, REAR_Y, REAR_R, 8, compound);
        addTyre(FRONT_X, FRONT_Y, FRONT_R, 6, compound);

        rearSpoke1 = newSpoke();
        rearSpoke2 = newSpoke();
        frontSpoke1 = newSpoke();
        frontSpoke2 = newSpoke();

        rearHub = new Kreis(auto, REAR_X - 2, REAR_Y - 2, 2);
        rearHub.setzeFarbe(accentColor);
        frontHub = new Kreis(auto, FRONT_X - 2, FRONT_Y - 2, 2);
        frontHub.setzeFarbe(accentColor);
        spin(0);

        // Show elements, hidden until the car drives
        brakeLight = new Rechteck(auto, 3, 62, 3, 4);
        brakeLight.setzeFarbe("rot");
        brakeLight.unsichtbarMachen();

        for (int i = 0; i < sparks.length; i++) {
            sparks[i] = new Rechteck(auto, 50, 91, 2, 2);
            sparks[i].setzeFarbe("gelb");
            sparks[i].unsichtbarMachen();
        }

        lightHousing = new Rechteck(auto, 36, 12, 72, 14);
        lightHousing.setzeFarbe("schwarz");
        lightHousing.unsichtbarMachen();
        for (int i = 0; i < startLights.length; i++) {
            startLights[i] = new Kreis(auto, 41 + i * 14, 15, 4);
            startLights[i].setzeFarbe("dunkelgrau");
            startLights[i].unsichtbarMachen();
        }
    }

    /**
     * Creates a random team car.
     */
    public FormulaOne() {
        this(0, 0);
    }

    /**
     * Spawns a random team car with a set start position.
     */
    public FormulaOne(int startX, int startY) {
        this("Formula", startX, startY, 1000, randomTeamColor(), "weiss");
    }

    private static String randomTeamColor() {
        return TEAM_COLORS[(int) (Math.random() * TEAM_COLORS.length)];
    }

    public int getRaceNumber() { return raceNumber; }

    /**
     * Drives with a full show: the car accelerates and brakes smoothly,
     * the wheels spin up with the speed, the suspension bounces, smoke rises
     * from the rear tyres at launch, the DRS flap opens on long straights,
     * sparks fly from the floor at speed, and the brake light and the brake
     * discs glow while slowing down.
     */
    @Override
    public void fahren(int dx, int dy) {
        int schritte = Math.max(Math.abs(dx), Math.abs(dy));
        int bisherX = 0, bisherY = 0;
        int bounce = 0;
        boolean forwards = dx > 0;

        for (int i = 1; i <= schritte; i++) {
            // 0..1: ramps up during the first 25 %, down during the last 25 %
            double speed = Math.min(1.0, Math.min(i, schritte - i + 1) / (0.25 * schritte));
            boolean braking = i > 0.75 * schritte;
            boolean launching = i <= 0.25 * schritte;

            int zielX = Math.round((float) dx * i / schritte);
            int zielY = Math.round((float) dy * i / schritte);

            // Suspension: the faster, the more the car vibrates
            int newBounce = (int) Math.round(Math.sin(i * 0.9) * speed);

            auto.xPos += zielX - bisherX;
            auto.yPos += (zielY - bisherY) + (newBounce - bounce);
            bisherX = zielX;
            bisherY = zielY;
            bounce = newBounce;
            auto.setzePosition(auto.xPos, auto.yPos);

            spin((int) (3 + 9 * speed));

            setDrs(forwards && schritte >= 80 && i > 0.3 * schritte && i <= 0.75 * schritte);
            updateSmoke(forwards && launching ? 1 - speed : 0);
            updateSparks(forwards && speed > (braking ? 0.5 : 0.7));
            setBraking(braking);

            StaticTools.warte((int) (14 - 10 * speed));
        }

        // Back to the resting state
        auto.yPos -= bounce;
        auto.setzePosition(auto.xPos, auto.yPos);
        setDrs(false);
        updateSmoke(0);
        updateSparks(false);
        setBraking(false);

        // keep inherited x in sync with the actual on-screen position
        x = auto.xPos;
        y = auto.yPos;
    }

    /**
     * Revs the engine on the spot: the wheels spin at full speed,
     * the car shakes and the rear tyres smoke.
     *
     * @param frames the length of the burnout (one frame is ~15 ms)
     */
    public void burnout(int frames) {
        for (int i = 0; i < frames; i++) {
            int shake = i % 2 == 0 ? 1 : -1;
            auto.setzePosition(auto.xPos + shake, auto.yPos + (i % 3 == 0 ? shake : 0));

            spin(40);
            updateSmoke(Math.min(1.0, i / 10.0 + 0.3));
            StaticTools.warte(15);
        }

        auto.setzePosition(auto.xPos, auto.yPos);
        updateSmoke(0);
    }

    /**
     * The race start: five red lights switch on one after another,
     * then they go out and the car launches with a burnout.
     */
    public void startSequenz() {
        lightHousing.sichtbarMachen();
        for (Kreis light : startLights) {
            light.sichtbarMachen();
        }

        for (Kreis light : startLights) {
            light.setzeFarbe("rot");
            StaticTools.warte(500);
        }
        StaticTools.warte(700);

        // Lights out and away we go
        lightHousing.unsichtbarMachen();
        for (Kreis light : startLights) {
            light.setzeFarbe("dunkelgrau");
            light.unsichtbarMachen();
        }
        burnout(40);
    }

    /**
     * Layers a tyre: black base, colored compound ring, black rubber and a silver rim.
     */
    private void addTyre(int centerX, int centerY, int radius, int rimRadius, String compound) {
        int[] radii = {radius, radius - 1, radius - 2, rimRadius};
        String[] colors = {"schwarz", compound, "schwarz", "hellgrau"};

        for (int i = 0; i < radii.length; i++) {
            Kreis ring = new Kreis(auto, centerX - radii[i], centerY - radii[i], radii[i]);
            ring.setzeFarbe(colors[i]);
        }
    }

    private Linie newSpoke() {
        Linie spoke = new Linie(auto);
        spoke.setzeLinienDicke(2);
        spoke.setzeFarbe("dunkelgrau");
        return spoke;
    }

    private void spin(int degrees) {
        wheelDegrees = (wheelDegrees + degrees) % 360;
        updateSpoke(rearSpoke1, REAR_X, REAR_Y, 6, 0);
        updateSpoke(rearSpoke2, REAR_X, REAR_Y, 6, 90);
        updateSpoke(frontSpoke1, FRONT_X, FRONT_Y, 5, 0);
        updateSpoke(frontSpoke2, FRONT_X, FRONT_Y, 5, 90);
    }

    private void updateSpoke(Linie spoke, int centerX, int centerY, int radius, int angleOffset) {
        double radians = Math.toRadians(wheelDegrees + angleOffset);
        int dx = (int) (Math.cos(radians) * radius);
        int dy = (int) (Math.sin(radians) * radius);

        spoke.setzeEndpunkte(centerX + dx, centerY + dy, centerX - dx, centerY - dy);
    }

    /**
     * Opens or closes the DRS flap: opened, its rear edge lifts up.
     */
    private void setDrs(boolean open) {
        if (open == drsOpen) return;
        drsOpen = open;
        drsFlap.setzeEndpunkte(5, open ? 47 : 53, 29, 55);
    }

    private void setBraking(boolean on) {
        if (on) brakeLight.sichtbarMachen();
        else brakeLight.unsichtbarMachen();

        // Glowing brake discs
        String hubColor = on ? "orange" : accentColor;
        rearHub.setzeFarbe(hubColor);
        frontHub.setzeFarbe(hubColor);
    }

    /**
     * Random sparks fly from the floor of the car.
     */
    private void updateSparks(boolean on) {
        String[] colors = {"gelb", "orange", "weiss"};

        for (Rechteck spark : sparks) {
            if (!on || Math.random() < 0.3) {
                spark.unsichtbarMachen();
                continue;
            }
            spark.setzeDimensionen(45 + (int) (Math.random() * 48), 90 + (int) (Math.random() * 4), 2, 2);
            spark.setzeFarbe(colors[(int) (Math.random() * colors.length)]);
            spark.sichtbarMachen();
        }
    }

    /**
     * Puffs of smoke behind the rear tyres.
     *
     * @param power 0 hides the smoke, 1 is the biggest cloud
     */
    private void updateSmoke(double power) {
        String[] colors = {"hellgrau", "weiss", "grau"};

        for (int i = 0; i < smoke.length; i++) {
            if (power <= 0) {
                smoke[i].unsichtbarMachen();
                continue;
            }
            int radius = (int) (2 + power * 4 + Math.random() * 2);
            int centerX = 7 - i * 2 + (int) (Math.random() * 3);
            int centerY = 80 + i * 5 - (int) (power * 3);
            smoke[i].setzeMittelpunktUndRadius(Math.max(radius, centerX), centerY, radius);
            smoke[i].setzeFarbe(colors[(int) (Math.random() * colors.length)]);
            smoke[i].sichtbarMachen();
        }
    }
}
