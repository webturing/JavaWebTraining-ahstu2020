package ai;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.sql.*;

@WebServlet("/solve")
public class SolveServlet extends HttpServlet {
    private String diverClass;
    private String userName;
    private String password;
    private String url;
    private Connection connection;
    private Statement stmt;

    @Override
    public void init() throws ServletException {
        diverClass = /* getServletConfig(). */getServletContext().getInitParameter("driver");
        userName = /* getServletConfig(). */getServletContext().getInitParameter("username");
        password = /* getServletConfig(). */getServletContext().getInitParameter("password");
        url = /* getServletConfig(). */getServletContext().getInitParameter("url");

        try {
            Class.forName(diverClass);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        long start=System.currentTimeMillis();
        int[] arr = new int[]{a, b, c, d};
        Arrays.sort(arr);
        String key = Arrays.toString(arr);
        String result = query(key);
        if (result == null) {
            System.err.println("重新计算结果并写入数据库");
            result = ai.Engine.solve24(a, b, c, d);
            save(key, result);
        }
        long end=System.currentTimeMillis();

        request.getSession().setAttribute("result", result);
        request.getSession().setAttribute("elapse", (end-start)/1000.0);
        response.sendRedirect("index.jsp");

    }

    private boolean save(String key, String result) {
        try {
            connection = DriverManager.getConnection(url, userName, password);
            stmt = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement("insert into point24(numbers,solution) value(?,?)");

            ps.setString(1, key);
            ps.setString(2, result);
            int x = ps.executeUpdate();
            System.err.println("写入数据库成功");
            stmt.close();
            connection.close();
            return x > 0;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("数据库链接失败");
            return false;
        }
    }

    private String query(String key) {
        String ans = null;

        try {
            connection = DriverManager.getConnection(url, userName, password);
            stmt = connection.createStatement();//key
            PreparedStatement ps = connection.prepareStatement("select solution from point24 where numbers=? LIMIT 1");
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ans = rs.getString(1);
                System.err.println("数据已经存在，直接读取数据库");

            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("数据库链接失败");
        }
        return ans;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
