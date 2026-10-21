public class Fahrzeugschlange {
    private Liste cars;
    private int distanceCars = 150;

    public Fahrzeugschlange(int nCars) {
        cars = new Liste();

        for (int i = 0; i < nCars; ++i) {
            switch ((int) (Math.random() * 7)) {
                case 0:
                    cars.amEndeEinfügen(new Wohnmobil(i * distanceCars, 0));
                    break;
                case 1:
                    cars.amEndeEinfügen(new Pickup(i * distanceCars, 0));
                    break;
                case 2:
                    cars.amEndeEinfügen(new LKW(i * distanceCars, 0));
                    break;
                case 3:
                    cars.amEndeEinfügen(new Auto(i * distanceCars, 0));
                    break;
                case 4:
                    cars.amEndeEinfügen(new Bully(i * distanceCars, 0));
                    break;
                case 5:
                    cars.amEndeEinfügen(new Pritschenwagen(i * distanceCars, 0));
                    break;
                default:
                    cars.amEndeEinfügen(new SportsCar(i * distanceCars, 0));
                    break;
            }
        }
    }

    public void aufrücken() {
        for (int i = cars.knotenAnzahl - 1; i >= 0; --i) {
            cars.getKnoten(i).getKraftfahrzeug().fahren(distanceCars);
        }
        
        // Verschiebung der Knoten in der Liste
    }
    
    public Liste getListe() { return cars; }
}