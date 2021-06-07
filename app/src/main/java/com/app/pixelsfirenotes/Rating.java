package com.app.pixelsfirenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class Rating extends AppCompatActivity {

    RatingBar ratingBar;
    Button btnSubmit;
    int myRating =0;
    private Toolbar toolbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);



        ratingBar= findViewById(R.id.ratingBar);
        btnSubmit=findViewById(R.id.buttonSub);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float a, boolean b) {

                int rating = (int) a;
                String message= null;



                switch (rating)
                {
                    case 1:
                        message = "Sorry to hear that! :(";
                        break;
                    case 2:
                        message = "We are open to Suggestions!";
                        break;
                    case 3:
                        message = "Good enough!";
                        break;
                    case 4:
                        message = "Great! Thank you!";
                        break;
                    case 5:
                        message = "Awesome! You are the best!";
                        break;

                }

                Toast.makeText(Rating.this, message, Toast.LENGTH_SHORT).show();
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s= String.valueOf(ratingBar.getRating());
                Toast.makeText(Rating.this, s+" Star", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}