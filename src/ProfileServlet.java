import javax.jms.Session;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
    String html2 = ".<br> How are you?</p></h1><br><table><tr><th>c</th><th>Product name</th><th></th><tr>\n";

    String html3 = "</table><br><form action=\"profile\" method=\"POST\"><input type=\"text\" name=\"good\"><input type=\"submit\" value=\"Add\"></form></div>\n" +
            "</body>\n" +
            "</html>";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        if (request.getParameter("good") != null) {
            User.addGood(request.getParameter("good"));
        } else {
            delete(request);
        }
        response.sendRedirect("http://localhost:8080/Infa_war_exploded/profile");

    }

    private void delete(HttpServletRequest request) {
        ArrayList<Good> goods = User.getGoods();
        for (Good g : goods) {
            if (request.getParameter("Delete" + g.getId()) != null) {
                User.removeGood(g);
                break;
            }
        }
    }

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
        String page = html1 + username + html2;
        String htmlGoods = "";
        ArrayList<Good> goods = User.getGoods();
        int counter = 1;
        for (Good g : goods) {
            htmlGoods += "<tr><td>" + counter++ + "</td><td>" + g.getName() + "</td><td><form action=\"profile\" method=\"POST\"><input type=\"submit\" name=\"Delete" + g.getId() + "\" value=\"Delete\" ></input></form></td></tr>";
        }
        page += htmlGoods + html3;
        writer.println(page);
    }
}