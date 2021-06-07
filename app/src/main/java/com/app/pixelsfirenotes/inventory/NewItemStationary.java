package com.app.pixelsfirenotes.inventory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.pixelsfirenotes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class NewItemStationary extends AppCompatActivity {

    private EditText nameedit;
    private EditText initqtyedit;
    private Button itemimage;
    private Uri newimguri;
    private ImageView newimages;
    private FirebaseAuth fAAuth;
    private String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        setTitle("Add Item");

        newimages = findViewById(R.id.newimages);
        nameedit = findViewById(R.id.nameedit);
        initqtyedit = findViewById(R.id.initqtyedit);
        itemimage = findViewById(R.id.imgselect);
        itemimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilechooser();
            }
        });
        fAAuth = FirebaseAuth.getInstance();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                savenote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openFilechooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            newimguri = data.getData();
            newimages.setImageURI(newimguri);
        }
    }

    private void savenote(){
        String title = nameedit.getText().toString();
        String initialquantity = initqtyedit.getText().toString();

        if(title.trim().isEmpty()||initialquantity.trim().isEmpty()){
            Toast.makeText(this, "Please add the Name and Initial quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        a = fAAuth.getCurrentUser().getUid();
        if(newimguri!=null){
            Toast.makeText(getApplicationContext(),"Uploading image", Toast.LENGTH_SHORT).show();
            String imgname = System.currentTimeMillis()+".jpg";
            FirebaseStorage.getInstance().getReference().child(imgname).putFile(newimguri)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            FirebaseStorage.getInstance().getReference().child(imgname).getDownloadUrl().addOnSuccessListener(task2 -> {
                                Toast.makeText(getApplicationContext(),"fetched url", Toast.LENGTH_SHORT).show();
                                CollectionReference dbStationaryRef = FirebaseFirestore.getInstance().collection("users");
                                dbStationaryRef.document(a).collection("StationaryRef").add(new Set_item(task2.toString(),title,"Qty available (kg):",initialquantity)).addOnSuccessListener(t2->{
                                    Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
                                    finish();
                                });
                            }) ;
                        } else {
                            Toast.makeText(getApplicationContext(),"failure while uploading image", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            CollectionReference dbStationaryRef = FirebaseFirestore.getInstance().collection("users");
            dbStationaryRef.document(a).collection("StationaryRef").add(new Set_item("",title,"Qty available (kg):",initialquantity));
            Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}