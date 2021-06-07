package com.app.pixelsfirenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Calendar extends AppCompatActivity {

    EditText title;
    EditText location;
    EditText description;
    Button addEvent;
    ImageButton Back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        title=findViewById(R.id.title1);
        location=findViewById(R.id.Location);
        description=findViewById(R.id.Description);
        addEvent=findViewById(R.id.buttonaddevent);
        Back = findViewById(R.id.imageButton1);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MainActivity.class));
            }
        });

        
        
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!title.getText().toString().isEmpty() && !location.getText().toString().isEmpty() && !description
                        .getText().toString().isEmpty()) {

                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, title.getText().toString());
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString());
                    intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                    intent.putExtra(Intent.EXTRA_EMAIL, "test@yahoo.com, test2@yahoo.com, test3@yahoo.com");

                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }else{
                        Toast.makeText(Calendar.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(Calendar.this, "Please fill all the fields!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });






    }
}