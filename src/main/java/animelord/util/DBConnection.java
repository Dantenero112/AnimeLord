package animelord.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    private static Connection con;

    private DBConnection() {
    }

    public static Connection getConnection() {

        try {

            if (con == null || con.isClosed()) {

                Properties prop = new Properties();

                prop.load(
                    DBConnection.class.getClassLoader().getResourceAsStream("db.properties")
                );

                String driver = prop.getProperty("db.driver");

                String url = prop.getProperty("db.url");

                String username = prop.getProperty("db.username");

                String password = prop.getProperty("db.password");

                Class.forName(driver);

                con = DriverManager.getConnection( url, username, password);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }

}