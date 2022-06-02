package emailverwaltung;


import javax.swing.*;

public class EmailMainComponent {
    private JPanel rootPanel;
    private JTextField textFieldId;
    private JTextField textFieldVorname;
    private JTextField textFieldNachname;
    private JTextField textFieldEmail;
    private JButton suchenButton;
    private JButton buttonFirst;
    private JButton buttonBack;
    private JButton anlegenButton;
    private JButton aendernButton;
    private JButton löschenButton;
    private JButton buttonLast;
    private JButton buttonNext;

    // zentral gespeicherte Referenz auf die DAO
    private EmailKontaktDAO dao;

    public EmailMainComponent() {
        // dao erstellen
        this.dao=new EmailKontaktDAO();

        // ActionListener registrieren
        // ActionListener --> hört zu ob ein Knopf gedrückt wird
        buttonFirst.addActionListener(e -> first());
        buttonLast.addActionListener(e -> last());
        aendernButton.addActionListener(e -> aendern());
        suchenButton.addActionListener(e -> suchen());
        buttonBack.addActionListener(e -> back());
        löschenButton.addActionListener(e -> delete());
        anlegenButton.addActionListener(e -> insert());
        buttonNext.addActionListener(e -> next());
        buttonBack.addActionListener(e -> prev());
    }

    private void next() {
        EmailKontakt current=new EmailKontakt();
        current.setId(Integer.parseInt(textFieldId.getText()));
        EmailKontakt next=this.dao.next(current);
        textFieldEmail.setText(next.getEmail());
        textFieldId.setText(Integer.toString(next.getId()));
        textFieldNachname.setText(next.getNachname());
        textFieldVorname.setText(next.getVorname());
    }

    private void prev() {
        EmailKontakt current=new EmailKontakt();
        current.setId(Integer.parseInt(textFieldId.getText()));
        EmailKontakt next=this.dao.previous(current);
        textFieldEmail.setText(next.getEmail());
        textFieldId.setText(Integer.toString(next.getId()));
        textFieldNachname.setText(next.getNachname());
        textFieldVorname.setText(next.getVorname());
    }

    private void aendern() {
        EmailKontakt toUpdate=new EmailKontakt();
        toUpdate.setEmail(textFieldEmail.getText());
        toUpdate.setVorname(textFieldVorname.getText());
        toUpdate.setNachname(textFieldNachname.getText());
        toUpdate.setId(Integer.parseInt(textFieldId.getText()));
        dao.insert(toUpdate);
    }

    private void insert() {
        EmailKontakt toStore=new EmailKontakt();
        toStore.setEmail(textFieldEmail.getText());
        toStore.setVorname(textFieldVorname.getText());
        toStore.setNachname(textFieldNachname.getText());
        dao.insert(toStore);
        last();
    }

    private void last() {
        EmailKontakt emailKontakt=this.dao.last();
        textFieldEmail.setText(emailKontakt.getEmail());
        textFieldId.setText(Integer.toString(emailKontakt.getId()));
        textFieldNachname.setText(emailKontakt.getNachname());
        textFieldVorname.setText(emailKontakt.getVorname());
    }

    private void delete() {
        dao.delete(Integer.parseInt(textFieldId.getText()));
        textFieldEmail.setText("");
        textFieldId.setText("");
        textFieldNachname.setText("");
        textFieldVorname.setText("");
    }

    private void back() {
    }

    private void suchen() {
        // textFieldId auslesen und direkt zu einem Int konvertieren
        int id=Integer.parseInt(textFieldId.getText());


        // Wir rufen in der DAO die select-Methode auf
        // die liefert uns einen fertigen EmailKontakt,
        // den wir nur noch anzeigen müssem
        EmailKontakt kontakt=dao.select(id);

        // Email aus dem geladenen EmailKontakt holen und in das Textfeld email setzen
        textFieldEmail.setText(kontakt.getEmail());
        textFieldVorname.setText(kontakt.getVorname());
        textFieldNachname.setText(kontakt.getNachname());
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
