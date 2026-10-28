//%$JGUIToolbox$%//ID fuer Toolboxdateien
/**
 * <h1>Rechteck mit abgerundeten Ecken</h1>
 * 
 * @author Hans Witt
 * 
 * Version 1.1 (14.7.2008)
 *     Hinzufuegen von Statusvariablen fuer Position ...
 * Version: 1.1.1 (17.7.2008) 
 *        Neue Komponenten werden von Unten nach Oben aufgebaut, d.h.vor die alten gesetzt
 * Version: 3 (4.8.2008) 
 *        ergaenzt fuer Containerklasse fuer GUI-Elemente
 * Version: 3.1 (14.8.2008) 
 *        Konstruktor auf int neuesX, int neuesY , int neueBreite, int neueHoehe angepasst		
 * @version: 3.2 (18.8.2008)
 *        Zustandsvariable auf protected gesetzt 
 * @version 5.0: (4.9.2010
 *  	   Entfernen fuer Graphikkomponente eingefuehrt
 *  
 *   	   Destruktor entfernt Graphikkomponente automatisch bei gc()
 * 
 */
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class RechteckMitRundenEcken implements IComponente {
	
	private CRechteckmitRundenEcken	obj;
	protected int					breite		= 0;
	protected int					hoehe		= 0;
	protected int					radius		= 0;								// Radius
																					// der
																					// Rundung
																					
	protected int					xPos		= 0;
	protected int					yPos		= 0;
	
	protected boolean				sichtbar	= true;
	protected boolean				gefuellt	= true;
	protected String				farbe		= StaticTools.leseNormalfarbe();
	
	/**
	 * Konstruktor fuer Hauptfenster
	 */
	public RechteckMitRundenEcken() {
		this(Zeichnung.gibZeichenflaeche());
	}
	
	/**
	 * Konstruktor fuer Hauptfenster
	 * 
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public RechteckMitRundenEcken(int neueBreite, int neueHoehe, int neuerRadius) {
		this(Zeichnung.gibZeichenflaeche(), 0, 0, neueBreite, neueHoehe,
				neuerRadius);
	}
	
	/**
	 * Konstruktor fuer Hauptfenster
	 * 
	 * @param neuesX
	 * @param neuesY
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public RechteckMitRundenEcken(int neuesX, int neuesY, int neueBreite,
			int neueHoehe, int neuerRadius) {
		this(Zeichnung.gibZeichenflaeche(), neuesX, neuesY, neueBreite,
				neueHoehe, neuerRadius);
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param behaelter
	 */
	public RechteckMitRundenEcken(IContainer behaelter) {
		this(behaelter, 0, 0, 100, 50, 10);
	}
	
	/**
	 * allgemeiner Konstruktor
	 * 
	 * @param behaelter
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public RechteckMitRundenEcken(IContainer behaelter, int neuesX, int neuesY,
			int neueBreite, int neueHoehe, int neuerRadius) {
		obj = new CRechteckmitRundenEcken();
		behaelter.add(obj, 0);
		setzeDimensionen(neuesX, neuesY, neueBreite, neueHoehe, neuerRadius);
		behaelter.validate();
	}
	
	/**
	 * Das Interface IComponente fordert eine Methode die eine BasisComponente
	 * zurueckliefert. Sie wird benoetigt, um ein Objekt zu einem anderen
	 * Behaelter hinzuzufuegen
	 */
	public BasisComponente getBasisComponente() {
		return obj;
	}
	
	public void sichtbarMachen() {
		sichtbar = true;
		obj.sichtbarMachen();
	}
	
	/**
	 * Mache unsichtbar. Wenn es bereits unsichtbar ist, tue nichts.
	 */
	public void unsichtbarMachen() {
		sichtbar = false;
		obj.unsichtbarMachen();
	}
	
	/**
	 * Bewege horizontal um 'entfernung' Bildschirmpunkte.
	 */
	public void nachRechtsBewegen() {
		horizontalBewegen(20);
	}
	
	/**
	 * Bewege einige Bildschirmpunkte nach links.
	 */
	public void nachLinksBewegen() {
		horizontalBewegen(-20);
	}
	
	/**
	 * Bewege einige Bildschirmpunkte nach oben.
	 */
	public void nachObenBewegen() {
		vertikalBewegen(-20);
	}
	
	/**
	 * Bewege einige Bildschirmpunkte nach unten.
	 */
	public void nachUntenBewegen() {
		vertikalBewegen(20);
	}
	
	/**
	 * Bewege vertikal um 'entfernung' Bildschirmpunkte.
	 */
	public void langsamVertikalBewegen(int entfernung) {
		int delta;
		
		if (entfernung < 0) {
			delta = -1;
			entfernung = -entfernung;
		} else {
			delta = 1;
		}
		
		for (int i = 0; i < entfernung; i++) {
			vertikalBewegen(delta);
			StaticTools.warte(10);
		}
	}
	
	/**
	 * Bewege vertikal um 'entfernung' Bildschirmpunkte.
	 */
	public void langsamHorizontalBewegen(int entfernung) {
		int delta;
		
		if (entfernung < 0) {
			delta = -1;
			entfernung = -entfernung;
		} else {
			delta = 1;
		}
		
		for (int i = 0; i < entfernung; i++) {
			horizontalBewegen(delta);
			StaticTools.warte(10);
		}
	}
	
	public void setzeGroesse(int neueBreite, int neueHoehe, int neuerRadius) {
		breite = neueBreite;
		hoehe = neueHoehe;
		radius = neuerRadius;
		if (radius > (hoehe / 2)) radius = hoehe / 2;
		if (radius > (breite / 2)) radius = breite / 2;
		obj.setzeGroesse(breite, hoehe, radius);
	}
	
	/**
	 * neue Position
	 * 
	 * @param neuesX
	 * @param neuesY
	 */
	public void setzePosition(int neuesX, int neuesY) {
		xPos = neuesX;
		yPos = neuesY;
		obj.setzePosition(xPos, yPos);
	}
	
	// Methode noetig zum Hinzufuegen mit Anpassung beim Behaelter
	// Die Enden werden relativ zur aktuellen position verschoben
	public void verschieben(int dx, int dy) {
		setzePosition(xPos + dx, yPos + dy);
	}
	
	/**
	 * 
	 * @param neuesX
	 * @param neuesY
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public void setzeDimensionen(int neuesX, int neuesY, int neueBreite,
			int neueHoehe, int neuerRadius) {
		xPos = neuesX;
		yPos = neuesY;
		breite = neueBreite;
		hoehe = neueHoehe;
		radius = neuerRadius;
		if (radius > hoehe) radius = hoehe;
		if (radius > breite) radius = breite;
		obj.setzeDimensionen(xPos, yPos, breite, hoehe, radius);
	}
	
	/*
	 * gueltige Farben: "rot", "gelb", "blau", "gruen", "lila" , "schwarz" ,
	 * "weiss" , "grau","pink","magenta","orange","cyan","hellgrau"
	 */
	public void setzeFarbe(String neueFarbe) {
		farbe = neueFarbe;
		obj.setzeBasisfarbe(neueFarbe);
	}
	
	/**
	 * Bewege horizontal um 'entfernung' Bildschirmpunkte.
	 */
	public void horizontalBewegen(int entfernung) {
		xPos += entfernung;
		obj.setzePosition(xPos, yPos);
	}
	
	/**
	 * Bewege vertikal um 'entfernung' Bildschirmpunkte.
	 */
	public void vertikalBewegen(int entfernung) {
		yPos += entfernung;
		obj.setzePosition(xPos, yPos);
	}
	
	public void fuellen() {
		gefuellt = true;
		obj.fuellen();
	}
	
	public void rand() {
		gefuellt = false;
		obj.rand();
	}
	
	/**
	 * Entfernen des Graphikobjekts
	 */
	public void entfernen() {
		if (obj != null) {
			obj.ausContainerEntfernen();
			obj = null;
		}
	}
	
	/**
	 * Destruktor
	 */
	@Override
	protected void finalize() {
	if (!Zeichnung.verweistesGUIElementEntfernen) return;
		if (obj != null) entfernen();
	}
	
}

