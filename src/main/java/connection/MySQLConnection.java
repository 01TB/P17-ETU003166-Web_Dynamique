package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MySQLConnection {

    protected static Context initContext = null;
    protected static Context envContext = null;
    protected static DataSource dataSource = null;
    
    // password="pA1m43EK";
    
    static {
        try {
            initContext = new InitialContext();
            envContext = (Context) initContext.lookup("java:comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/MyDB");
        } catch (Exception e) {
            System.err.println("Error loading MySQL Driver: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            // e.printStackTrace();
            return false;
        }
    }
}