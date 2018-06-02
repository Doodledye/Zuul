import java.util.*;
/**
 * Diese Klasse modelliert Räume in der Welt von Zuul.
 * 
 * Ein "Raum" repräsentiert einen Ort in der virtuellen Landschaft des
 * Spiels. Ein Raum ist mit anderen Räumen über Ausgänge verbunden.
 * Mögliche Ausgänge liegen im Norden, Osten, Süden und Westen.
 * Für jede Richtung hält ein Raum eine Referenz auf den 
 * benachbarten Raum.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
class Raum 
{
    public String beschreibung;
    public HashMap<String, Raum> ausgaenge;
    // private Raum nordausgang;
    // private Raum suedausgang;
    // private Raum ostausgang;
    // private Raum westausgang;
    // private Raum downausgang;
    // private Raum upausgang;

    /**
     * Erzeuge einen Raum mit einer Beschreibung. Ein Raum
     * hat anfangs keine Ausgänge.
     * @param beschreibung enthält eine Beschreibung in der Form
     *        "in einer Küche" oder "auf einem Sportplatz".
     */
    public Raum(String beschreibung) 
    {
        this.beschreibung = beschreibung;
    }

    /**
     * Definiere die Ausgänge dieses Raums. Jede Richtung
     * führt entweder in einen anderen Raum oder ist 'null'
     * (kein Ausgang).
     * @param norden Der Nordeingang.
     * @param osten Der Osteingang.
     * @param sueden Der Südeingang.
     * @param westen Der Westeingang.
     */
    public void setzeAusgaenge(Raum norden, Raum osten,
    Raum sueden, Raum westen, Raum down, Raum up) {
        if(norden != null){
            ausgaenge.put("north",norden);
        }
        if(osten != null){
            ausgaenge.put("east",osten);
        }        
        if(sueden != null){
            ausgaenge.put("south",sueden);
        }        
        if(westen != null){
            ausgaenge.put("west",westen);
        }     
        if(down != null){
            ausgaenge.put("down",down);
        }
        if(up != null){
            ausgaenge.put("up",up);
        }        
    }

    /**
     * @return Die Beschreibung dieses Raums.
     */
    public String gibBeschreibung()
    {
        return beschreibung;
    }

    public Raum gibAusgang(String richtung){
        return ausgaenge.get(richtung);
    }

}
