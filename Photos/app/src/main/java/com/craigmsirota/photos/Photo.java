package com.craigmsirota.photos;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class Photo implements Serializable {
    public ArrayList<Tag> tags = new ArrayList<Tag>();
    private Uri uri;

    public Photo(Uri uri) { this.uri = uri; }

    public void addTag(String type, String data){
        tags.add(new Tag(type, data));
        return;
    }

    public void addTag(String type){
        String data = type.substring(type.indexOf("=")+1);
        type = type.substring(0,type.indexOf("="));
        tags.add(new Tag(type, data));
        return;
    }

    public boolean deleteTag(String type, String data){
        return false;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String toString() {
        String str = uri.toString();

        for (Tag t : tags) {
            str = str + "\nTAG:" + t;
        }

        return str;

    }

}
