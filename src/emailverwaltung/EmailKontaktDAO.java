package emailverwaltung;

import java.sql.*;

public class EmailKontaktDAO {

    private String url;

    public EmailKontaktDAO() {
        try {
            // Treiber laden
            Class.forName ("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // URL für die Verbindung
        this.url="jdbc:sqlite:emails.db";
    }

    public EmailKontakt select(int id) {
        // Ein erstmal leerer EmailKontakt, in dem die
        // Ergebnisse abgespeichert werden sollen
        EmailKontakt emailKontakt=new EmailKontakt();

        try {
            // Verbindung aufbauen
            Connection conn= DriverManager.getConnection(url);

            // SQL-Statement - Fragezeichen als Platzhalter, das füllen wir gleich
            String sql="select * from Email where id = ?";

            // Statement vorbereiten, mit dem SQL-Befehl
            PreparedStatement stmt=conn.prepareStatement(sql);

            // Tauschen wir das Fragezeichen aus
            // 1 --> das erste Fragezeichen
            // 2 --> damit austauschen
            stmt.setInt(1, id);

            // Das vorbereitete Statement mit dem SQL abschicken
            // Da kommt ein ResultSet zurück, mit der Ergebnistabelle
            // Das ResultSet ermöglicht den Zugriff aus das Ergebnis des SQL-Befehl
            ResultSet rs=stmt.executeQuery();

            // Problem: Das ResultSet springt zeilenweise (also Zeile für Zeile) durch die Ergebnistabelle
            //          Am Anfang ist quasi keine Zeile geladen, d.h. der Zeiger steht
            //          vor der ersten Zeile
            // Lösung: rs.next()
            rs.next();

            // extrahieren aus dem ResultSet rs den Wert in der Spalte nachname
            String name=rs.getString("nachname");
            // und setzen ihn im EmailKontakt ein
            emailKontakt.setNachname(name);

            emailKontakt.setVorname(rs.getString("vorname"));
            emailKontakt.setEmail(rs.getString("email"));


        } catch(Exception e) {
            e.printStackTrace();
        }
        // Ergebnis an die GUI zurückliefern
        return emailKontakt;
    }

    public EmailKontakt first() {

        Connection conn=null;
        try {
            // Connection herstellen
            conn= DriverManager.getConnection(this.url);

            // SQL Befehl um den ersten Datensazu zu finden
            String sql="select * from Email where id=(select min(id) from email)";

            // Aus dem SQL-Befehl ein Statement machen
            PreparedStatement stmt=conn.prepareStatement(sql);

            // Statement abschicken, die Ergebnistabelle steht in rs
            ResultSet rs=stmt.executeQuery();

            // In der Ergebnistabelle auf den ersten Datensatz springen, vorher sind wir quasi bei 0
            rs.next();

            // Erstellen eines leeren EmailKontakts
            EmailKontakt emailKontakt=new EmailKontakt();

            // Email des Emailkontakts wird zum Wert der in rs in der Spalte email steht
            emailKontakt.setEmail(rs.getString("email"));
            emailKontakt.setNachname(rs.getString("nachname"));
            emailKontakt.setVorname(rs.getString("vorname"));
            emailKontakt.setId(rs.getInt("id"));

            // Connection schließen
            conn.close();

            // Ergebnis zurück zur GUI
            return emailKontakt;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    public EmailKontakt last() {
        return null;
    }

    public EmailKontakt next(EmailKontakt emailKontakt) {
        return null;
    }

    public EmailKontakt previous(EmailKontakt emailKontakt) {
        return null;
    }

    public void delete(EmailKontakt emailKontakt) {

    }

    public void insert(EmailKontakt emailKontakt) {

    }

    public void update(EmailKontakt emailKontakt) {

    }


}
