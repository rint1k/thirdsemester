import sun.plugin2.gluegen.runtime.CPU;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.SQLException;
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
        if (BusinessLogic.login(request, response)) {
            response.sendRedirect("http://localhost:8080/Infa_war_exploded/profile");
        } else {
            response.sendRedirect("http://localhost:8080/Infa_war_exploded/login");
        }
    }

    private void createPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();

        if (!BusinessLogic.isLogined(request)) {
            writer.println(html);
        } else {
            response.sendRedirect("http://localhost:8080/Infa_war_exploded/profile");
        }
    }
}
