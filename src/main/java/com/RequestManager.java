package com;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/RequestManager")
public class RequestManager extends HttpServlet {
    DatabaseManager db;
    ResultSet result;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String from_id = req.getParameter("from_id");
        String to_id = req.getParameter("to_id");
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        out.println("from :"+from_id);
        out.println("to :"+to_id);
        out.println("Values received");
        try {
            out.println("started fetching");
            ArrayList<Integer> requests = fetchRequests(to_id);
            requests.add(Integer.parseInt(from_id)); //request added
            out.println("request added");

            //add the notification of request to the user
            Notifications.friendRequestNotification(Integer.parseInt(from_id),Integer.parseInt(to_id));
            //Notification added to the database
            out.println("notification added");

            //save changes
            postRequests(to_id,requests);
            out.println("Changes saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void postRequests(String to_id,ArrayList<Integer> requests) throws Exception {
        db = new DatabaseManager();
        PreparedStatement stmt = db.getConnection().prepareStatement("update user set requests = (?) where id ="+to_id);
        stmt.setObject(1,requests);
        stmt.execute();
    }

    private ArrayList<Integer> fetchRequests(String to_id) throws Exception {
        db = new DatabaseManager();
        Statement stmt = db.getConnection().createStatement();
        result = stmt.executeQuery("select requests from user where id ="+to_id);
        ArrayList<Integer> requests;
        result.next();
        if(result.getBytes(1)!=null) {
            ObjectInputStream os = new ObjectInputStream(new ByteArrayInputStream(result.getBytes(1)));
             requests = (ArrayList<Integer>) os.readObject();
        }
        else
        {
            requests = new ArrayList<Integer>();
        }
        return requests;
    }
}
