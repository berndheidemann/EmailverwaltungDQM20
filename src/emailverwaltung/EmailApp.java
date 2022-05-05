package emailverwaltung;

import javax.swing.*;
import java.awt.*;

public class EmailApp extends JFrame {

    public EmailApp() {
        this.setPreferredSize(new Dimension(600, 230));
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Emailverwaltung");
        EmailMainComponent comp=new EmailMainComponent();
        this.setContentPane(comp.getRootPanel());
        pack();
    }


    public static void main(String[] args) {
        new EmailApp();
    }



}
