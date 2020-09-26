package com;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet(asyncSupported = true,name = "NotificationParser",urlPatterns = "/NotificationParser")
public class NotificationParser extends HttpServlet {
    Gson gson = new Gson();
    DatabaseManager db;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        PrintWriter out=resp.getWriter();
        ArrayList<Notifications> currentNotificationsList;
        User user = (User) req.getSession().getAttribute("user");
        int id= user.getId();
        try {
            ResultSet result = fetchNotificationsAndRequests(id);
            if (result.next())
            {
                if (result.getBytes(1)!=null) {
                    ObjectInputStream os = new ObjectInputStream(new ByteArrayInputStream(result.getBytes(1)));
                    currentNotificationsList = (ArrayList<Notifications>)os.readObject();
                    System.out.println("Full list"+currentNotificationsList.size());
                    //System.out.println(currentNotificationsList);
//                    unseen = new ArrayList<Notifications>();
//                    for (Notifications n:currentNotificationsList)
//                    {
//                        if (!n.isSeen())
//                        {
//                            unseen.add(n);
//                        }
//                    }
//                    System.out.println("Short list :"+unseen.size());

                    out.println(gson.toJson(currentNotificationsList));
                    //System.out.println(gson.toJson(currentNotificationsList));
                }
                else {
                    currentNotificationsList = new ArrayList<>();
                    out.println(gson.toJson(currentNotificationsList));
                    //System.out.println(gson.toJson(currentNotificationsList));
                }
                db.closeConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ResultSet fetchNotificationsAndRequests(int id) throws Exception {
        db= new DatabaseManager();
        PreparedStatement stmt = db.getConnection().prepareStatement("select notifications,requests from user where id = ?");
        stmt.setInt(1,id);
        ResultSet rs=stmt.executeQuery();
        return rs;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action)
        {
            case "delete": deleteNotification(req,resp);
                            break;
            case "accept":
                        deleteNotification(req,resp);
                        acceptFriendRequest(req,resp);
                        break;
            case "marker": marker(req, resp);
                        break;
        }
    }

    private ArrayList<Notifications> Remover(ArrayList<Notifications> list,int from,int to,String TYPE){
        Iterator<Notifications> i = list.iterator();
        while (i.hasNext())
        {
            Notifications n = i.next();
            if (n.getFrom_id()==from && n.getTo_id() == to && n.getTYPE().equals(TYPE))
            {
                list.remove(n);
                break;
            }
        }
        return list;
    }

    private void deleteNotification(HttpServletRequest req,HttpServletResponse resp)
    {
        int from = Integer.parseInt(req.getParameter("from_id"));
        User user = (User)req.getSession().getAttribute("user");
        int to = user.getId();
        String TYPE = req.getParameter("TYPE");
        try {
            db = new DatabaseManager();
            ResultSet result = fetchNotificationsAndRequests(to);
            if(result.next())
            {
                ObjectInputStream os = new ObjectInputStream(new ByteArrayInputStream(result.getBytes(1)));
                ArrayList<Notifications> list = (ArrayList<Notifications>) os.readObject();
                os = new ObjectInputStream(new ByteArrayInputStream(result.getBytes(2)));
                ArrayList<Integer> requests = (ArrayList<Integer>) os.readObject();
                System.out.println("Before deleting Notification :"+list.size()+" requests: "+requests.size());

                requests.remove(new Integer(from));
                //requests.forEach(r->System.out.println("request:"+r));
                list = Remover(list,from,to,TYPE);
                System.out.println("After deleting Notifications: "+list.size()+"requests: "+requests.size()+"(message from notificationparser servlet)");
                PreparedStatement statement = db.getConnection().prepareStatement("update user set notifications =?,requests=? where id =?");
                statement.setObject(1,list);
                statement.setObject(2,requests);
                statement.setInt(3,to);
                resp.getWriter().println(statement.execute());
                db.closeConnection();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void acceptFriendRequest(HttpServletRequest req,HttpServletResponse resp)
    {
        int from = Integer.parseInt(req.getParameter("from_id"));
        HashMap<Integer,String> friendsOfMe = new HashMap<Integer, String>(),hisFriends = new HashMap<Integer, String>();
        try {
            User user = (User) req.getSession().getAttribute("user");
            db = new DatabaseManager();
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement("select friends from user where id = ?");
            stmt.setInt(1,user.getId());
            ResultSet rs2,rs1 = stmt.executeQuery();
            if (rs1.next())
            {
                if (rs1.getBytes(1)!=null)
                {
                    ObjectInputStream os =new ObjectInputStream(new ByteArrayInputStream(rs1.getBytes(1)));
                    friendsOfMe = (HashMap<Integer, String>) os.readObject();
                    System.out.println("Before adding friendsOfMe :"+friendsOfMe);
                }
            }
            stmt.setInt(1,from);
            rs2 = stmt.executeQuery();
            if (rs2.next())
            {
                if (rs2.getBytes(1)!=null)
                {
                    ObjectInputStream os =new ObjectInputStream(new ByteArrayInputStream(rs2.getBytes(1)));
                    hisFriends = (HashMap<Integer, String>) os.readObject();
                    System.out.println("Before adding hisFriends :"+hisFriends);
                }
            }

            String myChatTable = "table_"+user.getId()+"_"+from;
            String hisChatTable = "table_"+from+"_"+user.getId();

            friendsOfMe.put(from,myChatTable);
            hisFriends.put(user.getId(),hisChatTable);

            System.out.println("After adding friendsOfMe :"+friendsOfMe);
            System.out.println("After adding hisFriends :"+hisFriends);
            stmt = con.prepareStatement("update user set friends =? where id = ?");
            stmt.setObject(1,friendsOfMe);
            stmt.setInt(2,user.getId());
            stmt.execute();

            Notifications.requestAcceptNotification(user.getId(),from);
            stmt = con.prepareStatement("update user set friends =? where id = ?");
            stmt.setObject(1,hisFriends);
            stmt.setInt(2,from);
            stmt.execute();
            createMessageTables(con,myChatTable,hisChatTable);


            User updated = db.FetchUser(user.getId());
            req.getSession().setAttribute("user",updated);

            resp.setContentType("text/plain");
            resp.getWriter().println("Request deleted, Notification deleted, friend added, friend of him changed");
            con.close();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createMessageTables(Connection c,String myChatTable,String hisChatTable) throws SQLException {
        Statement stmt= c.createStatement();
        stmt.addBatch("create table if not exists "+myChatTable+"(message text not null ,type char(1) not null check (type in('s','r')))");
        stmt.addBatch("create table if not exists "+hisChatTable+"(message text not null ,type char(1) not null check (type in('s','r')))");
        stmt.executeBatch();
    }

    private void marker(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        try {
            ArrayList<Notifications> myNotifs = new ArrayList<>();
            String json = req.getParameter("jsonData");
            System.out.println(json);

            Gson gson = new Gson();
            HashMap<String,ArrayList<Double>> map = new HashMap<>();
            map = gson.fromJson(json,HashMap.class);

            User user = (User) req.getSession().getAttribute("user");
            ResultSet rs = fetchNotificationsAndRequests(user.getId());
            if (rs.next())
            {
                ObjectInputStream os = new ObjectInputStream(new ByteArrayInputStream(rs.getBytes(1)));
                myNotifs = (ArrayList<Notifications>) os.readObject();
//                System.out.println("Before marking :");
//                myNotifs.forEach(System.out::println);

                for (Map.Entry<String,ArrayList<Double>> obj: map.entrySet())
                {
                    int array[] = obj.getValue().stream().mapToInt(i -> i.intValue()).toArray();
                    myNotifs=Mark(myNotifs,array,obj.getKey());
                }
            }
//            System.out.println("After marking:");
//            myNotifs.forEach(System.out::println);

            db = new DatabaseManager();
            PreparedStatement stmt = db.getConnection().prepareStatement("update user set notifications=? where id =?");
            stmt.setObject(1,myNotifs);
            stmt.setInt(2,user.getId());
            stmt.execute();


            db.closeConnection();

        }catch (Exception e) {
            e.printStackTrace(out);
            e.printStackTrace();
        }
    }

    private ArrayList<Notifications> Mark(ArrayList<Notifications> N, int[] ids, String type)
    {
        //ArrayList<Notifications> output = N;
        for (int id :ids)
        {
            Iterator<Notifications> iterator = N.iterator();
            while (iterator.hasNext())
            {
                Notifications notif = iterator.next();
                if (notif.isSeen())
                {
                    continue;
                }
                else {
                    if (notif.getFrom_id() == id && notif.getTYPE().equals(type))
                    {
                       int index = N.indexOf(notif);
                       notif.setSeen();
                       N.set(index,notif);
                    }
                }
            }
        }

        return N;
    }
}

