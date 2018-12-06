package com.craigmsirota.photos;


import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;


    public static ArrayList<Photo> uris = new ArrayList<>();

    public ImageAdapter(Context c) {
        context = c;
    }

    @Override
    public int getCount() {
        return uris.size();
    }

    @Override
    public Object getItem(int index) {
        return null;
    }

    @Override
    public long getItemId(int index) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int index, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageURI(uris.get(index).getUri());
        return imageView;
    }

    public Uri getID(int index){
        return uris.get(index).getUri();
    }

    public Photo getPhoto(int index){
        return uris.get(index);
    }

    public void add(Uri add) {
        uris.add(new Photo(add));
    }

    public void add(Photo add) {
        uris.add(add);
    }

    public void remove(int index) {
        uris.remove(index);
    }

    public void remove(Uri integer) {
        uris.remove(integer);
    }


    public ArrayList<Photo> getPhotos() {
        return uris;
    }

    public void clear() {
        uris.clear();
    }

}

