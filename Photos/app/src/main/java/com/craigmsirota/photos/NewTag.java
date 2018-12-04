package com.craigmsirota.photos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class NewTag extends AppCompatActivity {
    private RadioButton loc, person;
    private RadioGroup rg;
    private EditText tagData;
    private Button send, cancel;
    private int type = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tag);

        rg = (RadioGroup) findViewById(R.id.radiogroup);

        loc = (RadioButton) findViewById(R.id.location);
        person = (RadioButton) findViewById(R.id.person);

        tagData = (EditText) findViewById(R.id.data);
        send = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> tags = new ArrayList<>();

                for (Tag t: AlbumView.imgAdapter.uris.get(SlideShowView.index).tags) {
                    if (!(tags.contains(t.toString()))){
                        tags.add(t.toString());
                    }
                }

                type = rg.getCheckedRadioButtonId();
                Toast.makeText(getApplicationContext(),((int)type) + "", Toast.LENGTH_SHORT).show();
                if (!tagData.getText().toString().equals("")) {
                    switch (type) {
                        case 2131165275:
                            if (tags.contains("Location=" + tagData.getText().toString())) {
                                Toast.makeText(getApplicationContext(), "This tag already exists", Toast.LENGTH_SHORT).show();
                            } else {
                                AlbumView.imgAdapter.uris.get(SlideShowView.index).addTag("Location=" + tagData.getText().toString()); SlideShowView.gridView.setAdapter(SlideShowView.tagAdapter);
                                write();
                                finish();
                            }
                            //                   SlideShowView.tagAdapter.addAll(AlbumView.imgAdapter.uris.get(SlideShowView.index).tags);

                            break;
                        case 2131165294:
                            if (tags.contains("Person=" + tagData.getText().toString())) {
                                Toast.makeText(getApplicationContext(), "This tag already exists", Toast.LENGTH_SHORT).show();
                            } else {
                                AlbumView.imgAdapter.uris.get(SlideShowView.index).addTag("Person=" + tagData.getText().toString());
                                SlideShowView.gridView.setAdapter(SlideShowView.tagAdapter);
                                write();
                                finish();
                            }
                            break;
                        default:
                            break;

                    }
                }
            }
        });

    }


    public void write(){
// FILE PATH    /data/user/0/com.craigmsirota.photos/files/albums.albm
        try {
            ArrayList<Photo> uris = AlbumView.imgAdapter.getPhotos();

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
                for (Tag t : u.tags){
                    str = str + "\nTAG:" + t.toString();
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
}
