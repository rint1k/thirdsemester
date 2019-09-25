import javax.jms.Session;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    String html1 = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Profile</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<a href=\"http://localhost:8080/Infa_war_exploded/logout\" title=\"logout\">Logout</a>" +
            "<div align=\"center\">\n" +
            "    <h1><p>Hello, ";
    String html2 = ".<br> How are you?</p></h1>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("http://localhost:8080/Infa_war_exploded/login");
        } else {
            send(user.getUsername(), response);
        }
    }

    private void send(String username, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println(html1 + username + html2);
    }
}