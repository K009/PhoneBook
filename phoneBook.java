package com.codewithdom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class phoneBook extends JFrame implements ActionListener {

    private JPanel p = new JPanel();
    private JButton show;
    private JButton add;
    private JButton addD;
    private JButton delete;
    private JButton deleteD;
    private JButton search;
    private JButton searchD;
    private JButton exit;
    private JTextField TFmail, TFsurname, TFphone_number;
    private int rows = 4;

    private static Connection connect() {
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

    private static Connection c = connect();

    private static void createTable(Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        stmt.executeUpdate("CREATE TABLE tabel(name VARCHAR(64), mail VARCHAR(64), nr INTEGER);");
    }

    private static void fillTableWithData(Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        stmt.executeUpdate(" INSERT INTO tabel VALUES ('Alicja', 'alicja20@gmail.com', '123456789');");
        stmt.executeUpdate(" INSERT INTO tabel VALUES ('Marek', 'marek253@onet.pl', '754938125');");
        stmt.executeUpdate(" INSERT INTO tabel VALUES ('Anna', 'ania@interia.pl', '674209678');");
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

    private void print() throws SQLException {
        JFrame f1 = new JFrame("Showing database");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rows, 3));

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM tabel");
        while(rs.next()) {
            String name = rs.getString("name");
            String mail = rs.getString("mail");
            String nr = rs.getString("nr");
            panel.add(new JLabel(name));
            panel.add(new JLabel(mail));
            panel.add(new JLabel(nr));
        }
        rs.close();
        stmt.close();

        f1.getContentPane().add(panel);
        f1.setSize(500,300);
        f1.setLocationRelativeTo(null);
        f1.setVisible(true);
    }

    private void add(){
        JFrame f1 = new JFrame("Adding a new record");
        JPanel panel = new JPanel();
        JLabel mail = new JLabel("Mail: ");
        JLabel surname = new JLabel("Surname: ");
        JLabel nr_tel = new JLabel("Phone number: ");

        TFmail = new JTextField("mail address");
        TFsurname = new JTextField("surname");
        TFphone_number = new JTextField("phone number");
        addD = new JButton("Add a new record");
        addD.addActionListener(this);
        panel.add(surname);
        panel.add(TFsurname);
        panel.add(mail);
        panel.add(TFmail);
        panel.add(mail);
        panel.add(TFmail);
        panel.add(nr_tel);
        panel.add(TFphone_number);
        panel.add(addD);

        f1.getContentPane().add(panel);
        f1.setSize(500,100);
        f1.setLocationRelativeTo(null);
        f1.setVisible(true);
    }

    private void search(){
        JFrame f1 = new JFrame("Search for record");
        JPanel panel = new JPanel();
        JLabel surname = new JLabel("Enter the person's surname: ");

        TFsurname = new JTextField("surname");
        searchD = new JButton("Search:");
        searchD.addActionListener(this);
        panel.add(surname);
        panel.add(TFsurname);
        panel.add(searchD);

        f1.getContentPane().add(panel);
        f1.setSize(500,100);
        f1.setLocationRelativeTo(null);
        f1.setVisible(true);
    }

    private void searchD() throws SQLException {
        JFrame f1 = new JFrame("Searching for a record");
        JPanel panel = new JPanel();
        String surname = this.TFsurname.getText();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM tabel WHERE name="+"'"+surname+"'");

        while(rs.next()) {
            String name = rs.getString("name");
            String mail = rs.getString("mail");
            String nr = rs.getString("nr");
            panel.add(new JLabel(name));
            panel.add(new JLabel(mail));
            panel.add(new JLabel(nr));
        }
        f1.getContentPane().add(panel);
        f1.setSize(500,100);
        f1.setLocationRelativeTo(null);
        f1.setVisible(true);
    }
    private void delete() {
        JFrame f1 = new JFrame("Delete a record");
        JPanel panel = new JPanel();

        JLabel surname = new JLabel("Enter the person's surname: ");
        TFsurname = new JTextField("surname");
        deleteD = new JButton("Delete:");
        deleteD.addActionListener(this);
        panel.add(surname);
        panel.add(TFsurname);
        panel.add(deleteD);

        f1.getContentPane().add(panel);
        f1.setSize(500,100);
        f1.setLocationRelativeTo(null);
        f1.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == show) {
            show.setBackground(Color.GREEN);
            try {
                print();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (source == add) {
            add.setBackground(Color.GREEN);
            add();
        }
        else if(source == addD) {
            addD.setBackground(Color.GREEN);
            String surname = this.TFsurname.getText();
            System.out.println(surname);
            String mail = this.TFmail.getText();
            System.out.println(mail);
            int Inr_tel = Integer.parseInt(this.TFphone_number.getText());
            System.out.println(Inr_tel);

            try {
                Statement stmt = c.createStatement();
                stmt.executeUpdate("INSERT INTO tabel (name,mail,nr) VALUES" +  "('"+surname+"','"+mail+"','"+Inr_tel+"')");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            rows++;
        }

        else if (source == delete) {
            delete.setBackground(Color.GREEN);
            delete();
        }
        else if(source == deleteD) {
            deleteD.setBackground(Color.GREEN);
            String surname = this.TFsurname.getText();
            try {
                Statement stmt = c.createStatement();
                System.out.println(surname);
                int result = stmt.executeUpdate("DELETE FROM tabel WHERE name="+"'"+surname+"'");
                System.out.println(result);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            rows--;
        }

        else if (source == search){
            search.setBackground(Color.GREEN);
            search();
        }
        else if(source == searchD) {
            searchD.setBackground(Color.GREEN);
            try {
                searchD();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        else if(source == exit) {
            exit.setBackground(Color.RED);
            System.exit(0);
        }
    }
    public static void main(String[] args){
        //if you're running this program for the first time you should firstly run "initDB(c);" to initialize a database
        phoneBook k = new phoneBook();
        k.setSize(500,300);
        k.setLocationRelativeTo(null);
        k.setVisible(true);
    }
}
