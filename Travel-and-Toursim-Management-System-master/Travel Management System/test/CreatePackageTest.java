import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travel.management.system.CreatePackage;
import travel.management.system.Login;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class CreatePackageTest {

    private CreatePackage createPackage;

    @BeforeEach
    public void setUp() {
        createPackage = new CreatePackage();
        createPackage.tid.setText("1");
        createPackage.tname.setText("Test Package");
        createPackage.tdate.setText("2024-10-10");
        createPackage.tcost.setText("100.0");
    }

    @Test
    public void testCreatePackageUIComponents() {
        assertNotNull(createPackage.label);
        assertNotNull(createPackage.tid);
        assertNotNull(createPackage.tname);
        assertNotNull(createPackage.tdate);
        assertNotNull(createPackage.tcost);
        assertNotNull(createPackage.back);
        assertNotNull(createPackage.log);
        assertNotNull(createPackage.save);
    }



    @Test
    public void testActionPerformedMainMenu() {
        // Simulate clicking the back button
        createPackage.back.doClick();

        // Check if the main menu is displayed (adapt based on your main menu logic)
        assertFalse(createPackage.isVisible());
    }

    @Test
    public void testCreateDB() {
        // Simulate creating a package in the database
        createPackage.createDB();

        // Add assertions to verify database interactions here.
        // Since we can't access the real database in a unit test, this should be
        // a conceptual test, possibly verifying that no exceptions were thrown.
        // In a real-world scenario, you would check the database state here.

        // Placeholder for the test:
        assertDoesNotThrow(() -> {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "1234");
            Statement st = con.createStatement();
            // Clean up or check the state of the database here.
            con.close();
        });
    }
}
