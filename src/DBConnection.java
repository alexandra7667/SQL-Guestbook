/**
 * This class handles the database manipulation:
 * It establishes a connection and allows for table creation,
 * table insertion, retrieving all values from the table,
 * and closing the connection.
 *
 * @author alexandra härnström
 * @version 1
 */

import java.sql.*;

public class DBConnection {
    private Connection connection;
    private final String HOST = "xxx";
    private final String DBNAME = "xxx";
    private final String URL = "jdbc:mysql://" + HOST + "/" + DBNAME;
    private String USERNAME = "xxx";
    private String PASSWORD = "xxx";

    public DBConnection() {
        connectToDatabase();
        createGuestbook();
    }

    /**
     * This method establishes a connection to the database.
     */
    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Connected to database");
    }

    /**
     * This method creates a new table (if there is no table already)
     * with 5 columns: 1) name 2) email 3) website 4) comment 5) timestamp
     */
    private void createGuestbook() {
        try {
            PreparedStatement createTable = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + "guestbookentries"
                            + "  (name VARCHAR(255),"
                            + "   email VARCHAR(255),"
                            + "   website VARCHAR(255),"
                            + "   comment VARCHAR(255),"
                            + "   posting VARCHAR(255))");

            createTable.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method inserts a new posting into the database guestbook.
     * @param values - A String array: [0] name [1] email [2] website [3] comment [4] timestamp
     */
    protected void signGuestbook(String[] values) {
        try {
            PreparedStatement insertValues = connection.prepareStatement(
                    "INSERT INTO guestbookentries(name, email, website, comment, posting) " +
                            "VALUES ('" + values[0] + "', '" + values[1] + "','" + values[2] + "','" + values[3] + "','" + values[4] + "')");

            insertValues.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method formats and returns all values of the database's guestbook.
     * @return - A formatted String containing all inserted values from the guestbook
     */
    protected String getGuestbook() {
        ResultSet result = null;
        StringBuilder guestbook = new StringBuilder();

        try {
            PreparedStatement getValues = connection.prepareStatement(
                    "SELECT * FROM guestbookentries");

            result = getValues.executeQuery();

            while (result.next()) {
                for (int i = 1; i <= 5; i++) {
                    switch (i) {
                        case 1:
                            guestbook.append("Name: ");
                            break;
                        case 2:
                            guestbook.append("Email: ");
                            break;
                        case 3:
                            guestbook.append("Website: ");
                            break;
                        case 4:
                            guestbook.append("Comment: ");
                            break;
                        case 5:
                            guestbook.append("Posted: ");
                            break;
                    }
                    guestbook.append(result.getString(i) + "\n");
                }
                guestbook.append("\n");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return String.valueOf(guestbook);
    }

    /**
     * This method closes the database connection
     */
    public void closeConnection() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Disconnected from database");
    }

    public String getDBNAME() {
        return DBNAME;
    }

    public String getHOST() {
        return HOST;
    }
}