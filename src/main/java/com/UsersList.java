package com;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/UsersList")
public class UsersList extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User myuser = (User) req.getSession().getAttribute("user");
        try {
            Users members = new Users();
            DatabaseManager db = new DatabaseManager();
            Connection con = db.getConnection();

            if(req.getParameter("want")!=null)
            {
                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                Gson gson = new Gson();

                HashMap<Integer,ArrayList<String>> details =new HashMap<Integer, ArrayList<String>>();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select user.id, username, active, profile from user,pictures where user.id=pictures.id");
                while (rs.next())
                {
                    ArrayList<String> temp = new ArrayList<String>();
                    temp.add(rs.getString(2));
                    temp.add(String.valueOf(rs.getBoolean(3)));
                    temp.add(rs.getString(4));
                    details.put(rs.getInt(1),temp);
                }
                out.println(gson.toJson(details));
            }
            else
            {

                PreparedStatement stmt = con.prepareStatement("select user.id, name, username, dob, gender, data, profile, cover, active, requests from user,pictures where user.id = pictures.id and user.id != ?");
                stmt.setString(1,String.valueOf(myuser.getId()));
                ResultSet rs = stmt.executeQuery();
                stmt = con.prepareStatement("select friends from user where id =?");
                stmt.setInt(1,myuser.getId());
                ResultSet resultSet2 = stmt.executeQuery();
                resultSet2.next();
                ObjectInputStream os = new ObjectInputStream(new ByteArrayInputStream(resultSet2.getBytes(1)));
                HashMap<Integer,String> myFriends = (HashMap<Integer, String>)os.readObject();

                while (rs.next())
                {
                    if (myFriends.containsKey(rs.getInt(1)))
                    {
                        continue;
                    }
                    PersonalData pd = new PersonalData(rs.getString(2),rs.getString(3),rs.getString(5),rs.getString(4));
                     os = new ObjectInputStream(new ByteArrayInputStream(rs.getBytes(6)));
                    Data data = (Data)os.readObject();
                    User user = new User(pd,data);
                    user.set_pictures(rs.getString(7),rs.getString(8));
                    user.setId(rs.getInt(1));
                    user.setActive(rs.getBoolean(9));
                    if (rs.getBytes(10)!=null)
                    {
                        os = new ObjectInputStream(new ByteArrayInputStream(rs.getBytes(10)));
                        ArrayList<Integer> reqsts = (ArrayList<Integer>) os.readObject();
                        user.setRequests(reqsts);
                    }else { user.setRequests(new ArrayList<Integer>());}

                    members.user.add(user);
                }

                resp.setContentType("text/xml");
                PrintWriter out = resp.getWriter();
                JAXBContext contextObj = JAXBContext.newInstance(Users.class);
                Marshaller marshallerObj = contextObj.createMarshaller();
                marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                StringWriter writer = new StringWriter();
                marshallerObj.marshal(members,writer);
                System.out.println(writer.toString());
                out.println(writer.toString());
            }
            con.close();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
@XmlRootElement
class Users {
    List<User> user = new ArrayList<User>();
    public List<User> getUser() { return user; }
    public void setUser(List<User> user) { this.user = user; }
}
