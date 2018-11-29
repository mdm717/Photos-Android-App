package com.craigmsirota.photos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class AlbumView extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 42;
    public static GridView gridView;
    Button add, copy, paste, display, delete, move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_view);

        gridView = (GridView) findViewById(R.id.GridView);
        add = (Button) findViewById(R.id.add);
        copy = (Button) findViewById(R.id.Copy);
        paste = (Button) findViewById(R.id.paste);
        display = (Button) findViewById(R.id.display);
        delete = (Button) findViewById(R.id.delete);
        move = (Button) findViewById(R.id.move);

        add.setOnClickListener(new OnClickListener(){

            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, READ_REQUEST_CODE);

            }
            
        });
    }
}
