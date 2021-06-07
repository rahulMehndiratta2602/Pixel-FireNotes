package com.app.pixelsfirenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.pixelsfirenotes.inventory.Education;
import com.app.pixelsfirenotes.inventory.Food;
import com.app.pixelsfirenotes.inventory.House;
import com.app.pixelsfirenotes.inventory.Others;
import com.app.pixelsfirenotes.inventory.Stationary;
import com.app.pixelsfirenotes.inventory.Work;
import com.app.pixelsfirenotes.note.AddNote;

public class Dashboard extends AppCompatActivity {
    Button house;
    Button work;
    Button edu;
    Button gro;
    Button stat;
    Button oth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        house=findViewById(R.id.buttonhouse);
        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), House.class));
            }
        });


        work=findViewById(R.id.work);
        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Work.class));
            }
        });
        edu=findViewById(R.id.education);
        edu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Education.class));
            }
        });
        gro=findViewById(R.id.grocery);
        gro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Food.class));
            }
        });
        stat=findViewById(R.id.stat);
        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Stationary.class));
            }
        });
        oth=findViewById(R.id.oth);
        oth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Others.class));
            }
        });

    }





}
