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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/fetcher")
public class Fetcher extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User myuser = (User) req.getSession().getAttribute("user");
        ArrayList<User> thisismyresponse = new ArrayList<>();

        try {
            DatabaseManager manager = new DatabaseManager();
            Connection con = manager.getConnection();
            PreparedStatement stmt = con.prepareStatement("select user.id, name, username, dob, gender, data, profile, cover, active, requests from user,pictures where user.id = pictures.id and user.id != ?");
            stmt.setString(1, String.valueOf(myuser.getId()));
            ResultSet rs = stmt.executeQuery();
            stmt = con.prepareStatement("select friends from user where id =?");
            stmt.setInt(1, myuser.getId());
            ResultSet resultSet2 = stmt.executeQuery();
            resultSet2.next();
            ObjectInputStream os = new ObjectInputStream(new ByteArrayInputStream(resultSet2.getBytes(1)));
            HashMap<Integer, String> myFriends = (HashMap<Integer, String>) os.readObject();


            while (rs.next()) {
                if (myFriends.containsKey(rs.getInt(1))) {
                    continue;
                }
                PersonalData pd = new PersonalData(rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4));
                os = new ObjectInputStream(new ByteArrayInputStream(rs.getBytes(6)));
                Data data = (Data) os.readObject();
                User user = new User(pd, data);
                user.set_pictures(rs.getString(7), rs.getString(8));
                user.setId(rs.getInt(1));
                user.setActive(rs.getBoolean(9));
                if (rs.getBytes(10) != null) {
                    os = new ObjectInputStream(new ByteArrayInputStream(rs.getBytes(10)));
                    ArrayList<Integer> reqsts = (ArrayList<Integer>) os.readObject();
                    user.setRequests(reqsts);
                } else {
                    user.setRequests(new ArrayList<Integer>());
                }

                thisismyresponse.add(user);

                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                out.print(new Gson().toJson(thisismyresponse));
                System.out.println(new Gson().toJson(thisismyresponse));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}