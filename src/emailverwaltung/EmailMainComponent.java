package emailverwaltung;


import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class EmailMainComponent {
    private JPanel rootPanel;
    private JTextField textFieldId;
    private JTextField textFieldVorname;
    private JTextField textFieldNachname;
    private JTextField textFieldEmail;
    private JButton suchenButton;
    private JButton buttonFirst;
    private JButton buttonBack;
    private JButton neuButton;
    private JButton sichernButton;
    private JButton löschenButton;
    private JButton buttonLast;
    private JButton buttonNext;

    private EmailKontaktDAO dao;

    public EmailMainComponent() {
        this.dao=new EmailKontaktDAO();
        buttonFirst.addActionListener(e -> first());
        sichernButton.addActionListener(e -> sichern());
        suchenButton.addActionListener(e -> suchen());
    }

    private void suchen() {
        int id=Integer.parseInt(textFieldId.getText());

        // Wir rufen in der DAO die select-Methode auf
        // die liefert uns einen fertigen EmailKontakt,
        // den wir nur noch anzeigen müssem
        EmailKontakt kontakt=dao.select(id);

        textFieldEmail.setText(kontakt.getEmail());
        textFieldVorname.setText(kontakt.getVorname());
        textFieldNachname.setText(kontakt.getNachname());
    }

    private void sichern() {

    }



    private void first() {
        EmailKontakt emailKontakt=this.dao.first();
        textFieldEmail.setText(emailKontakt.getEmail());
        textFieldId.setText(Integer.toString(emailKontakt.getId()));
        textFieldNachname.setText(emailKontakt.getNachname());
        textFieldVorname.setText(emailKontakt.getVorname());

    }





    public JPanel getRootPanel() {
        return rootPanel;
    }
}
