public class Fahrzeugschlange {
    private Liste data;
    private int distanceData = 150;

    public Fahrzeugschlange(int ndata) {
        data = new Liste();

        for (int i = 0; i < ndata; ++i) {
            switch ((int) (Math.random() * 7)) {
                case 0:
                    data.amEndeEinfügen(new Wohnmobil(i * distanceData, 0));
                    break;
                case 1:
                    data.amEndeEinfügen(new Pickup(i * distanceData, 0));
                    break;
                case 2:
                    data.amEndeEinfügen(new LKW(i * distanceData, 0));
                    break;
                case 3:
                    data.amEndeEinfügen(new Auto(i * distanceData, 0));
                    break;
                case 4:
                    data.amEndeEinfügen(new Bully(i * distanceData, 0));
                    break;
                case 5:
                    data.amEndeEinfügen(new Pritschenwagen(i * distanceData, 0));
                    break;
                case 6:
                    data.amEndeEinfügen(new Person("Mensch", i * distanceData + 40, 15));
                    break;
                default:
                    data.amEndeEinfügen(new SportsCar(i * distanceData, 0));
                    break;
            }
        }
    }

    // public void aufrücken() {
        // for (int i = data.knotenAnzahl - 1; i >= 0; --i) {
            // data.getKnoten(i).getKraftfahrzeug().fahren(distanceData);
        // }
        
        // // Verschiebung der Knoten in der Liste
    // }
    
    public Liste getListe() { return data; }
}