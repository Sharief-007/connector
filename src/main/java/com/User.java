package com;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

@XmlRootElement
@SuppressWarnings("serial")
public class User implements Serializable {
    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String prf_picture;
    private String cvr_picture;
    public User() {}
    private PersonalData personalData;
    private Data data;
    private boolean active =false;

    HashMap<Integer,String> Friends = new HashMap<Integer, String>();

    ArrayList<Notifications> notifications = new ArrayList<Notifications>();
    ArrayList<Integer> requests = new ArrayList<Integer>();
    private Stack<Album> albums = new Stack<Album>();

    public User(PersonalData personalData, Data data) {
        this.personalData = personalData;
        this.data = data;
    }
    @XmlElement
    public PersonalData getPersonalData() {
        return personalData;
    }
    @XmlElement
    public Data getData() {
        return data;
    }
    @XmlElement
    public String getPrf_picture() {
        return prf_picture;
    }

    public void setPrf_picture(String prf_picture) {
        this.prf_picture = prf_picture;
    }
    @XmlElement
    public String getCvr_picture() {
        return cvr_picture;
    }

    public void setCvr_picture(String cvr_picture) {
        this.cvr_picture = cvr_picture;
    }

    void set_pictures(String prf_picture,String cvr_picture)
    {
        this.prf_picture = prf_picture;
        this.cvr_picture = cvr_picture;
    }

    public List<Notifications> getNotifications() {
        return notifications;
    }
    public void setNotifications(ArrayList<Notifications> notifications)
    {
        this.notifications = notifications;
    }
    public void addNotifications(Notifications not) {
        this.notifications.add(not);
    }

    public ArrayList<Integer> getMyFriends() {
        ArrayList<Integer> myFriends = new ArrayList<Integer>();
        this.Friends.forEach((key,value)->
        {
            myFriends.add(key);
        });
        return myFriends;
    }

//    public void setMyFriends(ArrayList<Integer> myFriends) {
//        this.myFriends = myFriends;
//    }

    public ArrayList<Integer> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Integer> requests) {
        this.requests = requests;
    }

    @XmlAttribute
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

    public HashMap<Integer, String> getFriends() {
        return Friends;
    }

    public void setFriends(HashMap<Integer, String> friends) {
        Friends = friends;
    }

    public Stack<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Stack<Album> albums) {
        this.albums = albums;
    }


}