import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class User {
    private static String username = "admin";
    private static String password = "123";
    private static ArrayList<Good> goods = new ArrayList<>();

    private static String cookieUsername = "username";
    private static String cookiePass = "password";

    public String getUsername() {
        return username;
    }

    public static boolean removeGood(Good good) {
        return goods.remove(good);
    }

    public static boolean addGood(String goodName) {
        return goods.add(new Good(String.valueOf(goods.size() + 1), goodName));
    }

    public User() {
        goods.add(new Good("1", "Bread"));
        goods.add(new Good("2", "Tea"));
        goods.add(new Good("3", "DoLLluK"));
    }

    static boolean isLogined(HttpServletRequest request) {
        Cookie userCookie = null;
        Cookie passCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieUsername.equals(c.getName())) {
                    userCookie = c;
                }
                if (cookiePass.equals(c.getName())) {
                    passCookie = c;
                }
            }
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        return (userCookie != null && passCookie != null) || user != null;
    }

    static boolean logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Cookie userCookie = null;
        Cookie passCookie = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieUsername.equals(c.getName())) {
                    userCookie = c;
                    userCookie.setMaxAge(0);
                }
                if (cookiePass.equals(c.getName())) {
                    passCookie = c;
                    passCookie.setMaxAge(0);
                }
            }
            if (userCookie != null && passCookie != null) {
                response.addCookie(userCookie);
                response.addCookie(passCookie);
            }
        }
        HttpSession session = request.getSession();
        session.invalidate();
        System.out.println(session);
        return true;
    }

    static boolean login(HttpServletRequest request, HttpServletResponse response) {
        if (username.equals(request.getParameter(cookieUsername)) && password.equals(request.getParameter(cookiePass))) {
            HttpSession session = request.getSession();
            session.setAttribute("user", new User());
            if (request.getParameter("remember") != null) {
                System.out.println("Remember for a month");
                Cookie userCookie = new Cookie(cookieUsername, request.getParameter(cookieUsername));
                Cookie passCookie = new Cookie(cookiePass, request.getParameter(cookiePass));
                userCookie.setMaxAge(120); //2592000 seconds = 30 days
                passCookie.setMaxAge(120); //2592000 seconds = 30 days
                response.addCookie(userCookie);
                response.addCookie(passCookie);
            }
            return true;
        } else {
            return false;
        }
    }

    static ArrayList<Good> getGoods() {
        System.out.println(goods);
        return goods;
    }
}
