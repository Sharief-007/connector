package com;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.TreeMap;

public class DatabaseManager {
    private Connection connection;
    private ResultSet rs;
    private Statement stmt;
    private PreparedStatement pstmt;

    private String driver ="com.mysql.cj.jdbc.Driver",url ="jdbc:mysql://localhost:3306/connector?verifyServerCertificate=false&useSSL=true";
    private String username="root",password="sharief";
    public DatabaseManager() throws Exception {
        createConnection();
    }
    private void createConnection() throws Exception {
        Class.forName(driver);
        connection = DriverManager.getConnection(url,username,password);
    }
    Connection getConnection(){
        return connection;
    }

    public User FetchUser(int id)
    {
        User user;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select * from user,credentials where user.id = credentials.id and user.id = "+id);
            rs.next();
            PersonalData personalData = new PersonalData(rs.getString(10),rs.getString(2),rs.getString(3),rs.getString(5),rs.getString(4));
            personalData.setEmail(rs.getString(12));
            HashMap<Integer,String> Friends;
            if(rs.getBytes(6)!=null)
            {
            	 ObjectInputStream os =new ObjectInputStream(new ByteArrayInputStream(rs.getBytes(6)));
            	 Friends = (HashMap<Integer,String>)os.readObject();
            	 Friends.forEach((key,value)->{System.out.println(key+":"+value);});
            }
            else {
            	Friends = new HashMap<Integer, String>();
            }
            ObjectInputStream os =new ObjectInputStream(new ByteArrayInputStream(rs.getBytes(7)));
            Data data =(Data)os.readObject();
            user = new User(personalData,data);
            user.setId(rs.getInt(1));
            user.setFriends(Friends);
            user.setActive(rs.getBoolean(10));
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select profile,cover,posts from pictures where id="+id);
            rs.next();
            user.set_pictures(rs.getString(1),rs.getString(2));
            if (rs.getString("posts")!=null)
            {
                Stack<Album> albumStack = new Gson().fromJson(rs.getString("posts"),new TypeToken<Stack<Album>>(){}.getType());
                user.setAlbums(albumStack);
            }
            //System.out.println(new Gson().toJson(user));
            return user;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void save(User user) throws SQLException {
        pstmt = connection.prepareStatement("update user set name = ?,dob = ?, gender =?,friends = ?,data =?,requests =?,notifications=? where id=?");
        pstmt.setString(1,user.getPersonalData().getName());
        pstmt.setString(2,user.getPersonalData().getDob());
        pstmt.setString(3,user.getPersonalData().getGender());
        pstmt.setObject(4,user.getFriends());
        pstmt.setObject(5,user.getData());
        pstmt.setObject(6,user.getRequests());
        pstmt.setObject(7,user.getNotifications());
        pstmt.setInt(8,user.getId());
        pstmt.execute();

        pstmt = connection.prepareStatement("update pictures set profile=?,cover=? where id=?");
        pstmt.setString(1,user.getPrf_picture());
        pstmt.setString(2,user.getCvr_picture());
        pstmt.setInt(3,user.getId());
        pstmt.execute();
    }

    void closeConnection() throws SQLException {
        this.connection.close();
    }
}
