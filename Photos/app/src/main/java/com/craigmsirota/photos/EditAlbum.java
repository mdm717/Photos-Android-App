package com.craigmsirota.photos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Prompts the user for a new name for an album and sets the name or cancels
 * @author Craig Sirota cms631
 * @author Matt Marrazzo mdm289
 */

public class EditAlbum extends AppCompatActivity {

    /**
     * This method sets the data and click listeners when an activity is created
     * @param savedInstanceState    Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_album);

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
                int index = HomeScreen.getIndex();
                if (!albumName.getText().toString().isEmpty() && !HomeScreen.albums.contains(albumName.getText().toString())) {

                    Toast.makeText(getApplicationContext(), HomeScreen.albums.get(index), Toast.LENGTH_SHORT).show();
                    try {

                        File old = new File((getFilesDir() + File.separator + HomeScreen.albums.get(index)+".list"));
                        FileOutputStream fileOutputStream = openFileOutput(albumName.getText().toString()+".list", MODE_PRIVATE);

                        //         File file = new File(HomeScreen.albums.get(index)+".list");
                        String path = old.toPath().toUri().toString();
                        FileReader fileInputStream = new FileReader(old);

                        String str = "";
                        int check;
                        while ((check = fileInputStream.read()) != -1) {
                            str = str + ((char) check + "");
                        }

                        fileOutputStream.write(str.getBytes());

                        getApplicationContext().deleteFile(new File(HomeScreen.albums.get(index)+".list").getName());
//                    .delete();
                        HomeScreen.albums.remove(index);
                        HomeScreen.albums.add(index,albumName.getText().toString());
                        index = -1;
                        write();

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, HomeScreen.albums);

                        HomeScreen.gridView.setAdapter(arrayAdapter);
                        finish();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
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

}
