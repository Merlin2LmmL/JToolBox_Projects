public class FahrzeugArray {
    private Kraftfahrzeug[] cars;
    private int distanceCars = 150;

    public FahrzeugArray(int nCars) {
        cars = new Kraftfahrzeug[nCars];

        for (int i = 0; i < nCars; ++i) {
            switch ((int) (Math.random() * 7)) {
                case 0:
                    cars[i] = new Wohnmobil(i * distanceCars, 0);
                    break;
                case 1:
                    cars[i] = new Pickup(i * distanceCars, 0);
                    break;
                case 2:
                    cars[i] = new LKW(i * distanceCars, 0);
                    break;
                case 3:
                    cars[i] = new Auto(i * distanceCars, 0);
                    break;
                case 4:
                    cars[i] = new Bully(i * distanceCars, 0);
                    break;
                case 5:
                    cars[i] = new Pritschenwagen(i * distanceCars, 0);
                    break;
                default:
                    cars[i] = new SportsCar(i * distanceCars, 0);
                    break;
            }
        }
    }

    public void aufrücken() {
        for (int i = cars.length - 1; i >= 0; --i) {
            cars[i].fahren(distanceCars);
        }
    }
}
