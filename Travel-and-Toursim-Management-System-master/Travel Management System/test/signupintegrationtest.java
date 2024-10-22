import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import travel.management.system.Signup;

import javax.swing.*;

public class signupintegrationtest {

    private Signup signup;
    private JFrame frame;

    @Before
    public void setUp() {
        signup = new Signup();
        frame = new JFrame();
        frame.add(signup.getContentPane());
        frame.setVisible(true);
    }

    @After
    public void tearDown() {
        frame.dispose();
    }

    @Test
    public void testCreateAccount() {
        // Set up test data
        String username = "testuser";
        String name = "Test User";
        String password = "password";
        String question = "Your Question ";
    }
}