import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BusinessLogic {

    private static Statement statement;
    private static DAO d;

    static ArrayList<Good> getGoodFromDB() {
        ArrayList<Good> goods = new ArrayList<>();
        System.out.println(goods);
        try {
            statement = d.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM good_db");
            while (rs.next()) {
                System.out.println(rs);
                goods.add(new Good(String.valueOf(rs.getInt(1)), rs.getString(2), rs.getDouble(3)));
            }
            return goods;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(goods);
        return goods;
    }

    static boolean isLogined(HttpServletRequest request) {
        Cookie userCookie = null;
        Cookie passCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (DAO.getCookieUsername().equals(c.getName())) {
                    userCookie = c;
                }
                if (DAO.getCookiePass().equals(c.getName())) {
                    passCookie = c;
                }
            }
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return userCookie != null && passCookie != null || user != null;
    }

    static boolean logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Cookie userCookie = null;
        Cookie passCookie = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (DAO.getCookieUsername().equals(c.getName())) {
                    userCookie = c;
                    userCookie.setMaxAge(0);
                }
                if (DAO.getCookiePass().equals(c.getName())) {
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
        try {
            DAO.closeCon();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    static boolean login(HttpServletRequest request, HttpServletResponse response) {
        if (DAO.getUsername().equals(request.getParameter(DAO.getCookieUsername())) && DAO.getPassword().equals(request.getParameter(DAO.getCookiePass()))) {
            HttpSession session = request.getSession();
            session.setAttribute("user", new User());
            if (request.getParameter("remember") != null) {
                System.out.println("Remember for a month");
                Cookie userCookie = new Cookie(DAO.getCookieUsername(), request.getParameter(DAO.getCookieUsername()));
                Cookie passCookie = new Cookie(DAO.getCookiePass(), request.getParameter(DAO.getCookiePass()));
                userCookie.setMaxAge(120); //2592000 seconds = 30 days
                passCookie.setMaxAge(120); //2592000 seconds = 30 days
                response.addCookie(userCookie);
                response.addCookie(passCookie);
            }
                d = new DAO();
            return true;
        } else {
            return false;
        }
    }

    public static boolean removeGood(String id) {
        try {
            statement = d.getConnection().createStatement();
            int result = statement.executeUpdate("DELETE FROM good_db WHERE id = " + Integer.parseInt(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean addGood(String goodName, double price) {
        try {
            statement = d.getConnection().createStatement();
            int result = statement.executeUpdate("INSERT INTO good_db (name, price) VALUE (\"" + goodName + "\", " + price + ")");
            System.out.println(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static String getMaxPricedGood() {
        String goodName = "";
        double maxPrice = 0;
        try {
            statement = d.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM good_db");
            while (rs.next()) {
                if(maxPrice < rs.getDouble(3)){
                    maxPrice = rs.getDouble(3);
                    goodName = rs.getString(2);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goodName;
    }
}
