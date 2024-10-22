package travel.management.system;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

public class AddCustomer extends JFrame {

	private JPanel contentPane;
	private JTextField t1; // ID
	private JTextField t2; // Name
	private JTextField t3; // Country
	private JTextField t5; // Permanent Address
	private JTextField t6; // Phone
	private JTextField t8; // Email
	private JComboBox<String> comboBox; // ID type
	private JRadioButton r1; // Male
	private JRadioButton r2; // Female

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				AddCustomer frame = new AddCustomer("");
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public AddCustomer(String username) throws SQLException {
		setBounds(580, 220, 850, 550);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblName = new JLabel("NEW CUSTOMER FORM");
		lblName.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		lblName.setBounds(118, 11, 260, 53);
		contentPane.add(lblName);

		JLabel l3 = new JLabel("Username :");
		l3.setBounds(35, 70, 200, 14);
		contentPane.add(l3);

		JTextField t7 = new JTextField();
		t7.setBounds(271, 70, 150, 20);
		contentPane.add(t7);
		t7.setColumns(10);

		JLabel lblId = new JLabel("ID Type :");
		lblId.setBounds(35, 110, 200, 14);
		contentPane.add(lblId);

		comboBox = new JComboBox<>(new String[]{"Passport", "Aadhar Card", "Voter Id", "Driving license"});
		comboBox.setBounds(271, 110, 150, 20);
		contentPane.add(comboBox);

		JLabel l2 = new JLabel("Number :");
		l2.setBounds(35, 150, 200, 14);
		contentPane.add(l2);

		t1 = new JTextField(); // ID
		t1.setBounds(271, 150, 150, 20);
		contentPane.add(t1);
		t1.setColumns(10);

		JLabel lblName_1 = new JLabel("Name :");
		lblName_1.setBounds(35, 190, 200, 14);
		contentPane.add(lblName_1);

		t2 = new JTextField();
		t2.setBounds(271, 190, 150, 20);
		contentPane.add(t2);
		t2.setColumns(10);

		JLabel lblGender = new JLabel("Gender :");
		lblGender.setBounds(35, 230, 200, 14);
		contentPane.add(lblGender);

		r1 = new JRadioButton("Male");
		r1.setFont(new Font("Raleway", Font.BOLD, 14));
		r1.setBackground(Color.WHITE);
		r1.setBounds(271, 230, 80, 12);
		contentPane.add(r1);

		r2 = new JRadioButton("Female");
		r2.setFont(new Font("Raleway", Font.BOLD, 14));
		r2.setBackground(Color.WHITE);
		r2.setBounds(350, 230, 100, 12);
		contentPane.add(r2);

		JLabel lblCountry = new JLabel("Country :");
		lblCountry.setBounds(35, 270, 200, 14);
		contentPane.add(lblCountry);

		t3 = new JTextField();
		t3.setBounds(271, 270, 150, 20);
		contentPane.add(t3);
		t3.setColumns(10);

		JLabel lblReserveRoomNumber = new JLabel("Permanent Address :");
		lblReserveRoomNumber.setBounds(35, 310, 200, 14);
		contentPane.add(lblReserveRoomNumber);

		t5 = new JTextField();
		t5.setBounds(271, 310, 150, 20);
		contentPane.add(t5);
		t5.setColumns(10);

		JLabel lblCheckInStatus = new JLabel("Phone :");
		lblCheckInStatus.setBounds(35, 350, 200, 14);
		contentPane.add(lblCheckInStatus);

		t6 = new JTextField();
		t6.setBounds(271, 350, 150, 20);
		contentPane.add(t6);
		t6.setColumns(10);

		JLabel lblDeposite = new JLabel("Email :");
		lblDeposite.setBounds(35, 390, 200, 14);
		contentPane.add(lblDeposite);

		t8 = new JTextField();
		t8.setBounds(271, 390, 150, 20);
		contentPane.add(t8);
		t8.setColumns(10);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Conn c = new Conn();
					String radio = r1.isSelected() ? "Male" : "Female";

					String s9 = t7.getText(); // username
					String s1 = (String) comboBox.getSelectedItem(); // ID type
					String s2 = t1.getText(); // ID number
					String s3 = t2.getText(); // Name
					String s4 = radio; // Gender
					String s5 = t3.getText(); // Country
					String s7 = t5.getText(); // Address
					String s8 = t6.getText(); // Phone
					String s10 = t8.getText(); // Email

					// Validation
					if (s9.isEmpty() || s3.isEmpty() || s5.isEmpty() || s7.isEmpty() || s8.isEmpty() || s10.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill all the fields");
						return;
					}

					// Username validation
					ResultSet rs = c.s.executeQuery("select * from customer where username = '" + s9 + "'");
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "Username already exists");
						return;
					}

					// Name validation (only letters allowed)
					if (!s3.matches("[a-zA-Z ]+")) {
						JOptionPane.showMessageDialog(null, "Name can only contain letters");
						return;
					}

					// Phone number validation
					if (!s8.matches("\\d{10}")) {
						JOptionPane.showMessageDialog(null, "Invalid phone number. Please enter 10 digits");
						return;
					}

					// Email validation
					Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@gmail\\.com$", Pattern.CASE_INSENSITIVE);
					Matcher matcher = pattern.matcher(s10);
					if (!matcher.find()) {
						JOptionPane.showMessageDialog(null, "Invalid email address");
						return;
					}

					// Insert into database
					String q1 = "INSERT INTO customer (username, id_type, number, name, gender, country, address, phone, email) "
							+ "VALUES('" + s9 + "','" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "','" + s7 + "','" + s8 + "','" + s10 + "')";
					c.s.executeUpdate(q1);

					JOptionPane.showMessageDialog(null, "Customer Added Successfully");
					setVisible(false);
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				} catch (NumberFormatException s) {
					JOptionPane.showMessageDialog(null, "Please enter a valid Number");
				}
			}
		});
		btnAdd.setBounds(100, 430, 120, 30);
		btnAdd.setBackground(Color.BLACK);
		btnAdd.setForeground(Color.WHITE);
		contentPane.add(btnAdd);

		JButton btnExit = new JButton("Back");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnExit.setBounds(260, 430, 120, 30);
		btnExit.setBackground(Color.BLACK);
		btnExit.setForeground(Color.WHITE);
		contentPane.add(btnExit);
	}
}
