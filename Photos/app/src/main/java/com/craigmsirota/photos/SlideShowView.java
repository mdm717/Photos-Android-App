package com.craigmsirota.photos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Displays a photo in a larger viewing window than the GridView from the AlbumView
 * Also displays tags and operations for the user to search, add tags, and delete tags
 * @author Craig Sirota cms631
 * @author Matt Marrazzo mdm289
 */

public class SlideShowView extends AppCompatActivity {
    public static int index;
    private int tagIndex = -1;
    public static Button add;
    private Button prev, next, delete;
    public ImageView imgView;
    public static GridView gridView;
    public static ArrayAdapter tagAdapter;

    /**
     * This method deactivates the delete tag button if there are no tags upon resuming
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (AlbumView.imgAdapter.uris.get(index).tags.size() == 0) {
            delete.setVisibility(View.INVISIBLE);
        } else {

            delete.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method sets the data and click listeners when an activity is created
     * @param savedInstanceState    Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show_view);

        index = AlbumView.index;

        imgView = (ImageView) findViewById(R.id.imageView);
        gridView = (GridView) findViewById(R.id.gridView);

        tagAdapter = new ArrayAdapter<Tag>(this, android.R.layout.simple_list_item_1, AlbumView.imgAdapter.uris.get(index).tags);

        gridView.setAdapter(tagAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tagIndex = i;
            }
        });

        prev = (Button) findViewById(R.id.prev);
        next = (Button) findViewById(R.id.next);
        add = (Button) findViewById(R.id.Add_Tag);
        delete = (Button) findViewById(R.id.Del_Tag);

        if (AlbumView.imgAdapter.uris.get(index).tags.size() == 0) {
            delete.setVisibility(View.INVISIBLE);
        } else {
            delete.setVisibility(View.VISIBLE);
        }

        if (AlbumView.imgAdapter.getCount() == 1) {
            prev.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
        } else if (index == 0) {
            prev.setVisibility(View.INVISIBLE);
        } else if (index == AlbumView.imgAdapter.getCount() - 1) {
            next.setVisibility(View.INVISIBLE);
        }

        try {
            //InputStream pictureInputStream = getContentResolver().openInputStream(AlbumView.album.list.get(index).getUri());
            InputStream pictureInputStream = getContentResolver().openInputStream(AlbumView.imgAdapter.uris.get(index).getUri());
            Bitmap currPic = BitmapFactory.decodeStream(pictureInputStream);
            imgView.setImageBitmap(currPic);
        } catch (FileNotFoundException e) {
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
                if (index > 0) {
                    index--;
                    if (AlbumView.imgAdapter.uris.get(index).tags.size() == 0) {
                        delete.setVisibility(View.INVISIBLE);
                    } else {
                        delete.setVisibility(View.VISIBLE);
                    }

                    if (next.getVisibility() == View.INVISIBLE) {
                        next.setVisibility(View.VISIBLE);
                    }
                    if (index == 0) {
                        prev.setVisibility(View.INVISIBLE);
                    }
                    try {
                        //InputStream pictureInputStream = getContentResolver().openInputStream(AlbumView.album.list.get(index).getUri());
                        InputStream pictureInputStream = getContentResolver().openInputStream(AlbumView.imgAdapter.uris.get(index).getUri());
                        Bitmap currPic = BitmapFactory.decodeStream(pictureInputStream);
                        imgView.setImageBitmap(currPic);

                        tagAdapter = new ArrayAdapter<Tag>(getApplicationContext(), android.R.layout.simple_list_item_1, AlbumView.imgAdapter.uris.get(index).tags);

                        gridView.setAdapter(tagAdapter);
                    } catch (FileNotFoundException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index < AlbumView.imgAdapter.getCount() - 1) {
                    index++;
                    if (AlbumView.imgAdapter.uris.get(index).tags.size() == 0) {
                        delete.setVisibility(View.INVISIBLE);
                    } else {
                        delete.setVisibility(View.VISIBLE);
                    }

                    if (prev.getVisibility() == View.INVISIBLE) {
                        prev.setVisibility(View.VISIBLE);
                    }
                    if (index == AlbumView.imgAdapter.getCount() - 1) {
                        next.setVisibility(View.INVISIBLE);
                    }
                    try {
                        //InputStream pictureInputStream = getContentResolver().openInputStream(AlbumView.album.list.get(index).getUri());
                        InputStream pictureInputStream = getContentResolver().openInputStream(AlbumView.imgAdapter.uris.get(index).getUri());
                        Bitmap currPic = BitmapFactory.decodeStream(pictureInputStream);
                        imgView.setImageBitmap(currPic);

                        tagAdapter = new ArrayAdapter<Tag>(getApplicationContext(), android.R.layout.simple_list_item_1, AlbumView.imgAdapter.uris.get(index).tags);

                        gridView.setAdapter(tagAdapter);
                    } catch (FileNotFoundException e1) {
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
                if (tagIndex != -1) {
                    AlbumView.imgAdapter.uris.get(index).tags.remove(tagIndex);
                    tagAdapter = new ArrayAdapter<Tag>(getApplicationContext(), android.R.layout.simple_list_item_1, AlbumView.imgAdapter.uris.get(index).tags);

                    gridView.setAdapter(tagAdapter);

                    write();
                }

                if (AlbumView.imgAdapter.uris.get(index).tags.size() == 0) {
                    delete.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /**
     * This method takes the app data and saves it into the corresponding album file
     */
    public void write(){
// FILE PATH    /data/user/0/com.craigmsirota.photos/files/albums.albm
        try {
            ArrayList<Photo> uris = AlbumView.imgAdapter.getPhotos();
            ArrayList<Tag> tags = new ArrayList<>();

            String str = "";
            FileOutputStream fileOutputStream = openFileOutput(HomeScreen.albumName+".list", MODE_PRIVATE);
            for (Photo u : uris) {
                if (str.equals("")) {
                    str = u.toString();

                } else {
                    str = str + "\n" + u.toString();
                }
            }

            fileOutputStream.write(str.getBytes());
            //fileOutputStream.write("BACON".getBytes());

        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}

