import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import travel.management.system.Conn;
import travel.management.system.DeleteCustomer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class DeleteCustomerTest {

    private DeleteCustomer deleteCustomer;
    private Conn mockConn;
    private Statement mockStatement;

    @BeforeEach
    public void setUp() throws SQLException {
        // Mock the Conn class and its behavior
        mockConn = Mockito.mock(Conn.class);
        mockStatement = Mockito.mock(Statement.class);

        // Create an instance of DeleteCustomer
        deleteCustomer = new DeleteCustomer();

        // Set the mock connection and statement
        Mockito.when(mockConn.s).thenReturn(mockStatement);
    }

    @Test
    public void testCheckCustomerDetails() throws SQLException {
        // Mock the result set for customer details
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);
        Mockito.when(mockResultSet.next()).thenReturn(true); // Simulate customer found
        Mockito.when(mockResultSet.getString(2)).thenReturn("John Doe"); // Mock name
        Mockito.when(mockResultSet.getString(3)).thenReturn("1234567890"); // Mock phone

        // Mock the SQL query execution for checking customer
        Mockito.when(mockStatement.executeQuery("select * from customer where username = 'testUser'"))
                .thenReturn(mockResultSet);

        // Set the selected username in the UI component
        deleteCustomer.c1.select("testUser");

        // Call the check button action listener
        deleteCustomer.b1.doClick(); // Simulate button click

        // Verify the customer details are set
        assertEquals("John Doe", deleteCustomer.l4.getText()); // Check name
        assertEquals("1234567890", deleteCustomer.l8.getText()); // Check phone
    }
}