@SuppressWarnings("serial")
class CRechteckmitRundenEcken extends BasisComponente {
	private int	radius	= 0;	// Radius der Rundung
								
	/**
	 * Konstruktor ohne Beschriftung
	 */
	public CRechteckmitRundenEcken() {
		
	}
	
	/**
	 * Die Darstellung der Komponente wird hier programmiert.
	 */
	
	@Override
	public void paintComponentSpezial(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// Graphik-Abmessungen
		breite = getSize().width;
		hoehe = getSize().height;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_MITER));
		g.setColor(farbe);
		
		if (gefuellt) {
			g2.fillRoundRect(0, 0, breite - 1, hoehe - 1, radius, radius);
		} else {
			g2.drawRoundRect(0, 0, breite - 1, hoehe - 1, radius, radius);
		}
	}
	
	public void setzeGroesse(int neueBreite, int neueHoehe, int neuerRadius) {
		radius = neuerRadius; // Radius der Rundung
		setzeGroesse(neueBreite, neueHoehe);
	}
	
	public void setzeDimensionen(int neuesX, int neuesY, int neueBreite,
			int neueHoehe, int neuerRadius) {
		radius = neuerRadius; // Radius der Rundung
		setzeDimensionen(neuesX, neuesY, neueBreite, neueHoehe);
	}
	
}
