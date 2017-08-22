package lms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Class describes connection to database
 */
public class DataSource implements AutoCloseable {
    private static final Logger LOGGER = Logger.getLogger(DataSource.class.getName());

    /**
     * JDBC driver name and database URL
     */
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/xtrello?user=root&password=0000";

    private Connection connection = null;

    public DataSource() {
        try {
            Class.forName(JDBC_DRIVER).newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(DB_URL);
            }
        } catch (SQLException e) {
            LOGGER.severe("Помилка підключення до бази:\t " + e.toString());
        }
        return connection;
    }
    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.severe("Помилка закриття бази:\t " + e.toString());
        }
    }
}
