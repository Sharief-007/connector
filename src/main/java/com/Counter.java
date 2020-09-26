package com;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

@WebServlet("/counter")
public class Counter extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DatabaseManager manager = new DatabaseManager();
            ResultSet resultSet = manager.getConnection().createStatement().executeQuery("select count(*) from credentials");
            resultSet.next();
            resp.getWriter().print(resultSet.getInt(1));
            manager.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
