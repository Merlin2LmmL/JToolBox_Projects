/**
 * Taktgeber ist eine Klasse, die in gleichen Zeitabstaenden eine Methode aufruft.
 * 
 *  Ueber die Methode wurdeSignalisiert() kann der Status abgefragt werden.
 *  Es wird gemeldet, ob sich seit der letzten Abfrage die ein Taktsignal ereignet hat
 *  
 *       ruecksetzenTimer() setzt den Status zurueck
 *       warteBisTaktsignal() wartet bis zum Taktsignal
 *      
 *  Uebergibt man dem taktsignal den Link auf eine Klasse mit dem Interface ITuwas,
 *  so wird beim Timersignal tuWasSelect(id) aufgerufen
 * 
 * @author Hans Witt
 * 
 * Version: 1.0 (2.8.2008)
 * Version: 2 (3.8.2008)
 *       angepasst an geaendertes ITuWas
 * @version: 3 (21.8.2008)
 * 	     Methode mehrfach( int anzahl ) implementiert
 * 		 Methode start() entfernt. Durch Setzen der Anzahl startet der Timer  	
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Taktgeber {
	private Timer		t;
	protected int		delay			= 1000;	// milliseconds
	protected int		startDelay		= 1000;
	
	protected boolean	timerSignal		= false;
	
	protected int		anzahl;
	protected boolean	begrenzteAnzahl	= false;
	
	public Taktgeber() {
		ActionListener taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tuWas();
			}
		};
		t = new Timer(delay, taskPerformer);
		setzteZeitZwischenAktionen(1000);
		setzteAnfangsZeitverzoegerung(1000);
	}
	
	/**
	 * Konstruktor mit gleichzeitigem Setzen des tuWas-Objekts
	 * 
	 * @param linkObj
	 * @param ID
	 */
	public Taktgeber(ITuWas linkObj, int ID) {
		this();
		setzeLink(linkObj, ID);
	}
	
	private void tuWas() {
		timerSignal = true;
		if (begrenzteAnzahl) { // Bei Methode eimal stopt der Timer automatisch
			anzahl--;
			if (anzahl == 0) t.stop();
		}
		if (linkObj != null) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					linkObj.tuWas(id);
				};
			}).start();
		}
		
	}
	
	private ITuWas	linkObj;
	private int		id	= 0;	// ID der Komponente fuer Callback
								
	/**
	 * Eine Komponente mit Signalisierung muss eine eindeutige ID haben
	 * 
	 * @param ID
	 */
	public void setzeID(int ID) {
		this.id = ID;
	}
	
	/**
	 * Eine Komponente mit Signalisierung braucht eine Objekt, dem es
	 * signalisieren kann
	 * 
	 * @param linkObj
	 */
	public void setzeLink(ITuWas linkObj) {
		this.linkObj = linkObj;
	}
	
	/**
	 * Callback-Funktion wird aufgerufen, wenn der Timer signalisiert
	 * 
	 * @param linkObj
	 * 
	 * @param ID
	 *            Parameter, der als ID an die Callback-Funktion uebergeben wird.
	 *            In der Callbackfunktion kann dann durch die ID das aufrufende
	 *            Objekt identifiziert werden.
	 * 
	 */
	public void setzeLink(ITuWas linkObj, int basisID) {
		this.linkObj = linkObj;
		id = basisID;
	}
	
	/**
	 * Es wird abgefragt, ob seit der letzten Nachfrage der Timer sihnalisiert
	 * hat
	 */
	public boolean wurdeSignalisiert() {
		boolean wert = timerSignal;
		timerSignal = false;
		return wert;
	}
	
	public void warteBisTaktsignal() {
		while (!wurdeSignalisiert()) {
			StaticTools.warte(20); // warte 20ms
		}
	}
	
	/**
	 * SetzeWiederholungsverzoegerung
	 */
	public void setzteZeitZwischenAktionen(int delay) {
		t.setDelay(delay);
	}
	
	/**
	 * SetzeAnfangsverzoegerung
	 */
	public void setzteAnfangsZeitverzoegerung(int delay) {
		t.setInitialDelay(delay);
	}
	
	/**
	 * Timer mit Wiederholung automatischer Stop nach anzahl Intervallen
	 */
	public void mehrfach(int anzahl) {
		t.stop();
		this.anzahl = anzahl;
		begrenzteAnzahl = true;
		t.setRepeats(true);
		t.start();
	}
	
	/**
	 * einmaliges Signalisieren
	 */
	public void einmal() {
		t.stop();
		begrenzteAnzahl = false;
		t.setRepeats(false);
		t.start();
	}
	
	/**
	 * einmaliges Signalisieren
	 */
	public void einmal(int delay) {
		t.stop();
		setzteAnfangsZeitverzoegerung(delay);
		begrenzteAnzahl = false;
		t.setRepeats(false);
		t.start();
	}
	
	/**
	 * Timer mit Wiederholung
	 */
	public void endlos() {
		t.stop();
		begrenzteAnzahl = false;
		t.setRepeats(true);
		t.start();
	}
	
	public void stop() {
		t.stop();
	}
	
	public boolean laufend() {
		return t.isRunning();
	}
	
}
