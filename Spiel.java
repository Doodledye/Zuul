/**
 *  Dies ist die Hauptklasse der Anwendung "Die Welt von Zuul".
 *  "Die Welt von Zuul" ist ein sehr einfaches, textbasiertes
 *  Adventure-Game. Ein Spieler kann sich in einer Umgebung bewegen,
 *  mehr nicht. Das Spiel sollte auf jeden Fall ausgebaut werden,
 *  damit es interessanter wird!
 * 
 *  Zum Spielen muss eine Instanz dieser Klasse erzeugt werden und
 *  an ihr die Methode "spielen" aufgerufen werden.
 * 
 *  Diese Instanz erzeugt und initialisiert alle anderen Objekte
 *  der Anwendung: Sie legt alle Räume und einen Parser an und
 *    et das Spiel. Sie wertet auch die Befehle aus, die der
 *  Parser liefert, und sorgt für ihre Ausführung.
 * 
 * @author  Michael Kölling und David J. Barnes
 * @version 2006.03.30
 */

// HalloWelt

class Spiel 
{
    private Parser parser;
    private Raum aktuellerRaum;

    /**
     * Erzeuge ein Spiel und initialisiere die interne Raumkarte.
     */
    public Spiel() 
    {
        raeumeAnlegen();
        parser = new Parser();
    }

    /**
     * Erzeuge alle Räume und verbinde ihre Ausgänge miteinander.
     */
    private void raeumeAnlegen()
    {
        Raum start, nothing, enemy1, enemy2, key, bomb, weapon1, weapon2, defuser, boost;

        // die Räume erzeugen
        start = new Raum("am Start");
        nothing = new Raum("in der Mitte");
        enemy1= new Raum("ACHTUNG! ein Gegner, er bewacht den Schlüsselraum.");
        enemy2 = new Raum("ACHTUNG! ein Gegner, er bewacht den Waffenraum 2 und den Defuserraum.");
        key = new Raum("im Schlüsselraum, hier ist der Schlüssel und eine Waffe.");
        bomb = new Raum("im Bombenraum sie müssen die Bombe defusen.");
        weapon1 = new Raum("in einem Waffenraum, hier ist eine Waffe.");
        weapon2 = new Raum("in einem Waffenraum, hier ist eine Waffe.");
        defuser = new Raum("ACHTUNG! hier ist ein Gegner, er bewacht den Defuser.");
        boost = new Raum("in einem Boostraum, hier ist ein Leistungsboost.");

        // die Ausgänge initialisieren
        start.setzeAusgaenge(null, nothing, null, null,null,null);
        nothing.setzeAusgaenge(enemy1, enemy2, bomb, start,boost,null);
        enemy1.setzeAusgaenge(null, null, nothing, key,null,null);
        key.setzeAusgaenge(null, enemy1, null, null,null,bomb);
        bomb.setzeAusgaenge(nothing, weapon1, null, null,null,null);
        weapon1.setzeAusgaenge(null, boost, null, bomb,null,null);
        boost.setzeAusgaenge(null, null, null, null,null,boost);
        enemy2.setzeAusgaenge(weapon2, defuser, null, nothing,null,null);
        weapon2.setzeAusgaenge(null, null, enemy2, null,null,null);
        bomb.setzeAusgaenge(null, null, null, enemy2,key,null);
    }

    /**
     * Die Hauptmethode zum Spielen. Läuft bis zum Ende des Spiels
     * in einer Schleife.
     */
    public void spielen() 
    {            
        willkommenstextAusgeben();

        // Die Hauptschleife. Hier lesen wir wiederholt Befehle ein
        // und führen sie aus, bis das Spiel beendet wird.

        boolean beendet = false;
        while (! beendet) {
            Befehl befehl = parser.liefereBefehl();
            beendet = verarbeiteBefehl(befehl);
        }
        System.out.println("Danke für dieses Spiel. Auf Wiedersehen.");
    }

