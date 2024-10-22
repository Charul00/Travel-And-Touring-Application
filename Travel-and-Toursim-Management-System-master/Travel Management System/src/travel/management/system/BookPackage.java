package travel.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class BookPackage extends JFrame {
	private JPanel contentPane;
	JTextField t1;  // Total Persons
	Choice c1;      // Package choice
	JLabel l1, l5;  // For displaying username and total price

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookPackage frame = new BookPackage("username_here");  // Pass username
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BookPackage(String username) {
		setBounds(420, 220, 1100, 500);  // Adjusted window size
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("travel/management/system/icons/bookpackage.jpg"));
		Image i3 = i1.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT);
		ImageIcon i2 = new ImageIcon(i3);
		JLabel la1 = new JLabel(i2);
		la1.setBounds(450, 50, 700, 300);
		add(la1);

		JLabel lblName = new JLabel("BOOK PACKAGE");
		lblName.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		lblName.setBounds(118, 11, 300, 53);
		contentPane.add(lblName);

		JLabel la2 = new JLabel("Username:");
		la2.setBounds(35, 70, 200, 14);
		contentPane.add(la2);

		l1 = new JLabel(username);
		l1.setBounds(271, 70, 200, 14);
		contentPane.add(l1);

		JLabel lblId = new JLabel("Select Package:");
		lblId.setBounds(35, 110, 200, 14);
		contentPane.add(lblId);

		c1 = new Choice();
		c1.add("Gold Package");
		c1.add("Silver Package");
		c1.add("Bronze Package");
		c1.setBounds(271, 110, 150, 30);
		add(c1);

		JLabel la3 = new JLabel("Total Persons:");
		la3.setBounds(35, 150, 200, 14);
		contentPane.add(la3);

		t1 = new JTextField();
		t1.setText("0");
		t1.setBounds(271, 150, 150, 20);
		contentPane.add(t1);
		t1.setColumns(10);

		JLabel lblDeposite = new JLabel("Total Price:");
		lblDeposite.setBounds(35, 190, 200, 14);
		contentPane.add(lblDeposite);

		l5 = new JLabel();  // Total Price label
		l5.setBounds(271, 190, 200, 14);
		l5.setForeground(Color.RED);
		contentPane.add(l5);

		// Check Price button to calculate total cost
		JButton b1 = new JButton("Check Price");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String p = c1.getSelectedItem();
				int cost = 0;
				int persons = Integer.parseInt(t1.getText());

				if (p.equals("Gold Package")) {
					cost += 12000;
				} else if (p.equals("Silver Package")) {
					cost += 25000;
				} else if (p.equals("Bronze Package")) {
					cost += 32000;
				}

				cost *= persons;
				l5.setText("Rs " + cost);
			}
		});
		b1.setBounds(50, 250, 120, 30);
		b1.setBackground(Color.BLACK);
		b1.setForeground(Color.WHITE);
		contentPane.add(b1);

		// Book button to store package booking in the database
		JButton btnNewButton = new JButton("Book");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Conn c = new Conn();
				try {
					String q1 = "insert into bookPackage(username, package, persons, price) values('"
							+ username + "', '" + c1.getSelectedItem() + "', '"
							+ t1.getText() + "', '" + l5.getText() + "')";
					c.s.executeUpdate(q1);

					JOptionPane.showMessageDialog(null, "Package Booked Successfully");
					setVisible(false);
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(200, 250, 120, 30);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setForeground(Color.WHITE);
		contentPane.add(btnNewButton);

		// Back button to close the booking window
		JButton btnExit = new JButton("Back");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnExit.setBounds(350, 250, 120, 30);
		btnExit.setBackground(Color.BLACK);
		btnExit.setForeground(Color.WHITE);
		contentPane.add(btnExit);

		getContentPane().setBackground(Color.WHITE);
	}
}
