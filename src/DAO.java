import java.sql.*;
import com.mysql.jdbc.Driver;

public class DAO {
    private static String username = "admin";
    private static String password = "123";

    private static String cookieUsername = "username";
    private static String cookiePass = "password";

    private static String db_user = "root";
    private static String db_password = "admin";
    private static String db_URL = "jdbc:mysql://localhost:3306/db1?serverTimezone=Europe/Moscow";

    private static User user;
    private static Connection connection = null;

    public DAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(1);
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1?serverTimezone=Europe/Moscow&useSSL=false", "root", "admin");
            System.out.println(1);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void closeCon() throws SQLException {
        connection.close();
    }

    public static Connection getConnection() {
        return connection;
    }

    public static User getUser() {
        return user;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getCookieUsername() {
        return cookieUsername;
    }

    public static String getCookiePass() {
        return cookiePass;
    }
}
