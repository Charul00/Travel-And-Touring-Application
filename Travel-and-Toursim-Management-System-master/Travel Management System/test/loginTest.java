import org.junit.jupiter.api.*;
import travel.management.system.Login;

import javax.swing.*;

class LoginTest {

    private Login loginFrame;

    @BeforeEach
    public void setUp() {
        loginFrame = new Login();
        loginFrame.setVisible(true);
    }

    @Test
    public void testSuccessfulLogin() {
        loginFrame.textField.setText("charul90");
        loginFrame.passwordField.setText("charul99");
        loginFrame.b1.doClick(); // Simulate button click
        Assertions.assertFalse(loginFrame.isVisible()); // Check if frame is hidden
    }

    @Test
    public void testFailedLoginInvalidCredentials() {
        loginFrame.textField.setText("invalidUser");
        loginFrame.passwordField.setText("wrongPassword");
        loginFrame.b1.doClick(); // Simulate button click
        // Check for JOptionPane message (mock this behavior in actual tests)
    }

    // Additional tests can be added here...

    @AfterEach
    public void tearDown() {
        loginFrame.dispose(); // Close the frame after each test
    }
}
