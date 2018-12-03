package com.craigmsirota.photos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class NewTag extends AppCompatActivity {
    RadioButton loc, person;
    EditText tagData;
    Button send, cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tag);

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

                boolean checked = ((RadioButton) view).isChecked();

                if (!tagData.getText().toString().equals("")) {
                    switch (view.getId()) {
                        case R.id.location:
                            if (checked) {
                                AlbumView.imgAdapter.uris.get(AlbumView.index).addTag("Location");
                                SlideShowView.gridView.setAdapter(SlideShowView.tagAdapter);
                                finish();
                            }
                        case R.id.person:
                            if (checked) {
                                AlbumView.imgAdapter.uris.get(AlbumView.index).addTag("Person");
                                SlideShowView.gridView.setAdapter(SlideShowView.tagAdapter);
                                finish();
                            }
                    }
                }
            }
        });

    }
}
