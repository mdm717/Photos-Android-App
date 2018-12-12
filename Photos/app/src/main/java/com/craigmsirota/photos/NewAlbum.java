package com.craigmsirota.photos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Displays a prompt for the user to create a new album
 * @author Craig Sirota cms631
 * @author Matt Marrazzo mdm289
 */

public class NewAlbum extends AppCompatActivity {

    /**
     * This method sets the data and click listeners when an activity is created
     * @param savedInstanceState    Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_album);

        final EditText albumName =  (EditText) findViewById(R.id.albumName);
        Button createButton =  (Button) findViewById(R.id.create),
                backButton = (Button) findViewById(R.id.cancel);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!albumName.getText().toString().isEmpty() && !HomeScreen.albums.contains(albumName.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    try {
                        FileOutputStream fileOutputStream = openFileOutput(albumName.getText().toString()+".list", MODE_PRIVATE);
                        HomeScreen.albums.add(albumName.getText().toString());
                        write();
                        finish();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * This method finishes the activity and returns to HomeScreen.class
     */
    private void back() {
        finish();
    }

    /**
     * This method takes the app data and saves it into the corresponding album file
     */
    public void write(){
// FILE PATH    /data/user/0/com.craigmsirota.photos/files/albums.albm
        try {
            String str = "";
            if (HomeScreen.albums.size() > 0) {
                str = HomeScreen.albums.get(0);
            }

            FileOutputStream fileOutputStream = openFileOutput("albums.albm", MODE_PRIVATE);
            for (int i = 1; i < HomeScreen.albums.size(); i++) {
                str = str.concat("\n" + HomeScreen.albums.get(i));
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
}
