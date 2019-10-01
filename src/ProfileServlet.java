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
    String html2 = ".<br> How are you?</p></h1><br><table><tr><th>N</th><th>Product name</th><th>Price</th><th></th><tr>\n";

    String html3 = "</table><br><form action=\"profile\" method=\"POST\"><input type=\"text\" name=\"good\" placeholder=\"Good's name\"><input type=\"text\" name=\"price\" placeholder=\"Price\"><input type=\"submit\" value=\"Add\"></form><br><br>" +
            "<form action=\"profile\" method=\"POST\"><input type=\"submit\" name=\"maxPrice\" value=\"Show the most expensive\"></form>";
    String html5 = "</div>\n" +
            "</body>\n" +
            "</html>";

    String html4 = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        if (request.getParameter("good") != null) {
            BusinessLogic.addGood(request.getParameter("good"), Double.parseDouble(request.getParameter("price")));
        } else if (request.getParameter("maxPrice") != null) {
            html4 = "<br><br><h1>The most expensive good is " + BusinessLogic.getMaxPricedGood() + "</h1>";
        } else {
            delete(request);
        }
        response.sendRedirect("http://localhost:8080/Infa_war_exploded/profile");

    }

    private void delete(HttpServletRequest request) {
        ArrayList<Good> goods = User.getGoods();
        for (Good g : goods) {
            if (request.getParameter("Delete" + g.getId()) != null) {
                BusinessLogic.removeGood(g);
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

    private void send(String name, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        String page = html1 + name + html2;
        String htmlGoods = "";
        ArrayList<Good> goods = User.getGoods();
        int counter = 1;
        for (Good g : goods) {
            htmlGoods += "<form action=\"profile\" method=\"POST\"><tr><td>" + counter++ + "</td><td>" + g.getName() + "</td><td>" + g.getPrice() + " Py6</td><td><input type=\"submit\" name=\"Delete" + g.getId() + "\" value=\"Delete\" ></input></td></tr></form>";
        }
        page += htmlGoods + html3;
        if (!html4.equals("")) {
            page += html4;
            System.out.println(html4);
        }
        page += html5;
        writer.println(page);
    }
}