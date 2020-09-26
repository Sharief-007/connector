package com;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/FriendsList")
public class FriendsList extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        User user =(User) req.getSession().getAttribute("user");
        try {
            DatabaseManager db = new DatabaseManager();
            Statement statement=db.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select friends from USER where id = "+user.getId());
            resultSet.next();
            ObjectInputStream os = new ObjectInputStream(new ByteArrayInputStream(resultSet.getBytes(1)));
            HashMap<Integer,String> friends = (HashMap<Integer, String>)os.readObject();
            ArrayList<Integer> onlyFrnds = new ArrayList<>();
            friends.forEach((k,v)->
            {
                onlyFrnds.add(k);
            });
            out.println(new Gson().toJson(onlyFrnds));
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
