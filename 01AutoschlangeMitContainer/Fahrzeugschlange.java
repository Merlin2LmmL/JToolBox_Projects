public class Fahrzeugschlange {
    private Liste list;
    private final int DISTANCEELEMENTS = 150;
    private final int VERTICALHIDDEN = 100;

    public Fahrzeugschlange(int nlist) {
        list = new Liste();

        for (int i = 0; i < nlist; ++i) {
            switch ((int) (Math.random() * 9)) {
                case 0:
                    list.amEndeEinfügen(new Wohnmobil(i * DISTANCEELEMENTS, 0));
                    break;
                // case 1:
                    // list.amEndeEinfügen(new Pickup(i * DISTANCEELEMENTS, 0));
                    // break;
                // case 2:
                    // list.amEndeEinfügen(new LKW(i * DISTANCEELEMENTS, 0));
                    // break;
                // case 3:
                    // list.amEndeEinfügen(new Auto(i * DISTANCEELEMENTS, 0));
                    // break;
                // case 4:
                    // list.amEndeEinfügen(new Bully(i * DISTANCEELEMENTS, 0));
                    // break;
                // case 5:
                    // list.amEndeEinfügen(new Pritschenwagen(i * DISTANCEELEMENTS, 0));
                    // break;
                // case 6:
                    // list.amEndeEinfügen(new Person("Mensch", i * DISTANCEELEMENTS, 0));
                    // break;
                // case 7:
                //     list.amEndeEinfügen(new FormulaOne(i * DISTANCEELEMENTS, 0));
                //     break;
                default:
                    list.amEndeEinfügen(new SportsCar(i * DISTANCEELEMENTS, 0));
                    break;
            }
        }
    }

    public void aufrücken() {
        for (int i = list.getLänge() - 1; i >= 0; --i) {
            list.getKnoten(i).getDatenelement().bewegen(DISTANCEELEMENTS, 0);
        }
    }
    
    public void einfügen(int pos, Datenelement dneu) {
        for (int i = list.getLänge() - 1; i >= pos; --i) {
            list.getKnoten(i).getDatenelement().bewegen(DISTANCEELEMENTS, 0);
        }
        
        dneu.setzePosition(pos * DISTANCEELEMENTS, -VERTICALHIDDEN);
        list.anPosEinfügen(dneu, pos).getDatenelement().bewegen(0, VERTICALHIDDEN);
    }
    
    public Liste getListe() { return list; }
}