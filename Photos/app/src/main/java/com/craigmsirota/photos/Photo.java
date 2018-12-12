package com.craigmsirota.photos;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a photo with a Uri and tags
 * @author Craig Sirota cms631
 * @author Matt Marrazzo mdm289
 */

public class Photo implements Serializable {
    public ArrayList<Tag> tags = new ArrayList<Tag>();
    private Uri uri;

    /**
     * This constructor creates a new photo object and assigns the photo's Uri
     * @param uri   the intended Uri of the photo object
     */
    public Photo(Uri uri) { this.uri = uri; }

    /**
     * Adds a tag to the photo
     * @param type  String containing either "Person" or "Location"
     * @param data  String containing the actual data of the tag
     */
    public void addTag(String type, String data){
        tags.add(new Tag(type, data));
        return;
    }

    /**
     * Adds a tag to the photo
     * @param tag   String containing the tag in the format "[Type]=[Data]" which will
     */
    public void addTag(String tag){
        String data = tag.substring(tag.indexOf("=")+1);
        tag = tag.substring(0,tag.indexOf("="));
        tags.add(new Tag(tag, data));
        return;
    }

    /**
     * Gets the Uri of the photo
     * @return  Uri of the photo
     */
    public Uri getUri() {
        return uri;
    }

    /**
     * Gives the caller a string containing the Uri of the photo and the tags
     * @return  String formatted to have the photo returned on the first line and each tag on following lines
     */
    public String toString() {
        String str = uri.toString();

        for (Tag t : tags) {
            str = str + "\nTAG:" + t;
        }

        return str;

    }

}
