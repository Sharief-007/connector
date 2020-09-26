package com;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Stack;

@WebServlet("/homepagedetailsprovider")
public class HomePageDetailsProvider extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User principal = (User)req.getSession().getAttribute("user");
        int id = principal.getId();

        try {
            DatabaseManager manager = new DatabaseManager();
            ResultSet rs1 = manager.getConnection().createStatement().executeQuery("select * from user where id = "+id);
            rs1.next();

            User responseObject = new User(cleanPersonalData(rs1,principal), cleanData(rs1));
            responseObject.setId(rs1.getInt(1));
            responseObject.setFriends(cleanFriends(rs1));
            responseObject.setActive(rs1.getBoolean("active"));


            ResultSet rs2 = manager.getConnection().createStatement().executeQuery("select * from pictures where id = "+id);
            rs2.next();
            responseObject.setPrf_picture(rs2.getString("profile"));
            responseObject.setCvr_picture(rs2.getString("cover"));
            responseObject.setAlbums(cleanAlbums(rs2));

            manager.closeConnection();

            resp.setContentType("text/plain");
            resp.getWriter().print(new Gson().toJson(responseObject));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static PersonalData cleanPersonalData(ResultSet rs,User user) throws SQLException {
        PersonalData personalData = new PersonalData(rs.getString("name"),
                rs.getString("username"),
                rs.getString("gender"),
                rs.getString("dob")
        );
        personalData.setEmail(user.getPersonalData().getEmail());
        return personalData;
    }

    private static Data cleanData(ResultSet rs) throws SQLException, IOException, ClassNotFoundException {
        byte[] array = rs.getBytes("data");
        Data data = (Data) new ObjectInputStream(new ByteArrayInputStream(array)).readObject();
        return data;
    }

    private static HashMap cleanFriends(ResultSet rs) throws SQLException, IOException, ClassNotFoundException {
        return (HashMap<Integer, String>) new ObjectInputStream(new ByteArrayInputStream(rs.getBytes("friends"))).readObject();
    }

    private static Stack<Album> cleanAlbums(ResultSet rs) throws SQLException, IOException, ClassNotFoundException {
        String posts = rs.getString("posts");
        if (posts!=null) {
            Stack<Album> albums = new Gson().fromJson(posts,new TypeToken<Stack<Album>>(){}.getType());
            //System.out.println(albums);
            return albums;
        }else{
            System.out.println("Empty Album");
            return new Stack<Album>();
        }
    }
}
