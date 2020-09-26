package com;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("uname"),pass = req.getParameter("psw");
        try {
            DatabaseManager db = new DatabaseManager();
            Statement stmt = db.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select id,password from credentials where gmail ='"+username+"'");
            rs.next();
            int id = rs.getInt(1);
            if(rs.getString(2).equals(pass))
            {
                stmt.execute("update user set active =true where id ="+id);
                User user = db.FetchUser(id);
                req.getSession().invalidate();
                HttpSession mysession = req.getSession(true);
                mysession.setAttribute("user",user);
                resp.sendRedirect("Home.jsp");
            }
            else
                throw new Exception("Invalid Credentials");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("ErrorPage.jsp");
        }
    }
}
