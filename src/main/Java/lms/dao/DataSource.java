package lms.dao;

import lms.views.HtmlViews.UserHtmlViews;

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
    private static final String DB_URL = UserHtmlViews.getInstance().getSqlLoginAndPassword().toString();


    private Connection connection = null;
    public DataSource() {
        try {
            Class.forName(JDBC_DRIVER).newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            LOGGER.severe("Some problem with JDBC driver: \t" + e.toString());
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(DB_URL);
            }
        } catch (SQLException e) {
            LOGGER.severe("Some problem with connection to database:\t " + e.toString());
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
            LOGGER.severe("Problem with closing database:\t " + e.toString());
        }
    }
}
