package com;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/PostServlet")
@MultipartConfig
public class PostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            DatabaseManager db = new DatabaseManager();
            User user = (User)request.getSession().getAttribute("user");

            int numberOfPosts = getCount(db,user);

            String albumName = "Album"+(numberOfPosts+1);
            String className = "mySlides"+(numberOfPosts+1);
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();

            Collection<Part> parts = request.getParts();
            out.println("Number of files from user :"+parts.size());
            out.println("Album Name :"+albumName);


            Album album = new Album(numberOfPosts+1,albumName,className);
            album.setClassName(className);
            String path = Folder(request,user,albumName);


            Iterator<Part> iterator = parts.iterator();
            while (iterator.hasNext())
            {
                Part part = iterator.next();
                if (part.getSize() == 0)
                {
                    out.println(part.getSubmittedFileName()+": is Empty.");
                    continue;
                }
                else {
                    String filename = "media"+(album.getMedia().size()+1)+"."+getExtension(part.getSubmittedFileName());
                    album.addMedia(filename,part.getContentType());


                    out.println("Full path :"+path+File.separator+filename);
                    part.write(path+File.separator+filename);

                }

            }

            album.getMedia().entrySet().forEach(System.out::println);

            if (numberOfPosts==0){
            PreparedStatement statement = db.getConnection().prepareStatement("update pictures set posts =? where id = ?");
            Stack<Album> stckOfAlbums = new Stack<Album>();
            stckOfAlbums.push(album);
            statement.setString(1,new Gson().toJson(stckOfAlbums));
            statement.setInt(2,user.getId());
            statement.execute();
            }else {
                Stack<Album> previousAlbums = getPreviousAlbums(db,user);
                previousAlbums.push(album);
                PreparedStatement statement = db.getConnection().prepareStatement("update pictures set posts = ? where id = ?");
                statement.setString(1,new Gson().toJson(previousAlbums));
                statement.setInt(2,user.getId());
                statement.execute();
            }

            incrementCount(db,user,numberOfPosts);



            //user.setAlbums(UserAlbums);

            //request.getSession().setAttribute("user",user);

            //UserAlbums.forEach(a->{a.getMedia().entrySet().forEach(System.out::println);});

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void incrementCount(DatabaseManager db, User user, int numberOfPosts) throws SQLException {
        int up = numberOfPosts+1;
        db.getConnection().createStatement().execute("update pictures set count = "+up+" where id = "+user.getId());
    }

    private String getExtension (String fileName)
    {
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    private String Folder(HttpServletRequest req, User user,String albumName) {
        String path = req.getServletContext().getRealPath("");
        path = path+File.separator+"images"+File.separator+user.getPersonalData().getUsername();
        path = path+File.separator+albumName;
        File file = new File(path);
        if (! file.exists())
        {
            file.mkdirs();
        }
        return file.getPath();
    }
    private HashMap<String,String> sortMyHAshMap(HashMap<String,String> hashMap)
    {
        HashMap<String,String> sorted = new HashMap<>();
        ArrayList<String> keys = new ArrayList<>(hashMap.keySet());
        System.out.println(keys);
        Collections.sort(keys);
        keys.forEach(k->{
            sorted.put(k,hashMap.get(k));
        });
        return  sorted;
    }

    private int getCount(DatabaseManager db,User user) throws SQLException {
        ResultSet rs = db.getConnection().createStatement().executeQuery("select count from pictures where id ="+user.getId());
        rs.next();
        return rs.getInt("count");
    }

    private Stack<Album> getPreviousAlbums(DatabaseManager db,User user) throws SQLException {
        ResultSet rs = db.getConnection().createStatement().executeQuery("select posts from pictures where id = "+user.getId());
        rs.next();
        String json = rs.getString("posts");
        Type type = new TypeToken<Stack<Album>>(){}.getType();

        Stack<Album> previousAlbums = new Gson().fromJson(json,type);
        return previousAlbums;
    }
}
