package com.craigmsirota.photos;

import java.io.Serializable;
import java.util.ArrayList;


public class Album implements Serializable {
    private String albumName;
    public ArrayList<Photo> list = new ArrayList<Photo>();

    public Album() {}

    public Album(String albumName){ this.albumName = albumName; }

    public String getAlbumName(){ return albumName; }

    public void setAlbumName(String albumName){ this.albumName = albumName; }

    public String toString(){
        return albumName;
    }
}
