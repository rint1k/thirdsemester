import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BusinessLogic {


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
            return true;
        } else {
            return false;
        }
    }

    public static boolean removeGood(Good good) {
        return DAO.getUser().goods.remove(good);
    }

    public static boolean addGood(String goodName, double price) {
        int lastId = Integer.parseInt(User.goods.get(User.goods.size() - 1).getId()) + 1;
        return DAO.getUser().goods.add(new Good(String.valueOf(lastId), goodName, price));
    }

    public static String getMaxPricedGood() {
        double maxPrice = 0;
        String goodName = "";
        for (Good g : User.getGoods()) {
            if (maxPrice < g.getPrice()) {
                maxPrice = g.getPrice();
                goodName = g.getName();
            }
        }
        return goodName;
    }
}
