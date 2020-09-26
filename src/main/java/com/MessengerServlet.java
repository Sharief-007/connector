package com;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/MessengerServlet")
public class MessengerServlet extends HttpServlet {
    DatabaseManager db;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int frnd = Integer.parseInt(request.getParameter("number"));
        String message = request.getParameter("message");
        User user = (User) request.getSession().getAttribute("user");
        try {
            String mychatTable = user.getFriends().get(frnd);
            String hischatTable = "table_"+frnd+"_"+user.getId();
            db = new DatabaseManager();
            Statement stmt = db.getConnection().createStatement();
            stmt.execute("insert into "+mychatTable+" values ('"+message+"','s')");
            stmt.execute("insert into "+hischatTable+" values ('"+message+"','r')");
            db.closeConnection();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        try {
            int frndId = Integer.parseInt(request.getParameter("number"));
            String chatTable = user.getFriends().get(frndId);
            db = new DatabaseManager();
            ArrayList<Message> allMessages = new ArrayList<Message>();
            Statement stmt = db.getConnection().createStatement();
            System.out.println(chatTable);
            ResultSet rs = stmt.executeQuery("select * from "+chatTable);
            while (rs.next())
            {
                allMessages.add(new Message(rs.getString(1),rs.getString(2)));
            }
            response.setContentType("text/plain");
            response.getWriter().println(new Gson().toJson(allMessages));
            db.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
