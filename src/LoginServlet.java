import sun.plugin2.gluegen.runtime.CPU;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    String username = "admin";
    String password = "123";
    String html = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Login</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div align=\"center\">\n" +
            "    <h1>Hello, Stranger</h1>\n" +
            "    <form action=\"login\" method=\"POST\">\n" +
            "        <label id=\"name\"><p>Enter your username:</p>\n" +
            "            <input name=\"username\">\n" +
            "        </label><br>\n" +
            "        <label><p>Enter your password:</p>\n" +
            "            <input name=\"password\" type=\"password\"></label><br><br>\n" +
            "<label>Remember me for a month<input name=\"remember\" type=\"checkbox\"><br><br></label>" +
            "        <input type=\"submit\">\n" +
            "    </form>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        createPage(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        if (username.equals(request.getParameter("username")) && password.equals(request.getParameter("password"))) {
            HttpSession session = request.getSession();
            User user = new User(request.getParameter("username"), request.getParameter("password"));
            session.setAttribute("user", user);
            System.out.println(request.getParameter("remember"));
            if (request.getParameter("remember") != null) {
                System.out.println("Remember for a month");
                Cookie userCookie = new Cookie("username", request.getParameter("username"));
                Cookie passCookie = new Cookie("password", request.getParameter("password"));
                userCookie.setMaxAge(60); //2592000 seconds = 30 days
                passCookie.setMaxAge(60); //2592000 seconds = 30 days
                response.addCookie(userCookie);
                response.addCookie(passCookie);
            }
            response.sendRedirect("http://localhost:8080/Infa_war_exploded/profile");
        } else {
            response.sendRedirect("http://localhost:8080/Infa_war_exploded/login");
        }
    }

    private void createPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        Cookie userCookie = null;
        Cookie passCookie = null;
        String cookieName = "username";
        String cookiePass = "password";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieName.equals(c.getName())) {
                    userCookie = c;
                }
                if (cookiePass.equals(c.getName())) {
                    passCookie = c;
                }
            }
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if ((userCookie == null || passCookie == null) && user == null) {
            writer.println(html);
        } else {
            response.sendRedirect("http://localhost:8080/Infa_war_exploded/profile");
        }
    }
}
