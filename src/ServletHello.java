import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class ServletHello extends HttpServlet {

    String html1 = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>INFA</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div align=\"center\">\n" +
            "    <h1>Hello, Stranger</h1>\n" +
            "    <form action=\"hello\">\n" +
            "        <label id=\"name\"><p>Enter your name:</p>\n" +
            "            <input name=\"name\">\n" +
            "        </label><br>\n" +
            "        <label><p>Enter your group:</p>\n" +
            "            <input name=\"group\"></label><br><br>\n" +
            "        <input type=\"submit\">\n" +
            "    </form>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";

    String html2 = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>INFA</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div align=\"center\">\n" +
            "    <h1><p>Hello, ";

    String html3 = ", from group ";

    String html4 = "</p></h1>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        try (PrintWriter writer = response.getWriter()) {
            String name = request.getParameter("name");
            String group = request.getParameter("group");
            send(writer, name, group);
        }
    }

    private void send(PrintWriter writer, String name, String group) {
        if (name == null || group == null && name == "" || group == "") {
            writer.println(html1);
        } else {
            writer.println(html2 + name + html3 + group + html4);
        }
    }
}