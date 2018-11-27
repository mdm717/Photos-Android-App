package com.craigmsirota.photos;

import java.io.Serializable;
import java.util.ArrayList;

public class Photo implements Serializable {
    public ArrayList<Tag> tags = new ArrayList<Tag>();
    private String url;

    public Photo(String url) { this.url = url; }

    public void addTag(String type, String data){
        tags.add(new Tag(type, data));
        return;
    }

    public boolean deleteTag(String type, String data){
        return false;
    }
}
