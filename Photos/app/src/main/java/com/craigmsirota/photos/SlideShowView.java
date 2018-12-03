package com.craigmsirota.photos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.craigmsirota.photos.AlbumView.album;

public class SlideShowView extends AppCompatActivity {
    public static int index;
    private Button prev, next, add, delete;
    public ImageView imgView;
    public static GridView gridView;
    public static ArrayAdapter<Tag> tagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show_view);

        index = AlbumView.index;

        imgView = (ImageView) findViewById(R.id.imageView);
        gridView = (GridView) findViewById(R.id.gridView);

        prev = (Button) findViewById(R.id.prev);
        next = (Button) findViewById(R.id.next);
        add = (Button) findViewById(R.id.Add_Tag);
        delete = (Button) findViewById(R.id.Del_Tag);

        try {
            //InputStream pictureInputStream = getContentResolver().openInputStream(AlbumView.album.list.get(index).getUri());
            InputStream pictureInputStream = getContentResolver().openInputStream(AlbumView.imgAdapter.uris.get(index).getUri());
            Bitmap currPic = BitmapFactory.decodeStream(pictureInputStream);
            imgView.setImageBitmap(currPic);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }




//        System.out.println(AlbumView.imgAdapter.uris.get(index).getUri().toString()+"-------------------------");
        /*
        AlbumView.imgAdapter.uris.get(index).getUri();
        imgView.setImageURI(AlbumView.imgAdapter.uris.get(index).getUri());
        tagAdapter = new ArrayAdapter<Tag>(this, android.R.layout.simple_list_item_1, AlbumView.imgAdapter.uris.get(index).tags);
        gridView.setAdapter(tagAdapter);
        */


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index>0){
                    index--;
                    try {
                        //InputStream pictureInputStream = getContentResolver().openInputStream(AlbumView.album.list.get(index).getUri());
                        InputStream pictureInputStream = getContentResolver().openInputStream(AlbumView.imgAdapter.uris.get(index).getUri());
                        Bitmap currPic = BitmapFactory.decodeStream(pictureInputStream);
                        imgView.setImageBitmap(currPic);
                    } catch (FileNotFoundException e2){
                        e2.printStackTrace();
                    }
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index<AlbumView.imgAdapter.uris.size()-1) {
                    index++;
                    try {
                        //InputStream pictureInputStream = getContentResolver().openInputStream(AlbumView.album.list.get(index).getUri());
                        InputStream pictureInputStream = getContentResolver().openInputStream(AlbumView.imgAdapter.uris.get(index).getUri());
                        Bitmap currPic = BitmapFactory.decodeStream(pictureInputStream);
                        imgView.setImageBitmap(currPic);
                    }catch(FileNotFoundException e1){
                        e1.printStackTrace();
                    }
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewTag.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
