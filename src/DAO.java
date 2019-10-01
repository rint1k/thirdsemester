public class DAO {
    private static String username = "admin";
    private static String password = "123";

    private static String cookieUsername = "username";
    private static String cookiePass = "password";

    private static User user;

    public DAO() {
        user = new User();
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
