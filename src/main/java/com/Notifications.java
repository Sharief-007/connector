package com;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Notifications implements Serializable {
    final static private String types[] ={"friend-request", "request-accept", "message"};
    private String TYPE=null;
    private boolean seen = false;
    private int from_id,to_id;
    private Notifications(String TYPE) throws Exception {
       for(String s:types)
       {
           if (s.equals(TYPE))
           {
               this.TYPE =TYPE;
               break;
           }
       }
       if (this.TYPE==null)
       {
           throw new Exception("Selected type is not defined");
       }
    }

    public String getTYPE() {
        return TYPE;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen() { this.seen = true; }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public static void friendRequestNotification(int from, int to) throws Exception {
        sendNotification(from,to,types[0]);
    }

    public static void requestAcceptNotification(int from, int to) throws Exception {
        sendNotification(from,to,types[1]);
    }

    public static void MessageNotification(int from, int to) throws Exception {
        sendNotification(from,to,types[2]);
    }

    @Override
    public String toString() {
        return "Notifications{" +
                "TYPE='" + TYPE + '\'' +
                ", seen=" + seen +
                ", from_id=" + from_id +
                ", to_id=" + to_id +
                '}';
    }

    private static void sendNotification(int from,int to, String TYPE) throws Exception {
        DatabaseManager db = new DatabaseManager();
        PreparedStatement statement = db.getConnection().prepareStatement("select notifications from user where id = ?");
        statement.setInt(1,to);
        ResultSet rs = statement.executeQuery();
        Notifications notification = new Notifications(TYPE);
        notification.setFrom_id(from);
        notification.setTo_id(to);
        ArrayList<Notifications> notificationsList;
        if(rs.next() && rs.getBytes(1)!=null) {
            ObjectInputStream os = new ObjectInputStream(new ByteArrayInputStream(rs.getBytes(1)));
            notificationsList = (ArrayList<Notifications>)os.readObject();
            notificationsList.forEach(n-> System.out.println("Before : "+n));
        }else {
            notificationsList = new ArrayList<>();
            System.out.println("Empty notifications list");
        }
        //addding the notification to fetched list
        notificationsList.add(notification);
        notificationsList.forEach(n -> System.out.println("After :"+n));
        statement = db.getConnection().prepareStatement("update user set notifications = ? where id =?");
        statement.setObject(1,notificationsList);
        statement.setInt(2,to);
        statement.execute();

        db.closeConnection();
    }
}
