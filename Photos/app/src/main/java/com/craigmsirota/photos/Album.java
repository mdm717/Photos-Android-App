package com.craigmsirota.photos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents an Album of photo
 * @author Craig Sirota cms631
 * @author Matt Marrazzo mdm289
 */

public class Album implements Serializable {
    private String albumName;
    public ArrayList<Photo> list = new ArrayList<Photo>();

    /**
     * This is the no-args constructor
     */
    public Album() {}

    /**
     * This constructor sets the album name
     * @param albumName String  the name of the album
     */
    public Album(String albumName){ this.albumName = albumName; }

    /**
     * Returns the name of an album
     * @return  a string containing the name of the album
     */
    public String getAlbumName(){ return albumName; }

    /**
     * Sets a new name for an album
     * @param albumName String
     */
    public void setAlbumName(String albumName){ this.albumName = albumName; }

    /**
     * Returns the name of an album
     * @return  a string containing the name of the album
     */
    public String toString(){
        return albumName;
    }
}
