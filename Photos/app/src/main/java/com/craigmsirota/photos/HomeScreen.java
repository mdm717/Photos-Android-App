package com.craigmsirota.photos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Displays the list of albums and valid operations on the albums, such as add, delete, search, open
 * @author Craig Sirota cms631
 * @author Matt Marrazzo mdm289
 */

public class HomeScreen extends AppCompatActivity {
    public static GridView gridView;
    Button newButton, delete, rename, open, search;
    public static ArrayList<String> albums;
    public static String albumName;
    private static int index;
    public static Photo copy;
    public static boolean isCopy;
    Album stockAlbum = new Album();

    /**
     * This method sets the data and click listeners when an activity is created
     * @param savedInstanceState    Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        albums = new ArrayList<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);



        gridView = (GridView) findViewById(R.id.gridView1);
        search = (Button) findViewById(R.id.search);
        newButton = (Button) findViewById(R.id.newButton);
        open = (Button) findViewById(R.id.open);
        delete = (Button) findViewById(R.id.delete);
        rename = (Button) findViewById(R.id.rename);
        open.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        rename.setVisibility(View.INVISIBLE);

        isCopy = false;

        for (String s:read()) {
            albums.add(s);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, albums);

        gridView.setAdapter(arrayAdapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                index = position;
                open.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                rename.setVisibility(View.VISIBLE);
            }
        });

        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index>=0 && index<albums.size()) {
                    getApplicationContext().deleteFile(new File(albums.get(index)+".list").getName());
//                    .delete();
                    albums.remove(index);
                    index = -1;

                    write();
                    albums.clear();
                    for (String s : read()) {
                        albums.add(s);
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_list_item_1, albums);

                    gridView.setAdapter(arrayAdapter);

                    open.setVisibility(View.INVISIBLE);
                    delete.setVisibility(View.INVISIBLE);
                    rename.setVisibility(View.INVISIBLE);
                } else {

                    Toast.makeText(getApplicationContext(),
                            "Failed to delete\nIndex = "+index+"\nSize = "+albums.size(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        open.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                albumName=albums.get(index);
                openAlbum();
            }
        });

        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearch();
            }
        });

        newButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewAlbum();
            }
        });


        rename.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditAlbum();
            }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("firstTime", true)) {
            // <---- run your one time code here
            try {
                String stockAlbumName = "stock";
                FileOutputStream fileOutputStream = openFileOutput(stockAlbumName + ".list", MODE_PRIVATE);
                HomeScreen.albums.add(stockAlbumName);
                String str = "android.resource://com.craigmsirota.photos/raw/stock1";
                addPhoto(str, fileOutputStream);
                str = "android.resource://com.craigmsirota.photos/raw/stock2";
                addPhoto(str, fileOutputStream);
                str = "android.resource://com.craigmsirota.photos/raw/stock3";
                addPhoto(str, fileOutputStream);
                str = "android.resource://com.craigmsirota.photos/raw/stock4";
                addPhoto(str, fileOutputStream);
                str = "android.resource://com.craigmsirota.photos/raw/stock5";
                addPhoto(str, fileOutputStream);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
            // mark first time has ran.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();
        }

        write();
    }

    /**
     * This method loads NewAlbum.class
     */
    private void openNewAlbum(){
        Intent intent = new Intent(this, NewAlbum.class);
        startActivity(intent);
    }

    /**
     * This method loads AlbumView.class
     */
    private void openAlbum(){
        Intent intent = new Intent(this, AlbumView.class);
        startActivity(intent);
    }

    /**
     * This method loads Search.class
     */
    private void openSearch(){
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    /**
     * This method loads EditAlbum.class
     */
    private void openEditAlbum(){
        Intent intent = new Intent(this, EditAlbum.class);
        startActivity(intent);
    }

    /**
     * This method reads the list of albums and stores it into the correct object
     * @return String[] an array of the names of the albums
     */
    public String[] read() {
        String[] strings = {};

        try {
            FileInputStream fileInputStream = openFileInput("albums.albm");

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            ArrayList<String> list = new ArrayList<String>();
            String lineIn;

            while ((lineIn = bufferedReader.readLine()) != null) {
                list.add(lineIn);
            }

            strings = new String[list.size()];

            for (int i = 0; i < list.size(); i++){
                strings[i] = list.get(i);
            }

            return strings;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }

    /**
     * This method writes the list of album names back to "albums.albm"
     */
    public void write(){
// FILE PATH    /data/user/0/com.craigmsirota.photos/files/albums.albm
        try {
            String str = "";
            if (albums.size() > 0) {
                str = albums.get(0);
            }

            FileOutputStream fileOutputStream = openFileOutput("albums.albm", MODE_PRIVATE);
            for (int i = 1; i < albums.size(); i++) {
                str = str.concat("\n" + albums.get(i));
            }

            fileOutputStream.write(str.getBytes());


        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This method returns the index of the most recently selected album
     * @return int  a variable containing the index of of the most recently selected album
     */
    public static int getIndex(){
        return index;
    }

    /**
     * This method is used to add photos to the stock album
     * @param photoFilePath this string should be a string version of the photo's Uri -- Uri.toString()
     * @param fos   this FileOutputStream should contain the file you are writing to, in our case, we used this for stock.list
     */
    private void addPhoto(String photoFilePath, FileOutputStream fos){
        File file = new File(photoFilePath);
  //      Photo picture = new Photo(Uri.fromFile(file));

        /**/

        try {
            fos.write((Uri.parse(photoFilePath).toString()+'\n').getBytes());
        } catch (IOException e){
            Toast.makeText(this, "FAILED", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