    /**
     * Einen Begrüßungstext für den Spieler ausgeben.
     */
    private void willkommenstextAusgeben()
    {
        System.out.println();
        System.out.println("Willkommen zu Zuul!");
        System.out.println("Zuul ist ein neues, unglaublich langweiliges Spiel.");
        System.out.println("Tippen sie 'help', wenn Sie Hilfe brauchen.");
        System.out.println();

        rauminfoAusgeben();
    }

    /**
     * Verarbeite einen gegebenen Befehl (führe ihn aus).
     * @param befehl Der zu verarbeitende Befehl.
     * @return 'true', wenn der Befehl das Spiel beendet, 'false' sonst.
     */
    private boolean verarbeiteBefehl(Befehl befehl) 
    {
        boolean moechteBeenden = false;

        if(befehl.istUnbekannt()) {
            System.out.println("Ich weiß nicht, was Sie meinen...");
            return false;
        }

        String befehlswort = befehl.gibBefehlswort();
        if (befehlswort.equals("help"))
            hilfstextAusgeben();
        else if (befehlswort.equals("go"))
            wechsleRaum(befehl);
        else if (befehlswort.equals("quit")) {
            moechteBeenden = beenden(befehl);
        }
        return moechteBeenden;
    }

    // Implementierung der Benutzerbefehle:

    /**
     * Gib Hilfsinformationen aus.
     * Hier geben wir eine etwas alberne und unklare Beschreibung
     * aus, sowie eine Liste der Befehlswörter.
     */
    private void hilfstextAusgeben() 
    {
        System.out.println("Sie haben sich verlaufen. Sie sind allein.");
        System.out.println("Sie irren auf dem Unigelände herum.");
        System.out.println();
        System.out.println("Ihnen stehen folgende Befehle zur Verfügung:");
        System.out.println("   go quit help");
    }

    /**
     * Versuche, den Raum zu wechseln. Wenn es einen Ausgang gibt,
     * wechsele in den neuen Raum, ansonsten gib eine Fehlermeldung
     * aus.
     */
    private void wechsleRaum(Befehl befehl) 
    {
        if(!befehl.hatZweitesWort()) {
            // Gibt es kein zweites Wort, wissen wir nicht, wohin...
            System.out.println("Wohin möchten Sie gehen?");
            return;
        }

        String richtung = befehl.gibZweitesWort();

        // Wir versuchen den Raum zu verlassen.
        Raum naechsterRaum = aktuellerRaum.gibAusgang(richtung);

        if (naechsterRaum == null) {
            System.out.println("In diese Richtung geht es nicht weiter!");
        }
        else {
            aktuellerRaum = naechsterRaum;
            rauminfoAusgeben();
        }
    }

    public void rauminfoAusgeben(){
        System.out.println("Sie sind " + aktuellerRaum.gibBeschreibung());
        System.out.print("Ausgänge: ");
        if(aktuellerRaum.gibAusgang("north") != null)
            System.out.print("north ");
        if(aktuellerRaum.gibAusgang("east") != null)
            System.out.print("east ");
        if(aktuellerRaum.gibAusgang("south") != null)
            System.out.print("south ");
        if(aktuellerRaum.gibAusgang("west") != null)
            System.out.print("west ");
        if(aktuellerRaum.gibAusgang("down") != null)
            System.out.print("down ");
        if(aktuellerRaum.gibAusgang("up") != null)
            System.out.print("up ");  

        System.out.println();
    }

    /**
     * "quit" wurde eingegeben. Überprüfe den Rest des Befehls,
     * ob das Spiel wirklich beendet werden soll.
     * @return 'true', wenn der Befehl das Spiel beendet, 'false' sonst.
     */
    private boolean beenden(Befehl befehl) 
    {
        if(befehl.hatZweitesWort()) {
            System.out.println("Was soll beendet werden?");
            return false;
        }
        else {
            return true;  // Das Spiel soll beendet werden.
        }
    }

}
