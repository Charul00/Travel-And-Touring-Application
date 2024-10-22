package travel.management.system;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class CreatePackage extends JFrame implements ActionListener {

    private Container c;
    public JLabel label;
    private JLabel pid;
    private JLabel pname;
    private JLabel date;
    private JLabel cost;
    private Font f;
    public JTextField tid;
    public JTextField tname;
    public JTextField tdate;
    public JTextField tcost;
    public JButton back;
    public JButton log;
    public JButton save;

    // Database connection constants
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public CreatePackage() {
        initComponents();
    }

    private void initComponents() {
        c = this.getContentPane();
        c.setLayout(null);

        this.setTitle("Package Pane");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f = new Font("", Font.BOLD, 20);
        label = new JLabel("Create Package");
        label.setBounds(150, 20, 200, 30);
        label.setFont(f);
        label.setForeground(Color.black);
        c.add(label);

        pid = new JLabel("PackageId");
        pid.setBounds(20, 70, 100, 30);
        c.add(pid);

        pname = new JLabel("PackageName");
        pname.setBounds(20, 120, 100, 30);
        c.add(pname);

        date = new JLabel("Date");
        date.setBounds(20, 170, 100, 30);
        c.add(date);

        cost = new JLabel("Cost");
        cost.setBounds(20, 220, 100, 30);
        c.add(cost);

        tid = new JTextField();
        tid.setBounds(120, 70, 100, 30);
        c.add(tid);

        tname = new JTextField();
        tname.setBounds(120, 120, 100, 30);
        c.add(tname);

        tdate = new JTextField();
        tdate.setBounds(120, 170, 100, 30);
        c.add(tdate);

        tcost = new JTextField();
        tcost.setBounds(120, 220, 100, 30);
        c.add(tcost);

        back = new JButton("Main Menu");
        back.setBounds(20, 300, 100, 50);
        back.addActionListener(this);
        c.add(back);

        log = new JButton("Log Out");
        log.setBounds(200, 300, 100, 50);
        log.addActionListener(this);
        c.add(log);

        save = new JButton("Save");
        save.setBounds(230, 160, 120, 50);
        save.addActionListener(this);
        c.add(save);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String clickButton = e.getActionCommand();

        if (clickButton.equals(log.getText())) {
            // Navigate to Login
            Login login = new Login();
            login.setVisible(true);
            this.setVisible(false);
        } else if (clickButton.equals(back.getText())) {
            // Navigate to Main Menu (AdminHome could be implemented later)
            // AdminHome ad = new AdminHome();
            // ad.setVisible(true);
            this.setVisible(false);
        } else if (clickButton.equals(save.getText())) {
            // Save package to the database
            if (validateInputs()) {
                createDB();
                JOptionPane.showMessageDialog(null, "New Package Saved");
            }
        }
    }

    private boolean validateInputs() {
        if (tid.getText().isEmpty() || tname.getText().isEmpty() || tdate.getText().isEmpty() || tcost.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void createDB() {
        String query = "INSERT INTO `package` VALUES('" + tid.getText() + "','" + tname.getText() + "','" + tdate.getText() + "','" + tcost.getText() + "')";
        System.out.println(query);

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement st = con.createStatement()) {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connection Established");
            st.execute(query);
            System.out.println("Package created successfully");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "JDBC Driver not found: " + e.getMessage(), "Driver Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new CreatePackage().setVisible(true);
    }
}
