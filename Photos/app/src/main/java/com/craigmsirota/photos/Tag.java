package com.craigmsirota.photos;

import java.io.Serializable;

/**
 * Represents a Tag for a photo
 * @author Craig Sirota cms631
 * @author Matt Marrazzo mdm289
 */

public class Tag implements Serializable {
    public String type;
    private String data;

    /**
     * This constructor sets the type and data of a tag object
     * @param type  String containing either "Location" or "Person"
     * @param data  String containing the actual data of the tag
     */
    public Tag(String type, String data){
        this.type = type;
        this.data = data;
    }

    /**
     * Sets the data of the tag
     * @param data String containing the new data for the tag
     */
    public void setData(String data){
        this.data = data;
    }

    /**
     * Gets the data of the tag for the caller
     * @return  String containing the tags data
     */
    public String getData(){
        return data;
    }

    /**
     * Gets the user a readable version of the tag
     * @return  String formatted so the user can easily read the tags
     */
    public String toString(){
        return type+"="+data;
    }
}
