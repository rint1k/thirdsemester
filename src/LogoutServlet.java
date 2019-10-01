import javax.jms.Session;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BusinessLogic.logout(request, response);
        response.sendRedirect("http://localhost:8080/Infa_war_exploded/login");
    }
}
