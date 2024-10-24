package travel.management.system;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Signup extends JFrame implements ActionListener {

    private JPanel contentPane;
    public JTextField textField; // Username
    public JTextField textField_1; // Name
    public JPasswordField passwordField; // Password
    public JTextField textField_3; // Security Answer
    public JButton b1; // Create button
    public JButton b2; // Back button
    public JComboBox<String> comboBox; // Security Question ComboBox

    public static void main(String[] args) {
        new Signup().setVisible(true);
    }

    public Signup() {
        setBounds(600, 250, 700, 406);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(null);

        // UI Components
        JLabel lblUsername = new JLabel("Username :");
        lblUsername.setForeground(Color.DARK_GRAY);
        lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblUsername.setBounds(99, 86, 92, 26);
        contentPane.add(lblUsername);

        JLabel lblName = new JLabel("Name :");
        lblName.setForeground(Color.DARK_GRAY);
        lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblName.setBounds(99, 123, 92, 26);
        contentPane.add(lblName);

        JLabel lblPassword = new JLabel("Password :");
        lblPassword.setForeground(Color.DARK_GRAY);
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPassword.setBounds(99, 160, 92, 26);
        contentPane.add(lblPassword);

        JLabel lblAnswer = new JLabel("Answer :");
        lblAnswer.setForeground(Color.DARK_GRAY);
        lblAnswer.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblAnswer.setBounds(99, 234, 92, 26);
        contentPane.add(lblAnswer);

        comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Your NickName?", "Your Lucky Number?",
                "Your child SuperHero?", "Your childhood Name ?"}));
        comboBox.setBounds(265, 202, 148, 20);
        contentPane.add(comboBox);

        JLabel lblSecurityQuestion = new JLabel("Security Question :");
        lblSecurityQuestion.setForeground(Color.DARK_GRAY);
        lblSecurityQuestion.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblSecurityQuestion.setBounds(99, 197, 140, 26);
        contentPane.add(lblSecurityQuestion);

        ImageIcon c1 = new ImageIcon(ClassLoader.getSystemResource("Travel/Management/System/icons/signup.png"));
        Image i1 = c1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon i2 = new ImageIcon(i1);

        JLabel l6 = new JLabel(i2);
        l6.setBounds(460, 70, 200, 200);
        add(l6);

        textField = new JTextField();
        textField.setBounds(265, 91, 148, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(265, 128, 148, 20);
        contentPane.add(textField_1);

        passwordField = new JPasswordField(); // Password field
        passwordField.setColumns(10);
        passwordField.setBounds(265, 165, 148, 20);
        contentPane.add(passwordField);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(265, 239, 148, 20);
        contentPane.add(textField_3);

        b1 = new JButton("Create");
        b1.addActionListener(this);
        b1.setFont(new Font("Tahoma", Font.BOLD, 13));
        b1.setBounds(140, 289, 100, 30);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        contentPane.add(b1);

        b2 = new JButton("Back");
        b2.addActionListener(this);
        b2.setFont(new Font("Tahoma", Font.BOLD, 13));
        b2.setBounds(300, 289, 100, 30);
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        contentPane.add(b2);

        JPanel panel = new JPanel();
        panel.setForeground(new Color(34, 139, 34));
        panel.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 0), 2), "Create-Account",
                TitledBorder.LEADING, TitledBorder.TOP, null, new Color(34, 139, 34)));
        panel.setBounds(31, 30, 640, 310);
        panel.setBackground(Color.WHITE);
        contentPane.add(panel);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            Conn con = new Conn();

            if (ae.getSource() == b1) {
                String username = textField.getText();
                String name = textField_1.getText();
                String password = new String(passwordField.getPassword());
                String question = (String) comboBox.getSelectedItem();
                String answer = textField_3.getText();

                // Validation checks
                if (!isUsernameUnique(con, username)) {
                    JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different one.");
                    return;
                }

                if (!isValidName(name)) {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty and must contain only letters.");
                    return;
                }

                if (!isValidUsername(username)) {
                    JOptionPane.showMessageDialog(null, "Username must be at least 3 characters long and can contain letters, numbers, and underscores.");
                    return;
                }

                if (!isValidPassword(password)) {
                    JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long and contain at least one special character.");
                    return;
                }

                if (answer.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please provide an answer to the security question.");
                    return;
                }

                String sql = "INSERT INTO account(username, name, password, question, answer) VALUES(?, ?, ?, ?, ?)";
                PreparedStatement st = con.c.prepareStatement(sql);

                st.setString(1, username);
                st.setString(2, name);
                st.setString(3, password);
                st.setString(4, question);
                st.setString(5, answer);

                int i = st.executeUpdate();
                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "Account Created Successfully ");
                }

                // Clear fields after successful signup
                textField.setText("");
                textField_1.setText("");
                passwordField.setText("");
                textField_3.setText("");
            }

            if (ae.getSource() == b2) {
                this.setVisible(false);
                new Login().setVisible(true);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private boolean isUsernameUnique(Conn con, String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM account WHERE username = ?";
        PreparedStatement st = con.c.prepareStatement(sql);
        st.setString(1, username);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count == 0; // Return true if no existing username
        }
        return false;
    }

    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("[a-zA-Z ]+");
    }

    private boolean isValidUsername(String username) {
        // Check if the username is valid (at least 3 characters long, letters, numbers, or underscores)
        return username != null && username.matches("^[a-zA-Z0-9_]{3,}$");
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
        return hasSpecialChar;
    }
}
