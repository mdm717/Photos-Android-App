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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.craigmsirota.photos.Album;
import com.craigmsirota.photos.HomeScreen;
import com.craigmsirota.photos.ImageAdapter;
import com.craigmsirota.photos.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class AlbumView extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 42;
    public static ImageAdapter imgAdapter;
    public static GridView gridView;
    Button add, copy, paste, display, delete, move;

    public static int index = 0;

    private static Photo photoCopy;

    public static Album album = new Album();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_view);

        gridView = findViewById(R.id.GridView);
        add = (Button) findViewById(R.id.add);
        copy = findViewById(R.id.Copy);
        copy.setVisibility(View.INVISIBLE);
        paste = (Button) findViewById(R.id.paste);
        paste.setVisibility(HomeScreen.isCopy ? View.VISIBLE : View.INVISIBLE);
        display = (Button) findViewById(R.id.display);
        display.setVisibility(View.INVISIBLE);
        delete = (Button) findViewById(R.id.delete);
        delete.setVisibility(View.INVISIBLE);
        move = (Button) findViewById(R.id.move);
        move.setVisibility(View.INVISIBLE);

        imgAdapter = new ImageAdapter(this);
        final GridView gridview = (GridView) findViewById(R.id.GridView);

        read();

        gridview.setAdapter(imgAdapter);


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
                if (index>=0 && index<imgAdapter.getCount()) {
                    imgAdapter.remove(index);
                    index = -1;

                    makeVisible(false);

                        write();
                    gridView.setAdapter(imgAdapter);
                } else {

                    Toast.makeText(getApplicationContext(),
                            "Failed to delete image "+index, Toast.LENGTH_SHORT).show();
                }

            }
        });

        paste.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view){
                imgAdapter.add(HomeScreen.copy);
                gridView.setAdapter(imgAdapter);
                write();
            }
        });

        display.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view){

                Intent intent = new Intent(getApplicationContext(), SlideShowView.class);
                startActivity(intent);
            }
        });

       copy.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
               HomeScreen.isCopy = true;
               paste.setVisibility(View.VISIBLE);
               HomeScreen.copy = imgAdapter.getID(index);
           }
       });

       move.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {

               HomeScreen.isCopy = true;
               paste.setVisibility(View.VISIBLE);
               HomeScreen.copy = imgAdapter.getID(index);
               imgAdapter.remove(index);
               gridView.setAdapter(imgAdapter);
               write();
           }
       });

       gridView.setOnItemClickListener(new OnItemClickListener() {
           public void onItemClick(AdapterView<?> parent, View v,
                                   int position, long id) {
               index = position;
               if (index!= -1){
                   makeVisible(true);
               }
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
            Photo picture = new Photo(data.getData());
            Uri imageUri = data.getData();


            Toast.makeText(getApplicationContext(), imageUri.toString(),Toast.LENGTH_LONG).show();

            imgAdapter.add(imageUri);
            gridView.setAdapter(imgAdapter);
            album.list.add(picture);
            write();

        }
    }


    public void read() {
        String[] strings = {};
        imgAdapter.clear();
        try {
            FileInputStream fileInputStream = openFileInput(HomeScreen.albumName + ".list");

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineIn;

            while ((lineIn = bufferedReader.readLine()) != null) {
                if (lineIn.substring(0,4).equals("TAG:")) {
                    imgAdapter.getPhoto(imgAdapter.getCount()-1).addTag(lineIn.substring(4));
                } else {
                    imgAdapter.add(Uri.parse(lineIn));
                }
            }

            gridView.setAdapter(imgAdapter);

            Toast.makeText(this, "Read From " + getFilesDir() + File.separator + HomeScreen.albumName + ".list",
                    Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(){
// FILE PATH    /data/user/0/com.craigmsirota.photos/files/albums.albm
        try {
            ArrayList<Photo> uris = imgAdapter.getPhotos();

            String str = "";
            FileOutputStream fileOutputStream = openFileOutput(HomeScreen.albumName+".list", MODE_PRIVATE);
            for (Photo u : uris) {
                if (str.equals("")) {
                    str = u.toString();

                    Toast.makeText(this, "Wrote " +u.toString(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    str = str + "\n" + u.toString();
                    Toast.makeText(this, "Wrote " +u.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            fileOutputStream.write(str.getBytes());

            Toast.makeText(this, "Saved to " + getFilesDir() + File.separator + HomeScreen.albumName+".list",
                    Toast.LENGTH_LONG).show();

        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void makeVisible(boolean vis){
        copy.setVisibility(vis ? View.VISIBLE : View.INVISIBLE);
        move.setVisibility(vis ? View.VISIBLE : View.INVISIBLE);
        display.setVisibility(vis ? View.VISIBLE : View.INVISIBLE);
        delete.setVisibility(vis ? View.VISIBLE : View.INVISIBLE);
    }
}
