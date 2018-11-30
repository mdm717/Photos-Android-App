package com.craigmsirota.photos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;


public class AlbumView extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 42;
    public static GridView gridView;
    Button add, copy, paste, display, delete, move;

    private static int index = 0;

    private static Photo photoCopy;

    public static Album album = new Album();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_view);

        gridView = findViewById(R.id.GridView);
        add = (Button) findViewById(R.id.add);
        copy = findViewById(R.id.Copy);
        paste = (Button) findViewById(R.id.paste);
        display = (Button) findViewById(R.id.display);
        delete = (Button) findViewById(R.id.delete);
        move = (Button) findViewById(R.id.move);

        add.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, READ_REQUEST_CODE);

            }

        });

        delete.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });

        paste.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view){

            }
        });

        display.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view){

            }
        });

       copy.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });

       move.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });
    }

    /**
     * This method is called after an image is chosen to be opened
     * during Add event by startActivityForResult
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == READ_REQUEST_CODE  && resultCode  == RESULT_OK && data != null) {

                index++;
                Photo picture = new Photo(data.toString());
                Uri imageUri = data.getData();

                /*
                try {
                    ImageView imageView = new ImageView();
                    gridView.addTouchables(new ImageView(bitmap));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */

            }


    }
}
