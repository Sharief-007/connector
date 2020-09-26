package com;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class Album implements Serializable {
    private int albumNumber;
    private String Album_ID,className;
    private TreeMap<Integer,ArrayList<String>> media = new TreeMap<Integer, ArrayList<String>>();

    public Album(int albumNumber, String album_ID, String className) {
        this.albumNumber = albumNumber;
        Album_ID = album_ID;
        this.className = className;
    }

    public int getAlbumNumber() {
        return albumNumber;
    }

    public void setAlbumNumber(int albumNumber) {
        this.albumNumber = albumNumber;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAlbum_ID() {
        return Album_ID;
    }

    public void setAlbum_ID(String album_ID) {
        Album_ID = album_ID;
    }

    public TreeMap<Integer,ArrayList<String>> getMedia() {
        return media;
    }

    public void setMedia(TreeMap<Integer,ArrayList<String>> media) {
        this.media = media;
    }

    public void addMedia(String file, String type)
    {
        ArrayList<String> m = new ArrayList<String>();
        m.add(file);
        m.add(type);
        this.media.put(media.size()+1,m);
    }

    public List<String> getMediaFiles()
    {
        ArrayList<String> files = new ArrayList<>();
        this.media.entrySet().forEach(k->{
            files.add(k.getValue().get(0));
        });
        return files;
    }
}
