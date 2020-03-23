package com.codewithdom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class phoneBook extends JFrame implements ActionListener {

    JPanel p = new JPanel();
    private JButton show;
    private JButton add;
    private JButton addD;
    private JButton delete;
    private JButton deleteD;
    private JButton search;
    private JButton searchD;
    private JButton save;
    private JButton exit;
    JTextField TFmail, TFsurname, TFphone_number;
    int rows = 5;

    public static Connection connect() {
        try {
            Class.forName("org.hsqldb.jdbcDriver" );
        } catch (Exception e) {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return null;
        }

        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:hsqldb:file:testdb", "SA", "");
        } catch (SQLException e) {
            System.err.println("ERROR: cannot conect to database testdb.");
            e.printStackTrace();
        }
        return c;
    }

    Connection c = connect();

    public static void createTable(Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        stmt.executeUpdate("CREATE TABLE tabelkaa(name VARCHAR(64), mail VARCHAR(64), nr INTEGER);");
    }

    public static void fillTableWithData(Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        stmt.executeUpdate(" INSERT INTO tabelkaa VALUES ('Alicja', 'alicja20@gmail.com', '123456789');");
        stmt.executeUpdate(" INSERT INTO tabelkaa VALUES ('Marek', 'marek253@onet.pl', '754938125');");
        stmt.executeUpdate(" INSERT INTO tabelkaa VALUES ('Anna', 'ania@interia.pl', '674209678');");
    }

    public static void initDB(Connection c) throws SQLException { //if u want to initialize a new dataBase
        createTable(c);
        fillTableWithData(c);
    }

    public phoneBook(){
    super("Phone Book - Menu");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        p.setLayout(new GridLayout(3, 2));
         add = new JButton("Add a new record");
        add.addActionListener(this);
        p.add(add);
         delete = new JButton("Delete record");
        delete.addActionListener(this);
        p.add(delete);
         search = new JButton("Search for record");
        search.addActionListener(this);
        p.add(search);
         show = new JButton("Print base");
        show.addActionListener(this);
        p.add(show);
        exit = new JButton("Exit");
        exit.addActionListener(this);
        p.add(exit);

        this.getContentPane().add(p);
        this.pack();
    }

    public void wyswietl() throws SQLException {
        JFrame f1 = new JFrame("Showing database");
        JPanel panelik = new JPanel();
        panelik.setLayout(new GridLayout(rows, 3));

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM tabelkaa");
        while(rs.next()) {
            String name = rs.getString("name");
            String mail = rs.getString("mail");
            String nr = rs.getString("nr");
            panelik.add(new JLabel(name));
            panelik.add(new JLabel(mail));
            panelik.add(new JLabel(nr));
        }
        rs.close();
        stmt.close();

        f1.getContentPane().add(panelik);
        f1.setSize(500,300);
        f1.setLocationRelativeTo(null);
        f1.setVisible(true);
    }

    public void dodaj(){
        JFrame f1 = new JFrame("Adding a new record");
        JPanel panelik = new JPanel();
        JLabel mail = new JLabel("Mail: ");
        JLabel nazwisko = new JLabel("Surname: ");
        JLabel nr_tel = new JLabel("Phone number: ");

        TFmail = new JTextField("mail address");
        TFsurname = new JTextField("surname");
        TFphone_number = new JTextField("phone number");
        addD = new JButton("Add a new record");
        addD.addActionListener(this);
        panelik.add(nazwisko);
        panelik.add(TFsurname);
        panelik.add(mail);
        panelik.add(TFmail);
        panelik.add(mail);
        panelik.add(TFmail);
        panelik.add(nr_tel);
        panelik.add(TFphone_number);
        panelik.add(addD);

        f1.getContentPane().add(panelik);
        f1.setSize(500,100);
        f1.setLocationRelativeTo(null);
        f1.setVisible(true);
    }

    public void wyszukaj(){
        JFrame f1 = new JFrame("Search for record");
        JPanel panelik = new JPanel();
        JLabel nazwisko = new JLabel("Enter the person's surname: ");

        TFsurname = new JTextField("surname");
        searchD = new JButton("Search:");
        searchD.addActionListener(this);
        panelik.add(nazwisko);
        panelik.add(TFsurname);
        panelik.add(searchD);

        f1.getContentPane().add(panelik);
        f1.setSize(500,100);
        f1.setLocationRelativeTo(null);
        f1.setVisible(true);
    }

    public void Iwyszukaj() throws SQLException {
        JFrame f1 = new JFrame("Searching for a record");
        JPanel panelik = new JPanel();
        String Inazwisko = this.TFsurname.getText();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM tabelkaa WHERE name="+"'"+Inazwisko+"'");

        while(rs.next()) { //wczytanie kolejnego rekordu z bazy
            String name = rs.getString("name");
            String mail = rs.getString("mail");
            String nr = rs.getString("nr");
            panelik.add(new JLabel(name));
            panelik.add(new JLabel(mail));
            panelik.add(new JLabel(nr));
        }
        f1.getContentPane().add(panelik);
        f1.setSize(500,100);
        f1.setLocationRelativeTo(null);
        f1.setVisible(true);
    }
    public void usun() throws SQLException {
        JFrame f1 = new JFrame("Delete a record");
        JPanel panelik = new JPanel();

        JLabel nazwisko = new JLabel("Enter the person's surname: ");
        TFsurname = new JTextField("surname");
        deleteD = new JButton("Delete:");
        deleteD.addActionListener(this);
        panelik.add(nazwisko);
        panelik.add(TFsurname);
        panelik.add(deleteD);

        f1.getContentPane().add(panelik);
        f1.setSize(500,100);
        f1.setLocationRelativeTo(null);
        f1.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == show) {
            show.setBackground(Color.GREEN);
            try {
                wyswietl();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (source == add) {
            add.setBackground(Color.GREEN);
            dodaj();
        }
        else if(source == addD) {
            addD.setBackground(Color.GREEN);
            String Inazwisko = this.TFsurname.getText();
            System.out.println(Inazwisko);
            String Imail = this.TFmail.getText();
            System.out.println(Imail);
            int Inr_tel = Integer.valueOf(this.TFphone_number.getText());
            System.out.println(Inr_tel);

            try {
                Statement stmt = c.createStatement();
                int result = 0;
                result = stmt.executeUpdate("INSERT INTO tabelkaa (name,mail,nr) VALUES" +  "('"+Inazwisko+"','"+Imail+"','"+Inr_tel+"')");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            rows++;
        }

        else if (source == save)
            save.setBackground(Color.GREEN);

        else if (source == delete) {
            delete.setBackground(Color.GREEN);
            try {
                usun();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        else if(source == deleteD) {
            deleteD.setBackground(Color.GREEN);
            String Inazwisko = this.TFsurname.getText();
            try {
                Statement stmt = c.createStatement();
                System.out.println(Inazwisko);
                int result = 0;
                result = stmt.executeUpdate("DELETE FROM tabelkaa WHERE name="+"'"+Inazwisko+"'");
                System.out.println(result);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            rows--;
        }

        else if (source == search){
            search.setBackground(Color.GREEN);
            wyszukaj();
        }
        else if(source == searchD) {
            searchD.setBackground(Color.GREEN);
            try {
                Iwyszukaj();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        else if(source == exit) {
            exit.setBackground(Color.RED);
            System.exit(0);
        }
    }
    public static void main(String[] args) throws SQLException {
	    phoneBook k = new phoneBook();
        k.setSize(500,300);
        k.setLocationRelativeTo(null);
        k.setVisible(true);
    }
}
