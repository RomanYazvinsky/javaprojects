package utilities;

import com.senla.hotel.constants.Constants;
import com.senla.hotel.exceptions.EmptyObjectException;
import com.senla.hotel.properties.DBProperties;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
    private static Logger logger = LogManager.getLogger(DateCreator.class);
    private Connection connection;
    private static DBConnector instance;

    private DBConnector() {
    }

    private Connection connect() {
        try {
            String host = DBProperties.getInstance(Constants.PATH_TO_DATABASE_PROPERTIES).getProperty("host");
            String username = DBProperties.getInstance(Constants.PATH_TO_DATABASE_PROPERTIES).getProperty("username");
            String password = DBProperties.getInstance(Constants.PATH_TO_DATABASE_PROPERTIES).getProperty("password");
            return DriverManager.getConnection(host, username, password);
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
        } catch (IOException e) {
            logger.log(Level.DEBUG, e.getMessage());
        } catch (EmptyObjectException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return null;
    }

    public static DBConnector getInstance() {
        if (instance == null) {
            instance = new DBConnector();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = connect();
            }
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
        return connection;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
        }
    }
}
