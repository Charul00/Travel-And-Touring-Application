package travel.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.regex.*;

public class UpdateCustomer extends JFrame {
	Connection conn = null;
	PreparedStatement pst = null;
	private JPanel contentPane;
	public JTextField t1, t3, t4, t6, t7, t8, t9;
	private JComboBox<String> idTypeComboBox; // Dropdown for ID Type
	private JComboBox<String> genderComboBox;
	private JButton btnUpdate;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				UpdateCustomer frame = new UpdateCustomer("");
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public UpdateCustomer(String username) throws SQLException {
		setBounds(580, 220, 850, 550);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("travel/management/system/icons/update.png"));
		Image i3 = i1.getImage().getScaledInstance(200, 400, Image.SCALE_DEFAULT);
		ImageIcon i2 = new ImageIcon(i3);
		JLabel l1 = new JLabel(i2);
		l1.setBounds(500, 40, 200, 400);
		add(l1);

		JLabel lblName = new JLabel("UPDATE CUSTOMER DETAILS");
		lblName.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		lblName.setBounds(118, 11, 300, 53);
		contentPane.add(lblName);

		JLabel l3 = new JLabel("Username :");
		l3.setBounds(35, 70, 200, 14);
		contentPane.add(l3);

		t1 = new JTextField();
		t1.setBounds(271, 70, 150, 20);
		contentPane.add(t1);
		t1.setColumns(10);

		JLabel lblIdType = new JLabel("ID Type :");
		lblIdType.setBounds(35, 110, 200, 14);
		contentPane.add(lblIdType);

		idTypeComboBox = new JComboBox<>(new String[]{"Passport", "Driver's License", "National ID"});
		idTypeComboBox.setEnabled(false); // Make ID Type non-editable initially
		idTypeComboBox.setBounds(271, 110, 150, 20);
		contentPane.add(idTypeComboBox);

		JLabel l2 = new JLabel("Number :");
		l2.setBounds(35, 150, 200, 14);
		contentPane.add(l2);

		t3 = new JTextField();
		t3.setBounds(271, 150, 150, 20);
		t3.setEditable(false); // Make number field non-editable initially
		contentPane.add(t3);
		t3.setColumns(10);

		JLabel lblName_1 = new JLabel("Name :");
		lblName_1.setBounds(35, 190, 200, 14);
		contentPane.add(lblName_1);

		t4 = new JTextField();
		t4.setBounds(271, 190, 150, 20);
		t4.setEditable(false); // Make name field non-editable initially
		contentPane.add(t4);
		t4.setColumns(10);

		JLabel lblGender = new JLabel("Gender :");
		lblGender.setBounds(35, 230, 200, 14);
		contentPane.add(lblGender);

		genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
		genderComboBox.setEnabled(false); // Make gender field non-editable initially
		genderComboBox.setBounds(271, 230, 150, 20);
		contentPane.add(genderComboBox);

		JLabel lblCountry = new JLabel("Country :");
		lblCountry.setBounds(35, 270, 200, 14);
		contentPane.add(lblCountry);

		t6 = new JTextField();
		t6.setBounds(271, 270, 150, 20);
		t6.setEditable(false); // Make country field non-editable initially
		contentPane.add(t6);
		t6.setColumns(10);

		JLabel lblAddress = new JLabel("Permanent Address :");
		lblAddress.setBounds(35, 310, 200, 14);
		contentPane.add(lblAddress);

		t7 = new JTextField();
		t7.setBounds(271, 310, 150, 20);
		t7.setEditable(false); // Make address field non-editable initially
		contentPane.add(t7);
		t7.setColumns(10);

		JLabel lblPhone = new JLabel("Phone :");
		lblPhone.setBounds(35, 350, 200, 14);
		contentPane.add(lblPhone);

		t8 = new JTextField();
		t8.setBounds(271, 350, 150, 20);
		t8.setEditable(false); // Make phone field non-editable initially
		contentPane.add(t8);
		t8.setColumns(10);

		JLabel lblEmail = new JLabel("Email :");
		lblEmail.setBounds(35, 390, 200, 14);
		contentPane.add(lblEmail);

		t9 = new JTextField();
		t9.setBounds(271, 390, 150, 20);
		t9.setEditable(false); // Make email field non-editable initially
		contentPane.add(t9);
		t9.setColumns(10);

