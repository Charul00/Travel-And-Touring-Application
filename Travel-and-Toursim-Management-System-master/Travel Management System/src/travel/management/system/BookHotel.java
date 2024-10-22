package travel.management.system;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class BookHotel extends JFrame {
	private JPanel contentPane;
	private JTextField t1, t2; // For total persons and number of days
	private Choice c1; // For hotel selection
	private String customerUsername; // To store the customer username

	public BookHotel(String username) {
		customerUsername = username; // Initialize username
		setBounds(580, 220, 850, 550);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("BOOK HOTEL");
		lblTitle.setFont(new Font("Yu Mincho", Font.PLAIN, 30));
		lblTitle.setBounds(250, 10, 200, 30);
		contentPane.add(lblTitle);

		JLabel lblHotel = new JLabel("Select Hotel:");
		lblHotel.setBounds(100, 70, 200, 30);
		contentPane.add(lblHotel);

		c1 = new Choice();
		c1.setBounds(300, 70, 150, 30);
		contentPane.add(c1);

		// Populate hotel choices from the database
		try {
			Conn c = new Conn();
			ResultSet rs = c.s.executeQuery("select * from hotels");
			while (rs.next()) {
				c1.add(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JLabel lblPersons = new JLabel("Total Persons:");
		lblPersons.setBounds(100, 120, 200, 30);
		contentPane.add(lblPersons);

		t1 = new JTextField();
		t1.setBounds(300, 120, 150, 30);
		contentPane.add(t1);

		JLabel lblDays = new JLabel("No. of Days:");
		lblDays.setBounds(100, 170, 200, 30);
		contentPane.add(lblDays);

		t2 = new JTextField();
		t2.setBounds(300, 170, 150, 30);
		contentPane.add(t2);

		JButton btnBook = new JButton("Book Now");
		btnBook.setBounds(300, 230, 150, 30);
		btnBook.setBackground(Color.BLACK);
		btnBook.setForeground(Color.WHITE);
		btnBook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String hotelName = c1.getSelectedItem();
				String totalPersons = t1.getText();
				String numberOfDays = t2.getText();

				// Validation for input fields
				if (hotelName.isEmpty() || totalPersons.isEmpty() || numberOfDays.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill out all fields.");
					return;
				}

				// Book hotel in the database
				try {
					Conn c = new Conn();
					String query = "insert into bookHotel values('" + customerUsername + "', '" + hotelName + "', '" + totalPersons + "', '" + numberOfDays + "')";
					c.s.executeUpdate(query);
					JOptionPane.showMessageDialog(null, "Hotel booked successfully!");
					setVisible(false); // Close current window
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error in booking hotel: " + ex.getMessage());
				}
			}
		});
		contentPane.add(btnBook);

		JButton btnBack = new JButton("Back");
		btnBack.setBounds(470, 230, 150, 30);
		btnBack.setBackground(Color.BLACK);
		btnBack.setForeground(Color.WHITE);
		btnBack.addActionListener(e -> setVisible(false));
		contentPane.add(btnBack);

		contentPane.setBackground(Color.WHITE);
	}

	public static void main(String[] args) {
		new BookHotel("").setVisible(true);
	}
}
