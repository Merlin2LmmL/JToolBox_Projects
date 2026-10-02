
/**
 * Abstrct class dictating all car classes.
 *
 * @author Merlin Ortner
 * @version 1.0.1
 */
public abstract class Kraftfahrzeug {
   protected int x, y, ps, laenge;
   protected String bezeichnung, typ;

   protected Behaelter auto;
   
   // Constructor
   public Kraftfahrzeug(String _bezeichnung, int _ps, int xPos, int yPos) {
       x = xPos;
       y = yPos;
       bezeichnung = _bezeichnung;
       ps = _ps;
       auto = new Behaelter(x, y, 140, 100);
   }
   
   public void setzePosition(int neuesX, int neuesY) {
       x = neuesX;
       y = neuesY;
       auto.setzePosition(x, y);
   }
   
   public abstract void fahren(int entfernung);
}