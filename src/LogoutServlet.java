import javax.jms.Session;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie userCookie = null;
        Cookie passCookie = null;
        String cookieName = "username";
        String cookiePass = "password";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieName.equals(c.getName())) {
                    userCookie = c;
                    userCookie.setMaxAge(0);
                }
                if (cookiePass.equals(c.getName())) {
                    passCookie = c;
                    passCookie.setMaxAge(0);
                }
            }
        }
        response.addCookie(userCookie);
        response.addCookie(passCookie);
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("http://localhost:8080/Infa_war_exploded/login");
    }
}
