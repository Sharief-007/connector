package com;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
    Connection con;
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        String psw = req.getParameter("psw"),mail = req.getParameter("email");
        adduser(mail,psw);
        //res.getWriter().println("psw:"+psw+"\n mail :"+mail+"User Added to database");
        req.getRequestDispatcher("Details.jsp").forward(req,res);
    }
    private void adduser(String mail,String pass)
    {
        try {
            DatabaseManager db = new DatabaseManager();
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement("insert into credentials (gmail, password) values (?,?)");
            stmt.setString(1,mail);
            stmt.setString(2,pass);
            if(stmt.execute()){
                System.out.println("Success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
