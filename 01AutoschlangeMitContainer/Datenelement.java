/**
 * Bridge between class Liste and Kraftfahrzeug/Person
 *
 * @author Merlin Ortner
 * @version 1.0.1
 */
public interface Datenelement {
    boolean equalsKey(String key);
    boolean isSmallerThan(Datenelement vergleich);
    String getBezeichnung();
}