package com.craigmsirota.photos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

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

//        System.out.println(AlbumView.imgAdapter.uris.get(index).getUri().toString()+"-------------------------");
        AlbumView.imgAdapter.uris.get(index).getUri();
        imgView.setImageURI(AlbumView.imgAdapter.uris.get(index).getUri());
        tagAdapter = new ArrayAdapter<Tag>(this, android.R.layout.simple_list_item_1, AlbumView.imgAdapter.uris.get(index).tags);
        gridView.setAdapter(tagAdapter);

        setContentView(R.layout.activity_slide_show_view);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
