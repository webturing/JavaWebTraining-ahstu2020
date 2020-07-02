package ai;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/solve")
public class SolveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        try {
            a = Integer.parseInt(request.getParameter("A"));
            b = Integer.parseInt(request.getParameter("B"));
            c = Integer.parseInt(request.getParameter("C"));
            d = Integer.parseInt(request.getParameter("D"));

        } catch (Exception e) {

        }
        String result = ai.Engine.solve24(a, b, c, d);

        request.getSession().setAttribute("result",result);
        response.sendRedirect("index.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
