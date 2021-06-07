package com.app.pixelsfirenotes.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.pixelsfirenotes.MainActivity;
import com.app.pixelsfirenotes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText firstName,lastName,email;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView mLoginButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.emailAddress);
        saveBtn = findViewById(R.id.saveBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstName.getText().toString().isEmpty()||lastName.getText().toString().isEmpty() || email.getText().toString().isEmpty()){
                    Toast.makeText(Details.this, "Fill the required Details", Toast.LENGTH_SHORT).show();
                    return;
                }
                fAuth.getCurrentUser().updateEmail(email.getText().toString()).addOnCompleteListener(task -> {
                    if( task.isSuccessful()) {
                        Toast.makeText(Details.this, "Notes are synced", Toast.LENGTH_SHORT).show();
                        Map<String,Object> userMap = new HashMap<>();
                        userMap.put("first",firstName.getText().toString());
                        userMap.put("last",lastName.getText().toString());
                        userMap.put("email",email.getText().toString());
                        fStore.collection("users").document(fAuth.getCurrentUser().getUid()).set(userMap)
                                .addOnCompleteListener(ds ->{
                                    if (ds.isSuccessful()){
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    } else {
                                        Toast.makeText(Details.this, "Failed linking user email", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Log.d(TAG, "onFailure: Failed to update email User " + task.getException().toString());
                        Toast.makeText(Details.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}