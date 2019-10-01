import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class User {
    private static String username = "admin";
    private static String password = "123";
    public static ArrayList<Good> goods = new ArrayList<>();

    private static String cookieUsername = "username";
    private static String cookiePass = "password";

    public String getUsername() {
        return username;
    }

    public static void setGoods(ArrayList<Good> goods) {
        User.goods = goods;
    }

    public User() {
        goods.add(new Good("1", "Bread", 10.4));
        goods.add(new Good("2", "Tea", 120));
        goods.add(new Good("3", "DoLLluK", 12));
    }

    static ArrayList<Good> getGoods() {
        System.out.println(goods);
        return goods;
    }
}