		// Check username button
		JButton btnCheck = new JButton("Check User");
		btnCheck.addActionListener(e -> {
			String usernameInput = t1.getText();
			if (usernameInput.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter a username to check.");
				return;
			}

			try {
				Conn c = new Conn();
				ResultSet rs = c.s.executeQuery("select * from customer where username = '" + usernameInput + "'");
				if (rs.next()) {
					// User found, populate fields and enable them for editing
					idTypeComboBox.setSelectedItem(rs.getString(2)); // ID Type
					t3.setText(rs.getString(3)); // Number
					t4.setText(rs.getString(4)); // Name
					genderComboBox.setSelectedItem(rs.getString(5)); // Gender
					t6.setText(rs.getString(6)); // Country
					t7.setText(rs.getString(7)); // Address
					t8.setText(rs.getString(8)); // Phone
					t9.setText(rs.getString(9)); // Email

					// Enable fields for editing
					idTypeComboBox.setEnabled(true);
					t3.setEditable(true);
					t4.setEditable(true);
					genderComboBox.setEnabled(true);
					t6.setEditable(true);
					t7.setEditable(true);
					t8.setEditable(true);
					t9.setEditable(true);

					btnUpdate.setEnabled(true); // Enable update button
				} else {
					JOptionPane.showMessageDialog(null, "Username not found in the database.");
					clearFields(); // Clear fields if user not found
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		btnCheck.setBounds(35, 430, 120, 30);
		btnCheck.setBackground(Color.BLACK);
		btnCheck.setForeground(Color.WHITE);
		contentPane.add(btnCheck);

		// Update button with validation
		btnUpdate = new JButton("Update");
		btnUpdate.setEnabled(false); // Initially disable the update button
		btnUpdate.addActionListener(e -> {
			if (validateFields()) {
				try {
					Conn c = new Conn();
					String s1 = t1.getText(); // Username
					String s2 = (String) idTypeComboBox.getSelectedItem(); // ID Type
					String s3 = t3.getText(); // Number
					String s4 = t4.getText(); // Name
					String s5 = (String) genderComboBox.getSelectedItem(); // Gender
					String s6 = t6.getText(); // Country
					String s7 = t7.getText(); // Address
					String s8 = t8.getText(); // Phone
					String s9 = t9.getText(); // Email

					String q1 = "update customer set id_type = '" + s2 + "', number = '" + s3 + "', name = '" + s4 + "', gender = '" + s5 + "', country = '" + s6 + "', address = '" + s7 + "', phone = '" + s8 + "', email = '" + s9 + "' where username = '" + s1 + "'";
					c.s.executeUpdate(q1);

					JOptionPane.showMessageDialog(null, "Customer Detail Updated Successfully");
					clearFields(); // Clear fields after update
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		btnUpdate.setBounds(260, 430, 120, 30);
		btnUpdate.setBackground(Color.BLACK);
		btnUpdate.setForeground(Color.WHITE);
		contentPane.add(btnUpdate);

		// Exit button
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(e -> System.exit(0));
		btnExit.setBounds(485, 430, 120, 30);
		btnExit.setBackground(Color.BLACK);
		btnExit.setForeground(Color.WHITE);
		contentPane.add(btnExit);
	}

	// Validate all fields before update
	private boolean validateFields() {
		if (t1.getText().isEmpty() || t3.getText().isEmpty() || t4.getText().isEmpty() ||
				t6.getText().isEmpty() || t7.getText().isEmpty() || t8.getText().isEmpty() || t9.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please fill all fields.");
			return false;
		}
		if (!isValidEmail(t9.getText())) {
			JOptionPane.showMessageDialog(null, "Please enter a valid email address.");
			return false;
		}
		return true;
	}

	// Check for valid email format
	private boolean isValidEmail(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		Pattern pattern = Pattern.compile(emailRegex);
		return pattern.matcher(email).matches();
	}

	// Clear all fields
	private void clearFields() {
		t1.setText("");
		idTypeComboBox.setSelectedIndex(0); // Reset ID Type dropdown
		t3.setText("");
		t4.setText("");
		genderComboBox.setSelectedIndex(0); // Reset Gender dropdown
		t6.setText("");
		t7.setText("");
		t8.setText("");
		t9.setText("");
		idTypeComboBox.setEnabled(false);
		t3.setEditable(false);
		t4.setEditable(false);
		genderComboBox.setEnabled(false);
		t6.setEditable(false);
		t7.setEditable(false);
		t8.setEditable(false);
		t9.setEditable(false);
		btnUpdate.setEnabled(false); // Disable update button after clearing
	}
}
