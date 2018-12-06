package com.craigmsirota.photos;

import java.io.Serializable;

public class Tag implements Serializable {
    public String type;
    private String data;

    public Tag(String type, String data){
        this.type = type;
        this.data = data;
    }

    public Tag() {}

    public void setData(String data){
        this.data = data;
    }

    public String getData(){
        return data;
    }

    public String toString(){
        return type+"="+data;
    }
}
