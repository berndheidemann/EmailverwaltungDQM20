package emailverwaltung;

import java.sql.*;

public class EmailKontaktDAO {

    // Wir brauchen in jeder Methode immer die URL für die Connection,
    // daher hier abspeichern
    private String url;

    // Konstruktor
    // beim Erzeugen der DAO wird das hier aufgerufen
    public EmailKontaktDAO() {
            // Treiber laden
        try {
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

        try {

            // Verbindung aufbauen
            Connection conn = DriverManager.getConnection(url);

            // ab hier --> conn ist die Verbindung zur DB

            // SQL-Statement - Fragezeichen als Platzhalter, das füllen wir gleich
            String sql = "select * from Email where id = ?";

            // Statement vorbereiten, mit dem SQL-Befehl
            // Statement ist das, was wir dann an die Datenbank schicken.
            // Kapselt den SQL-Befehl
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Tauschen wir das Fragezeichen aus

            // select * from Email where id = ?
            // 1 --> das erste Fragezeichen im SQL-Befehl austauschen

            // stmt.setString(1, "Foo") ---> Einen String setzen, inkl. Hochkommata
            // stmt.setInt(1, 42) --> setzen der Zahl 42, ohne Hochkommata
            stmt.setInt(1, id);
            // SQL ist jetzt, sofern id 3 --> select * from Email where id = 3

            // Mal mit zwei Fragezeichen
            // select * from Email where name like ? or id= ?
            // stmt.setString(1, "%eide%")
            // stmt.setInt(2, 42)
            // --->  select * from Email where name like '%eide' or id = 42


            // Das vorbereitete Statement mit dem SQL abschicken
            // Da kommt ein ResultSet zurück, mit der Ergebnistabelle
            // Das ResultSet ermöglicht den Zugriff aus das Ergebnis des SQL-Befehl
            ResultSet rs = stmt.executeQuery();

            // Problem: Das ResultSet springt zeilenweise (also Zeile für Zeile) durch die Ergebnistabelle
            //          Am Anfang ist quasi keine Zeile geladen, d.h. der Zeiger steht
            //          vor der ersten Zeile
            // Lösung: rs.next()
            rs.next();

            // extrahieren aus dem ResultSet rs den Wert in der Spalte nachname
            // Das ResultSet ist jetzt eine Zeile, die ungefähr so aufgebaut ist

            // Zeilenüberschriften --->   nachname  | vorname  | email
            // Inhalt              --->   Heidemann | Bernd    | a@b.de

            String name = rs.getString("nachname");

            EmailKontakt emailKontakt=new EmailKontakt();

            // und setzen ihn im EmailKontakt ein
            emailKontakt.setNachname(name);

            emailKontakt.setVorname(rs.getString("vorname"));
            emailKontakt.setEmail(rs.getString("email"));

            // Ergebnis an die GUI zurückliefern
            return emailKontakt;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public EmailKontakt first() {

        try {
            // Connection herstellen
            Connection conn= DriverManager.getConnection(this.url);

            // SQL Befehl um den ersten Datensatz zu finden
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
        try {
            Connection conn= DriverManager.getConnection(this.url);
            String sql="select * from Email where id=(select max(id) from email)";
            PreparedStatement stmt=conn.prepareStatement(sql);
            ResultSet rs=stmt.executeQuery();
            rs.next();
            EmailKontakt emailKontakt=new EmailKontakt();
            emailKontakt.setEmail(rs.getString("email"));
            emailKontakt.setNachname(rs.getString("nachname"));
            emailKontakt.setVorname(rs.getString("vorname"));
            emailKontakt.setId(rs.getInt("id"));
            conn.close();
            return emailKontakt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public EmailKontakt next(EmailKontakt emailKontakt) {
        try {
            Connection conn= DriverManager.getConnection(this.url);
            String sql="select * from email where id = (select min(id) from email where id > ?)";
            PreparedStatement stmt=conn.prepareStatement(sql);
            stmt.setInt(1, emailKontakt.getId());
            ResultSet rs=stmt.executeQuery();
            rs.next();
            EmailKontakt nextEmailKontakt=new EmailKontakt();
            nextEmailKontakt.setEmail(rs.getString("email"));
            nextEmailKontakt.setNachname(rs.getString("nachname"));
            nextEmailKontakt.setVorname(rs.getString("vorname"));
            nextEmailKontakt.setId(rs.getInt("id"));
            conn.close();
            return nextEmailKontakt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public EmailKontakt previous(EmailKontakt emailKontakt) {
        try {
            Connection conn= DriverManager.getConnection(this.url);
            String sql="select * from email where id = (select max(id) from email where id < ?)";
            PreparedStatement stmt=conn.prepareStatement(sql);
            stmt.setInt(1, emailKontakt.getId());
            ResultSet rs=stmt.executeQuery();
            rs.next();
            EmailKontakt nextEmailKontakt=new EmailKontakt();
            nextEmailKontakt.setEmail(rs.getString("email"));
            nextEmailKontakt.setNachname(rs.getString("nachname"));
            nextEmailKontakt.setVorname(rs.getString("vorname"));
            nextEmailKontakt.setId(rs.getInt("id"));
            conn.close();
            return nextEmailKontakt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int id) {
        try {

            // Connection aufbauen
            Connection conn =  DriverManager.getConnection(url);

            // SQL-Befehl schreiben
            String sql="delete from Email where id = ?";

            // Statement erzeugen
            PreparedStatement statement = conn.prepareStatement(sql);

            // ggf. die Fragezeichen im Statement austauschen
            statement.setInt(1, id);

            // Statement abschicken
            statement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(EmailKontakt emailKontakt) {
        try {
            Connection conn=DriverManager.getConnection(url);
            String sql="insert into Email (vorname, nachname, email) VALUES(?, ?, ?)";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setString(1, emailKontakt.getVorname());
            statement.setString(2, emailKontakt.getNachname());
            statement.setString(3, emailKontakt.getEmail());
            statement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void update(EmailKontakt emailKontakt) {
        try {
            Connection conn=DriverManager.getConnection(url);
            String sql="update email set vorname=?, nachname=?, email=? where id = ?";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setString(1, emailKontakt.getVorname());
            statement.setString(2, emailKontakt.getNachname());
            statement.setString(3, emailKontakt.getEmail());
            statement.setInt(4, emailKontakt.getId());
            statement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
